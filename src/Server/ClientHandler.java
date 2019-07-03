package Server;

import javax.sound.sampled.AudioFormat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    DataInputStream in;
    DataOutputStream out;
    DataOutputStream voiceOut;
    DataInputStream voiceIn;
    String userName;
    String password;
    boolean isTalking = false;
    ClientHandler(Socket client, Socket voice) throws IOException {
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
        voiceOut = new DataOutputStream(voice.getOutputStream());
        voiceIn = new DataInputStream(voice.getInputStream());
        userName = in.readUTF();
        System.out.println(userName);
        for (User user : Server.users){
            if(user.getUserName().equals(userName)){
                for (String contact : user.contacts){
                    out.writeUTF(contact);
                    out.flush();
                }
                out.writeUTF("END");
                out.flush();
                for (String call : user.calls){
                    out.writeUTF(call);
                    out.flush();
                }
                out.writeUTF("END");
                out.flush();
                break;
            }
        }

        start();
    }
    public DataInputStream getIn(){
        return in;
    }
    public DataOutputStream getOut(){
        return out;
    }
    public DataInputStream getVoiceIn() { return voiceIn; }
    public DataOutputStream getVoiceOut() { return voiceOut; }
    @Override
    public void run() {
        try {
            while (true) {
                String todo = in.readUTF();
                System.out.println("command" + todo);
                if (todo.equals("END")) {
                    Server.handlers.remove(this);
                    return;
                }
                if(todo.equals("ENDCALL")){
                    out.writeUTF("endcall");
                    out.flush();

                }
                if (todo.equals("CALL")) {
                    String userToCall = in.readUTF();
                    System.out.println(userToCall);
                    if(userToCall.equals(userName)){
                        voiceOut.writeUTF("error");
                        voiceOut.writeUTF("you cant call yourself");
                        voiceOut.flush();
                        continue;
                    }
                    boolean isExisted = false;

                    for (User user : Server.users) {
                        if (user.getUserName().equals(userToCall))
                            isExisted = true;
                    }
                    if (!isExisted) {
                        voiceOut.writeUTF("error");
                        voiceOut.flush();
                        voiceOut.writeUTF("user not existed");
                        voiceOut.flush();
                    } else {
                        boolean isOnline = false;
                        ClientHandler user = null;
                        for (ClientHandler clientHandler : Server.handlers) {
                            if (clientHandler.userName.equals(userToCall)) {
                                isOnline = true;
                                user = clientHandler;
                            }
                        }

                        if (!isOnline) {
                            voiceOut.writeUTF("error");
                            voiceOut.flush();
                            voiceOut.writeUTF("user is offline");
                            voiceOut.flush();
                        } else {
                            if (user.isTalking) {
                                voiceOut.writeUTF("error");
                                voiceOut.flush();
                                voiceOut.writeUTF("user is busy");
                                voiceOut.flush();
                            } else {
                                voiceOut.writeUTF("calling " + user.userName);
                                voiceOut.flush();
                                user.getOut().writeUTF("call");
                                user.getOut().flush();
                                user.getVoiceOut().writeUTF(userName);
                                user.getVoiceOut().flush();
                                String answer = user.getVoiceIn().readUTF();
                                System.out.println("answer is");
                                System.out.println(answer);
                                if (answer.equals("YES")) {
                                    voiceOut.writeUTF("accept");
                                    voiceOut.flush();
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    isTalking = true;
                                    user.isTalking =true;
                                    ClientVoiceHandler clientVoiceHandler = new ClientVoiceHandler(voiceOut,user.getVoiceIn());
                                    ClientVoiceHandler clientVoiceHandler1 = new ClientVoiceHandler(user.getVoiceOut(),voiceIn);
                                } else {
                                    voiceOut.writeUTF("reject");
                                    voiceOut.flush();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}