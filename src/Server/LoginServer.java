package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LoginServer {
    ServerSocket serverSocket =null;
    ArrayList<User> users = new ArrayList<>();
    public LoginServer(ArrayList<User> users) throws IOException {
        serverSocket= new ServerSocket(1234);
        this.users=users;
    }

    public void startLoginServer() throws IOException {
        while (true) {
            System.out.println("wait for LoginClient");
            Socket client = serverSocket.accept();
            System.out.println("LoginClient accepted");
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            while (true) {
                boolean flag = false;
                String command = inputStream.readUTF();
                System.out.println("COMMAND: " + command);
                if (command.equals("LOGIN")) {
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        outputStream.writeUTF(user.getUserName());
                        outputStream.writeUTF(user.getPassword());
                        outputStream.flush();
                        String reply = inputStream.readUTF();
                        System.out.println(reply);
                        if (reply.equals("CORRECT")) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        outputStream.writeUTF("END");
                        outputStream.writeUTF("END");
                        outputStream.flush();
                    }
                } else {
                    boolean flag2 = false;
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        outputStream.writeUTF(user.getUserName());
                        outputStream.flush();
                        String reply = inputStream.readUTF();
                        System.out.println(reply);
                        if (reply.equals("WRONG")) {
                            flag2 = true;
                            break;
                        }
                    }
                    if (flag2) continue;
                    outputStream.writeUTF("END");
                    outputStream.flush();
                    flag = true;
                    String username = inputStream.readUTF();
                    String password = inputStream.readUTF();
                    MainServer.users.add(new User(username, password));
                }
                if (flag)
                    break;

            }
            Server server = new Server(users);

        }
    }
}
