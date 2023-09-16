package Swish.Controllers;

//Imports
import Swish.Backend.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;


public class Start extends Application {

    //JavaFX injections below:

    //StackedPane
        @FXML
        StackPane stackPane = new StackPane();
        @FXML
        Pane loginPane = new Pane();
        @FXML
        Pane registerPane = new Pane();
        @FXML
        Pane troubleSigningInPane = new Pane();

    //Side Pane
        @FXML
        Button btnRegister = new Button();
        @FXML
        Button btnLoginScreen = new Button();
        @FXML
        Button btnTroubleSigningIn = new Button();
        @FXML
        Button btnExit = new Button();

    //Login Pane
        @FXML
        TextField txtEmailLogin = new TextField();
        @FXML
        PasswordField txtPasswordLogin = new PasswordField();
        @FXML
        Button btnLogin = new Button();

    //Register Pane
        @FXML
        TextField txtFirstName = new TextField();
        @FXML
        TextField txtLastName = new TextField();
        @FXML
        TextField txtEmailRegister = new TextField();
        @FXML
        PasswordField txtPasswordRegister = new PasswordField();
        @FXML
        DatePicker datePicker = new DatePicker();
        @FXML
        Button btnRegisterNewUser = new Button();

    //TroubleSigningIn Pane
        @FXML
        TextField txtEmailField = new TextField();
        @FXML
        Button btnResetPassword = new Button();

        //start method used for fxml files
    public static void main(String[] args) {
        launch(args);
    }

    //global variables
    public static DataAccess DA = new DataAccess(); //DataAccess object to be used across the controllers.
    public static int sessionID;
    public static User u;
    public static Address a = new Address();

    //displays a javafx alert onscreen taking message and alert type as parameters
    public static void displayAlert(String message, Alert.AlertType AT){
        Alert alert = new Alert(AT);
        alert.setContentText(message); //shows the message
        alert.showAndWait(); //waits for user action
    }

    //displays an input dialog for a user to input content into
    public static String displayTextInputDialog(String message){ //https://stackoverflow.com/questions/39942943/how-to-return-string-from-optionalstring
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(message); //sets the message text
        Optional<String> result = dialog.showAndWait(); //waits for user action
        return result.get(); //returns collected result from dialog
    }

    //Method that is called to verify that the user has filled in all given fields
    public String contentCheck(String content, String item){
        String temp = "Please enter your " + item;

        if(content.length() < 1) { // Will return temp if the given string has a length less than 1 i.e. content not filled in.
            return temp;
        }else{
            return null; //Will return a null value if the the given string passes the length check i.e. content was filled in.
        }
    }

    Parent root;
    double xOffset, yOffset;

    @Override
    public void start(Stage primaryStage){
        try{
            root = FXMLLoader.load(getClass().getResource("/fxml/start.fxml"));
            Scene loginScene = new Scene(root);
            primaryStage.initStyle(StageStyle.TRANSPARENT); //Removes the application border.
            loginScene.setFill(Color.TRANSPARENT); //Removes the application border.
            Image icon = new Image(getClass().getResourceAsStream("/Images/icon1.png")); //Retrieves the app icon.
            primaryStage.getIcons().add(icon); //Sets the app icon for the taskbar.
            primaryStage.setTitle("Swish Africa"); //Sets the title for the application.
            primaryStage.setScene(loginScene);
            primaryStage.show();

            //Detects a mouse press on the application by using a mouse event and mouse positioning.
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            //Allows the application window to be dragged by using a mouse event and mouse positioning.
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });

