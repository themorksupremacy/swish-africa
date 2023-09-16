package Swish.Controllers;

import Swish.Backend.Encryption;
import Swish.Backend.Product;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static Swish.Controllers.Start.*;

public abstract class BaseUser extends Application {

    ArrayList<Product> products = new ArrayList<Product>();
    protected Product product = new Product();
    private int firstObj = 0;
    private int lastObj;
    private int currentPos;

    //Labels
    @FXML
    Label lblName = new Label();
    @FXML
    Label lblName1 = new Label();
    @FXML
    Label lblPrice1 = new Label();
    @FXML
    Label lblName2 = new Label();
    @FXML
    Label lblPrice2 = new Label();
    @FXML
    Label lblName3 = new Label();
    @FXML
    Label lblPrice3 = new Label();
    @FXML
    Label lblName4 = new Label();
    @FXML
    Label lblPrice4 = new Label();
    @FXML
    Label lblName5 = new Label();
    @FXML
    Label lblPrice5 = new Label();
    @FXML
    Label lblName6 = new Label();
    @FXML
    Label lblPrice6 = new Label();
    @FXML
    Label lblImagePath = new Label();
    @FXML
    Label lblDisplayProductName = new Label();
    @FXML
    Label lblProductID1 = new Label();
    @FXML
    Label lblProductID2 = new Label();
    @FXML
    Label lblProductID3 = new Label();
    @FXML
    Label lblProductID4 = new Label();
    @FXML
    Label lblProductID5 = new Label();
    @FXML
    Label lblProductID6 = new Label();
    @FXML
    Label lblWarningName = new Label();
    @FXML
    Label lblWarningCostPrice = new Label();
    @FXML
    Label lblWarningSellPrice = new Label();
    @FXML
    Label lblWarningProductDescription = new Label();
    @FXML
    Label lblCompanyBalance = new Label();

    //Buttons
    @FXML
    Button btnCategoryTextbooks = new Button();
    @FXML
    Button btnCategoryStationery = new Button();
    @FXML
    Button btnCategoryClothing = new Button();
    @FXML
    Button btnCategoryBags = new Button();
    @FXML
    Button btnCategoryTechnology = new Button();
    @FXML
    Button btnPreviousPage = new Button();
    @FXML
    Button btnNextPage = new Button();
    @FXML
    Button btnViewProduct1 = new Button();
    @FXML
    Button btnViewProduct2 = new Button();
    @FXML
    Button btnViewProduct3 = new Button();
    @FXML
    Button btnViewProduct4 = new Button();
    @FXML
    Button btnViewProduct5 = new Button();
    @FXML
    Button btnViewProduct6 = new Button();
    @FXML
    Button btnAddImage = new Button();
    @FXML
    Button btnSignOut = new Button();

    //Panes
    @FXML
    StackPane stackPane = new StackPane();//
    @FXML
    Pane paneProducts = new Pane();
    @FXML
    Pane paneUsers = new Pane();
    @FXML
    Pane paneAddProduct = new Pane();
    @FXML
    Pane productPane1 = new Pane();
    @FXML
    Pane productPane2 = new Pane();
    @FXML
    Pane productPane3 = new Pane();
    @FXML
    Pane productPane4 = new Pane();
    @FXML
    Pane productPane5 = new Pane();
    @FXML
    Pane productPane6 = new Pane();
    @FXML
    Pane paneEditAccount = new Pane();
    @FXML
    Pane paneViewProduct = new Pane();
    @FXML
    Pane paneInitialPane = new Pane();

    @FXML
    ImageView imgProduct1 = new ImageView();
    @FXML
    ImageView imgProduct2 = new ImageView();
    @FXML
    ImageView imgProduct3 = new ImageView();
    @FXML
    ImageView imgProduct4 = new ImageView();
    @FXML
    ImageView imgProduct5 = new ImageView();
    @FXML
    ImageView imgProduct6 = new ImageView();
    @FXML
    ImageView imgViewProductImage = new ImageView();

