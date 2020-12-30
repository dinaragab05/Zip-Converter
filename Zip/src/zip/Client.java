/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Ahmed
 */
public class Client extends Thread {

    public int unit = 0;//array length for one time 
    public long recieve = 0;
    public long total = 0;
    String source;
    String destination;
    Progress frm;
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String source, String destination, Progress frm) {
        this.source = source;
        this.destination = destination;
        this.frm = frm;
        this.setPriority(MAX_PRIORITY);
    }

    public void connect() {
        try {
            client = new Socket("localhost", 500);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            System.out.println("cannot connect to the server +++" + ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            ZipFile(source, destination);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ZipFile(String source, String destination) throws IOException {
        //format destination path
        destination = (destination.substring(0, destination.indexOf(".")) + ".zip");

        FileOutputStream fileOutputStream = new FileOutputStream(destination);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        File f = new File(source);
        ZipEntry zipEntry = new ZipEntry(f.getName());
        zipOutputStream.putNextEntry(zipEntry);

        try {
            //connect to server
            connect();
            //send source file to server 
            out.writeUTF(source);

            byte[] bytes = new byte[100 * 1024];

            total = f.length(); //file total lenght

            frm.runprogress(this, frm);//open progress form

            //write zipped file
            while ((unit = in.read(bytes)) > 0) {
                recieve += unit;
                zipOutputStream.write(bytes, 0, unit);

            }

            out.writeBoolean(true);

            client.close();

        } catch (IOException ex) {
            System.out.println("server disconected +++" + ex.getMessage());
        } finally {

            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileOutputStream.close();

        }

    }

}
