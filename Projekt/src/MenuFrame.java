import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {
    private JButton start;
    private JButton description;
    private JButton exit;
    private Audio audio;

    public MenuFrame() {
        audio = new Audio();
        audio.base();

        setTitle("Memory Game");
        setSize(600, 600);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        start = new JButton("START");
        description = new JButton("DESCRIPTION");
        exit = new JButton("EXIT");

        start.setBackground(new Color(203, 189, 147));
        //Az effektek levetelehez kell
        start.setContentAreaFilled(false);
        start.setOpaque(true);

        description.setBackground(new Color(203, 189, 147));
        description.setContentAreaFilled(false);
        description.setOpaque(true);

        exit.setBackground(new Color(203, 189, 147));
        exit.setContentAreaFilled(false);
        exit.setOpaque(true);

        JPanel panel = new JPanel();
        panel.add(start);
        panel.add(description);
        panel.add(exit);
        panel.setBackground(new Color(237, 232, 208));
        panel.setLayout(new GridLayout(3, 1, 0, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        this.add(panel, BorderLayout.CENTER);

        setVisible(true);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingFrame();
                audio.effect3();
                dispose();
            }
        });

        description.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DescriptionFrame();
                audio.effect3();
                dispose();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audio.effect4();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new MenuFrame();
    }
}
