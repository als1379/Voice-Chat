package Server;

import java.util.ArrayList;

public class User {
    private String userName;
    private String password;
    public ArrayList<String> contacts = new ArrayList<>();
    public ArrayList<String> calls = new ArrayList<>();
    User(String userName, String password){
        this.userName=userName;
        this.password=password;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword() { return password; }
}
