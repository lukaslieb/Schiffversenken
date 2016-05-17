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
    boolean startClient(String hostname, JDialog dialog);

    boolean startServer(JDialog dialog);
    
    boolean stopClient();
    
    boolean stopServer();
    
    void setLogic(ILogic logic);
}
