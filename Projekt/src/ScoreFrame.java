import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreFrame extends JFrame {
    private JButton exit;
    private JLabel label;
    private Audio audio;

    public ScoreFrame(Model model) {
        audio = new Audio();
        audio.base();

        setTitle("Memory Game");
        setSize(600, 600);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        exit = new JButton("EXIT");
        exit.setBackground(new Color(203,189,147));

        exit.setContentAreaFilled(false);
        exit.setOpaque(true);

        label = new JLabel("Gratulalok! " + model.getScore() + "-bol nyertel!");
        label.setBackground(new Color(237,232,208));
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(exit);
        panel.setBackground(new Color(237,232,208));
        panel.setLayout(new GridLayout(2, 1, 80, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 35, 60, 35));

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
