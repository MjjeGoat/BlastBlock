import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameZone extends JPanel {

    private ImageIcon box;
    private Piece[][] grid = new Piece[6][6];

    public GameZone() {
        this.setPreferredSize(new Dimension(450, 800));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        box = new ImageIcon("src/res/box.png");
        getIntoBox();
        fillInv();

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        int boxWidth = 50;
        int boxHeight = 50;

        int gridCols = 6;
        int gridRows = 6;

        int totalWidth = gridCols * boxWidth;
        int totalHeight = gridRows * boxHeight;

        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - totalHeight) / 2;
        for (int i = 0; i < gridCols; i++) {
            for (int j = 0; j < gridRows; j++) {
                g.drawImage(box.getImage(), startX + i * boxWidth, startY + j * boxHeight, boxWidth, boxHeight, null);
            }
        }

    }

    public void getIntoBox() {
        int a = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                a++;
                grid[i][j] = new Piece(i,j);

            }
        }
    }

    public void fillInv(){
        Random rd = new Random();
        int num = rd.nextInt(12);
        Block block = new Block("src/blocks/L2");
        for (int i = 0; i < block.getCells().size(); i++) {
        }



    }

}
