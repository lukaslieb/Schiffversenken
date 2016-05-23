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
import Logic.Logic;
import Network.Network;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

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
    private ImageIcon t1 = new ImageIcon(getClass().getResource("/Gui/titleimage.jpg"));
    private ImageIcon t2 = new ImageIcon(getClass().getResource("/Gui/titleimage2.jpg"));
    private JLabel leftTopLabel = new JLabel();
    private JLabel rightTopLabel = new JLabel();

    private boolean setShips;

    private int fieldSize;

    private ILogic logic;

    private INetwork network = null;

    public Schiffversenken() {
        super();
        this.fieldSize = Constant.fieldSize;
        this.sa = ShipAlignment.HORIZONTAL;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new GridLayout(2, 2, 10, 10));
        getContentPane().setBackground(Color.black);
        this.fieldLeft = new Field[fieldSize][fieldSize];
        this.fieldRight = new Field[fieldSize][fieldSize];
        this.setShips = true;
        this.shipNumbers = 0;

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
        leftTopPanel.add(Titel1);
        leftTopLabel.setIcon(t2);
        rightTopLabel.setIcon(t1);
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
            network.setLogic((Logic) logic);
            logic.SetNetworkconnection((Network) network);
        }
        if (e.getSource() == mNetClient) {
            //String hostname = "localhost";//JOptionPane.showInputDialog(null, "Host Adresse: ", "localhost");
            //int eingabe = JOptionPane.showConfirmDialog(null, "Suche Server...", "Suche Server...", JOptionPane.CANCEL_OPTION);

            //if (!hostname.equals("")) {
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
            network.startClient(dialog);

            dialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    network.stopClient();
                    dialog.setVisible(false);
                }
            });

            dialog.setVisible(true);
            network.setLogic((Logic) logic);
            logic.SetNetworkconnection((Network) network);
            //}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (sa == ShipAlignment.HORIZONTAL) {
                sa = ShipAlignment.VERTICAL;
                if (setShips) {
                    drawWhite();
                }
                drawVertical(e);
            } else {
                sa = ShipAlignment.HORIZONTAL;
                if (setShips) {
                    drawWhite();
                }
                drawHorizontal(e);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (setShips && ownField(e)) {
            actualShipSize = Constant.ships[shipNumbers];
            if (e.getComponent().getBackground() == Color.white) {
                switch (sa) {
                    case HORIZONTAL:
                        drawHorizontal(e);
                        break;

                    case VERTICAL:
                        drawVertical(e);
                        break;
                }
            }
        } else if (setShips && !ownField(e)) {
            if (e.getComponent().getBackground() == Color.white) {
                updateField(getClickedFieldRight(e).getX(), getClickedFieldRight(e).getY(), PlayerField.ENEMY, FieldStatus.SHOOT);
            }

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (setShips) {
            drawWhite();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int row = 0; row < fieldSize; row++) {
                for (int col = 0; col < fieldSize; col++) {
                    if (fieldLeft[col][row].getButton() == e.getComponent()) {
                        if (setShips) {
                            //JOptionPane.showMessageDialog(this, col + "-" + row + "-" + sa +"-"+Constant.ships[shipNumbers]);
                            boolean collision = logic.setShip(col, row, sa, Constant.ships[shipNumbers]);
                            if (!collision) {
                                JOptionPane.showMessageDialog(this, "Kollision beim Platzieren des Schiffes erkannt");
                            } else {
                                shipNumbers++;
                                if (shipNumbers >= Constant.ships.length) {
                                    setShips = false;
                                    //placementOptions.setVisible(false);
                                }
                            }
                        } else {
                            //logic.shoot(row, col);
                        }
                    }
                    if (fieldRight[col][row].getButton() == e.getComponent()) {
                        if (!setShips) {
                            logic.shoot(col, row);
                        }
                    }
                }

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {

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

    public void drawWhite() {
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (fieldLeft[col][row].getButton().getBackground() == Color.yellow || fieldLeft[col][row].getButton().getBackground() == Color.red) {
                    fieldLeft[col][row].getButton().setBackground(Color.white);
                    fieldLeft[col][row].setImg(fieldLeft[col][row].getButton(), FieldStatus.UNUSED);
                }
                if (fieldRight[col][row].getButton().getBackground() == Color.white) {
                    fieldRight[col][row].getButton().setBackground(Color.white);
                    fieldRight[col][row].setImg(fieldRight[col][row].getButton(), FieldStatus.UNUSED);
                }
            }
        }
    }

    public void drawHorizontal(MouseEvent e) {
        int count = 1;
        getClickedButtonLeft(e).setBackground(Color.yellow);
        getClickedFieldLeft(e).setImg(fieldLeft[getClickedFieldLeft(e).getX()][getClickedFieldLeft(e).getY()].getButton(), FieldStatus.PLACESHIP);
        for (int i = actualShipSize; i > 1; i--) {
            if (getClickedFieldLeft(e).getX() + count < fieldSize) {
                if (fieldLeft[getClickedFieldLeft(e).getX() + count][getClickedFieldLeft(e).getY()].getButton().getBackground() != Color.black) {
                    fieldLeft[getClickedFieldLeft(e).getX() + count][getClickedFieldLeft(e).getY()].getButton().setBackground(Color.yellow);
                    getClickedFieldLeft(e).setImg(fieldLeft[getClickedFieldLeft(e).getX() + count][getClickedFieldLeft(e).getY()].getButton(), FieldStatus.PLACESHIP);
                }
            }
            count++;
        }
    }

    public void drawVertical(MouseEvent e) {
        int count = 1;
        getClickedButtonLeft(e).setBackground(Color.yellow);
        getClickedFieldLeft(e).setImg(fieldLeft[getClickedFieldLeft(e).getX()][getClickedFieldLeft(e).getY()].getButton(), FieldStatus.PLACESHIP);
        for (int i = actualShipSize; i > 1; i--) {
            if (getClickedFieldLeft(e).getY() + count < fieldSize) {
                if (fieldLeft[getClickedFieldLeft(e).getX()][getClickedFieldLeft(e).getY() + count].getButton().getBackground() != Color.black) {
                    fieldLeft[getClickedFieldLeft(e).getX()][getClickedFieldLeft(e).getY() + count].getButton().setBackground(checkCollision(e, i));
                    getClickedFieldLeft(e).setImg(fieldLeft[getClickedFieldLeft(e).getX()][getClickedFieldLeft(e).getY() + count].getButton(), FieldStatus.PLACESHIP);
                }
            }
            count++;
        }
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
