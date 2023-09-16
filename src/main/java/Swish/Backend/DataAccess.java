package Swish.Backend;

import Swish.Controllers.Start;
import javafx.scene.image.Image;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataAccess {
    //Database URL (inside project path)
    private static final String url = "jdbc:ucanaccess://SwishApp.accdb";

    //Single connection used across app
    private Connection conn;

    //Default constructor creating a connection to the DB
    public DataAccess() {
        try {
            conn = DriverManager.getConnection(url); //connection formed
        } catch (Exception ex) {
            ex.printStackTrace();
                    System.out.println("Cannot connect");
        }
    }

    //Returns a user from the database that matches the email and password parameters
    public User getUser(String email, String password) throws SQLException {
        String select = "SELECT userID, firstName, lastName, DOB, email, password, "
                + "userType FROM tblUser WHERE email = ? and password = ?";
        PreparedStatement ps = Start.DA.conn.prepareStatement(select);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet resultSet = ps.executeQuery();

        User u = null;

        //getters and setters used to form the user object that will be returned
        if (resultSet.next()) {
            int uID = resultSet.getInt("userID");
            String fn = resultSet.getString("firstName");
            String ln = resultSet.getString("lastName");
            Date dob = resultSet.getDate("DOB");
            String em = resultSet.getString("email");
            String pa = resultSet.getString("password");
            String ut = resultSet.getString("userType");

            u = new User();

            u.setUserID(uID);
            u.setFirstName(fn);
            u.setLastName(ln);
            u.setDob(dob);
            u.setEmailAddress(em);
            u.setPassword(pa);
            u.setUserType(ut);
        } else {
            return null; //returns null if user is not found
        }

        resultSet.close();
        ps.close();
        return u;
    }

    //returns an address from the address table that has the correct userID attached to it.
    public Address getAddress(int userID)throws SQLException{
        String sql = "SELECT * FROM tblAddress WHERE userID = " + userID + ";";
        PreparedStatement ps = Start.DA.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Address a = null;

        if(rs.next()){
            a = new Address();
            a.setAddressID(rs.getInt("addressID"));
            a.setUserID(rs.getInt("userID"));
            a.setHouseNumber(rs.getInt("houseNumber"));
            a.setStreetName(rs.getString("street"));
            a.setSuburb(rs.getString("suburb"));
            a.setCity(rs.getString("city"));
            a.setProvince(rs.getString("province"));
            a.setCountry(rs.getString("country"));
            a.setZipCode(rs.getString("zipCode"));
        }else{
            return null; //returns null if the there is no address attached to the given userID
        }
        rs.close();
        ps.close();
        return a;
    }

    //takes all the user fields as parameters to add a new user to the database
    public User addUser(String fn, String ln, String e,
                        String p, Date d, String ut) throws Exception {

        int r = 0;
        if (userExists(e)) {
            throw new Exception("Email already in use.");
        }

        String insert = "INSERT INTO tblUser (firstName, lastName, dob, "
                + "email, password, userType) "
                + "VALUES(?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setString(1, fn);
        ps.setString(2, ln);
        ps.setDate(3, d);
        ps.setString(4, e);
        ps.setString(5, Encryption.encrypt(p));
        ps.setString(6, ut);
        r = ps.executeUpdate(); //r will be the amount of lines updated
        if (!conn.getAutoCommit()) {
            conn.commit(); //if autocommit is off the added user will be saved
        }

        if (r > 0) { //creates a new user object if the update was successful
            User u = getUser(e, Encryption.encrypt(p));
            createAddress(u.getUserID());
            createCart(u.getUserID());
            return u;
        } else {
            throw new Exception("Insert count = " + r);
        }

    }

    //creates a new cart that will be linked with the given userID
    public void createCart(int userID) throws SQLException {
        String sql = "INSERT INTO tblCart(userID) VALUES(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.executeUpdate();
        ps.close();
    }

    //creates a new address that will be linked with the given userID
    public void createAddress(int userID) throws SQLException {
        String sql = "INSERT INTO tblAddress(userID) VALUES(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.executeUpdate();
        ps.close();
    }

    //checks if a user with the given email exists
    public boolean userExists(String e) {

        String selectEmail = "SELECT email from tblUser where email = ?";

        try {
            PreparedStatement psc = conn.prepareStatement(selectEmail);
            psc.setString(1, e);
            ResultSet rs = psc.executeQuery();
            if (rs.next()) {
                psc.close();
                return true; //returns true if the user exists
            }
        } catch (SQLException ex) {
            Alert.displayAlert(ex.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
        return false; //returns false if the user does not exist
    }

    //updates the password in the database for a given user
    public boolean updatePasswordAtStart(String password, String email) throws SQLException, Exception {
        String update = "UPDATE tblUser SET password = ? WHERE email = ?";

        PreparedStatement ps = conn.prepareStatement(update);

        int r = 0;

        if (userExists(email) == true) {
            ps.setString(1, Encryption.encrypt(password)); //encrypts the password before setting the string using the Encryption class
            ps.setString(2, email);
            r = ps.executeUpdate();
            if (!conn.getAutoCommit()) {
                conn.commit(); //if autocommit is off the password will be saved manually
            }
            } else {
                Alert.displayAlert("User does not exist", javafx.scene.control.Alert.AlertType.ERROR); //displays an alert if the user does not exist
            }

        ps.close();
        if (r > 0) {
            return true; //returns true if the password was updated
        } else {
            return false; //returns false if the password was not updated
        }
    }

    //returns an arraylist of products used to get multiple products using the category as the search criteria
    public ArrayList<Product> getProductsByCategory(String productCategory) {
        ArrayList<Product> products = new ArrayList<Product>(); //new arraylist of products

        try {
            String statement = "SELECT productID, productName,  productStock, productCostPrice, productSellPrice, productFile, productDescription FROM tblProduct WHERE productCategory = ?";
            PreparedStatement ps = Start.DA.conn.prepareStatement(statement);
            ps.setString(1, productCategory);
            ResultSet rs = ps.executeQuery();

            //resultset continues to produce results that match the given category
            while (rs.next()) {
                int pID = rs.getInt("productID");
                String pN = rs.getString("productName");
                int pS = rs.getInt("productStock");
                double pCP = rs.getDouble("productCostPrice");
                double pSP = rs.getDouble("productSellPrice");
                String pD = rs.getString("productDescription");
                File file = new File("savedImage.png"); //new file created
                FileOutputStream fos = new FileOutputStream(file); //file > fileoutputstream
                InputStream fis = rs.getBinaryStream("productFile"); //binarystream is taken from the database
                byte[] ba = fis.readAllBytes(); //binarysteam is read
                fos.write(ba); //fileOutputStream is written to
                fos.close();
                FileInputStream fis2 = new FileInputStream("savedImage.png"); //written to file is now an inputstream
                Image pI = new Image(fis2); //inputstream converted to image

                Product p = new Product();

                p.setProductID(pID);
                p.setProductName(pN);
                p.setProductStock(pS);
                p.setProductCostPrice(pCP);
                p.setProductSellPrice(pSP);
                p.setProductCategory(productCategory);
                p.setProductImage(pI);
                p.setDescription(pD);

                products.add(p); //single product is added to the arraylist after setters have been used
            }
            ps.close();
            rs.close();
        } catch (Exception ex) {
            Alert.displayAlert(ex.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
        return products; //returns the product arraylist
    }

    //new product is added to the database using product fields as parameters
    public void addProduct(String productName, int productStock, double productCostPrice, double productSellPrice,
                           String productCategory, String productDescription, File productFile) throws Exception {

        String insert = "INSERT INTO tblProduct (productName, productStock, productCostPrice, "
                + "productDescription, productCategory, productSellPrice, productFile) "
                + "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(insert);

        //statement is set up
        ps.setString(1, productName);
        ps.setInt(2, productStock);
        ps.setDouble(3, productCostPrice);
        ps.setString(4, productDescription);
        ps.setString(5, productCategory);
        ps.setDouble(6, productSellPrice);
        ps.setObject(7, productFile);
        FileInputStream fis = new FileInputStream(productFile);
        ps.setBinaryStream(7, fis, (int) productFile.length()); //length of file casted to an int and the fileinputstream is given to the database (saved as OLE object)

        ps.executeUpdate(); //statement executed

        if (!conn.getAutoCommit()) {
            conn.commit(); //manually saves the added product if autocommit is off
        }
        ps.close();
        fis.close();
        Start.displayAlert("Product added successfully", javafx.scene.control.Alert.AlertType.CONFIRMATION); //message displayed once the product has been added

    }

    //returns an arraylist of products used to get multiple products using the product's name as the search criteria
    public ArrayList<Product> searchProduct(String productName) {
        ArrayList<Product> products = new ArrayList<Product>(); //new arraylist

        try {
            String statement = "SELECT * FROM tblProduct WHERE productName LIKE '*" + productName + " *' ;"; //statement checks for products with similar names compared to given name
            PreparedStatement ps = Start.DA.conn.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();

            //resultset returns results as long as there are products with similar names
            while (rs.next()) {
                //results gotten from resultset
                int pID = rs.getInt("productID");
                String pN = rs.getString("productName");
                int pS = rs.getInt("productStock");
                double pCP = rs.getDouble("productCostPrice");
                double pSP = rs.getDouble("productSellPrice");
                File file = new File("savedImage.png");
                FileOutputStream fos = new FileOutputStream(file);
                InputStream fis = rs.getBinaryStream("productFile");
                byte[] ba = fis.readAllBytes();
                fos.write(ba);
                fos.close();
                FileInputStream fis2 = new FileInputStream("savedImage.png");
                Image pI = new Image(fis2);

                //new product object created and set
                Product p = new Product();

                p.setProductID(pID);
                p.setProductName(pN);
                p.setProductStock(pS);
                p.setProductCostPrice(pCP);
                p.setProductSellPrice(pSP);
                p.setProductImage(pI);

                products.add(p);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products; //returns the product arraylist
    }

    //returns all users in the database
    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<User>(); //new arraylist
        try {
            String statement = "SELECT * FROM tblUser;"; //selects all from the user table
            PreparedStatement ps = Start.DA.conn.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();

            //loops as long as there are users
            while(rs.next()){
                User u = new User();

                u.setUserID(rs.getInt("userID"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setDob(rs.getDate("DOB"));
                u.setEmailAddress(rs.getString("email"));
                u.setPassword(Encryption.decrypt(rs.getString("password")));
                u.setUserType(rs.getString("userType"));

                users.add(u); //the created user object is added to the arraylist
            }
            ps.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return users; //the user arraylist is returned
    }

    //promotes a given user using the userID
    public String promoteUser(int userID)throws SQLException{
        //sql to retrieve the usertype
        String sql = "SELECT userType FROM tblUser WHERE userID = " + userID + ";";
        PreparedStatement ps1 = Start.DA.conn.prepareStatement(sql);
        ResultSet rs = ps1.executeQuery();
        rs.next();
        String userType = rs.getString("userType");

        //promotion will be unsuccessful if the user is already admin
        if(userType.equals("Admin")){
            return "User promotion unsuccessful. User is already an admin.";
        }

        //sql to update the given user from customer to admin
        String statement = "UPDATE tblUser SET userType = 'Admin' WHERE userID = " + userID + ";";

        int r = 0;
        PreparedStatement ps2 = Start.DA.conn.prepareStatement(statement);
        r = ps2.executeUpdate();

        ps1.close();
        rs.close();
        ps2.close();
        if(r < 1){
            return "User promotion unsuccessful."; //returned if 0 rows are updated
        }
        else{
            return "User promotion successful"; //returned if the row containing the given user is updated
        }

    }


    //demotes a given user
    public String demoteUser(int userID)throws SQLException{
        //sql to retrieve the usertype
        String sql = "SELECT userType FROM tblUser WHERE userID = " + userID + ";";
        PreparedStatement ps1 = Start.DA.conn.prepareStatement(sql);
        ResultSet rs = ps1.executeQuery();
        rs.next();
        String userType = rs.getString("userType");

        //will fail if the user is already a customer
        if(userType.equals("Customer")){
            return "User demotion unsuccessful. User is already a customer.";
        }

        //sql to update the user's type
        String statement = "UPDATE tblUser SET userType = 'Customer' WHERE userID = " + userID + ";";

        int r = 0;
        PreparedStatement ps2 = Start.DA.conn.prepareStatement(statement);
        r = ps2.executeUpdate();

        ps1.close();
        rs.close();
        ps2.close();
        if(r < 1){
            return "User demotion unsuccessful."; //returned is no rows were updated
        }
        else{
            return "User demotion successful"; //returned if the row of the given user was updated
        }
    }

    //removes a given user using the userID as a search parameter
    public String removeUser(int userID)throws SQLException{ //https://stackoverflow.com/questions/37539325/unable-to-execute-delete-query-in-java
        int r = 0;
        String sql = "DELETE FROM tblUser WHERE userID = " + userID + ";";
        PreparedStatement ps = Start.DA.conn.prepareStatement(sql);
        r = ps.executeUpdate();
        ps.close();

        if(r < 1){
            return "Failed to remove user."; //message returned if the row of the given user is not updated
        }else{
            return "User removed successfully."; //message returned if the row of the given user is updated
        }
    }

    //searches for a user using their last name
    public ArrayList<User> searchUser(String lastName){
        ArrayList<User> users = new ArrayList<User>(); //new arraylist

        try {
            String statement = "SELECT * FROM tblUser WHERE lastName LIKE '" + lastName + "';";
            PreparedStatement ps = Start.DA.conn.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();

            //loops as long as their are similar users
            while(rs.next()){
                User u = new User(); //new user object created

                u.setUserID(rs.getInt("userID"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setDob(rs.getDate("DOB"));
                u.setEmailAddress(rs.getString("email"));
                u.setPassword(Encryption.decrypt(rs.getString("password")));
                u.setUserType(rs.getString("userType"));

                users.add(u); //user object added to the arraylist
            }
            ps.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return users; //the user arraylist is returned

    }

    //edits certain fields of the given user
    public boolean editUser(User user)throws SQLException{
        int r = 0;
        //sql to update names and email (other fields can not be updated by the user)
        String sqlUser = "UPDATE tblUser SET firstName = '" + user.getFirstName() + "', lastName = '" + user.getLastName() + "', email = '" + user.getEmailAddress() + "' WHERE userID = " + user.getUserID() + ";";
        PreparedStatement ps = Start.DA.conn.prepareStatement(sqlUser);
        r = ps.executeUpdate();
        ps.close();

        if(r < 1)
            return false; //returns false if the update failed
        else
            return true; //returns true if the update succeeded

    }

    //edits all the fields of a given address matching a user
    public boolean editAddress(Address address, User user)throws SQLException{
        int r = 0;
        //sql to update all address fields that are linked to a userID
        String sql = "UPDATE tblAddress SET houseNumber = "
                + address.getAddressID() + ", street = '" + address.getStreetName() +
                "', suburb = '" + address.getSuburb() + "', city = '" + address.getCity() +
                "', province = '" + address.getProvince() + "', country = '" + address.getCountry() + "', zipCode = '" + address.getZipCode() + "' WHERE userID = " + user.getUserID() + ";";

        PreparedStatement ps = Start.DA.conn.prepareStatement(sql);
        r = ps.executeUpdate(); //update executed
        ps.close();

        if(r < 1)
            return false; //returns false if the update failed
        else
            return true; //returns true if the update succeeded

    }

    //retrieves a single product using the productID as search parameters
    public Product getProductByID(int productID) throws SQLException, IOException {
        String sql = "SELECT * FROM tblProduct WHERE productID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, productID); //sets the productID to be searched for
        ResultSet rs = ps.executeQuery();

        //new product object is created
        Product product = new Product();

        while(rs.next()){
            product.setProductID(productID);
            product.setProductName(rs.getString("productName"));
            product.setProductCostPrice(rs.getDouble("productCostPrice"));
            product.setProductSellPrice(rs.getDouble("productSellPrice"));
            product.setProductStock(rs.getInt("productStock"));
            product.setProductCategory(rs.getString("productCategory"));
            File file = new File("savedImage.png"); //file object created
            FileOutputStream fos = new FileOutputStream(file); //file > fileOutputStream
            InputStream fis = rs.getBinaryStream("productFile"); //binary stream from database > inputstream
            byte[] ba = fis.readAllBytes(); //inputstream's bytes are read into a byte array
            fos.write(ba); //fileOutputStream is written to
            fos.close();
            FileInputStream fis2 = new FileInputStream("savedImage.png");
            Image pI = new Image(fis2);
            product.setProductImage(pI);
            product.setDescription(rs.getString("productDescription"));
        }
        ps.close();
        rs.close();
        return product; //returns the product object after all fields have been set
    }

    //retrieves the company balance
    public double getCompanyBalance() throws SQLException {
        String sql = "SELECT companyBalance FROM tblCompanyInfo WHERE ID = 1"; //only one row so ID will always equal 1
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(); //query executed
        rs.next();
        double balance = rs.getDouble("companyBalance"); //balance set
        ps.close();
        rs.close();
        return balance; //balance returned
    }

    //increases the company balance by the given amount
    public void increaseCompanyBalance(double plus) throws SQLException {
        String sql = "UPDATE tblCompanyInfo SET companyBalance = ? WHERE ID = 1";
        double sum = getCompanyBalance() + plus; //current balance + given amount to increase by
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, sum);
        ps.executeUpdate(); //update is executed
        ps.close();
    }

    //decreases the company balance by the given amount
    public void decreaseCompanyBalance(double minus) throws SQLException {
        double sum = getCompanyBalance() - minus; //current balance - given amount to decrease by
        String sql = "UPDATE tblCompanyInfo SET companyBalance = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, sum);
        ps.executeUpdate(); //update is executed
        ps.close();
    }

    //refunds all the stock of a given product
    public void refundStock(int productID) throws SQLException, IOException {
        Product product = getProductByID(productID); //product is retrieved using productID
        double sum = product.getProductStock() * product.getProductCostPrice(); //equation to calculate how much must be refunded
        sum += getCompanyBalance(); //company balance added to the sum
        String sql = "UPDATE tblCompanyInfo SET companyBalance = ? WHERE ID = 1"; //company balance is set
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, sum);
        ps.executeUpdate(); //update executed
        ps.close();
    }

    //removes a given product
    public boolean removeProduct(int productID) throws SQLException, IOException {
        int r = 0;
        refundStock(productID); //refunds the stock of the product
        String sql = "DELETE FROM tblProduct WHERE productID = " + productID + ";"; //sql to delete product from product table
        PreparedStatement ps = Start.DA.conn.prepareStatement(sql);
        r = ps.executeUpdate(); //update executed
        ps.close();
        if(r < 1){
            return false; //returns false if the update was unsuccessful
        }else{
            return true; //returns true if the update was successful
        }

    }

    //orders a specific amount of stock of a given product
    public boolean orderStock(int stockToOrder, Product product) throws SQLException {
        int r = 0;
        String sql = "UPDATE tblProduct SET productStock = ? WHERE productID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, product.getProductStock() + stockToOrder); //current stock and stock being ordered are added in the statement
        ps.setInt(2, product.getProductID());
        r = ps.executeUpdate();
        if(r < 1){
            ps.close();
            return false; //update unsuccessful and company balance remains as it was
        }else{
            decreaseCompanyBalance(stockToOrder * product.getProductCostPrice()); //company balance is decreased
            ps.close();
            return true; //update successful and company balance is charged
        }
    }

    //returns a string of productIDs in a user's cart
    public String getCart(int userID) throws SQLException {
        String sql = "SELECT products FROM tblCart WHERE userID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID); //userID set equal to the userID parameter
        ResultSet rs = ps.executeQuery();
        rs.next();
        String list = rs.getString("products"); //list is set as the string of productIDs
        ps.close();
        rs.close();
        return list; //list is returned
    }

    //converts the string of productIDs in a cart to an arraylist of products
    public ArrayList<Product> getCartItems(int userID) throws SQLException, IOException {
        ArrayList<Product> items = new ArrayList<Product>(); //arraylist for the products is created
        Scanner sc = new Scanner(getCart(userID)).useDelimiter(","); //scanner to scan the string using the comma delimiter
        //loops as long as there are IDs in the string
        try {
            while (sc.hasNext()) {
                int productID = sc.nextInt();
                Product p = getProductByID(productID);//product object created after getting the product using productID
                items.add(p);//product object added to items arraylist
            }
        }catch(InputMismatchException e){
        }
        sc.close(); //scanner closed
        System.out.println(items);
        return items;//items arraylist is returned
    }

    //removes the a product from a user's cart using the productID
    public void removeItemFromCart(int productID, int userID) throws SQLException {
        String list = getCart(userID); //gets the string of productIDs in the cart
        String[] listArr = list.split(","); //productIDs are separated and stored in an array
        int i = 0;
        String newCart = "";
        for(i = 0; i < listArr.length; i++){ //loop to add each productID to the new cart string
            if(listArr[i].equals(productID + "")){
                break; //loop ends if the product to be removed is found
            }
            newCart += listArr[i] + ",";
        }
        for(i++; i < listArr.length; i++){ //new loop created starting after the position of the productID to be removed
            newCart += listArr[i] + ",";
        }
        String sql = "UPDATE tblCart SET products = ? WHERE userID = ?"; //statement to update the cart with the new string
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newCart);
        ps.setInt(2, userID);
        ps.executeUpdate(); //update executed
        ps.close();
    }

    //clears the products in a user's cart
    public void clearCart(int userID) throws SQLException {
        String sql = "UPDATE tblCart SET products = '' WHERE userID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID); //userID set in statement
        ps.executeUpdate(); //update executed
        ps.close();
    }

    //adds a new order to the order table
    public boolean newOrder(int userID, int addressID, double cost, int numItems) throws SQLException {
        int r = 0;
        LocalDate d = LocalDate.now(); //today's date
        Date today = Date.valueOf(d); // converts localdate to sqldate
        String sql = "INSERT INTO tblOrders (userID, addressID, orderDate, orderCost, itemsOrdered) VALUES(?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, addressID);
        ps.setDate(3, today);
        ps.setDouble(4, cost);
        ps.setInt(5, numItems);
        r = ps.executeUpdate(); //update executed

        ps.close();
        if(r > 0)
            return true; //returns true if the order was inserted successfully
        else
            return false; //returns false if the order was not inserted
    }

    //adds a product to the user's cart using the productID
    public String addToCart(int productID, int userID) throws SQLException {
        int r = 0;
        String initial;
        String sql = "UPDATE tblCart SET products = ? WHERE userID = ?";
        if(getCart(userID) == null){ //checks if the user's cart is empty or not
            initial = ""; //if empty the cart string is blank
        }else{
            initial = getCart(userID);
        }
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, initial + productID + ","); //current cart string + productID of the product to be added and the delimiter
        ps.setInt(2, userID); //sets the userID in the statement
        r = ps.executeUpdate(); //update executed
        if(r > 0){
            return "Product added to cart."; //returned if the update was successful
        }else{
            return "Product could not be added to cart."; //returned if the update was unsuccessful
        }

    }

    //gets all the orders a user has made
    public ArrayList<Order> getOrdersByID(int userID) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>(); //new arraylist created
        String sql = "SELECT * FROM tblOrders WHERE userID = ?"; //selects all
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();

        //loops while there are more orders made by the user
        while(rs.next()){
            Order o = new Order(rs.getInt("ID"), rs.getInt("addressID"), rs.getInt("userID"), rs.getDate("orderDate"), rs.getDouble("orderCost"), rs.getInt("itemsOrdered"));
            orders.add(o); //order object added to arraylist
        }
        ps.close();
        return orders; //arraylist returned
    }

    //takes away 1 item from the stock of a product (used when an item is purchased)
    public void minusStock(String cart) throws SQLException {
        Scanner sc = new Scanner(cart).useDelimiter(","); //scans the user's cart
        String sql = "UPDATE tblProduct SET productStock = productStock - 1 WHERE productID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        //loops while there are more products in the cart
        while(sc.hasNext()){
            int id = sc.nextInt(); //checks for the next integer in the cart string
            ps.setInt(1, id);
            ps.executeUpdate(); //executes the update each time the loop is run
        }
        ps.close();
    }



}
