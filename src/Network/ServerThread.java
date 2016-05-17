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
import javax.swing.JDialog;

/**
 *
 * @author Lukas
 */
public class ServerThread extends Thread{
    private int port = 1337;
    private ServerSocket server;
    private Socket socket;
    private JDialog dialog;
    private Network network;
    
    public ServerThread(JDialog dialog, Network network){
        super();
        try{
        server = new ServerSocket(port);
        }
        catch(IOException ex){
            System.err.println("Error: "+ex.getMessage());
        }
        this.dialog = dialog;
        this.network = network;
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
            socket = server.accept();
            System.out.println("Server: Client connected");
            dialog.setVisible(false);
            network.setSocket(socket);
        }
        catch (Exception ex){
            System.err.println("Server Error: " + ex.getMessage());
        }
    }
}
