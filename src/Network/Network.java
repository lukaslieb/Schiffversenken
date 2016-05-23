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
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            hostname = discoverUDPServer();
            System.out.println("UDP Broadcast found address: "+hostname);
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
            //Start UDP Broadcast Server
            Thread discoveryThread = new Thread(DiscoveryThread.getInstance());
            discoveryThread.start();
            //start TCP Server
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
    public void sendGameWin(boolean win){
        String msg = "{ \"type\": \"3\", \"win\": \""+win+"\" }";
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
        boolean win;
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
                win = obj.getBoolean("win");
                logic.gameWin(win);
                break;
            case 4:
                System.out.println(obj.getString("message"));
                break;
            default:
                System.out.println("Wrong Message Type");
        }
    }
    
    private String discoverUDPServer(){
        DatagramSocket c;
        
        String foundIP = null;
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            c = new DatagramSocket();
            c.setBroadcast(true);

            byte[] sendData = "DISCOVER_BATTLESHIPSERVER_REQUEST".getBytes();

            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                c.send(sendPacket);
                System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } 
            catch (Exception e) {
            }

            /*// Broadcast the message over all the network interfaces
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    // Send the broadcast package!
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        c.send(sendPacket);
                    } 
                    catch (Exception e) {
                    }

                    System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }*/

            System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);

            //We have a response
            System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

            //Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.equals("DISCOVER_BATTLESHIPSERVER_RESPONSE")) {
                //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
                foundIP = receivePacket.getAddress().getHostAddress();
            }

            //Close the port!
            c.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundIP;
    }
}

