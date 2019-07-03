package Client;

import controllers.Homepage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.imageio.IIOException;
import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class NotificationHandler extends Thread {
    DataInputStream in = null;
    DataOutputStream out = null;
    DataOutputStream voiceOut = null;
    DataInputStream voiceIn = null;
    public String answer="NOTYET";
    public String command="KEEP";
    NotificationHandler(DataInputStream in , DataOutputStream out , DataOutputStream voiceOut , DataInputStream voiceIn ){
        this.in=in;
        this.out=out;
        this.voiceIn = voiceIn;
        this.voiceOut = voiceOut;
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String todo = in.readUTF();
                System.out.println("Notif" + todo);
                if(todo.equals("call")){
                    String user = voiceIn.readUTF();
                    System.out.println(user + "is calling you");
                    answer = "NOTYET";
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            answer = Homepage.showAnswer(user);
                        }
                    });
                    while (answer.equals("NOTYET")){
                        Thread.sleep(1000);
                        System.out.println(answer);
                    }
                    voiceOut.writeUTF(answer);
                    voiceOut.flush();
                    if(answer.equals("YES")) {
                        VoiceReciever reciever = new VoiceReciever(voiceIn);
                        VoiceSender sender = new VoiceSender(voiceOut);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
