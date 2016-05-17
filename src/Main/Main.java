/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Gui.Schiffversenken;
import Logic.Logic;
import Logic.NetworkTest4Logic;
import java.util.HashSet;

/**
 *
 * @author Lukas
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Schiffversenken spiel = new Schiffversenken();
                spiel.setVisible(true);
                Logic logic = new Logic();
                
                spiel.setLogic(logic);
                logic.setPlayField(spiel);
                
                NetworkTest4Logic Network1to2 = new NetworkTest4Logic();
                logic.SetNetworkconnection(Network1to2);
                
                //Network1to2.setIlogic(logic);

                
                Schiffversenken spiel2 = new Schiffversenken();
                spiel2.setVisible(true);
                Logic logic2 = new Logic();
                
                spiel2.setLogic(logic2);
                logic2.setPlayField(spiel2);
                
                NetworkTest4Logic Network2to1 = new NetworkTest4Logic();
                logic2.SetNetworkconnection(Network2to1);
                
                
                Network1to2.setIlogic(logic2);
                Network2to1.setIlogic(logic);
            }
        });
    }
}
