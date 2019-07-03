package Server;

import javax.sound.sampled.AudioFormat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    static ArrayList<ClientHandler> handlers = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    ServerSocket serverSocket;
    ServerSocket voiceServerSocket;
    Server(ArrayList<User> users) {
        this.users=users;
        try {
            serverSocket = new ServerSocket(7000);
            voiceServerSocket = new ServerSocket(8000);
        }catch (IOException e){

        }
        start();
    }

    @Override
    public void run() {
        while (true){
            System.out.println("wait for Client");
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client accepted");
            System.out.println("wait for voice");
            Socket voice = null;
            try {
                voice = voiceServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("voice accepted");
            ClientHandler clientHandler = null;
            try {
                clientHandler = new ClientHandler(client,voice);
            } catch (IOException e) {
                e.printStackTrace();
            }
            handlers.add(clientHandler);
        }
    }
}
