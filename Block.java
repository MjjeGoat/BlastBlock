import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Block extends JPanel {
    private int type;
    private Point initialClick;
    private GameZone gameZone;
    private int width = 15;
    private int height = 15;
    private ArrayList<String[]> shape = new ArrayList<>();

    public Block(int type, GameZone gameZone) {
        this.type = type;
        this.gameZone = gameZone;
        this.setLayout(null);
        this.setOpaque(false);
        this.setBackground(Color.gray);
        loadShapeFromFile();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                gameZone.placeBlock(Block.this, getX(), getY());
                gameZone.getBlocks().remove(Block.this);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getX();
                int thisY = getY();
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                setLocation(thisX + xMoved, thisY + yMoved);
                gameZone.preview(Block.this, getX(), getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("src/res/part.png");

        for (int r = 0; r < shape.size(); r++) {
            String[] row = shape.get(r);
            for (int c = 0; c < row.length; c++) {
                if (Integer.parseInt(row[c]) == 1) {
                    g.drawImage(icon.getImage(), (width * c) + width, (height * r) + height, width, height, this);
                }
            }
        }
    }

    public void loadShapeFromFile() {
        shape.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src/blocks/" + type))) {
            String line;
            while ((line = br.readLine()) != null) {
                shape.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getShape() {
        return shape;
    }

    public int getType() {
        return type;
    }
}
