/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import javax.swing.JOptionPane;
import Datatypes.Constant;
import Datatypes.FieldStatus;
import Datatypes.PlayerField;
import Interface.IEnemy;
import Interface.INetwork;
import Interface.IPlayingField;
import Network.ClientThread;
import Network.Network;
import Network.ServerThread;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Lukas
 */
public class Schiffversenken extends JFrame implements ActionListener, MouseListener, IPlayingField {

    private JMenuBar mBar = new JMenuBar();
    private JMenu mFile = new JMenu("File");
    private JMenu mHelp = new JMenu("Help");
    private JMenuItem mNewLocal = new JMenuItem("Local");
    private JMenu mNewNet = new JMenu("Netzwerk");
    private JMenuItem mNetHost = new JMenuItem("Als Host");
    private JMenuItem mNetClient = new JMenuItem("Mit Host verbinden");
    private JMenuItem mExit = new JMenuItem("Exit");
    private JMenuItem mAbout = new JMenuItem("About");

    private JLabel player1 = new JLabel();
    private JLabel player2 = new JLabel();
    private JLabel Titel1 = new JLabel();
    private JLabel Titel2 = new JLabel();
    private JPanel leftTopPanel = new JPanel();
    private JPanel rightTopPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private Field[][] fieldLeft;
    private Field[][] fieldRight;

    private int fieldSize = Constant.fieldSize;

    private INetwork network = null;

    public Schiffversenken() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new GridLayout(2, 2));
        this.fieldLeft = new Field[fieldSize][fieldSize];
        this.fieldRight = new Field[fieldSize][fieldSize];

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
        mNewLocal.addActionListener(this);
        mNetHost.addActionListener(this);
        mNetClient.addActionListener(this);

        add(leftTopPanel);
        add(rightTopPanel);
        add(leftPanel);
        add(rightPanel);

        leftPanel.setLayout(new GridLayout(fieldSize, fieldSize, 0, 0));
        rightPanel.setLayout(new GridLayout(fieldSize, fieldSize, 0, 0));
        leftTopPanel.setLayout(new BorderLayout());
        rightTopPanel.setLayout(new BorderLayout());

        leftTopPanel.add(Titel1, BorderLayout.NORTH);
        Titel1.setText("SCHIFFE");
        Titel1.setFont(Titel1.getFont().deriveFont(40.0f));
        Titel1.setHorizontalAlignment(JLabel.RIGHT);

        rightTopPanel.add(Titel2, BorderLayout.NORTH);
        Titel2.setText("VERSENKEN");
        Titel2.setFont(Titel1.getFont().deriveFont(40.0f));
        Titel2.setHorizontalAlignment(JLabel.LEFT);

        leftTopPanel.add(player1, BorderLayout.SOUTH);
        player1.setText("Player 1");
        player1.setFont(Titel1.getFont().deriveFont(25.0f));
        player1.setHorizontalAlignment(JLabel.CENTER);

        rightTopPanel.add(player2, BorderLayout.SOUTH);
        player2.setText("Player 2");
        player2.setFont(Titel1.getFont().deriveFont(25.0f));
        player2.setHorizontalAlignment(JLabel.CENTER);

        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                fieldLeft[col][row] = new Field(col, row);
                fieldLeft[col][row].getButton().addMouseListener(this);
                fieldLeft[col][row].getButton().setBackground(Color.white);
                leftPanel.add(fieldLeft[col][row].getButton());
            }
        }

        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                fieldRight[col][row] = new Field(col, row);
                fieldRight[col][row].getButton().addMouseListener(this);
                fieldRight[col][row].getButton().setBackground(Color.blue);
                rightPanel.add(fieldRight[col][row].getButton());
            }
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mExit) {
            System.exit(0);
        }
        if (e.getSource() == mAbout) {
            JOptionPane.showMessageDialog(this, "Copyright 2015 Hochschule Luzern, Technik & Architektur");
        }
        if (e.getSource() == mNewLocal) {
            JOptionPane.showMessageDialog(this, "Not implementet right now");
        }
        if (e.getSource() == mNetHost) {
            NetworkHost dialog = new NetworkHost(this, "Warte auf Spieler");
            /*ServerThread server = new ServerThread(dialog);

            server.start();

            dialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    server.serverExit();
                    dialog.setVisible(false);
                }
            });
             */
            network = new Network();

            network.startServer(dialog);

            dialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    network.stopServer();
                    dialog.setVisible(false);
                }
            });

            dialog.setVisible(true);
        }
        if (e.getSource() == mNetClient) {
            String hostname = JOptionPane.showInputDialog(null, "Host Adresse: ", "localhost");

            if (!hostname.equals("")) {
                NetworkSearchHost dialog = new NetworkSearchHost(this, "Suche Host");
                /*ClientThread client = new ClientThread(hostname, dialog);

                client.start();

                dialog.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        client.exitClient();
                        dialog.setVisible(false);
                    }
                });*/

                network = new Network();
                network.startClient(hostname, dialog);

                dialog.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        network.stopClient();
                        dialog.setVisible(false);
                    }
                });

                dialog.setVisible(true);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (fieldLeft[col][row].getButton() == e.getComponent()) {
                    System.out.println("You clicked on the left Field: X=" + fieldLeft[col][row].getX() + " Y=" + fieldLeft[col][row].getY());
                    fieldLeft[col][row].getButton().setBackground(Color.black);
                }
                if (fieldRight[col][row].getButton() == e.getComponent()) {
                    System.out.println("You clicked on the right Field: X=" + fieldRight[col][row].getX() + " Y=" + fieldRight[col][row].getY());
                    fieldRight[col][row].getButton().setBackground(Color.red);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void updateField(int x, int y, PlayerField field, FieldStatus status) {

        switch (field) {
            case ENEMY:
                this.fieldRight[x][y].getButton().setBackground(status.getColor());
                break;

            case OWN:
                this.fieldLeft[x][y].getButton().setBackground(status.getColor());
                break;

            default:

                break;
        }
    }

    @Override
    public void gameOver(boolean won) {
        if (won) {
            JOptionPane.showMessageDialog(null, "YOU WON!");
        } else {
            JOptionPane.showMessageDialog(null, "YOU LOST!");
        }
    }
}
