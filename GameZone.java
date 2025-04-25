import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GameZone extends JPanel {
    private Box[][] grid = new Box[6][6];
    private ArrayList<Block> blocks = new ArrayList<>();
    private MainScreen mainScreen;
    private GameOver gameOver = new GameOver(this,mainScreen);

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Box[][] getGrid() {
        return grid;
    }

    private int howManyPlaced = 0;
    private ArrayList<Point> points = new ArrayList<>();

    public GameZone(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.setPreferredSize(new Dimension(450, 800));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setDoubleBuffered(true);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                grid[row][col] = new Box();
            }
        menuButton();
        }


        Inventory inventory = new Inventory(this);
        this.add(inventory);

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
                        }

                    }
                    rowOffset++;
                }

                Container parent = block.getParent();
                if (parent != null) {
                    parent.remove(block);
                    parent.revalidate();
                    parent.repaint();
                    howManyPlaced++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
        checkIfAll();
        newInventory();

        System.out.println(gameOver.endGame());

        if (gameOver.endGame()){
            mainScreen.showCardPanel("Game Over");
            gameOver.clearBoard();
            newInventory();
        }

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = grid[0][0].getWidth();
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

        g.setColor(new Color(135,206,235));
        for (Point p : points) {
            int x = startX + p.y * cellSize;
            int y = startY + p.x * cellSize;
            g.fillRect(x, y, cellSize, cellSize);
        }
    }

    public void newInventory() {
        System.out.println(howManyPlaced);
        if (howManyPlaced == 3) {
            howManyPlaced = 0;

            for (Component comp : getComponents()) {
                if (comp instanceof Inventory) {
                    remove(comp);
                }
            }

            Inventory inv = new Inventory(this);
            this.add(inv);
            this.revalidate();
            this.repaint();
        }
    }


    public void checkIfAll() {
        ArrayList<Point> willBeDeleted = new ArrayList<>();
        ImageIcon box = new ImageIcon("src/res/box.png");
        for (int row = 0; row < 6; row++) {
            boolean fullRow = true;
            for (int col = 0; col < 6; col++) {
                if (!grid[row][col].isOn()) {
                    fullRow = false;
                    break;
                }
            }

            if (fullRow) {
                for (int col = 0; col < 6; col++) {
                    willBeDeleted.add(new Point(row, col));
                }
            }
        }

        for (int col = 0; col < 6; col++) {
            boolean fullCol = true;
            for (int row = 0; row < 6; row++) {
                if (!grid[row][col].isOn()) {
                    fullCol = false;
                    break;
                }
            }

            if (fullCol) {
                for (int row = 0; row < 6; row++) {
                    willBeDeleted.add(new Point(row, col));
                }
            }
        }

        for (int i = 0; i < willBeDeleted.size(); i++) {
            grid[willBeDeleted.get(i).x][willBeDeleted.get(i).y].setOn(false);
            grid[willBeDeleted.get(i).x][willBeDeleted.get(i).y].setImageb(box);
        }
        willBeDeleted.clear();
        repaint();
    }


    public int getHowManyPlaced() {
        return howManyPlaced;
    }

    private void menuButton(){
        ImageIcon menu = new ImageIcon("src/res/MainMenu.png");
        ImageIcon resized = new ImageIcon(menu.getImage().getScaledInstance(100,75, Image.SCALE_DEFAULT));
        JButton menuBut = new JButton(resized);
        menuBut.setBounds(350,0,100,75);
        this.add(menuBut);

        menuBut.addActionListener(e -> {
            mainScreen.showCardPanel("MainMenu");
        });
    }
}