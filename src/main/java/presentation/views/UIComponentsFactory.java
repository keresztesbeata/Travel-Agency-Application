package presentation.views;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class UIComponentsFactory {

    public static Alert getAlertDialog(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.getDialogPane().getStylesheets().add("/styles/dialogStyle.css");
        alert.getDialogPane().getStyleClass().add("dialog-pane");
        alert.getDialogPane().setHeader(new Label(alertType.name()));
        alert.getDialogPane().getHeader().getStyleClass().add("header");
        alert.setTitle(title);
        Text messageText = new Text(message);
        messageText.setWrappingWidth(250);
        alert.getDialogPane().setContent(messageText);
        alert.setWidth(250);
        return alert;
    }
    public static Alert getConfirmDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add("/styles/dialogStyle.css");
        alert.getDialogPane().getStyleClass().add("dialog-pane");
        alert.getDialogPane().setHeader(new Label("Confirm?"));
        alert.getDialogPane().getHeader().getStyleClass().add("header");
        alert.setTitle(title);
        Text messageText = new Text(message);
        messageText.setWrappingWidth(250);
        alert.getDialogPane().setContent(messageText);
        alert.setWidth(250);
        return alert;
    }


}
