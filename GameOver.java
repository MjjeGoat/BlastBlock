import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GameOver extends JPanel {
    public GameOver() {
        this.setBackground(Color.black);
     }

    GameZone gameZone;

    public boolean endGame() {
        for (int i = 0; i < 3; i++) {
            try (BufferedReader br = new BufferedReader(new FileReader(gameZone.getBlocks().get(i)))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(",");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
