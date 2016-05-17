package Gui;

import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sandro
 */
public class Field {

    private JButton button;
    private int x;
    private int y;

    public Field(int x, int y) {
        this.button = new JButton();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public JButton getButton() {
        return this.button;
    }

}
