import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Inventory extends JPanel {

    private GameZone gameZone;

    public Inventory(GameZone gameZone) {
        this.gameZone = gameZone;
        this.setLayout(null);
        this.setBackground(Color.GRAY);
        this.setBounds(75,575,300,100);
        fillInv();
    }

    public void fillInv(){
     Random rd = new Random();
        for (int i = 0; i < 3; i++) {
            int type = rd.nextInt(12);
            Block block  = new Block(type,gameZone);
            block.setBounds(i*100,0,100,100);
            block.setLocation(75 + i * 100, 575);
            gameZone.getBlocks().add("src/blocks/" + type);
            gameZone.add(block);
            gameZone.repaint();
        }



    }
}
