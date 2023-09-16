package Swish.Controllers;

import Swish.Backend.Product;
import Swish.Backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static Swish.Controllers.Start.*;

//inherits from the BaseUser class (everything the customer and admin share)
public class AdminDashboard extends BaseUser implements Initializable {

    @FXML
    public static Label lblName = new Label(); //label for the user's name onscreen


    //Global Vars
    private int firstObj = 0;
    private int lastObj;
    private int currentPos;

    //arraylists
    ArrayList <Product> products = new ArrayList<Product>();
    ArrayList <User> users = new ArrayList<>();

    public static void main(String[] args) {launch(args);}

    //start method used for fxml files
    @Override
    public void start(Stage primaryStage) throws IOException {
    }

    //method for when editaccount button is pressed
    @FXML //specifies that it is a method relating to the fxml file used
    private void btnEditAccountActionPerformed(ActionEvent event){
        event.consume(); //default code from when a button is pressed
        editAccount(); //method from BaseUser class
    }

    //method for when textbooks category is selected
    @FXML
    private void btnCategoryTextbooksActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear(); //clears current pane
        stackPane.getChildren().add(paneProducts); //sets new pane
        setTextBooks(); //method from BaseUser class
    }

    //method for when stationery category is selected
    @FXML
    private void btnCategoryStationeryActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setStationery(); //method from BaseUser class
    }

    //method for when clothing category is selected
    @FXML
    private void btnCategoryClothingActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setClothing(); //method from BaseUser class
    }

    //method for when bags category is selected
    @FXML
    private void btnCategoryBagsActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setBag(); //method from BaseUser class
    }

    //method for when technology category is selected
    @FXML
    private void btnCategoryTechnologyActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setTechnology(); //method from BaseUser class
    }

    //method for when the next page button is pressed
    @FXML
    private void btnPreviousPageActionPerformed(ActionEvent event){
        event.consume();
        nextPage(); //method from BaseUser class
    }

    //method for when the previous page button is pressed
    @FXML
    private void btnNextPageActionPerformed(ActionEvent event) {
        event.consume();
        prevPage(); //method from BaseUser class
    }

    //method for when the add product option button is pressed
    @FXML
    private void btnAddProductOptionActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        stackPane.getChildren().clear(); //current pane cleared
        stackPane.getChildren().add(paneAddProduct); //new pane set
        resetFields(); //all fields of the current screen are cleared of any data
        txtAreaProductDescription.setStyle("text-area-background: #000000; -fx-text-fill: white"); //correct style set for the text area
        txtAreaProductDescription.setWrapText(true); //text area will move to a new line when the other side is reached
    }

    //method for when add image button is selected
    @FXML
    private void btnAddImageActionPerformed(ActionEvent event) throws IOException { //Exclusive to admin user.
        event.consume();
        Stage secondaryStage = new Stage(); //creates a new stage onscreen
        FileChooser fileChooser = new FileChooser(); //new filechooser object
        fileChooser.setTitle("Select product image"); //title of stage set
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")); //sets the allowed file extensions
        File file = fileChooser.showOpenDialog(secondaryStage); //stage is shown
        String parent = file.getCanonicalPath(); //path of chosen file is retreived
        lblImagePath.setText(parent); //label is  set to the chosen file's path
        product.setProductFile(file); //product file setter used
    }

    //method for when the add product button is pressed
    @FXML
    private void btnAddProductActionPerformed(ActionEvent event)throws Exception{//Exclusive to admin user.
        event.consume();
        int stockVal = (int)sliderProductStock.getValue(); //gets the value of stock slider
        if(validateAddProduct() == true) { //checks that all fields are filled in correctly
            if(Double.parseDouble(txtProductCostPrice.getText()) >= Double.parseDouble(txtProductSellPrice.getText())) { //sell price cannot be less than cost price
                displayAlert("The product's cost price cannot be higher than the sell price.", Alert.AlertType.ERROR);
                return;
            }
            //checks that the company balance will not go below R25000 if stock is ordered
            if(product.validateStockOrder(stockVal, Double.parseDouble(txtProductCostPrice.getText()), DA.getCompanyBalance()) == true){
                DA.addProduct(txtProductName.getText(), stockVal, Double.parseDouble(txtProductCostPrice.getText()), Double.parseDouble(txtProductSellPrice.getText()), menuProductCategory.getText(), txtAreaProductDescription.getText(), product.getProductFile());
                DA.decreaseCompanyBalance(stockVal * Double.parseDouble(txtProductCostPrice.getText())); //company balance decreased after check is successful
                displayAlert("Stock ordered successfully\nCurrent balance: R" + DA.getCompanyBalance(), Alert.AlertType.CONFIRMATION);
                lblCompanyBalance.setText("R" + DA.getCompanyBalance()); //company balance is refreshed at top of the window
            }else{
                displayAlert("Stock not ordered. Company balance may not go below R25000", Alert.AlertType.ERROR); //if the check fails
            }
        }else{
            displayAlert("Please make sure the highlighted fields are all correctly filled in.", Alert.AlertType.ERROR); //if the fields are not all filled in correctly
        }
        }

    //method for when search button is pressed
    @FXML
    private void btnSearchActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        search(); //method from BaseUser class
    }

    //method for when textbook category is selected in add product screen
    @FXML
    private void menuItemTextbookActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        //all other categories disabled if textbook is selected
        if(menuItemTextbook.isSelected() == true){
            menuProductCategory.setText("Textbook");
            menuItemStationery.setDisable(true);
            menuItemClothing.setDisable(true);
            menuItemBag.setDisable(true);
            menuItemTechnology.setDisable(true);
        }else{
            //all categories enabled if textbook is selected
            menuProductCategory.setText("Product Category");
            menuItemStationery.setDisable(false);
            menuItemClothing.setDisable(false);
            menuItemBag.setDisable(false);
            menuItemTechnology.setDisable(false);
        }

    }

    //method for when stationery category is selected in add product screen
    @FXML
    private void menuItemStationeryActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        //all other categories disabled if stationery is selected
        if(menuItemStationery.isSelected() == true){
            menuProductCategory.setText("Stationery");
            menuItemTextbook.setDisable(true);
            menuItemClothing.setDisable(true);
            menuItemBag.setDisable(true);
            menuItemTechnology.setDisable(true);
        }else{
            //all categories enabled if stationery is selected
            menuProductCategory.setText("Product Category");
            menuItemTextbook.setDisable(false);
            menuItemClothing.setDisable(false);
            menuItemBag.setDisable(false);
            menuItemTechnology.setDisable(false);
        }
    }

    //method for when clothing category is selected in add product screen
    @FXML
    private void menuItemClothingActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        //all other categories disabled if clothing is selected
        if(menuItemClothing.isSelected() == true){
            menuProductCategory.setText("Clothing");
            menuItemTextbook.setDisable(true);
            menuItemStationery.setDisable(true);
            menuItemBag.setDisable(true);
            menuItemTechnology.setDisable(true);
        }else{
            //all categories enabled if clothing is selected
            menuProductCategory.setText("Product Category");
            menuItemTextbook.setDisable(false);
            menuItemStationery.setDisable(false);
            menuItemBag.setDisable(false);
            menuItemTechnology.setDisable(false);
        }
    }

    //method for when bag category is selected in add product screen
    @FXML
    private void menuItemBagActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        //all other categories disabled if bag is selected
        if(menuItemBag.isSelected() == true){
            menuProductCategory.setText("Bag");
            menuItemTextbook.setDisable(true);
            menuItemStationery.setDisable(true);
            menuItemClothing.setDisable(true);
            menuItemTechnology.setDisable(true);
        }else{
            //all categories enabled if bag is selected
            menuProductCategory.setText("Product Category");
            menuItemTextbook.setDisable(false);
            menuItemStationery.setDisable(false);
            menuItemClothing.setDisable(false);
            menuItemTechnology.setDisable(false);
        }
    }

    //method for when technology category is selected in add product screen
    @FXML
    private void menuItemTechnologyActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        //all other categories disabled if technology is selected
        if(menuItemTechnology.isSelected() == true){
            menuProductCategory.setText("Technology");
            menuItemTextbook.setDisable(true);
            menuItemStationery.setDisable(true);
            menuItemClothing.setDisable(true);
            menuItemBag.setDisable(true);
        }else{
            //all categories enabled if technology is selected
            menuProductCategory.setText("Product Category");
            menuItemTextbook.setDisable(false);
            menuItemStationery.setDisable(false);
            menuItemClothing.setDisable(false);
            menuItemBag.setDisable(false);
        }
    }

    //method for when signout button is pressed
    @FXML
    private void btnSignOutActionPerformed(ActionEvent event){
        event.consume();
        signOut(); //method from BaseUser class
    }

    //method for when view user button is pressed
    @FXML
    private void btnViewUsersOptionActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneUsers); //sets pane
        listAllUsers.getItems().clear(); //clears list
        users = DA.getAllUsers(); //users arraylist is populated using the getallusers method
        try{
            //loop used to add each user to the list
            for(int i = 0; i < users.size(); i++){
                listAllUsers.getItems().add(users.get(i).toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //method for when refresh list button is pressed
    @FXML
    private void btnRefreshUserListActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        listAllUsers.getItems().clear(); //clears list
        txtUserLastName.setText("");
        users = DA.getAllUsers(); //user arraylist is populated
        try{
            //loop to fill the list with users
            for(int i = 0; i < users.size(); i++){
                listAllUsers.getItems().add(users.get(i).toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //method for when the promote user button is selected
    @FXML
    private void btnPromoteUserActionPerformed(ActionEvent event)throws SQLException { //Exclusive to admin user.
        event.consume();
        try{
            Scanner scLine = new Scanner(String.valueOf(listAllUsers.getSelectionModel().getSelectedItem())); //scanner to scan selected string
            displayAlert(DA.promoteUser(scLine.nextInt()), Alert.AlertType.INFORMATION); //promotes the selected user
            scLine.close(); //scanner closed
        }catch(InputMismatchException e){
            displayAlert("No user selected.", Alert.AlertType.ERROR); //exception thrown if no user is selected
        }
    }

    //method for when the demote user button is selected
    @FXML
    private void btnDemoteUserActionPerformed(ActionEvent event)throws SQLException{ //Exclusive to admin user.
        event.consume();
        try{
            Scanner scLine = new Scanner(String.valueOf(listAllUsers.getSelectionModel().getSelectedItem())); //scans selected string
            displayAlert(DA.demoteUser(scLine.nextInt()), Alert.AlertType.INFORMATION); //user demoted
            scLine.close();
        }catch(InputMismatchException e){ //exception caught if no user is selected
            displayAlert("No user selected.", Alert.AlertType.ERROR);
        }
    }

    //method for when the remove user button is pressed
    @FXML
    private void btnRemoveUserActionPerformed(ActionEvent event)throws SQLException{ //Exclusive to admin user.
        event.consume();
        try{
            Scanner scLine = new Scanner(String.valueOf(listAllUsers.getSelectionModel().getSelectedItem())); //scans the selected string
            displayAlert(DA.removeUser(scLine.nextInt()), Alert.AlertType.INFORMATION); //user removed
            scLine.close(); //scanner closed
        }catch(InputMismatchException e){ //exception  caught if no user is selected
            displayAlert("No user selected.", Alert.AlertType.ERROR);
        }
    }

    //method for when the search for user button is pressed
    @FXML
    private void btnSearchForUserActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        if(contentCheck(txtUserLastName.getText()) == false){ //checks that the textfield is filled in
            displayAlert("Please enter the user's last name below before searching.", Alert.AlertType.ERROR);
            txtUserLastName.setStyle("-fx-background-color: #f53137; -fx-text-fill: black; -fx-prompt-text-fill: black");
        }else {
            users = DA.searchUser(txtUserLastName.getText()); //users arraylist is populated using searchuser method
            listAllUsers.getItems().clear(); //list is cleared
            try {
                //loop used to fill list with user arraylist
                for (int i = 0; i < users.size(); i++) {
                    listAllUsers.getItems().add(users.get(i).toString());
                }
            } catch (Exception e) { //exceptions caught
                e.printStackTrace();
            }
            if (users.size() < 1) { //if there are no users in the arraylist then no matching users exist
                displayAlert("No users found.", Alert.AlertType.ERROR);
            }
        }
    }

    //method for when save changes button is pressed
    @FXML
    private void btnSaveChangesActionPerformed(ActionEvent event)throws SQLException{
        event.consume();
        saveChangesToAccount(); //method from BaseUser class
    }

    //method for when reset password button is selected
    @FXML
    private void btnResetPasswordActionPerformed(ActionEvent event) throws Exception {
        event.consume();
        resetPassword(); //method from BaseUser class
    }

    //checks that all fields are filled in correctly for when adding a product
    private boolean validateAddProduct(){ //Exclusive to admin user.

        if(txtProductName.getText().length() < 1 || txtProductName.getText().length() > 18){
            txtProductName.setStyle("-fx-background-color: #f53137; -fx-text-fill: white"); //sets up the textfield
            lblWarningName.setStyle("-fx-text-fill: red"); //sets up label
            lblWarningName.setText("18 characters max."); //sets up label
        }else{
            txtProductName.setStyle("-fx-background-color: black; -fx-text-fill: white");
            lblWarningName.setStyle("-fx-text-fill: #000000");
            lblWarningName.setText("");
        }
        if(checkDouble(txtProductCostPrice.getText()) == false || txtProductCostPrice.getText().length() < 1){ //if the content is not a digit
            txtProductCostPrice.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            lblWarningCostPrice.setStyle("-fx-text-fill: red");
            lblWarningCostPrice.setText("Enter only a number");
        }else{
            txtProductCostPrice.setStyle("-fx-background-color: black; -fx-text-fill: white");
            lblWarningCostPrice.setStyle("-fx-text-fill: #000000");
            lblWarningCostPrice.setText("");
        }
        if(checkDouble(txtProductSellPrice.getText()) == false || txtProductSellPrice.getText().length() < 1){ //if a the content is not a digit
            txtProductSellPrice.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            lblWarningSellPrice.setStyle("-fx-text-fill: red");
            lblWarningSellPrice.setText("Enter only a number");
        }else{
            txtProductSellPrice.setStyle("-fx-background-color: black; -fx-text-fill: white");
            lblWarningSellPrice.setStyle("-fx-text-fill: #000000");
            lblWarningSellPrice.setText("");
        }
        if(menuProductCategory.getText().equals("Product Category") || menuProductCategory.getText() == null){
            menuProductCategory.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
        }else{
            menuProductCategory.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(sliderProductStock.getValue() == 0){ //stock must be ordered
            sliderProductStock.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
        }else{
            sliderProductStock.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        if(product.getProductFile() == null){ //a file must have been selected using the filechooser
            btnAddImage.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            lblImagePath.setText("No file selected.");
            lblImagePath.setStyle("-fx-text-fill: red");
        }else{
            btnAddImage.setStyle("-fx-background-color: black; -fx-text-fill: white");
            lblImagePath.setStyle("-fx-text-fill: white");
        }
        if(txtAreaProductDescription.getText().length() < 1 || txtAreaProductDescription.getText().length() > 280){
            txtAreaProductDescription.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            lblWarningProductDescription.setStyle("-fx-text-fill: red");
            lblWarningProductDescription.setText("280 characters max.");
            return false; //returns false if at leat one field is not correctly filled in
        }else{
            txtAreaProductDescription.setStyle("-fx-background-color: black; -fx-text-fill: white");
            lblWarningProductDescription.setStyle("-fx-text-fill: black");
            lblWarningProductDescription.setText("");
        }
        return true; //returns true if all fields are correctly filled in
    }

    //method for viewing the first product on screen
    @FXML
    private void btnViewProduct1ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct1(); //method from BaseUser class
        resetFields(); //textfields in the app are reset
    }

    //method for viewing the second product on screen
    @FXML
    private void btnViewProduct2ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct2(); //method from BaseUser class
        resetFields(); //textfields in the app are reset
    }

    //method for viewing the third product on screen
    @FXML
    private void btnViewProduct3ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct3(); //method from BaseUser class
        resetFields(); //textfields in the app are reset
    }

    //method for viewing the fourth product on screen
    @FXML
    private void btnViewProduct4ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct4(); //method from BaseUser class
        resetFields(); //textfields in the app are reset
    }

    //method for viewing the fifth product on screen
    @FXML
    private void btnViewProduct5ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct5(); //method from BaseUser class
        resetFields(); //textfields in the app are reset
    }

    //method for viewing the sixth product on screen
    @FXML
    private void btnViewProduct6ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct6(); //method from BaseUser class
        resetFields(); //textfields in the app are reset
    }

    //method for when the remove product button is pressed
    @FXML
    private void btnRemoveProductActionPerformed(ActionEvent event) throws SQLException, IOException { //Exclusive to admin user.
        event.consume();
        if(DA.removeProduct(product.getProductID()) == true){ //if the product could be removed successfully
            displayAlert("Product removed successfully and stock refunded.\nCurrent Balance: R" + DA.getCompanyBalance(), Alert.AlertType.CONFIRMATION);
            stackPane.getChildren().clear();
            stackPane.getChildren().add(paneInitialPane);
            lblCompanyBalance.setText("R" + DA.getCompanyBalance()); //company balance updated at top of the window
        }else{
            displayAlert("Unsuccessful. Product could not be removed.", Alert.AlertType.ERROR); //if removal of product is unsuccessful
        }
    }

    //method for is view product details is pressed
    @FXML
    private void btnViewProductDetailsActionPerformed(ActionEvent event){ //Exclusive to admin user.
        event.consume();
        displayAlert(product.toString(), Alert.AlertType.INFORMATION); //product class's toString used to print the details
    }

    //method for is the order stock button is pressed
    @FXML
    private void btnOrderStockActionPerformed(ActionEvent event) throws SQLException { //Exclusive to admin user.
        event.consume();
        if(DA.orderStock(Integer.parseInt(txtStockToOrder.getText()), product) == true){ //if stock could be ordered
            displayAlert("Stock ordered successfully.", Alert.AlertType.INFORMATION);
            lblCompanyBalance.setText("R" + DA.getCompanyBalance()); //company balance updated at top of window
        }else{
            displayAlert("Stock could not be ordered.", Alert.AlertType.ERROR); //if order stock failed
        }
    }

    //used to reset all the necessary fields of the app
    private void resetFields(){
        txtStockToOrder.setText("");
        txtProductName.setText("");
        txtProductCostPrice.setText("");
        txtProductSellPrice.setText("");
        txtAreaProductDescription.setText("");
        product.setProductFile(null);
        menuProductCategory.setText("Product Category");
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    //executed when the AdminDashboard class if run
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblName.setText(u.getFirstName());
        try {
            lblCompanyBalance.setText("R" + DA.getCompanyBalance());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
