import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Block extends JPanel {
    private int type;
    private Point initialClick;
    private GameZone gameZone;
    private int width;
    private int height;


    public Block(int type,GameZone gameZone) {
        this.gameZone = gameZone;
        this.type = type;
        this.setLayout(null);
        this.setOpaque(false);
        this.setBackground(Color.gray);


        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                gameZone.placeBlock(Block.this, getX(), getY());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getX();
                int thisY = getY();
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                setLocation(thisX + xMoved, thisY + yMoved);
                gameZone.preview(Block.this,getX(),getY());
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/blocks/" + getType()));
            String line = "";
            ImageIcon icon = new ImageIcon("src/res/part.png");
            int j = 1;
            width = 15;
            height = 15;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (Integer.parseInt(split[i]) == 1) {
                        g.drawImage(icon.getImage(),(width * i)+width, (height * j)+height, width, height,this);
                    }
                }
                j++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getType() {
        return type;
    }



}
