package Client;

import javafx.scene.control.Alert;

import javax.sound.sampled.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    public String user;
    private DataOutputStream out;
    private DataInputStream in;
    private Socket voiceSocket;
    private DataOutputStream outVoice;
    private DataInputStream inVoice;
    public ArrayList<String> contacts = new ArrayList<>();
    public ArrayList<String> calls = new ArrayList<>();
    /* Constructor */
    public Client(String user) throws IOException {
        System.out.println("Connecting to server");
        socket = new Socket("localhost",7000);
        System.out.println("Client Connected");
        this.user = user;

        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        voiceSocket = new Socket("localhost" , 8000);
        System.out.println("voice connected");

        outVoice = new DataOutputStream(voiceSocket.getOutputStream());
        inVoice = new DataInputStream(voiceSocket.getInputStream());
        out.writeUTF(user);
        out.flush();
        while (true){
            String contact = in.readUTF();
            if(contact.equals("END"))
                break;
            contacts.add(contact);
        }
        while (true){
            String call = in.readUTF();
            if(call.equals("END"))
                break;
            calls.add(call);
        }
        NotificationHandler notificationHandler = new NotificationHandler(in,out,outVoice,inVoice);

    }

    public void call(String user) throws LineUnavailableException, IOException {
        out.writeUTF("CALL");
        out.writeUTF(user);
        out.flush();
        String reply = inVoice.readUTF();
        System.out.println(reply);
        if(reply.equals("error")){
            String error = inVoice.readUTF();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Massage");
            alert.setContentText(error);
            alert.showAndWait();
        }
        else {
            String answer = inVoice.readUTF();
            System.out.println(user + answer );
            if(answer.equals("accept")){
                System.out.println(answer);
                VoiceSender sender = new VoiceSender(outVoice);
                VoiceReciever reciever = new VoiceReciever(inVoice);
            }
        }
    }
    public class voiceChatHandeler extends Thread{
        DataInputStream in;
        DataOutputStream out;
        voiceChatHandeler(DataInputStream in , DataOutputStream out){
            this.in=in;
            this.out=out;
            start();
        }

        @Override
        public void run() {
            AudioFormat format = new AudioFormat(8000.F,16,1,true,false);
            DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speaker = null;
            try {
                speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                speaker.open(format);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            speaker.start();
            byte[] data = new byte[8000];
            while (true){
                try {
                    if(in.available() <= 0)
                        continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int readCount = 0;
                try {
                    readCount = in.read(data,0,data.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(readCount);
                if(readCount>0){
                    speaker.write(data,0,readCount);
                }
            }

        }
    }

}
