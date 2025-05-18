import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Controls game logic and UI updates during gameplay.
 * Handles clearing the board, and triggering end-game logic.
 */
public class GameControl extends JPanel {
    private ImageIcon menu = new ImageIcon("src/res/MainMenu.png");
    private GameZone zone;
    private MainScreen mainScreen;
    private JLabel scoreLabel;
    private int finalScore;

    /**
     * Constructs the GameControl panel with references to the game zone and main screen.
     *
     * @param zone       The GameZone object representing the playing area.
     * @param mainScreen The MainScreen containing UI dimensions.
     */
    public GameControl(GameZone zone, MainScreen mainScreen) {
        this.zone = zone;
        this.mainScreen = mainScreen;
        this.setPreferredSize(new Dimension(mainScreen.frameWidth, mainScreen.frameHeight));
        this.setBackground(Color.black);
        this.setLayout(null);
        menuButton();
    }

    /**
     * Displays the score label based on the score after game ends.
     */
    public void scoreDisplay() {
        finalScore = zone.getScore();
        int width = (int) (mainScreen.getWidth() * 0.6);
        int height = (int) (mainScreen.getHeight() * 0.3);
        int x = (mainScreen.getWidth() - width) / 2;
        int y = (int) ((mainScreen.getHeight() - height) / 1.5);
        if (scoreLabel == null) {
            scoreLabel = new JLabel("Score: " + finalScore);
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, (int) (mainScreen.getWidth() * 0.07)));
            scoreLabel.setBounds(x, y, width, height);
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(scoreLabel);
        } else {
            scoreLabel.setText("Score: " + finalScore);
        }
    }

    /**
     * Checks whether the game should end by verifying if any blocks can still be placed.
     *
     * @return {@code true} if no blocks can be placed; {@code false} If some block can still be placed.
     */
    public boolean endGame() {
        for (Block block : zone.getBlocks()) {
            if (canFitAnywhere(block)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether a given block can fit anywhere on the grid.
     *
     * @param block The block to be tested.
     * @return {@code true} if it can fit somewhere; {@code false} If block do not fit inside grid.
     */
    private boolean canFitAnywhere(Block block) {
        Box[][] grid = zone.getGrid();
        ArrayList<String[]> shape = block.getShape();
        boolean canFitAnywhere = false;

        int shapeHeight = shape.size();
        int shapeWidth = shape.get(0).length;


        for (int row = 0; row <= 6 - shapeHeight; row++) {
            for (int col = 0; col <= 6 - shapeWidth; col++) {
                if (canPlaceShapeAt(shape, row, col, grid)) {
                    canFitAnywhere = true;
                }
            }
        }

        return canFitAnywhere;
    }

    /**
     * Checks whether a given block shape can be placed at a specific position on the grid.
     *
     * @param shape   The 2D shape of the block.
     * @param baseRow The row index to start placing from.
     * @param baseCol The column index to start placing from.
     * @param grid    The current game grid.
     * @return {@code true} if it can be placed; {@code false} if it can not be placed.
     */
    private boolean canPlaceShapeAt(ArrayList<String[]> shape, int baseRow, int baseCol, Box[][] grid) {
        for (int r = 0; r < shape.size(); r++) {
            String[] row = shape.get(r);
            for (int c = 0; c < row.length; c++) {
                if (Integer.parseInt(row[c]) == 1) {
                    int gridRow = baseRow + r;
                    int gridCol = baseCol + c;
                    if (gridRow < 0 || gridRow >= 6 || gridCol < 0 || gridCol >= 6 || grid[gridRow][gridCol].isOn()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Clears the game board, resets block images and status, and clear inventory.
     */
    public void clearBoard() {
        ImageIcon box = new ImageIcon("src/res/box.png");
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                zone.getGrid()[j][k].setOn(false);
                zone.getGrid()[j][k].setImageb(box);
            }
        }
        for (Block block : zone.getBlocks()) {
            zone.remove(block);
        }
        zone.inventory();
        revalidate();
        repaint();
    }

    /**
     * Creates and adds a menu button to return to the main menu screen.
     */
    private void menuButton() {
        int width = (int) (mainScreen.getWidth() * 0.25);
        int height = (int) (mainScreen.getHeight() * 0.09375);
        int x = (int) (mainScreen.getWidth() - width);
        int y = 0;
        ImageIcon resized = new ImageIcon(menu.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JButton menuBut = new JButton(resized);
        menuBut.setBounds(x, y, width, height);
        this.add(menuBut);


        menuBut.addActionListener(e -> {
            mainScreen.showCardPanel("MainMenu");
        });
    }

    /**
     * Checks for any full rows or columns, clears them, and updates the score and combo counter.
     */
    public void checkIfAll() {
        ArrayList<Point> willBeDeleted = new ArrayList<>();
        ImageIcon box = new ImageIcon("src/res/box.png");
        for (int row = 0; row < 6; row++) {
            boolean fullRow = true;
            for (int col = 0; col < 6; col++) {
                if (!zone.getGrid()[row][col].isOn()) {
                    fullRow = false;
                    break;
                }
            }

            if (fullRow) {
                for (int col = 0; col < 6; col++) {
                    willBeDeleted.add(new Point(row, col));
                }
                zone.setBlockCountCombo(0);
                zone.setScore(zone.getScore() + (100 * zone.getCombo()));
                zone.setCombo(zone.getCombo() + 1);
            }
        }

        for (int col = 0; col < 6; col++) {
            boolean fullCol = true;
            for (int row = 0; row < 6; row++) {
                if (!zone.getGrid()[row][col].isOn()) {
                    fullCol = false;
                    break;
                }
            }

            if (fullCol) {
                for (int row = 0; row < 6; row++) {
                    willBeDeleted.add(new Point(row, col));
                }
                zone.setBlockCountCombo(0);
                zone.setScore(zone.getScore() + (100 * zone.getCombo()));
                zone.setCombo(zone.getCombo() + 1);
            }
        }

        for (int i = 0; i < willBeDeleted.size(); i++) {
            zone.getGrid()[willBeDeleted.get(i).x][willBeDeleted.get(i).y].setOn(false);
            zone.getGrid()[willBeDeleted.get(i).x][willBeDeleted.get(i).y].setImageb(box);
        }
        willBeDeleted.clear();
        repaint();
    }

    /**
     * Checks if the entire board is empty.
     *
     * @return {@code true} if the board is fully clear; {@code false} otherwise.
     */
    public boolean allClear() {
        boolean result = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (zone.getGrid()[i][j].isOn()) {
                    result = false;
                }
            }
        }
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Paints the "Game Over" picture when the game ends.
     *
     * @param g The graphics context to draw to.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon gameOverImage = new ImageIcon("src/res/GameOver.png");

        int size = (int) (mainScreen.getWidth() * 0.65);
        int x = getWidth() / 2 - size / 2;
        int y = getHeight() / 2 - size / 2;
        g.drawImage(gameOverImage.getImage(), x, y, size, size, this);
    }
}
