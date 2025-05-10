import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GameZone extends JPanel {
    private Box[][] grid = new Box[6][6];
    private ArrayList<Block> blocks = new ArrayList<>();
    protected MainScreen mainScreen;
    private int score = 0;
    private int combo = 1;
    private int howManyPlaced = 0;
    private int blockCountCombo = 0;
    protected GameControl gameControl;
    protected Player player = new Player();
    private Shop shop;
    private GameFeatures gameFeatures;

    public int getBlockCountCombo() {
        return blockCountCombo;
    }

    public void setBlockCountCombo(int blockCountCombo) {
        this.blockCountCombo = blockCountCombo;
    }

    public int getHowManyPlaced() {
        return howManyPlaced;
    }

    public void setHowManyPlaced(int howManyPlaced) {
        this.howManyPlaced = howManyPlaced;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameOver(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Box[][] getGrid() {
        return grid;
    }

    private ArrayList<Point> points = new ArrayList<>();

    public GameZone(MainScreen mainScreen) {
        gameFeatures = new GameFeatures(this);
        this.mainScreen = mainScreen;
        this.setSize(new Dimension(mainScreen.getWidth(), mainScreen.getHeight()));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setDoubleBuffered(true);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                grid[row][col] = new Box();
            }
        }
        inventory();
        gameFeatures.menuButton();
        gameFeatures.score();
        gameFeatures.combo();
        gameFeatures.highScore();
    }

    public void preview(Block block, int pixelX, int pixelY) {
        points.clear();

        int cellSize = grid[0][0].getWidth();
        int startX = (getWidth() - (6 * cellSize)) / 2;
        int startY = (getHeight() - (6 * cellSize)) / 2;

        int baseCol = (pixelX - startX) / cellSize;
        int baseRow = (pixelY - startY) / cellSize;

        try (BufferedReader br = new BufferedReader(new FileReader("src/blocks/" + block.getType()))) {
            String line;
            int rowOffset = 0;
            boolean canPlace = true;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                for (int colOffset = 0; colOffset < split.length; colOffset++) {
                    if (Integer.parseInt(split[colOffset]) == 1) {
                        int row = baseRow + rowOffset;
                        int col = baseCol + colOffset;
                        if (row < 0 || row >= 6 || col < 0 || col >= 6 || grid[row][col].isOn()) {
                            canPlace = false;
                            break;
                        }
                        points.add(new Point(row, col));
                    }
                }

                if (!canPlace) {
                    points.clear();
                    break;
                }
                rowOffset++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    //ChatGPT metoda
    public void placeBlock(Block block, int pixelX, int pixelY) {
        int cellSize = grid[0][0].getWidth();
        int startX = (getWidth() - (6 * cellSize)) / 2;
        int startY = (getHeight() - (6 * cellSize)) / 2;
        int baseCol = (pixelX - startX) / cellSize;
        int baseRow = (pixelY - startY) / cellSize;


        boolean placed = false;
        gameFeatures.highScore();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/blocks/" + block.getType()));
            String line;
            int rowOffset = 0;
            boolean canPlace = true;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                for (int colOffset = 0; colOffset < split.length; colOffset++) {
                    if (Integer.parseInt(split[colOffset]) == 1) {
                        int gridRow = baseRow + rowOffset;
                        int gridCol = baseCol + colOffset;
                        if (gridRow < 0 || gridRow >= 6 || gridCol < 0 || gridCol >= 6 ||
                                grid[gridRow][gridCol].isOn()) {
                            canPlace = false;
                            break;
                        }
                    }
                }
                if (!canPlace) {
                    break;
                }
                rowOffset++;
            }

            br.close();

            if (canPlace) {
                br = new BufferedReader(new FileReader("src/blocks/" + block.getType()));
                rowOffset = 0;
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(",");
                    for (int colOffset = 0; colOffset < split.length; colOffset++) {
                        if (Integer.parseInt(split[colOffset]) == 1) {
                            int gridRow = baseRow + rowOffset;
                            int gridCol = baseCol + colOffset;
                            grid[gridRow][gridCol].setOn(true);
                            grid[gridRow][gridCol].setImageb(new ImageIcon("src/res/part.png"));
                            points.clear();
                            placed = true;
                            repaint();
                        }
                    }
                    rowOffset++;
                }

                if (placed) {
                    player.setHighscore(score);
                    howManyPlaced++;
                    blockCountCombo++;
                    gameControl.checkIfAll();
                    if (gameControl.allClear()) {
                        setScore(score + (500*combo));
                    }
                    if (blockCountCombo == 3) {
                        combo = 1;
                    }
                    if (howManyPlaced == 3) {
                        howManyPlaced = 0;
                        blocks.clear();
                        inventory();
                    }
                    this.blocks.remove(block);
                    this.remove(block);
                    gameFeatures.score();
                    gameFeatures.combo();
                    repaint();
                }
                if (gameControl.endGame()) {
                    player.countMoney(this);
                    player.setHighscore(score);
                    gameFeatures.endGameButtons();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = (int) (mainScreen.getWidth() * 0.115);
        int totalWidth = 6 * cellSize;
        int totalHeight = 6 * cellSize;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - totalHeight) / 2;

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                ImageIcon img = grid[row][col].getImageb();
                if (img != null) {
                    g.drawImage(img.getImage(), startX + col * cellSize, startY + row * cellSize, cellSize, cellSize, null);
                }
            }
        }

        g.setColor(new Color(135, 206, 235));
        for (Point p : points) {
            int x = startX + p.y * cellSize;
            int y = startY + p.x * cellSize;
            g.fillRect(x, y, cellSize, cellSize);
        }
    }


    public void inventory() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof Inventory) {
                this.remove(comp);
            }
        }
        Inventory inventory = new Inventory(this);
        this.add(inventory);
        this.repaint();
    }

}