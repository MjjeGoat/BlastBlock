import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameOver extends JPanel {

    private GameZone zone;
    private MainScreen mainScreen;

    public GameOver(GameZone zone,MainScreen mainScreen) {
        this.zone = zone;
        this.mainScreen = mainScreen;
        this.setBackground(Color.black);
        menuButton();
    }


    public boolean endGame() {
        System.out.println(zone.getBlocks());
        boolean gameOver = true;
        for (Block block : zone.getBlocks()) {
            System.out.println(block);
            if (canFitAnywhere(block)) {
                gameOver = false;
            }
        }
        if (gameOver){
            return true;
        }else {
            return false;
        }
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
        boolean placed = false;
        for (int rowOffset = 0; rowOffset < shape.size(); rowOffset++) {
            for (int colOffset = 0; colOffset < shape.get(rowOffset).length; colOffset++) {
                if (Integer.parseInt(shape.get(rowOffset)[colOffset]) == 1) {
                    int r = baseRow + rowOffset;
                    int c = baseCol + colOffset;
                    if (r < 0 || r >= 6 || c < 0 || c >= 6 || grid[r][c].isOn()) {
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
        for(Block block : zone.getBlocks()) {
            zone.remove(block);
        }
        repaint();
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
