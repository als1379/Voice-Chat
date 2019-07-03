package controllers;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {

    @FXML
    private Button signInButton;

    PageLoader pageLoader = new PageLoader();
    public static Socket socket;
    public static DataInputStream in;
    public static DataOutputStream out;
    public static Client client = null;

    @FXML
    void onSignInClicked(ActionEvent event) throws IOException {
        socket = new Socket("localhost",1234);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        pageLoader.loading(event,"../style/sign_in.fxml",460,625);
    }

    public static DataOutputStream getOut(){
        return out;
    }
    public static DataInputStream getIn(){
        return in;
    }
    public static Socket getSocket(){
        return socket;
    }
    public static Client getClient() { return client; }
}
