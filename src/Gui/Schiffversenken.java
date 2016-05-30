/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Datatypes.Constant;
import Datatypes.FieldStatus;
import Datatypes.PlayerField;
import Datatypes.ShipAlignment;
import Interface.ILogic;
import Interface.INetwork;
import Interface.IPlayingField;
import Logic.FirstPlayer;
import Logic.Logic;
import Network.Network;
import java.applet.Applet;
import java.awt.BorderLayout;
import Logic.KI;
import Logic.Logic;
import Network.Network;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.applet.AudioClip;
import java.io.InputStream;
import java.net.URL;


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
    private JPanel placementOptions = new JPanel();
    private JLabel shipSize = new JLabel();
    private JLabel shipCount = new JLabel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private Field[][] fieldLeft;
    private Field[][] fieldRight;
    private int shipNumbers;
    private ShipAlignment sa;
    private int actualShipSize;
    private ImageIcon t1 = new ImageIcon(getClass().getResource("/Gui/Picture/titleimage.jpg"));
    private ImageIcon t2 = new ImageIcon(getClass().getResource("/Gui/Picture/titleimage2.jpg"));
    private ImageIcon t1MyTurn = new ImageIcon(getClass().getResource("/Gui/Picture/titleimageMyTurn.jpg"));
    private ImageIcon t2EnemyTurn = new ImageIcon(getClass().getResource("/Gui/Picture/titleimage2EnemyTurn.jpg"));
    private JLabel leftTopLabel = new JLabel();
    private JLabel rightTopLabel = new JLabel();
    private boolean holdLeftMouse;
    private boolean preview;

    private AudioClip placeShip;
    private static AudioClip hit;
    private static AudioClip water;

    private boolean setShips;

    private int fieldSize;

    private ILogic logic;

    private INetwork network = null;
    
    private KI ki;

    public Schiffversenken() {
        super();
        this.fieldSize = Constant.fieldSize;
        this.sa = ShipAlignment.HORIZONTAL;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(795, 795);
        setResizable(false);
        setLayout(new GridLayout(2, 2, 10, 10));
        getContentPane().setBackground(Color.black);
        this.fieldLeft = new Field[fieldSize][fieldSize];
        this.fieldRight = new Field[fieldSize][fieldSize];
        this.setShips = true;
        this.shipNumbers = 0;
        holdLeftMouse = false;
        preview = false;

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

        leftPanel.setBackground(Color.black);
        rightPanel.setBackground(Color.black);
        rightTopPanel.setBackground(Color.black);
        leftTopPanel.setBackground(Color.black);
        //leftTopPanel.add(Titel1);
        leftTopLabel.setIcon(t1);
        rightTopLabel.setIcon(t2);
        leftTopPanel.add(leftTopLabel);
        rightTopPanel.add(rightTopLabel);

        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                fieldLeft[col][row] = new Field(col, row);
                fieldLeft[col][row].getButton().addMouseListener(this);
                updateField(col, row, PlayerField.OWN, FieldStatus.UNUSED);
                leftPanel.add(fieldLeft[col][row].getButton());
            }
        }

        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                fieldRight[col][row] = new Field(col, row);
                fieldRight[col][row].getButton().addMouseListener(this);
                updateField(col, row, PlayerField.ENEMY, FieldStatus.UNUSED);
                rightPanel.add(fieldRight[col][row].getButton());
            }
        }
        setVisible(true);
    }

    public void soundPlaceShip() {
        URL urlLine = getClass().getResource("/Gui/Music/placeShip.wav");
        placeShip = Applet.newAudioClip(urlLine);
        placeShip.play();
    }

    public static void soundHit() {
        URL urlLine = Schiffversenken.class.getResource("/Gui/Music/hit.wav");
        hit = Applet.newAudioClip(urlLine);
        hit.play();
    }

    public static void soundWater() {
        URL urlLine = Schiffversenken.class.getResource("/Gui/Music/water.wav");
        water = Applet.newAudioClip(urlLine);
        water.play();
    }

    public void setLogic(ILogic logic) {
        this.logic = logic;
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
            ki = new KI();
            logic.SetNetworkconnection(ki);
            ki.setLogic((Logic)logic);
            
        }
        if (e.getSource() == mNetHost) {
            NetworkHost dialog = new NetworkHost(this, "Warte auf Spieler");

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
            network.setLogic((Logic) logic);
            logic.SetNetworkconnection((Network) network);

            network.getServerStatus(); //wait until the network is connected
            //define starting Player (true = local, false = enemy)
            boolean firstPlayer = FirstPlayer.getFirstPlayer();//this function returns true if the local player starts
            network.sendFirstPlayer(!firstPlayer);
            logic.setFirstPlayer(firstPlayer);
        }
        if (e.getSource() == mNetClient) {

            NetworkSearchHost dialog = new NetworkSearchHost(this, "Suche Host");

            network = new Network();
            network.startClient(dialog);

            dialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    network.stopClient();
                    dialog.setVisible(false);
                }
            });

            network.setLogic((Logic) logic);
            logic.SetNetworkconnection((Network) network);

            dialog.setVisible(true);

            network.getServerStatus();
            dialog.setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        boolean myField = ownField(e);
        if (myField) {
            int xl = getClickedFieldLeft(e).getX();
            int yl = getClickedFieldLeft(e).getY();

            if (e.getButton() == MouseEvent.BUTTON3) { //rechte maustaste im eigenen Feld
                sa = ShipAlignment.invertAlignment(sa);
                actualShipSize = Constant.ships[shipNumbers];
                logic.setPreviewSip(xl, yl, sa, actualShipSize);
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        boolean myField = ownField(e);
        if (myField) {
            int xl = getClickedFieldLeft(e).getX();
            int yl = getClickedFieldLeft(e).getY();

            if (setShips && myField && holdLeftMouse == false) {
                actualShipSize = Constant.ships[shipNumbers];
                logic.setPreviewSip(xl, yl, sa, actualShipSize);
            }
        }

        if (!setShips && !myField && e.getComponent().getBackground() == Color.white) {
            getClickedFieldRight(e).setImg(getClickedFieldRight(e).getButton(), FieldStatus.SHOOT);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!setShips && !ownField(e) && e.getComponent().getBackground() == Color.white) {
            getClickedFieldRight(e).setImg(getClickedFieldRight(e).getButton(), FieldStatus.UNUSED);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        holdLeftMouse = false;
        boolean myField = ownField(e);
        if (myField) {
            int xl = getClickedFieldLeft(e).getX();
            int yl = getClickedFieldLeft(e).getY();

            if (e.getButton() == MouseEvent.BUTTON1 && setShips) {
                boolean nocollision = logic.setShip(xl, yl, sa, Constant.ships[shipNumbers]);
                if (nocollision) { //wenn keine kollision besteht
                    shipNumbers++;
                    this.soundPlaceShip();
                    if (shipNumbers >= Constant.ships.length) {
                        setShips = false;
                    }
                }
            }
        }
        else if(!myField && !setShips){
            int xr = getClickedFieldRight(e).getX();
            int yr = getClickedFieldRight(e).getY();
            
            if(e.getButton() == MouseEvent.BUTTON1 && e.getComponent().getBackground() == Color.white){
                logic.shoot(xr, yr);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
        if (e.getButton() == MouseEvent.BUTTON1 && ownField(e) && setShips) {
            holdLeftMouse = true;
        }
    }

    @Override
    public void myTurn(boolean myTurn
    ) {
        if (myTurn) {
            leftTopLabel.setIcon(t1MyTurn);
            rightTopLabel.setIcon(t2);

        } else {
            leftTopLabel.setIcon(t1);
            rightTopLabel.setIcon(t2EnemyTurn);
        }
    }

    @Override
    public void updateField(int x, int y, PlayerField field, FieldStatus status
    ) {

        switch (field) {
            case ENEMY:
                this.fieldRight[x][y].getButton().setBackground(status.getColor());
                this.fieldRight[x][y].setImg(this.fieldRight[x][y].getButton(), status);
                break;

            case OWN:
                this.fieldLeft[x][y].getButton().setBackground(status.getColor());
                this.fieldLeft[x][y].setImg(this.fieldLeft[x][y].getButton(), status);
                break;

            default:

                break;
        }
    }

    @Override
    public void gameOver(boolean won
    ) {
        if (won) {
            JOptionPane.showMessageDialog(null, "YOU WON!");
        } else {
            JOptionPane.showMessageDialog(null, "YOU LOST!");
        }
    }

    public JButton getClickedButtonLeft(MouseEvent e) {
        JButton actualButton = new JButton();
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (fieldLeft[col][row].getButton() == e.getComponent()) {
                    actualButton = fieldLeft[col][row].getButton();
                }
            }
        }
        return actualButton;
    }

    public JButton getClickedButtonRight(MouseEvent e) {
        JButton actualButton = new JButton();
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (fieldRight[col][row].getButton() == e.getComponent()) {
                    actualButton = fieldRight[col][row].getButton();
                }
            }
        }
        return actualButton;
    }

    public Field getClickedFieldRight(MouseEvent e) {
        Field actualField = new Field();
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (fieldRight[col][row].getButton() == e.getComponent()) {
                    actualField = fieldRight[col][row];
                }
            }
        }
        return actualField;
    }

    public Field getClickedFieldLeft(MouseEvent e) {
        Field actualField = null;
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (fieldLeft[col][row].getButton() == e.getComponent()) {
                    actualField = fieldLeft[col][row];
                }
            }
        }
        return actualField;
    }

    public boolean ownField(MouseEvent e) {
        boolean ownField = true;
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (fieldRight[col][row].getButton() == e.getComponent()) {
                    ownField = false;
                }
            }
        }
        return ownField;
    }

    public Color checkCollision(MouseEvent e, int size) {
        Color c;
        //boolean checkCollision = logic.setShip(getClickedFieldLeft(e).getX(), getClickedFieldLeft(e).getY(), sa, size);
        boolean checkCollision = true;
        //System.out.println(checkCollision);
        if (!checkCollision) {
            c = Color.red;
        } else {
            c = Color.yellow;
        }
        return c;
    }
}
