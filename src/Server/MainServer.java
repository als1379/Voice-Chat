package Server;

import java.io.IOException;
import java.util.ArrayList;

public class MainServer {
    public static Server server;
    public static ArrayList<User> users = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        User ali = new User("ali", "ali");
        User morteza = new User("morteza","morteza");
        ali.contacts.add("ahmad");
        ali.calls.add("morteza");
        ali.calls.add("morteza");
        ali.calls.add("ahmad");
        User ahmad = new User("ahmad", "ahmad");
        users.add(ali);
        users.add(ahmad);
        users.add(morteza);
        LoginServer loginServer = new LoginServer(users);
        loginServer.startLoginServer();
    }

}
