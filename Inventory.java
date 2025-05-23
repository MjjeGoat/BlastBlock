import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The {@code Inventory} class represents the bottom section of the game screen where
 * the player is provided with new {@code Block} objects to place into the grid.
 * It displays and manages three randomly generated blocks at a time.
 */
public class Inventory extends JPanel {

    private GameZone gameZone;

    /**
     * Constructs an {@code Inventory} panel linked to a specific {@code GameZone}.
     * Sets the size and position based on the {@code GameZone}'s dimensions
     * and initializes it by generating and displaying three blocks.
     *
     * @param gameZone the game zone that this inventory is associated with
     */
    public Inventory(GameZone gameZone) {
        this.gameZone = gameZone;
        this.setLayout(null);
        this.setBackground(Color.GRAY);

        int width = (int) (gameZone.getWidth() * 0.6667);
        int height = (int) (gameZone.getHeight() * 0.1);
        int x = (int) (gameZone.getWidth() * 0.166666667);
        int y = (int) (gameZone.getHeight() * 0.72875);


        this.setSize(width, height);
        this.setBounds(x, y, width, height);
        gameZone.getBlocks().clear();
        fillInv();
    }

    /**
     * Fills the inventory with three randomly generated {@code Block} objects.
     * Each block is positioned within the panel and added both to the
     * {@code GameZone}'s block list and to the game UI.
     */
    public void fillInv(){
        Random rd = new Random();

        int invX = this.getX();
        int invY = this.getY();
        int invWidth = this.getWidth();
        int invHeight = this.getHeight();

        int blockWidth = (int) (invWidth * 0.2);
        int blockHeight = (int) (invHeight * 0.8);
        int spacing = (invWidth - 3 * blockWidth) / 4;

        for (int i = 0; i < 3; i++) {
            int type = rd.nextInt(18);
            Block block = new Block(type, gameZone);
            block.loadShapeFromFile();

            int x = invX + spacing + i * (blockWidth + spacing);
            int y = invY + (invHeight - blockHeight) / 2;

            block.setBounds(x, y, blockWidth, blockHeight);
            block.setStart(new Point(x, y));

            gameZone.getBlocks().add(block);
            gameZone.add(block);
        }
    }
}
