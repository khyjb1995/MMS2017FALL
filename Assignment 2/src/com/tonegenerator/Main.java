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
    byte audioData[] = new byte[16000*4];

    public Main() {
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
            if(cosineWaveRadioButton.isSelected()) stereoPanning();

        }

        void sinetones(){
            channels = 1;
            int bytesPerSamp = 2;
            sampleRate = 16000.0F;
            double freq = 262;
            int sampLength = byteLength/bytesPerSamp;
            int cnt = 0;
            for(cnt = 0; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue = (Math.sin(2*Math.PI*freq*time));
                shortBuffer.put((short)(16000*sinValue));
            }

            /*
            freq = 294;
            for(cnt = 0; cnt < sampLength; cnt++){
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
            freq = 294;
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
        }//end method tones
        //-------------------------------------------//

        //This method generates a stereo speaker sweep,
        // starting with a relatively high frequency
        // tone on the left speaker and moving across
        // to a lower frequency tone on the right
        // speaker.
        void stereoPanning(){
            channels = 1;//Java allows 1 or 2
            //Each channel requires two 8-bit bytes per
            // 16-bit sample.
            int bytesPerSamp = 2;
            sampleRate = 16000.0F;
            double freq = freqScroll.getValue() * 10;//arbitrary frequency
            // Allowable 8000,11025,16000,22050,44100
            int sampLength = byteLength/bytesPerSamp;
            int cnt = 0;
            for(cnt = 0; cnt < sampLength/8; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq+= 20;
            for(; cnt < (sampLength/8) * 2; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq+=20;
            for(; cnt < (sampLength/8) * 3; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq-=40;
            for(; cnt < (sampLength/8) * 4; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            for(; cnt < (sampLength/8) * 5; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq+= 20;
            for(; cnt < (sampLength/8) * 6; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq+=20;
            for(; cnt < (sampLength/8) * 7; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
            freq-=40;
            for(; cnt < sampLength; cnt++){
                double time = cnt/sampleRate;
                double sinValue =
                        (Math.cos(2*Math.PI*freq*time)); //+
                //Math.sin(2*Math.PI*(freq/1.8)*time) +
                //Math.sin(2*Math.PI*(freq/1.5)*time))/3.0;
                shortBuffer.put((short)(16000*sinValue));
            }//end for loop
        }//end method stereoPanning
    }
}
