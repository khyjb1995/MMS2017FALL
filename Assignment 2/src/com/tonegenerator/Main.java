package com.tonegenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JPanel panelMain;
    private JRadioButton sineWaveRadioButton;
    private JRadioButton squareWaveRadioButton;
    private JRadioButton sineXCosineRadioButton1;
    private JRadioButton cosineWaveRadioButton;
    private JRadioButton triangleWaveRadioButton;
    private JRadioButton sineXCosineRadioButton;
    private JButton generateBtn;
    private JButton playorfileBtn;
    private JScrollBar freqScroll;
    private JScrollBar amplitudeScroll;
    private JTextField freqInput;
    private JTextField amplitudeInput;
    private JLabel durationTxt;
    private JLabel freqTxt;
    private JLabel amplitudeTxt;
    private JRadioButton readRadioButton;
    private JRadioButton readFileRadioButton;
    private JTextField textField1;
    private JButton button1;
    private JPanel panelLeft;
    private JPanel panelRight;

    public Main() {
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tone Generator");
        frame.setContentPane(new Main().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}
