import javax.swing.*;
import java.awt.*;

public class Shop extends JPanel {

    MainScreen mainScreen;

    public Shop(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.setBackground(Color.BLUE);
        this.setPreferredSize(new Dimension(450, 800));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        menuButton();
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
