import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingFrame extends JFrame {
    private JButton start;
    private JComboBox comboBox;
    private JPanel panel;
    private Audio audio;

    public SettingFrame() {
        audio = new Audio();
        audio.base();

        setTitle("Memory Game");
        setSize(300, 300);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        start = new JButton("START");
        start.setBackground(new Color(203, 189, 147));

        start.setContentAreaFilled(false);
        start.setOpaque(true);

        String[] items = {"1", "2", "3", "4", "5", "6", "7", "8"};
        comboBox = new JComboBox(items);

        panel = new JPanel();
        panel.setBackground(new Color(237, 232, 208));
        panel.add(comboBox);
        panel.add(start);
        panel.setLayout(new GridLayout(2, 1, 0, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        add(panel);
        setVisible(true);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audio.effect3();
                Object selectedItem = comboBox.getSelectedItem();
                String selectedItemString = (String) selectedItem;
                int selectedItemInt = Integer.parseInt(selectedItemString);
                new GameFrame(selectedItemInt);
                dispose();
            }
        });
    }
}
