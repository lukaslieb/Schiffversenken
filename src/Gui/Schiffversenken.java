/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Network.ClientThread;
import Network.ServerThread;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Lukas
 */
public class Schiffversenken extends JFrame implements ActionListener{

    private JMenuBar mBar = new JMenuBar();
    private JMenu mFile = new JMenu("File");
    private JMenu mHelp = new JMenu("Help");
    private JMenu mNewLocal = new JMenu("Local");
    private JMenuItem mLocalHuman = new JMenuItem("Mensch");
    private JMenuItem mLocalComp = new JMenuItem("Computer");
    private JMenu mNewNet = new JMenu("Netzwerk");
    private JMenuItem mNetHost = new JMenuItem("Als Host");
    private JMenuItem mNetClient = new JMenuItem("Mit Host verbinden");
    private JMenuItem mExit = new JMenuItem("Exit");
    private JMenuItem mAbout = new JMenuItem("About");
    
    public Schiffversenken(){ 
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        
        mNewLocal.add(mLocalHuman);
        mNewLocal.add(mLocalComp);
        mNewNet.add(mNetHost);
        mNewNet.add(mNetClient);
        mFile.add(mNewLocal);
        mFile.add(mNewNet);
        
        mFile.addSeparator();
        
        mFile.add(mExit);
        
        mBar.add(mFile);
        
        mHelp.add(mAbout);
        mBar.add(mHelp);
        
        setJMenuBar(mBar);
        
        mExit.addActionListener(this);
        mAbout.addActionListener(this);
        mLocalHuman.addActionListener(this);
        mLocalComp.addActionListener(this);
        mNetHost.addActionListener(this);
        mNetClient.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mExit){
            System.exit(0);
        }
        if(e.getSource() == mAbout){
            JOptionPane.showMessageDialog(this, "Copyright 2015 Hochschule Luzern, Technik & Architektur");
        }
        if(e.getSource() == mLocalHuman){
            JOptionPane.showMessageDialog(this, "Not implementet right now");
        }
        if(e.getSource() == mLocalComp){
            JOptionPane.showMessageDialog(this, "Not implementet right now");
        }
        if(e.getSource() == mNetHost){
            NetworkHost dialog = new NetworkHost(this, "Warte auf Spieler");
            ServerThread server = new ServerThread(dialog);
            
            server.start();
            
            dialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    server.serverExit();
                    dialog.setVisible(false);
                }
            });
            
            dialog.setVisible(true);
        }
        if(e.getSource() == mNetClient){
            String hostname = JOptionPane.showInputDialog(null, "Host Adresse: ", "localhost");
            
            if(!hostname.equals("")){
                NetworkSearchHost dialog = new NetworkSearchHost(this, "Suche Host");
                ClientThread client = new ClientThread(hostname, dialog);
                
                client.start();
                
                dialog.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        client.exitClient();
                        dialog.setVisible(false);
                    }
                });
                
                dialog.setVisible(true);
            }
        }
    }
}
