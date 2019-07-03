package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PageLoader {
    public void loading(ActionEvent event, String url,int witdh, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(url));
        Parent parent = fxmlLoader.load();
        Scene parentS = new Scene(parent,witdh,height);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(parentS);
        window.show();
    }
}
