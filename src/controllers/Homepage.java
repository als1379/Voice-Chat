package controllers;

import Client.Client;
import Server.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Homepage {
    Client user;
    boolean contaactFlag = true;
    boolean callstFlag = true;
    PageLoader pageLoader = new PageLoader();

    public Homepage(){
        this.user=Controller.getClient();
    }

    @FXML
    private Button callsButton;

    @FXML
    private Button contactsButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button addContactButton;

    @FXML
    private VBox scrollVbox;

    @FXML
    private Label circumstancesLabel;

    @FXML
    private ImageView callPictureButton;

    @FXML
    private Button callButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField findContact;

    @FXML
    private TextField searchUsers;

    @FXML
    void initialize(){
        usernameLabel.setText(user.user);
    }
    @FXML
    void onContactsButtonClicked(ActionEvent event) {
        ArrayList<String> contactArray = user.contacts;
        scrollVbox.getChildren().clear();
        for (String i : contactArray) {
            HBox thecontact = new HBox();
            thecontact.setAlignment(Pos.CENTER);
            thecontact.setSpacing(50);
            Label username = new Label();
            username.setAlignment(Pos.CENTER);
            username.setText(i);
            username.setStyle(
                    "-fx-text-fill: #657786;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;"
            );
            username.setText("@" + i);
            Button call = new Button("call");
            call.setStyle(
                    "-fx-background-color: #197509;" +
                            "-fx-background-radius: 20em; " +
                            "-fx-text-fill: #ffffff;" +
                            "-fx-min-width: 50;" +
                            "-fx-font-size: 16px;"
            );

            //handling button call
            call.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(searchUsers.getText()!=""){
                        try {
                            user.call(i);
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

            thecontact.getChildren().addAll(username, call);
            thecontact.setPadding(new Insets(5, 20, 5, 20));
            thecontact.setStyle(
                    "-fx-border-width: 0px 0px 1px 0px;" +
                            "-fx-border-color: #e1e8ed;" +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: arial;" +
                            "-fx-background-color: #E1E8ED;" +
                            "-fx-min-width: 300px; " +
                            "-fx-min-height: 50px; " +
                            "-fx-max-width: 300; "
            );
            //add theTweet Vbox in tweetLoader
            scrollVbox.getChildren().add(0, thecontact);
        }

    }

    @FXML
    void onCallsButtonClicked(ActionEvent event) {
        scrollVbox.getChildren().clear();
        ArrayList<String> callsArray = user.calls;
        for (String i : callsArray) {
            HBox thecontact = new HBox();
            thecontact.setAlignment(Pos.CENTER);
            thecontact.setSpacing(50);
            Label username = new Label();
            username.setAlignment(Pos.CENTER);
            username.setText(i);
            username.setStyle(
                    "-fx-text-fill: #657786;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;"
            );
            username.setText("@" + i);
            Button call = new Button("call");
            call.setStyle(
                    "-fx-background-color: #197509;" +
                            "-fx-background-radius: 20em; " +
                            "-fx-text-fill: #ffffff;" +
                            "-fx-min-width: 50;" +
                            "-fx-font-size: 16px;"
            );

            //handling button call
            call.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(searchUsers.getText()!=""){
                        try {
                            user.call(i);
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

            thecontact.getChildren().addAll(username, call);
            thecontact.setPadding(new Insets(5, 20, 5, 20));
            thecontact.setStyle(
                    "-fx-border-width: 0px 0px 1px 0px;" +
                            "-fx-border-color: #e1e8ed;" +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: arial;" +
                            "-fx-background-color: #E1E8ED;" +
                            "-fx-min-width: 300px; " +
                            "-fx-min-height: 50px; " +
                            "-fx-max-width: 300; "
            );
            //add theTweet Vbox in tweetLoader
            scrollVbox.getChildren().add(0, thecontact);
        }
    }

    @FXML
    void onfindContactClicked(ActionEvent event) {

    }

    @FXML
    void onAddContactButtonClicked(ActionEvent event) {

    }

    @FXML
    void onSearchedUserClicked(ActionEvent event) {

    }

    @FXML
    void onCallButtonClicked(ActionEvent event) throws IOException, LineUnavailableException {
        if(searchUsers.getText()!=""){
            user.call(searchUsers.getText());
        }
    }

    @FXML
    void onEndCallButtonClicked(ActionEvent event) throws IOException, LineUnavailableException {
        if(searchUsers.getText()!=""){
            user.call(searchUsers.getText());
        }
    }


    @FXML
    void onCallPictureButtonClicked(ActionEvent event) {

    }

    public static String showAnswer(String user){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText(user + "calling you");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return "YES";
        } else {
            return "NO";
        }
    }
    public static String endcall(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Massage");
        alert.setContentText("END CALL?");
        alert.showAndWait();
        return "END";
    }
}
