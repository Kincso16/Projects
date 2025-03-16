import javax.swing.*;
import java.awt.*;

public class ShapeDrawer extends JFrame {
    private JComboBox<String> colorBox;
    private JComboBox<String> shapeBox;
    private DrawPanel drawPanel;
    private Timer timer;

    public ShapeDrawer() {
        setTitle("Shape Drawer");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        // Szín választó
        String[] colors = {"Red", "Green", "Blue"};
        colorBox = new JComboBox<>(colors);
        JPanel colorPanel = new JPanel();
        colorPanel.add(new JLabel("Color: "));
        colorPanel.add(colorBox);
        add(colorPanel);

        // Forma választó
        String[] shapes = {"Circle", "Square", "Triangle"};
        shapeBox = new JComboBox<>(shapes);
        JPanel shapePanel = new JPanel();
        shapePanel.add(new JLabel("Shape: "));
        shapePanel.add(shapeBox);
        add(shapePanel);

        // Rajzoló panel
        drawPanel = new DrawPanel();
        add(drawPanel);

        // Start gomb
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> updateDrawing());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel);

        // Időzítő 10 másodperces frissítéshez
        timer = new Timer(1000, e -> updateDrawing());
        timer.start();
    }

    private void updateDrawing() {
        String selectedColor = (String) colorBox.getSelectedItem();
        String selectedShape = (String) shapeBox.getSelectedItem();
        drawPanel.setShapeAndColor(selectedShape, selectedColor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShapeDrawer frame = new ShapeDrawer();
            frame.setVisible(true);
        });
    }

    private static class DrawPanel extends JPanel {
        private String shape = "Circle";
        private Color color = Color.RED;

        public void setShapeAndColor(String shape, String colorName) {
            this.shape = shape;
            switch (colorName) {
                case "Red": this.color = Color.RED; break;
                case "Green": this.color = Color.GREEN; break;
                case "Blue": this.color = Color.BLUE; break;
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(color);
            int size = Math.min(getWidth(), getHeight()) / 2;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            switch (shape) {
                case "Circle":
                    g.fillOval(x, y, size, size);
                    break;
                case "Square":
                    g.fillRect(x, y, size, size);
                    break;
                case "Triangle":
                    int[] xPoints = {x + size / 2, x, x + size};
                    int[] yPoints = {y, y + size, y + size};
                    g.fillPolygon(xPoints, yPoints, 3);
                    break;
            }
        }
    }
}
