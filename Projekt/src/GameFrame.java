import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private int value;
    private int rotateValue;
    private Model[] models;
    private View[] views;

    public GameFrame(int size) {
        setTitle("Memory Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JPanel panel = new JPanel();
        JLabel label = new JLabel("TIME: 0");
        label.setBounds(150, 0, 300, 80);

        JLabel labelBestScore = Controller.labelBestScore();
        labelBestScore.setBounds(500, 0, 300, 80);

        int bestScore = Controller.readFromFile();

        JPanel panelBelow = new JPanel();

        models = new Model[size * 2];
        views = new View[size * 2];

        int[] cards;
        cards = Controller.shuffle(size * 2);
        for (int i = 0; i < size * 2; ++i) {
            models[i] = new Model(size, bestScore, cards[i], screenSize.width, screenSize.height);
            views[i] = new View(models[i]);

            Controller controller = new Controller(models[i], views[i], this);
            controller.start();
            panelBelow.add(views[i]);
            panelBelow.setBounds(0, 80, screenSize.width, screenSize.height - 80);
        }
        panelBelow.setBackground(new Color(237,232,208));
        if (size < 7) {
            panelBelow.setLayout(new GridLayout(2, size / 2, 40, 40));
            panelBelow.setBorder(BorderFactory.createEmptyBorder(60, 40, 100, 40));
        } else {
            panelBelow.setLayout(new GridLayout(2, size / 2, 10, 10));
            panelBelow.setBorder(BorderFactory.createEmptyBorder(60, 10, 100, 10));
        }

        panel.add(label);
        panel.add(labelBestScore);
        panel.add(panelBelow);
        panel.setLayout(null);
        add(panel);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value++;
                label.setText("TIME: " + value);
            }
        });

        timer.start();

        setVisible(true);

        rotateCard();
    }

    public int getValue() {
        return value;
    }

    private void rotateCard() {
        rotateValue = 0;
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateValue++;
                if(rotateValue == 2) {
                    for(int i = 0; i < views.length; ++i) {
                        models[i].setOk(false);
                        views[i].repaint();
                    }
                }
            }
        });

        timer.start();
    }
}
