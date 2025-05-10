import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ImageIcon logo = new ImageIcon("src/res/Logo.png");
    protected int frameHeight;
    protected int frameWidth;

    public MainScreen() {
        setTitle("BlastBlock");
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        double aspectRatio = 45.0 / 80.0;

        int frameHeight = (int) (screenHeight * 0.8);
        int frameWidth = (int) (frameHeight * aspectRatio);

        if (frameWidth > screenWidth * 0.8) {
            frameWidth = (int) (screenWidth * 0.8);
            frameHeight = (int) (frameWidth / aspectRatio);
        }

        setSize(frameWidth, frameHeight);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(frameWidth, frameHeight));

        Player player = new Player();
        GameZone gameZone = new GameZone(this);
        Shop shop = new Shop(this, gameZone, player);
        GamePanel gamePanel = new GamePanel(this,shop);
        GameControl gameControl = new GameControl(gameZone, this);
        gameZone.setGameOver(gameControl);

        cardPanel.add(gamePanel, "MainMenu");
        cardPanel.add(gameZone, "GameZone");
        cardPanel.add(shop, "Shop");
        cardPanel.add(gameControl, "GameControl");

        add(cardPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        System.out.println("Frame size: " + frameWidth + "x" + frameHeight);
    }

    public void showCardPanel(String cardName) {
        cardLayout.show(cardPanel, cardName);
        repaint();
    }
}
