/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javax.swing.JDialog;

/**
 * Interface witch define the metods to start a network connection.
 * @author Lukas
 */
public interface INetwork {
    boolean startClient(JDialog dialog);

    boolean startServer(JDialog dialog);
    
    boolean stopClient();
    
    boolean stopServer();
    
    void setLogic(ILogicEnemy logic);
    
    /**
    * This function send wether the reciever is startplayer or not
    * @param firstPlayer = true="reciever is first player", false=reciever does not strat
    */
    void sendFirstPlayer(boolean firstPlayer);
}