    //Menus
    @FXML
    MenuItem btnInbox = new MenuItem();
    @FXML
    MenuItem btnEditAccount = new MenuItem();
    @FXML
    RadioMenuItem menuItemTextbook = new RadioMenuItem();
    @FXML
    RadioMenuItem menuItemStationery = new RadioMenuItem();
    @FXML
    RadioMenuItem menuItemClothing = new RadioMenuItem();
    @FXML
    RadioMenuItem menuItemBag = new RadioMenuItem();
    @FXML
    RadioMenuItem menuItemTechnology = new RadioMenuItem();
    @FXML
    ChoiceBox choiceBoxCountry = new ChoiceBox();


    //Text fields
    @FXML
    TextArea txtAreaProductDescription = new TextArea();
    @FXML
    TextField txtSearch = new TextField();
    @FXML
    TextField txtProductName = new TextField();
    @FXML
    TextField txtProductCostPrice = new TextField();
    @FXML
    TextField txtProductSellPrice = new TextField();
    @FXML
    TextField txtUserLastName = new TextField();
    @FXML
    TextField txtHouseNumber = new TextField();
    @FXML
    TextField txtStreetName = new TextField();
    @FXML
    TextField txtSuburb = new TextField();
    @FXML
    TextField txtCity = new TextField();
    @FXML
    TextField txtProvince = new TextField();
    @FXML
    TextField txtZipCode = new TextField();
    @FXML
    TextField txtEditFirstName = new TextField();
    @FXML
    TextField txtEditLastName = new TextField();
    @FXML
    TextField txtEditEmail = new TextField();
    @FXML
    TextField txtStockToOrder = new TextField();
    @FXML
    TextArea txtDisplayProductDescription = new TextArea();

    //Other
    @FXML
    MenuButton menuProductCategory = new MenuButton();
    @FXML
    Slider sliderProductStock = new Slider();
    @FXML
    ListView listAllUsers = new ListView();

    //start method for all fxml files
    @Override
    public void start(Stage stage) throws Exception {

    }

    //sets all the panes where products are diplayed to be invisible (used to display only necessary panes for products)
    void clearPanes(){
        //blank out label text
        lblName1.setText("");
        lblPrice1.setText("");
        lblName2.setText("");
        lblPrice2.setText("");
        lblName2.setText("");
        lblPrice2.setText("");
        lblName3.setText("");
        lblPrice3.setText("");
        lblName4.setText("");
        lblPrice4.setText("");
        lblName5.setText("");
        lblPrice5.setText("");
        lblName6.setText("");
        lblPrice6.setText("");

        //blank out button text
        btnViewProduct1.setText("");
        btnViewProduct2.setText("");
        btnViewProduct3.setText("");
        btnViewProduct4.setText("");
        btnViewProduct5.setText("");
        btnViewProduct6.setText("");

        //disable buttons
        btnViewProduct1.setDisable(true);
        btnViewProduct2.setDisable(true);
        btnViewProduct3.setDisable(true);
        btnViewProduct4.setDisable(true);
        btnViewProduct5.setDisable(true);
        btnViewProduct6.setDisable(true);

        //set pane colour to white
        productPane1.setStyle("-fx-background-color: #ffffff");
        productPane2.setStyle("-fx-background-color: #ffffff");
        productPane3.setStyle("-fx-background-color: #ffffff");
        productPane4.setStyle("-fx-background-color: #ffffff");
        productPane5.setStyle("-fx-background-color: #ffffff");
        productPane6.setStyle("-fx-background-color: #ffffff");

        //clear imageviews
        imgProduct1.setImage(null);
        imgProduct2.setImage(null);
        imgProduct3.setImage(null);
        imgProduct4.setImage(null);
        imgProduct5.setImage(null);
        imgProduct6.setImage(null);
    }

