/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Interface.IEnemy;
import Interface.ILogicEnemy;
import Interface.INetwork;
import java.net.Socket;
import javax.swing.JDialog;
import org.json.JSONObject;
import Datatypes.FieldStatus;

/**
 *
 * @author Lukas
 */
public class Network implements INetwork, IEnemy{
    private ILogicEnemy logic;
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
    public boolean startClient(String hostname, JDialog dialog) {
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
    public void setLogic(ILogicEnemy logic) {
        this.logic = logic;
    }
    
    public void setSocket(Socket socket){
        this.socket = socket;
        startReaderWriter();
    }
    
    private void startReaderWriter(){
        reader = new NetworkReader(socket, this);
        writer = new NetworkWriter(socket);
        new Thread(reader).start(); 
        
        //TestBlock
        /*if(client != null){
            System.out.println("send message");
            //comWithEnemy("PRG2 Project");
            sendMoveToEnemy(5, 7);
        }*/
    }

    @Override
    public void sendMoveToEnemy(int x, int y) {
        //message 1: sendMoveToEnemy
        //message 2: sendFieldStatus
        //message 3: sendGameOver
        //message 4: sendMessage
        String msg = "{ \"type\": \"1\", \"x\": \""+x+"\",\"y\": \""+y+"\" }";
        writer.sendMessage(msg);
    }

    @Override
    public void comWithEnemy(String message) {
        String msg = "{ \"type\": \"4\", \"message\": \""+message+"\" }";
        writer.sendMessage(msg);
    }
    
    public void reciveMessage(String ans){
        System.out.println(ans);
        JSONObject obj = new JSONObject(ans);
        int x;
        int y;
        String msg;
        FieldStatus status;
        switch(obj.getInt("type")){
            case 1:
                x = obj.getInt("x");
                y = obj.getInt("y");
                System.out.println(x+", "+y);
                status = logic.shootFromEnemy(x, y);
                msg = "{ \"type\": \"2\", \"x\": \""+x+"\",\"y\": \""+y+"\",\"status\": \""+status.name()+"\" }";
                writer.sendMessage(msg);
                break;
            case 2:
                x = obj.getInt("x");
                y = obj.getInt("y");
                status = FieldStatus.getEnumState(obj.getString("status"));
                logic.shootReply(x, y, status);
                break;
            case 3:
                //TODO sendGameOver
                break;
            case 4:
                System.out.println(obj.getString("message"));
                break;
            default:
                System.out.println("Wrong Message Type");
        }
        /*TODO
        shootFromEnemy() call with a send back to the other player (fieldstatus)
        shootReply() call with recived fieldstatus
        */
    }
}
