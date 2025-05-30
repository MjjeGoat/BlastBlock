import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A panel that displays prices for various items the player can buy,
 * such as rerolls, continues, multipliers, and skins.
 * It also provides a button to navigate back to the Shop panel.
 */
public class PriceList extends JPanel {

    private Player player;
    private MainScreen mainScreen;
    private JLabel rerollPrice;
    private JLabel continuePrice;
    private JLabel multiplierPrice;
    private JLabel skin1Price;
    private JLabel skin2Price;
    private JLabel skin3Price;
    private JLabel skin4Price;
    private ArrayList<JLabel> prices = new ArrayList<>();

    /**
     * Constructs a PriceList panel.
     * Initializes labels showing prices for each item and adds a shop button.
     *
     * @param player     the player whose data is used to calculate prices
     * @param mainScreen reference to the main application window for sizing.
     */
    public PriceList(Player player,MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.player = player;
        this.setPreferredSize(new Dimension(mainScreen.getWidth(), mainScreen.getHeight()));
        this.setBackground(Color.GRAY);
        this.setLayout(null);
        showPrices();
        shopButton();

    }

    /**
     * Creates and adds a button that navigates to the Shop panel when clicked.
     */
    private void shopButton() {
        int width = (int) (mainScreen.getWidth() * 0.25);
        int height = (int) (mainScreen.getHeight() * 0.09375);
        int x = mainScreen.getWidth() - width;
        int y = 0;
        ImageIcon menu = new ImageIcon("src/res/Shop.png");
        ImageIcon resized = new ImageIcon(menu.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JButton shopBut = new JButton(resized);
        shopBut.setBackground(Color.BLACK);
        shopBut.setBounds(x, y, width, height);
        this.add(shopBut);

        shopBut.addActionListener(e -> {
            mainScreen.showCardPanel("Shop");
        });
    }

    /**
     * Updates the multiplier price label based on the player's current multiplier.
     * The price is calculated as multiplier * 100000.
     */
    public void updateMultiplierPrice() {
        double multiplier = player.getMultiplier();
        double price = multiplier * 100000;

        multiplierPrice.setText("Multiplier price: " + String.format("%.0f", price));
        multiplierPrice.revalidate();
        multiplierPrice.repaint();
    }

    /**
     * Initializes and displays price labels for reroll, continue, multiplier, and skins.
     */
    public void showPrices() {
        double multiplierPriceValue = player.getMultiplier() * 100000;
        rerollPrice = new JLabel("Reroll Price: 10000");
        continuePrice = new JLabel("Continue Price: 50000");
        multiplierPrice = new JLabel("Multiplier Price: " + String.format("%.1f", multiplierPriceValue));
        skin1Price = new JLabel("Skin Buko Price: 150000");
        skin2Price = new JLabel("Skin Bazilisek Price: 150000");
        skin3Price = new JLabel("Skin Hever Price: 150000");
        skin4Price = new JLabel("Skin Mrazak Price: 150000");


        prices.add(rerollPrice);
        prices.add(continuePrice);
        prices.add(multiplierPrice);
        prices.add(skin1Price);
        prices.add(skin2Price);
        prices.add(skin3Price);
        prices.add(skin4Price);

        for (int i = 0; i < prices.size(); i++) {
            JLabel price = prices.get(i);
            price.setForeground(Color.BLACK);
            price.setFont(new Font("Arial", Font.BOLD, (int) (mainScreen.getWidth() * 0.05)));
            price.setBounds((int) (mainScreen.getWidth() * 0.2), (int) ((int) (mainScreen.getHeight() * 0.1 * i) + mainScreen.getHeight() * 0.1), (int) (mainScreen.getWidth() * 0.7), (int) (mainScreen.getHeight() * 0.1025));
            this.add(price);
        }
        this.repaint();
    }


}
