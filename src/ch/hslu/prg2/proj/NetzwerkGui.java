/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.prg2.proj;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Lukas
 */
public class NetzwerkGui extends JFrame implements ActionListener{
    JTextField hostname = new JTextField();
    JTextField serverport = new JTextField();
    JTextField port = new JTextField();
    JButton serverButton = new JButton("Set Own Port");
    JButton connectButton = new JButton("Connect");
    JLabel serverhostname;
    JLabel connectionStatus = new JLabel("Do nothing");
    JPanel connectionPanel = new JPanel(new GridLayout(2, 3));
    
    Netzwerk connection;
    Netzwerk server;
    
    public NetzwerkGui(){
        try{
            this.serverhostname = new JLabel(InetAddress.getLocalHost().getHostName());
        }
        catch (UnknownHostException e){
            this.serverhostname.setText("Unknown Hostname");
        }
        setLayout(new BorderLayout());
        
        /*serverport.setColumns(10);
        hostname.setColumns(10);
        port.setColumns(10);*/
        
        connectionPanel.add(serverhostname);
        connectionPanel.add(serverport);
        connectionPanel.add(serverButton);
        
        connectionPanel.add(hostname);
        connectionPanel.add(port);
        connectionPanel.add(connectButton);
        
        add(connectionPanel, BorderLayout.NORTH);
        add(connectionPanel, BorderLayout.CENTER);
        add(connectionStatus, BorderLayout. SOUTH);
        
        serverButton.addActionListener(this);
        connectButton.addActionListener(this);
    }
    
    private void createAndShowUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 150);
    }
    
    /*public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NetzwerkGui gui = new NetzwerkGui();
                gui.createAndShowUI();
            }
        });
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        String b = e.getActionCommand();
        if(b.equals("Set Own Port")){
            ServerSocket listen = null;
            try{
                listen = new ServerSocket(1337);
            }
            catch (IOException ex){
                System.out.println("Error: "+ex.getMessage());
            }
            while (true){
                try{
                    Socket client = listen.accept();
                    NetzwerkServer handler = new NetzwerkServer(client);
                    new Thread(handler).start();
                }
                catch(Exception ex){
                    System.err.println("Error: " + ex.getMessage());
                }
            }
        }
        else if(b.equals("Connect")){
            String sHostname = hostname.getText();
            String sPort = port.getText();
            if(!(sHostname.equals("") && sPort.equals(""))){
                connection = new Netzwerk(sPort, sHostname);
            }
        }
    }
}
