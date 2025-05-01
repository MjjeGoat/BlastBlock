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
        finalScore = zone.getScore();
        this.zone = zone;
        this.mainScreen = mainScreen;
        this.setPreferredSize(new Dimension(450, 800));
        this.setBackground(Color.black);
        this.setLayout(null);
        menuButton();
    }

    public void scoreDisplay() {
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
        for (Block block : zone.getBlocks()) {
            if (canFitAnywhere(block)) {
                return false;
            }
        }
        return true;
    }

    private boolean canFitAnywhere(Block block) {
        Box[][] grid = zone.getGrid();

        try (BufferedReader br = new BufferedReader(new FileReader("src/blocks/" + block.getType()))) {
            ArrayList<String[]> shape = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                shape.add(line.split(","));
            }

            int shapeHeight = shape.size();
            int shapeWidth = shape.get(0).length;

            for (int row = 0; row <= 6 - shapeHeight; row++) {
                for (int col = 0; col <= 6 - shapeWidth; col++) {
                    if (canPlaceShapeAt(shape, row, col, grid)) {
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    private boolean canPlaceShapeAt(ArrayList<String[]> shape, int baseRow, int baseCol, Box[][] grid) {
        for (int rowOffset = 0; rowOffset < shape.size(); rowOffset++) {
            String[] row = shape.get(rowOffset);
            for (int colOffset = 0; colOffset < row.length; colOffset++) {
                if (Integer.parseInt(row[colOffset].trim()) == 1) {
                    int r = baseRow + rowOffset;
                    int c = baseCol + colOffset;
                    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c].isOn()) {
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
        scoreDisplay();
        finalScore = 0;
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
