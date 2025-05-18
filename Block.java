import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a draggable block composed of smaller parts within a game zone.
 * Each block has a type determining its shape and interacts with the {@link GameZone}.
 */
public class Block extends JPanel {
    private int type;
    private Point initialClick;
    private GameZone gameZone;
    private ArrayList<String[]> shape = new ArrayList<>();
    private Point start;

    /**
     * Constructs a Block of a specific type and assigns it to a game zone.
     *
     * @param type     The block type, used to load its shape.
     * @param gameZone The game zone where the block will be placed.
     */
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
                returnToInventory();
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
                gameZone.preview(Block.this, getX(), getY());
            }
        });
    }

    /**
     * Custom painting of the block using an icon for each occupied cell.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("src/res/part.png");

        int width = (int) (gameZone.getWidth() * 0.04275);
        int height = (int) (gameZone.getWidth() * 0.04275);

        for (int r = 0; r < shape.size(); r++) {
            String[] row = shape.get(r);
            for (int c = 0; c < row.length; c++) {
                if (Integer.parseInt(row[c]) == 1) {
                    g.drawImage(icon.getImage(), (width * c), (height * r), width, height, this);
                }
            }
        }
    }

    /**
     * Sets the original location from which the block can return if block will be placed outside the screen.
     *
     * @param start The point to return to.
     */
    public void setStart(Point start) {
        this.start = start;
        setLocation(start);
    }

    /**
     * Loads the block shape from a file based on its type.
     * Each line in the file represents a row of the block using 1s and 0s.
     */
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

    /**
     * Returns the block's shape as a list of string arrays.
     *
     * @return A list representing the block's shape.
     */
    public ArrayList<String[]> getShape() {
        return shape;
    }


    /**
     * Returns the block type.
     *
     * @return The type identifier of the block.
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the block to its start location if it is dragged outside the screen.
     */
    public void returnToInventory() {
        if (getX() > gameZone.getWidth() || getY() > gameZone.getHeight() || getX() < 0 || getY() < 0) {
            setLocation(start);
        }
    }
}
