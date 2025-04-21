import javax.swing.*;
import java.awt.*;

public class GameZone extends JPanel {

    private ImageIcon box;

    public GameZone() {
        this.setPreferredSize(new Dimension(450, 800));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        box = new ImageIcon("src/res/box.png");
        Inventory inv = new Inventory(this);
        this.add(inv);

    }


    public void paintComponent(Graphics g) {
        Box box = new Box();
        super.paintComponent(g);
        g.setColor(Color.black);


        int gridCols = 6;
        int gridRows = 6;

        int totalWidth = gridCols * box.getWidth();
        int totalHeight = gridRows * box.getHeight();

        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - totalHeight) / 2;
        for (int i = 0; i < gridCols; i++) {
            for (int j = 0; j < gridRows; j++) {
                g.drawImage(box.getImageb().getImage(), startX + i * box.getWidth(), startY + j * box.getHeight(), box.getWidth(), box.getHeight(), null);
            }
        }
    }
}
