/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Lukas
 */
public class NetworkWriter{
    private Socket socket;
    private PrintWriter outStream;
    
    public NetworkWriter(Socket socket){
        this.socket = socket;
        try{
            outStream = new PrintWriter(socket.getOutputStream()); 
        }
        catch(IOException ex){
            System.err.println("NetworkWriter Error: " + ex.getMessage());
        }
    }
    
    public void sendMessage(String msg){
        outStream.println(msg);
        outStream.flush(); 
    }
    
}
