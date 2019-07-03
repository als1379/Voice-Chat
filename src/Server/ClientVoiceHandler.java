package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ClientVoiceHandler extends Thread {
    DataInputStream in = null;
    DataOutputStream out = null;

    ClientVoiceHandler(DataOutputStream out, DataInputStream in) {
        this.in = in;
        this.out = out;
        start();
    }

    @Override
    public void run() {
        byte[] data = new byte[8000];
        try {

            while (true) {

                if (in.available() <= 0)
                    continue;

                int readCount = 0;

                readCount = in.read(data, 0, data.length);

                System.out.println(readCount);
                if (readCount > 0) {

                    out.write(data, 0, readCount);

                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
