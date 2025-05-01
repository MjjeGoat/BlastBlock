import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ImageIcon logo = new ImageIcon("src/res/Logo.png");


    public MainScreen() {
        setTitle("BlastBlock");
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.setSize(new Dimension(450,800));

        GamePanel gamePanel = new GamePanel(this);
        GameZone gameZone = new GameZone(this);
        Shop shop = new Shop(this);
        GameOver gameOver = new GameOver(gameZone,this);
        gameZone.setGameOver(gameOver);

        cardPanel.add(gamePanel, "MainMenu");
        cardPanel.add(gameZone, "GameZone");
        cardPanel.add(shop, "Shop");
        cardPanel.add(gameOver, "GameOver");



        add(cardPanel);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showCardPanel(String cardName) {
        cardLayout.show(cardPanel, cardName);
        repaint();
    }
}
