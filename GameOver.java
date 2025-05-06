import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameOver extends JPanel {
    private ImageIcon menu = new ImageIcon("src/res/MainMenu.png");
    private ImageIcon resized = new ImageIcon(menu.getImage().getScaledInstance(100, 75, Image.SCALE_DEFAULT));
    private JButton menuBut = new JButton(resized);
    private GameZone zone;
    private MainScreen mainScreen;
    private JLabel scoreLabel;
    private int finalScore;

    public GameOver(GameZone zone, MainScreen mainScreen) {
        this.zone = zone;
        this.mainScreen = mainScreen;
        this.setPreferredSize(new Dimension(450, 800));
        this.setBackground(Color.black);
        this.setLayout(null);
        menuButton();
    }

    public void scoreDisplay() {
        finalScore = zone.getScore();
        if (scoreLabel == null) {
            scoreLabel = new JLabel("Score: " + finalScore);
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 36));
            scoreLabel.setBounds(75, 220, 300, 50);
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(scoreLabel);
        } else {
            scoreLabel.setText("Score: " + finalScore);
        }
    }


    public boolean endGame() {
        System.out.println(zone.getBlocks());
        for (Block block : zone.getBlocks()) {
            if (canFitAnywhere(block)) {
                return false;
            }
        }
        return true;
    }

    private boolean canFitAnywhere(Block block) {
        Box[][] grid = zone.getGrid();
        ArrayList<String[]> shape = block.getShape();
        boolean canFitAnywhere = false;

        if (shape == null || shape.isEmpty()) return false;

        int shapeHeight = shape.size();
        int shapeWidth = shape.get(0).length;

        for (int row = 0; row <= 6 - shapeHeight; row++) {
            for (int col = 0; col <= 6 - shapeWidth; col++) {
                if (canPlaceShapeAt(shape, row, col, grid)) {
                    System.out.println("Blok se vejde na pozici: řádek = " + row + ", sloupec = " + col);
                    canFitAnywhere = true;
                }
            }
        }

        return canFitAnywhere;
    }

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
        finalScore = zone.getScore();
        revalidate();
        repaint();
    }

    private void menuButton() {
        menuBut.setBounds(350, 0, 100, 75);
        this.add(menuBut);

        menuBut.addActionListener(e -> {
            mainScreen.showCardPanel("MainMenu");
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon gameOverImage = new ImageIcon("src/res/GameOver.png");

        int width = 300;
        int height = 250;
        int x = getWidth() / 2 - width / 2;
        int y = getHeight() / 2 - height / 2;


        g.drawImage(gameOverImage.getImage(), x, y, 300, 300, this);
    }
}
