/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.net.Socket;

/**
 *
 * @author Lukas
 */
public class NetworkWriter implements Runnable{
    private Socket socket;
    
    public NetworkWriter(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
