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
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author Lukas
 */
public class NetworkSearchHost extends JDialog{
    private JButton bCancel = new JButton("Abbrechen");
    private JLabel lWait = new JLabel("Verbinde zu Host");
    
    public NetworkSearchHost(JFrame owner, String title){
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
