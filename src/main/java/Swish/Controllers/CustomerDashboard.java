package Swish.Controllers;

import Swish.Backend.Order;
import Swish.Backend.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import static Swish.Controllers.Start.*;
import static Swish.Controllers.Start.displayAlert;

public class CustomerDashboard extends BaseUser implements Initializable {

    //Buttons
    @FXML
    Button btnProceedToCheckout = new Button();
    @FXML
    Button btnAddToCart = new Button();
    //Textfields & Areas
    @FXML
    TextArea txtAddressOutput = new TextArea();
    @FXML
    TextArea txtMailMessage = new TextArea();
    @FXML
    TextArea txtHelp = new TextArea();
    //Labels
    @FXML
    Label lblStock = new Label();
    @FXML
    Label lblTotalCurrentCost = new Label();
    @FXML
    Label lblShippingTime = new Label();
    @FXML
    Label lblOrderCost = new Label();
    @FXML
    Label lblPrice = new Label();
    //Panes
    @FXML
    Pane paneCart = new Pane();
    @FXML
    Pane paneCheckOut = new Pane();
    @FXML
    Pane paneInbox = new Pane();
    //Lists
    @FXML
    ListView listCartItems = new ListView();
    @FXML
    ListView listInbox = new ListView();

    public static void main(String[]args){launch(args);}

    //start method used for fxml files
    @Override
    public void start(Stage primaryStage){}

    //method for when the inbox button is pressed
    @FXML
    private void btnInboxActionPerformed(ActionEvent event) throws SQLException {
        event.consume();
        stackPane.getChildren().clear(); //current pane cleared
        stackPane.getChildren().add(paneInbox); //pane is set
        listInbox.getItems().clear(); //list is cleared
        ArrayList<Order> orders = DA.getOrdersByID(u.getUserID()); //arraylist of ordered created

        try{
            //loop used to fill list
            for(int i = 0; i < orders.size(); i++){
                listInbox.getItems().add(orders.get(i).toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //method for when edit account button is pressed
    @FXML
    private void btnEditAccountActionPerformed(ActionEvent event){
        event.consume();
        editAccount(); //method from BaseUser class
    }

    //method for when the textbooks category is selected
    @FXML
    private void btnCategoryTextbooksActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setTextBooks(); //method from BaseUser class
    }

    //method for when the stationery category is selected
    @FXML
    private void btnCategoryStationeryActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setStationery(); //method from BaseUser class
    }

    //method for when the clothing category is selected
    @FXML
    private void btnCategoryClothingActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setClothing(); //method from BaseUser class
    }

    //method for when the bags category is selected
    @FXML
    private void btnCategoryBagsActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setBag(); //method from BaseUser class
    }

