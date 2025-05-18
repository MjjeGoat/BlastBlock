import javax.swing.*;
import java.awt.*;
/**
 * The {@code GamePanel} class represents the main menu screen of the game,
 * including the Play, Shop, and Exit buttons, as well as the game logo.
 * It handles transitions between panels and basic menu interaction.
 */
public class GamePanel extends JPanel {

    private MainScreen mainScreen;
    private Shop shop;

    /**
     * Constructs a {@code GamePanel} associated with the given {@code MainScreen} and {@code Shop}.
     *
     * @param mainScreen the main screen frame managing panel transitions
     * @param shop the shop panel used to display the in-game shop
     */
    public GamePanel(MainScreen mainScreen,Shop shop) {
        this.shop = shop;
        this.mainScreen = mainScreen;
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setSize(new Dimension(mainScreen.getWidth(), mainScreen.getHeight()));

        startButton();
        shopButton();
        exitButton();
    }

    /**
     * Creates and adds the Play button to the panel.
     * When clicked, it transitions to the GameZone panel.
     */
    private void startButton() {
        int buttonWidth = (int) (mainScreen.getWidth() * 0.44);
        int buttonHeight = (int) (mainScreen.getHeight()*0.1);

        ImageIcon icon = new ImageIcon("src/res/PlayBut.png");
        ImageIcon resized = new ImageIcon(icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));

        JButton play = new JButton();
        play.setBackground(Color.white);
        play.setBounds((mainScreen.getWidth() - buttonWidth) / 2, (int) (mainScreen.getHeight()*0.5625), buttonWidth, buttonHeight);
        play.setIcon(resized);


        play.addActionListener(e -> {
            mainScreen.showCardPanel("GameZone");
        });

        this.add(play);
    }

    /**
     * Creates and adds the Shop button to the panel.
     * When clicked, it transitions to the Shop panel and updates the displayed money.
     */
    private void shopButton() {
        int buttonWidth = (int) (mainScreen.getWidth() * 0.44);
        int buttonHeight = (int) (mainScreen.getHeight() * 0.1);


        ImageIcon icon = new ImageIcon("src/res/Shop.png");
        ImageIcon resized = new ImageIcon(icon.getImage().getScaledInstance((int) buttonWidth/2, (int) buttonHeight/2, Image.SCALE_SMOOTH));

        JButton play = new JButton();
        play.setBackground(Color.BLACK);
        play.setContentAreaFilled(false);
        play.setBounds(((mainScreen.getWidth() - buttonWidth) / 2), (int) (mainScreen.getHeight()*0.6875),buttonWidth,buttonHeight);
        play.setIcon(resized);


        play.addActionListener(e -> {
            mainScreen.showCardPanel("Shop");
            shop.moneyShow();
        });

        this.add(play);
    }

    /**
     * Creates and adds the Exit button to the panel.
     * When clicked, it closes the app.
     */
    private void exitButton() {
        int buttonWidth = (int) (mainScreen.getWidth() * 0.44);
        int buttonHeight = (int) (mainScreen.getHeight() * 0.1);

        ImageIcon icon = new ImageIcon("src/res/Exit.png");
        ImageIcon resized = new ImageIcon(icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));

        JButton play = new JButton();
        play.setBackground(Color.white);
        play.setBounds((mainScreen.getWidth() - buttonWidth) / 2, (int) (mainScreen.getHeight()*0.8125), buttonWidth, buttonHeight);
        play.setIcon(resized);


        play.addActionListener(e -> {
            mainScreen.dispose();
        });

        this.add(play);
    }

    /**
     * Paints the game logo image centered near the top of the panel.
     *
     * @param g the {@code Graphics} object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon logo = new ImageIcon("src/res/Logo.png");
        int width = (int) (mainScreen.getWidth()*0.66);
        int height = (int) (mainScreen.getHeight()*0.375);
        int x = (int) ((this.getWidth() - width) / 2);
        int y = (int) ((this.getHeight() -height) / 2.5);
        g.drawImage(logo.getImage(), x, y, width, height, null);
    }
}
