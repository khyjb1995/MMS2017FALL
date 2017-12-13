package com.tonegenerator;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    byte audioData[] = new byte[16000*14];

    public Main() {
        final ButtonGroup synButtonGroup = new ButtonGroup();
        synButtonGroup.add(sineWaveRadioButton);
        synButtonGroup.add(cosineWaveRadioButton);
        synButtonGroup.add(triangleWaveRadioButton);
        synButtonGroup.add(squareWaveRadioButton);
        synButtonGroup.add(sineXCosineRadioButton1);
        synButtonGroup.add(otherRadioButton);
        synButtonGroup.add(readRadioButton);
        synButtonGroup.add(readFileRadioButton);

        playorfileBtn.setEnabled(false);
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playorfileBtn.setEnabled(false);
                new SynGen().getSyntheticData(audioData);
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
            new ListenThread().start();
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void outputdata() {
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

    class ListenThread extends Thread{
        byte playBuffer[] = new byte[16384];

        public void run(){
            try{
                generateBtn.setEnabled(false);
                playorfileBtn.setEnabled(false);

                sourceDataLine.open(audioFormat);
                sourceDataLine.start();

                int cnt;
                long startTime = new Date().getTime();
                while((cnt = audioInputStream.read(
                        playBuffer, 0,
                        playBuffer.length))
                        != -1){
                    if(cnt > 0){
                        sourceDataLine.write(playBuffer, 0, cnt);
                    }
                }
                sourceDataLine.drain();

                int elapsedTime = (int)(new Date().getTime() - startTime);
                durationTxt.setText("" + elapsedTime);

                sourceDataLine.stop();
                sourceDataLine.close();

                generateBtn.setEnabled(true);
                playorfileBtn.setEnabled(true);
            }catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    class SynGen{
        ByteBuffer byteBuffer;
        ShortBuffer shortBuffer;
        int byteLength;

        void getSyntheticData(byte[] synDataBuffer){
            byteBuffer = ByteBuffer.wrap(synDataBuffer);
            shortBuffer = byteBuffer.asShortBuffer();

            byteLength = synDataBuffer.length;

            if(sineWaveRadioButton.isSelected()) sinetones();
        }

        void sinetones(){
            channels = 1;
            int bytesPerSamp = 2;
            sampleRate = 16000.0F;
            double freq = 262;
            int sampLength = byteLength/bytesPerSamp;
            readTxt.setText(String.valueOf(sampLength));
            int cnt = 0;
            int tonecnt = -1;
            for(cnt = 0; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue = (Math.sin(2*Math.PI*freq*time));
                shortBuffer.put((short)(16000*sinValue));
                if(cnt % 8000 == 0)
                {
                    tonecnt++;
                }
                if(tonecnt == 0) freq = 262;
                if(tonecnt == 1) freq = 294;
                if(tonecnt == 2) freq = 330;
                if(tonecnt == 3) freq = 262;
                if(tonecnt == 4) freq = 262;
                if(tonecnt == 5) freq = 294;
                if(tonecnt == 6) freq = 330;
                if(tonecnt == 7) freq = 262;
                if(tonecnt == 8) freq = 330;
                if(tonecnt == 9) freq = 349;
                if(tonecnt == 10) freq = 392;
                if(tonecnt == 11) freq = 330;
                if(tonecnt == 12) freq = 349;
                if(tonecnt == 13) freq = 392;
                durationTxt.setText(String.valueOf(tonecnt));
            }

            /*
            freq = 294;
            for(; cnt < sampLength/2; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.sin(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop

            freq = 330;
            for(; cnt < sampLength*0.75; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.sin(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq = 294;
            for(; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.sin(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            /*
            freq = 330;
            for(cnt = 0; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.sin(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq = 330;
            for(cnt = 0; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.sin(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq = 330;
            for(cnt = 0; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.sin(2*Math.PI*freq*time));
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop*/
        }
    }
}