    //method for when the technology category is selected
    @FXML
    private void btnCategoryTechnologyActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        setTechnology(); //method from BaseUser class
    }

    //method for when the previous page button is clicked
    @FXML
    private void btnPreviousPageActionPerformed(ActionEvent event){
        event.consume();
        nextPage(); //method from BaseUser class
    }

    //method for when the next page button is clicked
    @FXML
    private void btnNextPageActionPerformed(ActionEvent event) {
        event.consume();
        prevPage(); //method from BaseUser class
    }

    //method for when the first product onscreen is viewed
    @FXML
    private void btnViewProduct1ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct1(); //method from BaseUser class
        viewProductCustomer(); //sets the view product pane for customer type
    }

    //method for when the second product onscreen is viewed
    @FXML
    private void btnViewProduct2ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct2(); //method from BaseUser class
        viewProductCustomer(); //sets the view product pane for customer type
    }

    //method for when the third product onscreen is viewed
    @FXML
    private void btnViewProduct3ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct3(); //method from BaseUser class
        viewProductCustomer(); //sets the view product pane for customer type
    }

    //method for when the fourth product onscreen is viewed
    @FXML
    private void btnViewProduct4ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct4(); //method from BaseUser class
        viewProductCustomer(); //sets the view product pane for customer type
    }

    //method for when the fifth product onscreen is viewed
    @FXML
    private void btnViewProduct5ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct5(); //method from BaseUser class
        viewProductCustomer(); //sets the view product pane for customer type
    }

    //method for when the sixth product onscreen is viewed
    @FXML
    private void btnViewProduct6ActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneViewProduct);
        viewProduct6(); //method from BaseUser class
        viewProductCustomer();//sets the view product pane for customer type
    }

    //method for when the save changes to account button is pressed
    @FXML
    private void btnSaveChangesActionPerformed(ActionEvent event)throws SQLException{
        event.consume();
        saveChangesToAccount(); //method from BaseUser class
    }

    //method for when the reset password button is pressed
    @FXML
    private void btnResetPasswordActionPerformed(ActionEvent event) throws Exception {
        event.consume();
        resetPassword(); //method from BaseUser class
    }

    //method for when the signout button is pressed
    @FXML
    private void btnSignOutActionPerformed(ActionEvent event){
        event.consume();
        signOut(); //method from BaseUser class
    }

    //method for when the search button is pressed
    @FXML
    private void btnSearchActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneProducts);
        search(); //method from BaseUser class
        enableCategories(); //method from BaseUser class
    }

    //method for when the add to cart button is pressed
    @FXML
    private void btnAddToCartActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        String msg = DA.addToCart(product.getProductID(), u.getUserID()); //adds the productID to cart string
        int stock = product.getProductStock() -1; //stock is edited (not in database until product is purchased)
        product.setProductStock(stock); //new productstock is set
        if (product.getProductStock() > 0) { //if there is still stock left more can be added to the cart
            displayAlert(msg, Alert.AlertType.INFORMATION);
            btnAddToCart.setDisable(false);
        }else{
            displayAlert(msg,Alert.AlertType.INFORMATION); //if there is no stock left no more can be added to the cart
            displayAlert("The last available item was added to your cart and no more can be ordered.\nNote: this does not reserve the item for you, the item will be reserved once you have made an order.", Alert.AlertType.INFORMATION);
            btnAddToCart.setDisable(true);
        }
    }

    //method for when the cart button is pressed
    @FXML
    private void btnCartActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(paneCart);
        enableCategories(); //method from BaseUser class
        setCartList(); //sets the list of items in the cart
    }

    //sets up the list of items in the cart
    private void setCartList() throws SQLException, IOException {
        listCartItems.getItems().clear(); //list is cleared
        products = DA.getCartItems(u.getUserID()); //products arraylist is populated
        if(DA.getCart(u.getUserID()).length() == 0){ //cannot proceed to checkout if cart is empty
            btnProceedToCheckout.setDisable(true);
        }else { //can proceed to checkout as cart is not empty
            btnProceedToCheckout.setDisable(false);
        }
        lblTotalCurrentCost.setText("R" + Product.totalCost(products)); //current cost of the cart set
        try{
            //list is filled using products arraylist
            for(int i = 0; i < products.size(); i++){
                listCartItems.getItems().add(products.get(i).toString(true));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //method for when the remove from cart button is pressed
    @FXML
    private void btnRemoveItemFromCartActionPerformed(ActionEvent event) throws SQLException, IOException {
        event.consume();
        try {
            int pID = products.get(listCartItems.getSelectionModel().getSelectedIndex()).getProductID();
            DA.removeItemFromCart(pID, u.getUserID()); //item is removed from cart
            setCartList(); //list updated
        }catch(InputMismatchException e){ //exception caught if no item is selected
            displayAlert("No item selected", Alert.AlertType.ERROR);
        }

    }

    //method for when the proceed to checkout button is pressed
    @FXML
    private void btnProceedToCheckOutActionPerformed(ActionEvent event) throws SQLException {
        event.consume();
        try {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(paneCheckOut);
            if (a.getCountry().equals("South Africa")) { //determines shipping time using address country
                lblShippingTime.setText("1-2 Working Days");
            } else {
                lblShippingTime.setText("5-7 Working Days");
            }
            lblOrderCost.setText("" + Product.totalCost(products));
            txtAddressOutput.setText(a.toString());
            txtAddressOutput.setEditable(false);
            txtMailMessage.setEditable(false);
        }catch(NullPointerException e){ //exception caught if there is no address added to the account
            displayAlert("Please add an address to your account before trying to make a purchase (done on edit account screen).", Alert.AlertType.ERROR);
        }
    }

    //method for when the order button is pressed
    @FXML
    private void btnOrderActionPerformed(ActionEvent event) throws SQLException {
        event.consume();
        if(DA.newOrder(u.getUserID(), a.getAddressID(), Double.parseDouble(lblOrderCost.getText()), products.size()) == true) {
            DA.minusStock(DA.getCart(u.getUserID())); //stock is adjusted accordingly
            DA.clearCart(u.getUserID()); //cart is cleared of all products
            DA.increaseCompanyBalance(Double.parseDouble(lblOrderCost.getText())); //company balance is increased accordingly
            stackPane.getChildren().clear(); //current screen cleared
            stackPane.getChildren().add(paneInitialPane); //starting screen is set
            displayAlert("Your order has been placed and the receipt was sent to your Swish account's inbox. Thank you for shopping with Swish.", Alert.AlertType.INFORMATION);
        }else{//if the purchase failed
            displayAlert("Order was unsuccessful. Please try again later.", Alert.AlertType.ERROR);
        }
    }

    //method for when the help button is pressed
    @FXML
    private void btnHelpActionPerformed(ActionEvent event) throws IOException {
        event.consume();
        AnchorPane AdminDashboard = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/help.fxml")); //help file loaded
        Stage helpWindow = new Stage(); //new stage created
        Image icon = new Image(getClass().getResourceAsStream("/Images/icon1.png"));
        helpWindow.getIcons().add(icon); //app icon set
        helpWindow.setTitle("Swish Title"); //title set
        helpWindow.setScene(new Scene(AdminDashboard));
        helpWindow.setResizable(false);
        txtHelp.setEditable(false);
        helpWindow.show(); //help window shown
    }

    //method to setup the viewproduct screen for customer type user
    private void viewProductCustomer(){
        lblPrice.setText("R" + product.getProductSellPrice()); //price is set
        if(product.checkStock() == true){ //if product is in stock
            lblStock.setText("In Stock");
            lblStock.setStyle("-fx-text-fill: #39d022");
            btnAddToCart.setDisable(false); //can add to cart
        }else if(product.checkStock() == false){ //if product is not in stock
            lblStock.setText("Out of Stock");
            lblStock.setStyle("-fx-text-fill: #dd2020");
            btnAddToCart.setDisable(true); //product cannot be added to cart
        }
    }

    //executed when the AdminDashboard class if run
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblName.setText(u.getFirstName());
        txtHelp.setEditable(false);
    }
}
