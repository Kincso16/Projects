import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DescriptionFrame extends JFrame {

    private JLabel label;
    private JButton exit;
    private Audio audio;

    public DescriptionFrame() {
        audio = new Audio();
        audio.base();

        setTitle("Description");
        setSize(600, 600);
        setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setResizable(false);

        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get("file/description.txt")));
        } catch (IOException e) {
            System.out.println("Hiba a file beolvasasakor.");
        }

        label = new JLabel(text);
        label.setBackground(new Color(203,189,147));
        label.setFont(new Font("Arial", Font.BOLD, 14));

        exit = new JButton("EXIT");
        exit.setBackground(new Color(203,189,147));
        exit.setContentAreaFilled(false);
        exit.setOpaque(true);

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(exit);
        panel.setBackground(new Color(237,232,208));
        panel.setLayout(new GridLayout(2, 1, 80, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 80, 80, 80));

        this.add(panel, BorderLayout.CENTER);

        setVisible(true);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audio.effect3();
                new MenuFrame();
                dispose();
            }
        });
    }

}
