import javax.swing.*;
import java.awt.*;

public class Shop extends JPanel {

    MainScreen mainScreen;
    Player player = new Player();
    JLabel moneyLabel = new JLabel("Money: " + player.getMoney());

    public Shop(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.setBackground(Color.BLUE);
        this.setPreferredSize(new Dimension(450, 800));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        menuButton();
        moneyShow();
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

    private void moneyShow(){
        moneyLabel.setText("Money: " + player.getMoney());
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 30));
        moneyLabel.setForeground(Color.BLACK);
        moneyLabel.setBounds(10,10,250,150);
        this.add(moneyLabel);
    }
}
