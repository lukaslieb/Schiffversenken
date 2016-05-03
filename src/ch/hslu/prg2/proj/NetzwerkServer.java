/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.prg2.proj;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Lukas
 */
public class NetzwerkServer implements Runnable{
    private Socket client;
    
    public NetzwerkServer(Socket c){
         client = c;
    }

    @Override
    public void run() {
        try (OutputStream out = client.getOutputStream();
                InputStream in = client.getInputStream()){
            int data = in.read();
            while (data != -1){
                out.write(data);
                data = in.read();
            }
            out.flush();
        }
        catch(IOException ex){
            System.err.println("Error: "+ex.getMessage());
        }
    }
}
