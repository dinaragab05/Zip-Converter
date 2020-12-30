/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zipserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ahmed
 */
public class ZipServer {

    public static void main(String[] args) {

        ServerSocket server = null;
        try {
            server = new ServerSocket(500);
        } catch (IOException ex) {

        }
        try {
            //connect to client
            Socket client = server.accept();
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            //get the 'file to zip' path from client
            String ReadedFile = in.readUTF();
            File f = new File(ReadedFile);
            FileInputStream fileInputStream = new FileInputStream(f);

            //how many bytes to read in one time      
            byte[] bytes = new byte[100 * 1024];

            //send data from the server to client
            int bytesSize;
            while ((bytesSize = fileInputStream.read(bytes)) > 0) {
                // out.writeInt(bytesSize);
                out.write(bytes, 0, bytesSize);
            }
            out.close();
            in.readBoolean();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
