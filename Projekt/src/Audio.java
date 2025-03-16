import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Audio {
    private Clip clip;
    private int value;

    public void base() {
        value = 0;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/example.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    value++;
                    if (value % 200 == 0) {
                        clip.setMicrosecondPosition(0);
                    }
                }
            });
            timer.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }

    public void effect() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/applause_y.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }

    public void effect3() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/bottle_x.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }

    public void effect4() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/okay-bye.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }
}
