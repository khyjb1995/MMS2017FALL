package com.tonegenerator;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

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
    private JTextField readTxt;
    private JButton outputButton;
    private JPanel panelLeft;
    private JPanel panelRight;

    AudioFormat audioFormat;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;

    float sampleRate = 16000.0F;
    int sampleSizeInBits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;
    byte audioData[] = new byte[16000*4];

    public Main() {
        playorfileBtn.setEnabled(false);
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playorfileBtn.setEnabled(false);
                //new SynGen().getSyntheticData(audioData);
                playorfileBtn.setEnabled(true);
            }
        });
        playorfileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playorfiledata();
            }
        });
    }

    private void playorfiledata() {
        try{
            InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);

            audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
            audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat,
                    audioData.length/audioFormat.getFrameSize());

            DataLine.Info dataLineInfo =
                    new DataLine.Info(
                            SourceDataLine.class,
                            audioFormat);

            sourceDataLine = (SourceDataLine)
                    AudioSystem.getLine(
                            dataLineInfo);

            if(readRadioButton.isSelected()){
                new ListenThread().start();
            }else{
                generateBtn.setEnabled(false);
                playorfileBtn.setEnabled(false);

                try{
                    AudioSystem.write(
                            audioInputStream,
                            AudioFileFormat.Type.WAVE,
                            new File(readTxt.getText() + ".wav"));
                }catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                generateBtn.setEnabled(true);
                playorfileBtn.setEnabled(true);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tone Generator");
        frame.setContentPane(new Main().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        new Main();
    }

}
