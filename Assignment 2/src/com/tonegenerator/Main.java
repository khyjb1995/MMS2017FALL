package com.tonegenerator;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Date;

public class Main {
    private JPanel panelMain;
    private JRadioButton sineWaveRadioButton;
    private JRadioButton squareWaveRadioButton;
    private JRadioButton sineXCosineRadioButton1;
    private JRadioButton cosineWaveRadioButton;
    private JRadioButton triangleWaveRadioButton;
    private JRadioButton otherRadioButton;
    private JButton generateBtn;
    private JButton playorfileBtn;
    private JScrollBar freqScroll;
    private JScrollBar amplitudeScroll;
    private JTextField freqInput;
    private JTextField amplitudeInput;
    private JLabel durationTxt;
    private JLabel freqTxt;
    private JLabel amplitudeTxt;
    private JRadioButton bassoRadioButton;
    private JRadioButton bothRadioButton;
    private JTextField readTxt;
    private JButton outputButton;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JRadioButton altoRadioButton;
    private JRadioButton readFileRadioButton;
    private JTextField fileNameTxt;

    AudioFormat audioFormat;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;

    float sampleRate = 16000.0F;
    int sampleSizeInBits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;
    byte audioData[];
    int song[];
    static final int[][] freqs = {
            {16, 17, 18, 20, 21, 22, 23, 25, 26, 28, 29, 31}, //Level 0
            {33, 35, 37, 39, 41, 44, 46, 49, 52, 55, 58, 62}, //Level 1
            {65, 69, 73, 78, 82, 87, 93, 98, 104, 110, 117, 124}, //Level 2
            {131, 139, 147, 156, 165, 175, 185, 196, 208, 220, 233, 247}, //Level 3
            {262, 278, 294, 311, 330, 349, 370, 392, 415, 440, 466, 494}, //Level 4
            {523, 554, 587, 622, 659, 699, 740, 784, 831, 880, 932, 988}, //Level 5
            {1047, 1109, 1175, 1245, 1319, 1397, 1475, 1568, 1661, 1760, 1865, 1976}, //Level 6
            {2093, 2218, 2349, 2489, 2637, 2794, 2960, 3136, 3322, 3520, 3729, 3951}, //Level 7
            {4186, 4435, 4699, 4978, 5274, 5588, 5920, 6272, 6645, 7040, 7459, 7902} //Level 8
    };


