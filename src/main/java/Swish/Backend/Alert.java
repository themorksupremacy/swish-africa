package Swish.Backend;

public class Alert {

    //method to display message onscreen with specific message type
    public static void displayAlert(String message, javafx.scene.control.Alert.AlertType AT){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AT);
        alert.setContentText(message);
        alert.showAndWait(); //waits for user's action
    }

}
