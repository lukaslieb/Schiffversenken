/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Gui.NetworkHost;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Lukas
 */
public class ServerThread extends Thread{
    private int port = 1337;
    private ServerSocket server;
    private NetworkHost dialog;
    
    public ServerThread(NetworkHost dialog){
        super();
        try{
        server = new ServerSocket(port);
        }
        catch(IOException ex){
            System.err.println("Error: "+ex.getMessage());
        }
        this.dialog = dialog;
    }
    
    public boolean serverExit(){
        try{
            server.close();
            return true;
        }
        catch (Exception ex){
            System.err.println("Server Exit Error: " + ex.getMessage());
            return false;
        }
    }
    
    @Override
    public void run(){
        try{
            Socket client = server.accept();
            
            dialog.setVisible(false);
        }
        catch (Exception ex){
            System.err.println("Server Error: " + ex.getMessage());
        }
    }
}
