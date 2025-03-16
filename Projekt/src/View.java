import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JPanel {
    private Model model;

    public View(Model model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img;
        try {
            if(model.isOk()) {
                img = ImageIO.read(new File("img/" + model.getIndex() + ".png"));
            } else {
                img = ImageIO.read(new File("img/0.png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(img, 0, 0, model.getWidth()/model.getSize(), model.getHeight()/2, null);
    }
}