    public Main() {
        final ButtonGroup synButtonGroup = new ButtonGroup();
        synButtonGroup.add(sineWaveRadioButton);
        synButtonGroup.add(cosineWaveRadioButton);
        synButtonGroup.add(triangleWaveRadioButton);
        synButtonGroup.add(squareWaveRadioButton);
        synButtonGroup.add(sineXCosineRadioButton1);
        synButtonGroup.add(otherRadioButton);
        synButtonGroup.add(bassoRadioButton);
        synButtonGroup.add(bothRadioButton);
        final ButtonGroup crtButtonGroup = new ButtonGroup();
        crtButtonGroup.add(altoRadioButton);
        crtButtonGroup.add(bassoRadioButton);
        crtButtonGroup.add(bothRadioButton);
        crtButtonGroup.add(readFileRadioButton);
        freqInput.setText(String.valueOf(freqScroll.getValue()));

        playorfileBtn.setEnabled(false);
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playorfileBtn.setEnabled(false);
                if (sineWaveRadioButton.isSelected() || cosineWaveRadioButton.isSelected()
                        || triangleWaveRadioButton.isSelected() || squareWaveRadioButton.isSelected()) {
                    audioData = new byte[16000 * 2];
                    new SynGen().getSyntheticData(audioData);
                } else if (bassoRadioButton.isSelected() || altoRadioButton.isSelected() || bothRadioButton.isSelected()) {
                    String textGet = readTxt.getText();
                    int txtCount = 0;
                    int level = 4;
                    for (int i = 0; i < textGet.length(); i++) {
                        if (textGet.charAt(i) != '^') txtCount++;
                        //fileNameTxt.setText(fileNameTxt.getText() + textGet.charAt(i));
                    }
                    //fileNameTxt.setText(String.valueOf(txtCount));
                    song = new int[txtCount];
                    boolean flag = false;
                    for (int i = 0, j = 0; i < textGet.length(); i++) {
                        switch (textGet.charAt(i)) {
                            case '1':
                                if (flag) {
                                    song[j] = freqs[level][1];
                                    flag = false;
                                    j++;
                                } else {
                                    song[j] = freqs[level][0];
                                    j++;
                                }
                                break;
                            case '2':
                                if (flag) {
                                    song[j] = freqs[level][3];
                                    flag = false;
                                    j++;
                                } else {
                                    song[j] = freqs[level][2];
                                    j++;
                                }
                                break;
                            case '3':
                                song[j] = freqs[level][4];
                                j++;
                                break;
                            case '4':
                                if (flag) {
                                    song[j] = freqs[level][6];
                                    flag = false;
                                    j++;
                                } else {
                                    song[j] = freqs[level][5];
                                    j++;
                                }
                                break;
                            case '5':
                                if (flag) {
                                    song[j] = freqs[level][8];
                                    flag = false;
                                    j++;
                                } else {
                                    song[j] = freqs[level][7];
                                    j++;
                                }
                                break;
                            case '6':
                                if (flag) {
                                    song[j] = freqs[level][10];
                                    flag = false;
                                    j++;
                                } else {
                                    song[j] = freqs[level][9];
                                    j++;
                                }
                                break;
                            case '7':
                                song[j] = freqs[level][11];
                                j++;
                                break;
                            case '^':
                                flag = true;
                                break;
                            case '-':
                                song[j] = 1;
                                j++;
                                break;
                            case '_':
                                song[j] = 0;
                                j++;
                                break;
                            default:
                                durationTxt.setText("Back");
                                break;
                        }
                    }
                    audioData = new byte[16000 * txtCount];
                    new SynGen().getSyntheticData(audioData);
                    //durationTxt.setText("backsyn");
                }

                playorfileBtn.setEnabled(true);
            }
        });
        playorfileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playorfiledata();
            }
        });
        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputdata();
            }
        });
        freqScroll.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                freqInput.setText(String.valueOf(freqScroll.getValue()));
            }
        });
        freqInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                freqScroll.setValue(Integer.parseInt(freqInput.getText()));
            }
        });
    }

    private void playorfiledata() {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
            audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat,
                    audioData.length / audioFormat.getFrameSize());
            DataLine.Info dataLineInfo =
                    new DataLine.Info(
                            SourceDataLine.class,
                            audioFormat);
            sourceDataLine = (SourceDataLine)
                    AudioSystem.getLine(
                            dataLineInfo);
            new ListenThread().start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void outputdata() {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
            audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat,
                    audioData.length / audioFormat.getFrameSize());
            DataLine.Info dataLineInfo =
                    new DataLine.Info(
                            SourceDataLine.class,
                            audioFormat);
            sourceDataLine = (SourceDataLine)
                    AudioSystem.getLine(
                            dataLineInfo);
            generateBtn.setEnabled(false);
            playorfileBtn.setEnabled(false);
            try {
                AudioSystem.write(
                        audioInputStream,
                        AudioFileFormat.Type.WAVE,
                        new File(fileNameTxt.getText() + ".wav"));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
            generateBtn.setEnabled(true);
            playorfileBtn.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tone Generator");
        frame.setContentPane(new Main().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        new Main();
    }

    class ListenThread extends Thread {
        byte playBuffer[] = new byte[16384];

        public void run() {
            try {
                generateBtn.setEnabled(false);
                playorfileBtn.setEnabled(false);

                sourceDataLine.open(audioFormat);
                sourceDataLine.start();

                int cnt;
                long startTime = new Date().getTime();
                while ((cnt = audioInputStream.read(
                        playBuffer, 0,
                        playBuffer.length))
                        != -1) {
                    if (cnt > 0) {
                        sourceDataLine.write(playBuffer, 0, cnt);
                    }
                }
                sourceDataLine.drain();

                int elapsedTime = (int) (new Date().getTime() - startTime);
                durationTxt.setText("" + elapsedTime);

                sourceDataLine.stop();
                sourceDataLine.close();

                generateBtn.setEnabled(true);
                playorfileBtn.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    class SynGen {
        ByteBuffer byteBuffer;
        ShortBuffer shortBuffer;
        int byteLength;

        void getSyntheticData(byte[] synDataBuffer) {
            byteBuffer = ByteBuffer.wrap(synDataBuffer);
            shortBuffer = byteBuffer.asShortBuffer();
            byteLength = synDataBuffer.length;
            if (sineWaveRadioButton.isSelected()) sinetones();
            else if (cosineWaveRadioButton.isSelected()) cosinetones();
            else if (bassoRadioButton.isSelected()) bassotones();
        }

        void sinetones() {
            channels = 1;
            int bytesPerSamp = 2;
            sampleRate = 16000.0F;
            double freq = freqScroll.getValue();
            int sampLength = byteLength / bytesPerSamp;
            for (int cnt = 0; cnt < sampLength; cnt++) {
                double time = cnt / sampleRate;
                double sinValue = (Math.sin(2 * Math.PI * freq * time));
                shortBuffer.put((short) (16000 * sinValue));
            }
        }

        void cosinetones() {
            channels = 1;
            int bytesPerSamp = 2;
            sampleRate = 16000.0F;
            double freq = freqScroll.getValue();
            int sampLength = byteLength / bytesPerSamp;
            for (int cnt = 0; cnt < sampLength; cnt++) {
                double time = cnt / sampleRate;
                double sinValue = (Math.cos(2 * Math.PI * freq * time));
                shortBuffer.put((short) (16000 * sinValue));
            }
        }

        void bassotones() {
            channels = 1;
            int bytesPerSamp = 2;
            int n = -1;
            double sinValue = 0;
            sampleRate = 16000.0F;
            //double freq = song[0];
            int sampLength = byteLength / bytesPerSamp;
            durationTxt.setText(String.valueOf(sampLength));

            for(int cnt = 0; cnt < sampLength; cnt++){
                if(cnt % 8000 == 0) n++;
                double time = cnt/sampleRate;
                if(cnt % 8000 < 7500){
                    sinValue = (Math.sin(2*Math.PI*song[n]*time));
                }
                else {

                        if((n+1 != sampLength/8000)){
                            if(song[n+1] == 0 ){
                                sinValue = (Math.sin(2*Math.PI*song[n]*time));
                            }
                            else {
                                sinValue = (Math.sin(2*Math.PI*1*time));
                            }
                        }
                        else {
                            sinValue = (Math.sin(2*Math.PI*1*time));
                        }

                }
                shortBuffer.put((short)(16000*sinValue));
            }
            /*
            for(int cnt = 0; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue = (Math.sin(2*Math.PI*freq*time));
                shortBuffer.put((short)(16000*sinValue));
            }
            */
        }
    }
}
