import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class KakukktojasJatek {
    private JFrame frame;
    private JPanel viszonPanel;
    private JTextField inputField;
    private JLabel scoreLabel;
    private JButton checkButton;
    private List<String> riddles;
    private String currentSolution;
    private int score = 0;
    private Random random = new Random();

    public KakukktojasJatek(String filePath) {
        loadRiddles(filePath);
        initUI();
    }

    private void loadRiddles(String filePath) {
        riddles = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists() || !file.canRead()) {
            System.out.println("Hiba: A fájl nem található vagy nem olvasható. Alapértelmezett feladványok betöltése...");
            riddles.add("alma körte banán dinnye eper szilva KÓKUSZ");
            riddles.add("asztal szék kanapé ágy szekrény POLC");
            riddles.add("kutya macska ló tehén juh KACSA");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    riddles.add(line);
                }
            } catch (IOException e) {
                System.out.println("Hiba a fájl beolvasásakor. Alapértelmezett lista betöltése...");
                riddles.add("alma körte banán dinnye eper szilva KÓKUSZ");
            }
        }
    }

    private void initUI() {
        frame = new JFrame("Kakukktojás Játék");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        viszonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentSolution != null) {
                    String[] words = currentSolution.split(" ");
                    for (String word : words) {
                        int x = random.nextInt(getWidth() - 100);
                        int y = random.nextInt(getHeight() - 30);
                        int fontSize = random.nextInt(10) + 20;
                        g.setFont(new Font("Arial", Font.BOLD, fontSize));
                        g.drawString(word, x, y);
                    }
                }
            }
        };
        viszonPanel.setPreferredSize(new Dimension(500, 250));
        frame.add(viszonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        inputField = new JTextField(15);
        checkButton = new JButton("Ellenőrzés");
        scoreLabel = new JLabel("Helyes válaszok: 0");

        bottomPanel.add(inputField);
        bottomPanel.add(checkButton);
        bottomPanel.add(scoreLabel);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        checkButton.addActionListener(e -> checkAnswer());
        inputField.addActionListener(e -> checkAnswer());

        frame.setVisible(true);

        new Thread(this::startGameLoop).start();
    }

    private void startGameLoop() {
        while (true) {
            try {
                Thread.sleep(3000);
                showNewRiddle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showNewRiddle() {
        if (riddles.isEmpty()) return;

        String randomRiddle = riddles.get(random.nextInt(riddles.size()));
        String[] words = randomRiddle.split(" ");
        currentSolution = randomRiddle;

        viszonPanel.repaint();
    }

    private void checkAnswer() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty() && currentSolution != null) {
            String[] words = currentSolution.split(" ");
            String correctAnswer = words[words.length - 1]; // Az utolsó szó a kakukktojás

            if (userInput.equalsIgnoreCase(correctAnswer)) {
                score++;
                scoreLabel.setText("Helyes válaszok: " + score);
            }

            inputField.setText("");
            showNewRiddle();
        }
    }

    public static void main(String[] args) {
        String filePath = (args.length > 0) ? args[0] : "feladvanyok.txt";
        SwingUtilities.invokeLater(() -> new KakukktojasJatek(filePath));
    }
}
