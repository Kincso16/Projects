import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BilliardSimulation extends JPanel implements MouseListener, Runnable {
    private static final int WIDTH = 800, HEIGHT = 600;
    private static final int BALL_RADIUS = 15;
    private static final int HOLE_RADIUS = 20;
    private static final int MAX_COLLISIONS = 3;

    private int ballX, ballY;
    private int velocityX = 0, velocityY = 0;
    private int collisions = 0;
    private boolean running = false;
    private int targetX, targetY;  // Célpont (piros zseb)
    private int remainingGames;

    public BilliardSimulation(int games) {
        this.remainingGames = games;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.GREEN);
        this.addMouseListener(this);
        resetGame();
    }

    private void resetGame() {
        Random rand = new Random();
        ballX = rand.nextInt(WIDTH - 2 * BALL_RADIUS) + BALL_RADIUS;
        ballY = rand.nextInt(HEIGHT - 2 * BALL_RADIUS) + BALL_RADIUS;

        // Piros célzseb helye (jobb alsó sarokban)
        targetX = WIDTH - 50;
        targetY = HEIGHT - 50;

        velocityX = 0;
        velocityY = 0;
        collisions = 0;
        running = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rekeszek (zsebek) kirajzolása
        g.setColor(Color.BLACK);
        g.fillOval(20, 20, HOLE_RADIUS, HOLE_RADIUS);
        g.fillOval(WIDTH - 40, 20, HOLE_RADIUS, HOLE_RADIUS);
        g.fillOval(20, HEIGHT - 40, HOLE_RADIUS, HOLE_RADIUS);
        g.fillOval(WIDTH - 40, HEIGHT - 40, HOLE_RADIUS, HOLE_RADIUS);
        g.fillOval(WIDTH / 2 - 10, 10, HOLE_RADIUS, HOLE_RADIUS);
        g.fillOval(WIDTH / 2 - 10, HEIGHT - 30, HOLE_RADIUS, HOLE_RADIUS);

        // Célzseb pirosra
        g.setColor(Color.RED);
        g.fillOval(targetX, targetY, HOLE_RADIUS, HOLE_RADIUS);

        // Golyó kirajzolása
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, BALL_RADIUS * 2, BALL_RADIUS * 2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!running) {
            int clickX = e.getX();
            int clickY = e.getY();
            velocityX = (clickX - ballX) / 20;
            velocityY = (clickY - ballY) / 20;
            running = true;
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        while (running) {
            ballX += velocityX;
            ballY += velocityY;

            // Ütközés falakkal
            if (ballX <= 0 || ballX >= WIDTH - BALL_RADIUS * 2) {
                velocityX = -velocityX;
                collisions++;
            }
            if (ballY <= 0 || ballY >= HEIGHT - BALL_RADIUS * 2) {
                velocityY = -velocityY;
                collisions++;
            }

            // Cél elérése
            if (Math.hypot(ballX - targetX, ballY - targetY) < HOLE_RADIUS) {
                System.out.println("NYERTÉL!");
                running = false;
            }

            // Ha túl sok ütközés történt
            if (collisions >= MAX_COLLISIONS) {
                System.out.println("VESZTETTÉL!");
                running = false;
            }

            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException ignored) {
            }
        }

        // Új játék, ha még maradt ismétlés
        if (--remainingGames > 0) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    // Felesleges egéresemények
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        int games = 5;
        if (args.length > 0) {
            try {
                int n = Integer.parseInt(args[0]);
                if (n > 0) {
                    games = n;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        JFrame frame = new JFrame("Billiárd Szimuláció");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BilliardSimulation panel = new BilliardSimulation(games);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