    //sets up the view product pane
    void setViewProductPane(Product product){
        lblDisplayProductName.setText(product.getProductName()); //product name set
        imgViewProductImage.setImage(product.getProductImage()); //product image set
        txtDisplayProductDescription.setText(product.getDescription()); //product description set
        txtDisplayProductDescription.setWrapText(true); //text area set up
        txtDisplayProductDescription.setEditable(false);
    }

    //checks that all fields of edit account pane have been filled in correctly
    boolean validateEditAccount()throws NumberFormatException{
        boolean check = true;

        if(txtEditFirstName.getText().length() < 1){  //name is not filled in
            txtEditFirstName.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtEditFirstName.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(txtEditLastName.getText().length() < 1){ //name is not filled in
            txtEditLastName.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtEditLastName.setStyle("-fx-background-color: black; -fx-text-fill: white");;
        }
        if(txtEditEmail.getText().length() < 1){ //email is not filled in
            txtEditEmail.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtEditEmail.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(txtHouseNumber.getText().length() < 1) { //house number is not filled in
            txtHouseNumber.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else if(checkInt(txtHouseNumber.getText()) == false){ //house number is not a digit
            txtHouseNumber.setStyle("-fx-background-color: yellow; -fx-text-fill: white");
            check = false;
        }else{
            txtHouseNumber.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(txtStreetName.getText().length() < 1){ //street name not filled in
            txtStreetName.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtStreetName.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(txtSuburb.getText().length() < 1){ //suburb not filled in
            txtSuburb.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtSuburb.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(txtCity.getText().length() < 1){ //city not filled in
            txtCity.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtCity.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(txtProvince.getText().length() < 1){ //province not filled in
            txtProvince.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtProvince.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(choiceBoxCountry.getValue() == null){ //country not selected
            choiceBoxCountry.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            choiceBoxCountry.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(txtZipCode.getText().length() < 1){ //zip code not filled in
            txtZipCode.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            check = false;
        }else{
            txtZipCode.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }

        if(check == false){
            return false; //returns false if at least one field is not filled in correctly
        }else{
            return true; //returns true if all fields are correctly filled in
        }
    }

    //sets the options for the countries on the edit account screen
    void setChoiceBoxCountry(){ //http://tutorials.jenkov.com/javafx/choicebox.html
        choiceBoxCountry.getItems().add("Algeria"); //adds a new country choice
        choiceBoxCountry.getItems().add("Angola");
        choiceBoxCountry.getItems().add("DR Congo");
        choiceBoxCountry.getItems().add("Egypt");
        choiceBoxCountry.getItems().add("Ethiopia");
        choiceBoxCountry.getItems().add("Ghana");
        choiceBoxCountry.getItems().add("Kenya");
        choiceBoxCountry.getItems().add("Madagascar");
        choiceBoxCountry.getItems().add("Morocco");
        choiceBoxCountry.getItems().add("Mozambique");
        choiceBoxCountry.getItems().add("Nigeria");
        choiceBoxCountry.getItems().add("South Africa");
        choiceBoxCountry.getItems().add("Sudan");
        choiceBoxCountry.getItems().add("Tanzania");
        choiceBoxCountry.getItems().add("Uganda");
    }

    //sets up each of the edit account screen
    void editAccount(){
        setChoiceBoxCountry();
        stackPane.getChildren().clear(); //clears current screen
        stackPane.getChildren().add(paneEditAccount); //sets new screen
        txtEditFirstName.setText(u.getFirstName()); //set first name text field
        txtEditLastName.setText(u.getLastName());//set last name text field
        txtEditEmail.setText(u.getEmailAddress()); //set email text field

        //sets address fields using getters (will display the current address of the user if they have one)
        txtHouseNumber.setText("" + a.getHouseNumber());
        txtStreetName.setText(a.getStreetName());
        txtSuburb.setText(a.getSuburb());
        txtCity.setText(a.getCity());
        txtProvince.setText(a.getProvince());
        choiceBoxCountry.setValue(a.getCountry());
        txtZipCode.setText(a.getZipCode());
    }

    //checks to see if a value is an int
    private boolean checkInt(String str){ //https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
        try{
            Integer.parseInt(str);
            return true;
        }catch(NumberFormatException e){ //if exception is caught the value is not an int
            return false;
        }
    }

    //checks to see if a value is an double
    boolean checkDouble(String str){ //https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
        try{
            Double.parseDouble(str);
            return true;
        }catch(NumberFormatException e){ //if exception is caught the value is not an double
            return false;
        }
    }

    //sets up window for when the textbook category is selected
    void setTextBooks(){
        enableCategories(); //all categories enabled
        btnCategoryTextbooks.setDisable(true); //textbooks disabled
        products = DA.getProductsByCategory("Textbook");
        populatePanes(0); //panes are populated
        btnPreviousPage.setDisable(true);
        if(products.size() > 6) //if there are more than 6 textbooks the next button will be available
            btnNextPage.setDisable(false);
        else
            btnNextPage.setDisable(true);
    }

    //sets up window for when the stationery category is selected
    void setStationery(){
        enableCategories(); //all categories enabled
        btnCategoryStationery.setDisable(true);
        products = DA.getProductsByCategory("Stationery");
        populatePanes(0);
        btnPreviousPage.setDisable(true);
        if(products.size() > 6) //if there are more than 6 stationery items the next button will be available
            btnNextPage.setDisable(false);
        else
            btnNextPage.setDisable(true);
    }

    //sets up window for when the clothing category is selected
    void setClothing(){
        enableCategories(); //all categories enabled
        btnCategoryClothing.setDisable(true);
        products = DA.getProductsByCategory("Clothing");
        populatePanes(0);
        btnPreviousPage.setDisable(true);
        if(products.size() > 6) //if there are more than 6 clothing items the next button will be available
            btnNextPage.setDisable(false);
        else
            btnNextPage.setDisable(true);
    }

    //sets up window for when the bags category is selected
    void setBag(){
        enableCategories(); //all categories enabled
        btnCategoryBags.setDisable(true);
        products = DA.getProductsByCategory("Bag");
        populatePanes(0);
        btnPreviousPage.setDisable(true);
        if(products.size() > 6) //if there are more than 6 bags the next button will be available
            btnNextPage.setDisable(false);
        else
            btnNextPage.setDisable(true);
    }

    //sets up window for when the technology category is selected
    void setTechnology(){
        enableCategories(); //all categories enabled
        btnCategoryTechnology.setDisable(true);
        products = DA.getProductsByCategory("Technology");
        populatePanes(0);
        btnPreviousPage.setDisable(true);
        if(products.size() > 6) //if there are more than 6 technology items the next button will be available
            btnNextPage.setDisable(false);
        else
            btnNextPage.setDisable(true);
    }

    //makes sure that every category button is enabled
    void enableCategories(){
        btnCategoryTextbooks.setDisable(false);
        btnCategoryStationery.setDisable((false));
        btnCategoryClothing.setDisable(false);
        btnCategoryBags.setDisable(false);
        btnCategoryTechnology.setDisable(false);
    }

    //method to setup the window when the next page button is pressed
    void nextPage(){
        btnPreviousPage.setDisable(false);
        populatePanes(currentPos);
    }

    //method to setup the window when the previous page button is pressed
    void prevPage(){
        currentPos -= 6;
        populatePanes(firstObj); //panes populated
        btnNextPage.setDisable(false);
        if(currentPos <= 6){ //means that there's no previous items to view
            btnPreviousPage.setDisable(true);
        }
    }

    //sets up the window for when the search button is pressed
    void search(){
        enableCategories(); //categories enabled
        products = DA.searchProduct(txtSearch.getText()); //arraylist populated using search method
        populatePanes(0);
        btnPreviousPage.setDisable(true); //previous page button disabled at start
    }

    //used to perform a signing out of the logged in user
    void signOut(){
        Start a = new Start(); // new start object
        Stage b = new Stage(); //new stage
        Stage currentWindow = (Stage) btnSignOut.getScene().getWindow();
        currentWindow.close(); //current window closed
        a.start(b); //new start screen shown
    }

    //saves all fields on the edit account screen
    void saveChangesToAccount() throws SQLException {
        if(validateEditAccount() == false){ //checks that all fields are correctly filled in
            displayAlert("Missing information please fill in the highlighted fields.\nYellow: Incorrect data type provided.\nRed: Field not filled in.", Alert.AlertType.ERROR);
            return;
        }

        //user class setters used passing text fields
        u.setFirstName(txtEditFirstName.getText());
        u.setLastName(txtEditLastName.getText());
        u.setEmailAddress(txtEditEmail.getText());

        //address class setters used passing text fields
        a.setHouseNumber(Integer.parseInt(txtHouseNumber.getText()));
        a.setStreetName(txtStreetName.getText());
        a.setSuburb(txtSuburb.getText());
        a.setCity(txtCity.getText());
        a.setProvince(txtProvince.getText());
        a.setCountry((String) choiceBoxCountry.getValue());
        a.setZipCode(txtZipCode.getText());

        //editUser and editAddress methods called
        if(DA.editUser(u) && DA.editAddress(a, u) == true){
            displayAlert("User details and address updated successfully.", Alert.AlertType.INFORMATION);
        }else{
            displayAlert("Update unsuccessful.", Alert.AlertType.ERROR);
        }
    }

    //executed when the user resets their password while logged in
    void resetPassword() throws Exception {
        String currentPass = displayTextInputDialog("Enter your current password"); //current password must be entered
        String encrypted = Encryption.encrypt(currentPass);
        displayAlert(currentPass, Alert.AlertType.ERROR);
        if(encrypted.equals(u.getPass())){ //compares entered password
            String newPass = displayTextInputDialog("Enter your new password."); //new password entered
            if(DA.updatePasswordAtStart(newPass, u.getEmailAddress()) == true); //calls update password method
            displayAlert("Password updated successfully.", Alert.AlertType.CONFIRMATION);
            u.setPassword(Encryption.encrypt(newPass));//new password encrypted
        }else{
            displayAlert("Incorrect password." + u.getPass() + " " + encrypted, Alert.AlertType.ERROR);
        }
    }

    //checks if a value is empty or filled in
    protected boolean contentCheck(String content){
        if(content.length() < 1) {
            return false;
        }else{
            return true;
        }
    }

    //used to set up the screen when the first product's view button is pressed
    void viewProduct1() throws SQLException, IOException {
        enableCategories();
        product = DA.getProductByID(Integer.parseInt(lblProductID1.getText()));
        setViewProductPane(product);
    }

    //used to set up the screen when the second product's view button is pressed
    void viewProduct2() throws SQLException, IOException {
        enableCategories();
        product = DA.getProductByID(Integer.parseInt(lblProductID2.getText()));
        setViewProductPane(product);
    }

    //used to set up the screen when the third product's view button is pressed
    void viewProduct3() throws SQLException, IOException {
        enableCategories();
        product = DA.getProductByID(Integer.parseInt(lblProductID3.getText()));
        setViewProductPane(product);
    }

    //used to set up the screen when the fourth product's view button is pressed
    void viewProduct4() throws SQLException, IOException {
        enableCategories();
        product = DA.getProductByID(Integer.parseInt(lblProductID4.getText()));
        setViewProductPane(product);
    }

    //used to set up the screen when the fifth product's view button is pressed
    void viewProduct5() throws SQLException, IOException {
        enableCategories();
        product = DA.getProductByID(Integer.parseInt(lblProductID5.getText()));
        setViewProductPane(product);
    }

    //used to set up the screen when the sixth product's view button is pressed
    void viewProduct6() throws SQLException, IOException {
        enableCategories();
        product = DA.getProductByID(Integer.parseInt(lblProductID6.getText()));
        setViewProductPane(product);
    }

    //fills in all the values for the panes displaying product previews on screen
    void populatePanes(int startPos){
        int stop = startPos + 6; //stops filling in panes at this value

        if(stop > products.size()){
            stop = products.size();
        }

        clearPanes(); //panes are all cleared before being set up

        try {
            for (int i = 1; i < 7; i++) {
                Product pr = products.get(startPos); //gets the first product of the arraylist using starting position

                //switch case to set up each pane
                switch (i) {
                    //first pane
                    case 1 -> {
                        lblName1.setText(pr.getProductName());
                        lblPrice1.setText("R" + pr.getProductSellPrice());
                        btnViewProduct1.setDisable(false);
                        btnViewProduct1.setText("View Product");
                        productPane1.setStyle("-fx-background-radius:  20; -fx-background-color:  #484b4d");
                        imgProduct1.setImage(pr.getProductImage());
                        Integer id = pr.getProductID();
                        lblProductID1.setText(id.toString());
                        startPos++;
                    }
                    //second pane
                    case 2 -> {
                        lblName2.setText(pr.getProductName());
                        lblPrice2.setText("R" + pr.getProductSellPrice());
                        btnViewProduct2.setDisable(false);
                        btnViewProduct2.setText("View Product");
                        productPane2.setStyle("-fx-background-radius:  20; -fx-background-color:  #484b4d");
                        imgProduct2.setImage(pr.getProductImage());
                        Integer id = pr.getProductID();
                        lblProductID2.setText(id.toString());
                        startPos++;
                    }
                    //third pane
                    case 3 -> {
                        lblName3.setText(pr.getProductName());
                        lblPrice3.setText("R" + pr.getProductSellPrice());
                        btnViewProduct3.setDisable(false);
                        btnViewProduct3.setText("View Product");
                        productPane3.setStyle("-fx-background-radius:  20; -fx-background-color:  #484b4d");
                        imgProduct3.setImage(pr.getProductImage());
                        Integer id = pr.getProductID();
                        lblProductID3.setText(id.toString());
                        startPos++;
                    }
                    //fourth pane
                    case 4 -> {
                        lblName4.setText(pr.getProductName());
                        lblPrice4.setText("R" + pr.getProductSellPrice());
                        btnViewProduct4.setDisable(false);
                        btnViewProduct4.setText("View Product");
                        productPane4.setStyle("-fx-background-radius:  20; -fx-background-color:  #484b4d");
                        imgProduct4.setImage(pr.getProductImage());
                        Integer id = pr.getProductID();
                        lblProductID4.setText(id.toString());
                        startPos++;
                    }
                    //fifth pane
                    case 5 -> {
                        lblName5.setText(pr.getProductName());
                        lblPrice5.setText("R" + pr.getProductSellPrice());
                        btnViewProduct5.setDisable(false);
                        btnViewProduct5.setText("View Product");
                        productPane5.setStyle("-fx-background-radius:  20; -fx-background-color:  #484b4d");
                        imgProduct5.setImage(pr.getProductImage());
                        Integer id = pr.getProductID();
                        lblProductID5.setText(id.toString());
                        startPos++;
                    }
                    //sixth pane
                    case 6 -> {
                        lblName6.setText(pr.getProductName());
                        lblPrice6.setText("R" + pr.getProductSellPrice());
                        btnViewProduct6.setDisable(false);
                        btnViewProduct6.setText("View Product");
                        productPane6.setStyle("-fx-background-radius:  20; -fx-background-color:  #484b4d");
                        imgProduct6.setImage(pr.getProductImage());
                        Integer id = pr.getProductID();
                        lblProductID6.setText(id.toString());
                        startPos++;
                    }
                }

            }
        }catch(Exception ex){ //next page is disabled if no products are left in the arraylist
            btnNextPage.setDisable(true);
        }
        currentPos = startPos; //current position is the starting position in the array for the current page
    }



}
