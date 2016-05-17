/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Gui.Schiffversenken;
import Logic.Logic;
import Network.Network;

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
            }
        });
    }
}
