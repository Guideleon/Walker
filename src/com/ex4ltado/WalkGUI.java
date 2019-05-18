package com.ex4ltado;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.movement.position.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WalkGUI extends JFrame {

    private JButton walk;
    private JButton cancel;
    private JComboBox category;
    private JComboBox locations;
    private JTextField posX;
    private JTextField posY;
    private JLabel lblPosX;
    private JLabel lblPosY;
    private JLabel lblError;


    public WalkGUI(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        setTitle(generatedString.toUpperCase());
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new WrapLayout());
        setSize(350,200);
        setLocationRelativeTo(null);

        category = new JComboBox(Category.values());
        locations = new JComboBox(BankLocation.values());
        lblPosX = new JLabel("World X");
        lblPosY = new JLabel("World Y");
        lblError = new JLabel("");
        lblError.setOpaque(true);
        lblError.setBackground(Color.RED);
        posX = new JTextField("X");
        posX.setPreferredSize(new Dimension(100,20));
        posX.setEnabled(false);
        posY = new JTextField("Y");
        posY.setPreferredSize(new Dimension(100,20));
        posY.setEnabled(false);
        walk = new JButton("Walk");
        walk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (category.getSelectedItem().equals(Category.BANKS)){
                    BankLocation bankLocation = (BankLocation) locations.getSelectedItem();
                    Walker.posToWalk = bankLocation.getPosition();
                } else {
                    try{
                        Walker.posToWalk = new Position(Integer.parseInt(posX.getText()), Integer.parseInt(posY.getText()));
                    } catch (NumberFormatException ex){
                        lblError.setText("Put numbers bro, you want ban your account?");
                    }
                }
            }
        });
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Walker.posToWalk = null;
            }
        });

        category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (category.getSelectedItem().equals(Category.POSITION)){
                    locations.setEnabled(false);
                    posX.setEnabled(true);
                    posY.setEnabled(true);
                } else {
                    lblError.setText("");
                    locations.setEnabled(true);
                    posX.setEnabled(false);
                    posY.setEnabled(false);
                }
            }
        });
        add(category);
        add(locations);
        add(lblPosX);
        add(posX);
        add(lblPosY);
        add(posY);
        add(walk);
        add(cancel);
        add(lblError);
        setVisible(true);
    }
}
