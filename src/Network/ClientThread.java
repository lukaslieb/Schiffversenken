/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.net.Socket;
import Gui.NetworkSearchHost;
import java.io.IOException;
import javax.swing.JDialog;

/**
 *
 * @author Lukas
 */
public class ClientThread extends Thread{
    private String hostname;
    private int port = 1337;
    private JDialog dialog;
    private boolean exit = false;
    private boolean connected = false;
    private Socket client;
    
    public ClientThread(String hostname, JDialog dialog){
        super();
        this.hostname = hostname;
        this.dialog = dialog;
    }
    
    public void exitClient(){
        exit = true;
        dialog.setVisible(false);
    }
    
    @Override
    public void run(){
        try{
            while(!connected && !exit){
                try{
                    Socket client = new Socket(hostname, port);
                    System.out.println("Client: Server connected");
                    connected = true;
                }
                catch (IOException e){
                    //Hostsuche
                }
            }
            if (connected){
                dialog.setVisible(false);
            }
        }
        catch(Exception ex){
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
