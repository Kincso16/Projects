import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {
    static private int ok;
    static private int gameOver;
    private Model model;
    private View view;
    static private Model model1;
    static private View view1;
    private GameFrame gameFrame;
    private Audio audio;

    public Controller(Model model, View view, GameFrame gameFrame) {
        this.model = model;
        this.view = view;
        this.gameFrame = gameFrame;
        ok = 0;
        gameOver = 0;
        audio = new Audio();
        audio.base();
    }

    public void start() {
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!model.isOk()) {
                    model.setOk(true);
                    view.repaint();
                    ok++;

                    if (ok == 1) {
                        model1 = model;
                        view1 = view;
                    } else {
                        ok = 0;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if (gameOver == model.getSize() - 1) {
                                    model.setScore(gameFrame.getValue());

                                    if (model.getScore() < model.getBestScore() || model.getBestScore() == -1) {
                                        saveScore(model.getScore());
                                    }

                                    new ScoreFrame(model);
                                    gameFrame.dispose();
                                }

                                if (TurnOver(model, model1)) {
                                    try {
                                        Thread.sleep(1200);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    model.setOk(false);
                                    model1.setOk(false);
                                    view.repaint();
                                    view1.repaint();
                                } else {
                                    gameOver++;
                                    audio.effect();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public static int[] shuffle(int db) {
        int[] cards = IntStream.rangeClosed(1, db)
                .map(i -> i <= db / 2 ? i : i % (db / 2) + 1)
                .boxed()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), list -> {
                            Collections.shuffle(list, new Random());
                            return list;
                        }
                ))
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();

        return cards;
    }

    public static int readFromFile() {
        int bestScore = -1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("file/score.txt"));
            String text = "";
            text = reader.readLine();
            if (!Objects.isNull(text)) {
                bestScore = Integer.parseInt(text);
            }
        } catch (FileNotFoundException e) {
            File newFile = new File("file/score.txt");
            try {
                newFile.createNewFile();
            } catch (IOException ex) {
                System.out.println("hiba a file letrehozasaban!");
            }
        } catch (IOException e) {
        }
        return bestScore;
    }

    public static void saveScore(int score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("file/score.txt"));
            writer.write(Integer.toString(score));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JLabel labelBestScore() {
        JLabel label = new JLabel(
                Optional.ofNullable(readFromFile())
                        .filter(score -> score != -1)
                        .map(score -> "BEST TIME: " + score)
                        .orElse("BEST TIME: Elso alkalom!")
        );

        return label;
    }

    private boolean TurnOver(Model model, Model model1) {
        if (model.getIndex() == model1.getIndex()) {
            return false;
        }
        return true;
    }
}
