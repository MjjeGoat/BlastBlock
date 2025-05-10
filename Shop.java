import javax.swing.*;
import java.awt.*;

public class Shop extends JPanel {

    private MainScreen mainScreen;
    private GameZone gameZone;
    private Player player;
    private JLabel moneyLabel;
    private JButton buyReroll;
    private JButton buyContinue;

    public Shop(MainScreen mainScreen,GameZone gameZone, Player player) {
        this.player = player;
        this.mainScreen = mainScreen;
        this.gameZone = gameZone;
        this.setBackground(Color.darkGray);
        this.setPreferredSize(new Dimension(450, 800));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        menuButton();
        moneyShow();
        buyReroll();
        buyContinue();
    }


    private void menuButton(){
        int width = (int) (mainScreen.getWidth() * 0.25);
        int height = (int) (mainScreen.getHeight() * 0.09375);
        int x = mainScreen.getWidth() - width;
        int y = 0;
        ImageIcon menu = new ImageIcon("src/res/MainMenu.png");
        ImageIcon resized = new ImageIcon(menu.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JButton menuBut = new JButton(resized);
        menuBut.setBounds(x, y, width, height);
        this.add(menuBut);

        menuBut.addActionListener(e -> {
            mainScreen.showCardPanel("MainMenu");
        });
    }

    protected void moneyShow(){
        if (moneyLabel == null) {
            moneyLabel = new JLabel();
            moneyLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight() * 0.04)));
            moneyLabel.setForeground(Color.BLACK);
            moneyLabel.setBounds((int) (gameZone.getWidth() * 0.02222), (int) (gameZone.getHeight() * 0.0125), (int) (gameZone.getWidth() * 0.555556), (int) (gameZone.getHeight() * 0.1875));
            this.add(moneyLabel);
        }else {
            moneyLabel.setText("Money: " + player.getMoney());
        }
    }

    private void buyReroll(){
        ImageIcon buyRerollIcon = new ImageIcon("src/res/rerollinv.png");
        ImageIcon resized = new ImageIcon(buyRerollIcon.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.75), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        buyReroll = new JButton(resized);
        buyReroll.setBounds((int) (gameZone.getWidth() * 0.125), (int) (gameZone.getHeight() * 0.2),(int) (gameZone.getWidth() * 0.75), (int) (gameZone.getHeight() * 0.125));
        this.add(buyReroll);
    }

    private void buyContinue(){
        ImageIcon buyRerollIcon = new ImageIcon("src/res/rerollinv.png");
        ImageIcon resized = new ImageIcon(buyRerollIcon.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.75), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        buyContinue = new JButton(resized);
        buyContinue.setBounds((int) (gameZone.getWidth() * 0.125), (int) (gameZone.getHeight() * 0.4),(int) (gameZone.getWidth() * 0.75), (int) (gameZone.getHeight() * 0.125));
        this.add(buyContinue);
    }
}
