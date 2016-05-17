/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Datatypes.FieldStatus;
import Interface.IEnemy;
import Interface.ILogic;
import Interface.INetwork;
import java.net.Socket;
import javax.swing.JDialog;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import netscape.javascript.JSObject;

/**
 *
 * @author Lukas
 */
public class Network implements INetwork, IEnemy{
    private ILogic logic;
    private Socket socket;
    private ServerThread server;
    private ClientThread client;
    private String hostname;
    private NetworkReader reader;
    private NetworkWriter writer;
    
    public Network(){
        server = null;
        client = null;
        logic = null;
        hostname = "localhost";
    }
    
    @Override
    public boolean startClient(String hosthame, JDialog dialog) {
        this.hostname = hostname;
        try{
            client = new ClientThread(hostname, dialog, this);
            client.start();
            return true;
        }
        catch(Exception ex){
            System.err.println("Error: "+ ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean startServer(JDialog dialog) {
        try{
            server = new ServerThread(dialog, this);
            server.start();
            return true;
        }
        catch(Exception ex){
            System.err.println("Error: "+ ex.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean stopServer() {
        return server.serverExit();
    }
    
    @Override
    public boolean stopClient(){
        try{
            client.exitClient();
            return true;
        }
        catch(Exception ex){
            System.err.println("Error: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public void setLogic(ILogic logic) {
        this.logic = logic;
    }
    
    public void setSocket(Socket socket){
        this.socket = socket;
        startReaderWriter();
    }
    
    private void startReaderWriter(){
        reader = new NetworkReader(socket);
        writer = new NetworkWriter(socket);
        new Thread(reader).start(); 
        
        //TestBlock
        if(client != null){
            System.out.println("send message");
            comWithEnemy("PRG2 Project");
        }
    }

    @Override
    public void sendMoveToEnemy(int x, int y) {
        String msg = "X:"+x+",Y:"+y;
        writer.sendMessage(msg);
    }

    @Override
    public void comWithEnemy(String msg) {
        writer.sendMessage(msg);
    }
    
    public void reciveMessage(String ans){
        System.out.println(ans);
    }
}
