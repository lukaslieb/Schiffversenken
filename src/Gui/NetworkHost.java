/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Dialog Window with shows the opening of a Server.
 * @author Lukas
 */
public class NetworkHost extends JDialog{
    private JButton bCancel = new JButton("Abbrechen");
    private JLabel lWait = new JLabel("Warte auf Spieler");
    
    public NetworkHost(JFrame owner, String title){
        super(owner, title, true);

        add(lWait);
        add(bCancel);
        
        setLayout(new FlowLayout());
        setSize(300, 100);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    public void addActionListener(ActionListener l){
        bCancel.addActionListener(l);
    }
}