        //Catches any input/output exceptions on the start page.
        }catch(IOException ex){
            exception(ex);
        }

    }

    //Called when the side pane's register button is pressed and will replace the right-hand pane with the register screen.
    @FXML
    private void btnRegisterActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(registerPane);
    }

    //Called when the side pane's login button is pressed and will replace the right-hand pane with the login screen.
    @FXML
    private void btnLoginScreenActionPerformed(ActionEvent event){
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(loginPane);
    }

    //Called when the side pane's trouble button is pressed and will replace the right-hand pane with the trouble screen.
    @FXML
    private void btnTroubleSigningInActionPerformed(ActionEvent event) {
        event.consume();
        stackPane.getChildren().clear();
        stackPane.getChildren().add(troubleSigningInPane);
    }

    //Closes the application when exit is pressed.
    @FXML
    private void btnExitActionPerformed(ActionEvent event){
        event.consume();
        System.exit(0); //process ended
    }

    //loginPane buttons
    @FXML
    private void btnLoginActionPerformed(ActionEvent event){
        event.consume();

        if(validateLogin() == false){ //validates all fields on the login screen
            displayAlert("Missing information. Please fill in the highlighted fields.", Alert.AlertType.ERROR);
            return; //returns if they are not correctly filled in
        }

        String email = txtEmailLogin.getText().toLowerCase();
        String password = txtPasswordLogin.getText();
        String encrypted = Encryption.encrypt(password);

        try {
            u = DA.getUser(email, encrypted); //searches for a user using the entered email and password
            a = DA.getAddress(u.getUserID()); //gets matching address using userID

            contentCheck(email, "email");
            contentCheck(password, "password");

            if(u == null){ //if the user doesn't exit login fails
                displayAlert("Incorrect email or password.", Alert.AlertType.ERROR);
                return;
            }

            if(!u.getPass().equals(encrypted)){ //if the password does not match login fails
                displayAlert("Incorrect email or password.", Alert.AlertType.ERROR);
                return;
            }

            //loads admin window if the user is an admin
            if (u.getUserType().equals("Admin")) {
                sessionID = u.getUserID();
                a = DA.getAddress(u.getUserID());
                AnchorPane AdminDashboard = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/AdminDashboard.fxml"));
                Stage adminWindow = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("/Images/icon1.png"));
                adminWindow.getIcons().add(icon);
                adminWindow.setTitle("Swish");
                adminWindow.setScene(new Scene(AdminDashboard));
                adminWindow.setResizable(false);
                adminWindow.show();
                Swish.Controllers.AdminDashboard ad = new AdminDashboard();
                Stage currentWindow = (Stage) btnRegister.getScene().getWindow();
                currentWindow.close();

            } else{ //loads the customer window is the user is not an admin
                sessionID = u.getUserID();
                a = DA.getAddress(u.getUserID());
                AnchorPane customerDashboard = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/CustomerDashboard.fxml"));
                Stage cusWindow = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("/Images/icon1.png"));
                cusWindow.getIcons().add(icon);
                cusWindow.setTitle("Swish");
                cusWindow.setScene(new Scene(customerDashboard));
                cusWindow.setResizable(false);
                cusWindow.show();
                Stage currentWindow = (Stage) btnRegister.getScene().getWindow();
                currentWindow.close();
            }

        } catch (NullPointerException e) { //exception caught if the user is not found
            displayAlert("Incorrect username or password.", Alert.AlertType.ERROR);
            return;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    //registerPane Buttons
    @FXML
    private void btnRegisterNewUserActionPerformed(ActionEvent event){
        if(validateRegister() == false){ //checks that all fields are correctly filled in
            displayAlert("Missing information. Please fill in the highlighted fields.", Alert.AlertType.ERROR);
            return; //returns if they are not correctly filled in
        }

        String code = User.resetPswrd();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmailRegister.getText();
        String password = txtPasswordRegister.getText();
        LocalDate localDOB = datePicker.getValue();
        String userType = "Customer";
        Date dob = Date.valueOf(localDOB);

        if(DA.userExists(email) == false){ //checks if there is an existing user with the same email
            displayAlert("User already exists", Alert.AlertType.ERROR);
            return; //returns if there is an existing user with the same email
        }

            try{ //user is added passing the textfields's values as parameters
                DA.addUser(firstName, lastName, email, password, dob, userType);
                displayAlert("User registered.", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                displayAlert(ex.getMessage(), Alert.AlertType.ERROR);
            }

    }

    //troubleSigningInPane Buttons
    @FXML
    private void btnResetPasswordActionPerformed(ActionEvent event) throws Exception {
        event.consume();

        if(validatePasswordReset() == false){ //checks that all fields are filled in correctly
            displayAlert("Missing information. Please fill in the highlighted fields.", Alert.AlertType.ERROR);
            return; //returns if they are not correctly filled in
        }

        String email = txtEmailField.getText();
        String pass = User.resetPswrd();
        String message = "Your temporary password is: " + pass + "\n Reset your password once you have logged in.";

        if(DA.userExists(email) == true){ //checks if the user exists
            if (DA.updatePasswordAtStart(pass, email) == true) { //password is updated in the database
                EmailSender.sendEmail(email, "Password Reset", message); //password reset email sent to user's email address (may freeze the app for a few seconds)
                displayAlert("Your temporary password has been sent to your email.\nCheck your spam folder if the email is not in your inbox.", Alert.AlertType.INFORMATION);
            }
        }else{ //email is not sent if the user does not exist
            displayAlert("User does not exist.", Alert.AlertType.ERROR);
        }
    }

    private void exception(Exception ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }

    //validation methods
    private boolean validateLogin(){

        if(txtEmailLogin.getText().length() < 1){
            txtEmailLogin.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
        }else{
            txtEmailLogin.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }
        if(txtPasswordLogin.getText().length() < 1){
            txtPasswordLogin.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            return false;
        }else{
            txtPasswordLogin.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }

        return true;
    }

    //checks that all fields on the register screen are correctly filled in
    private boolean validateRegister(){

        if(txtEmailRegister.getText().length() < 1){
            txtEmailRegister.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
        }else{
            txtEmailRegister.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }
        if(txtPasswordRegister.getText().length() < 1){
            txtPasswordRegister.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
        }else{
            txtPasswordRegister.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }
        if(txtFirstName.getText().length() < 1){
            txtFirstName.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
        }else{
            txtFirstName.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }
        if(txtLastName.getText().length() < 1){
            txtLastName.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
        }else{
            txtLastName.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }
        if(datePicker.getValue() == null) {
            datePicker.setStyle("-fx-control-inner-background: #f53137; ");
        }else if(checkIf18(datePicker.getValue())){
            datePicker.setStyle("-fx-control-inner-background: #f53137; ");
            return false;
        }else{
            datePicker.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }
        return true;
    }

    //checks that all fields on the trouble signing pane are filled in correctly
    private boolean validatePasswordReset(){
        if(txtEmailField.getText().length() < 1){
            txtEmailField.setStyle("-fx-background-color: #f53137; -fx-text-fill: white");
            return false;
        }else{
            txtEmailField.setStyle("-fx-background-color: #313436; -fx-text-fill: white");
        }
        return true;
    }
    //validates that the user is 18 years or older
    private boolean checkIf18(LocalDate dob){
        LocalDate d = LocalDate.now();
        Period period = Period.between(dob, d);
        return period.getYears() < 18;
    }

}


