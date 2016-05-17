/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Lukas
 */
public class NetworkReader implements Runnable{
    private Socket socket;
    private BufferedReader inStream;
    private String line;
    private Network network;
    
    public NetworkReader(Socket socket, Network network){
        this.socket = socket;
        this.network = network;
    }
    
    @Override
    public void run() {
        try{
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((line = inStream.readLine()) != null){
                //System.out.println(line);
                network.reciveMessage(line);
            } 
        }
        catch(IOException ex){
            System.err.println("NetworkReader Error: " + ex.getMessage());
        }
    }
    
}
