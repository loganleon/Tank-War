package loganleo.tank.entitiy;

import javax.sound.sampled.*;
import java.util.Objects;

// This class to play sound. not important
public class Audio extends Thread {
    private AudioFormat audioFormat = null;
    private SourceDataLine sourceDataLine = null;
    private AudioInputStream audioInputStream = null;


    public Audio(String fileName) {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(Audio.class.getClassLoader().getResource(fileName)));
            this.audioFormat = audioInputStream.getFormat();
            //to store the data of audio
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] b = new byte[1024];
            int length = 0;
            sourceDataLine.open(audioFormat, 1024);
            sourceDataLine.start();
            //if still can be read
            while ((length = audioInputStream.read(b)) > 0) {
                sourceDataLine.write(b, 0, length);
            }
            audioInputStream.close();
            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
