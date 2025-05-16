import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Shop extends JPanel {

    private MainScreen mainScreen;
    private GameZone gameZone;
    private Player player;
    private JLabel moneyLabel;
    private JButton continueButton;
    private JButton multiplierButton;
    private JButton rerollButton;
    private JButton skinsButton;
    private JLabel rerollLabel;
    private JLabel continueLabel;
    private JLabel multipilerLabel;
    private JPopupMenu noMoney = new JPopupMenu("You don't have enough money");
    private JPopupMenu skins = new JPopupMenu();
    private JPopupMenu ownedSkins = new JPopupMenu();


    public Shop(MainScreen mainScreen, GameZone gameZone, Player player) {
        this.player = player;
        this.mainScreen = mainScreen;
        this.gameZone = gameZone;
        this.setBackground(Color.darkGray);
        this.setPreferredSize(new Dimension(gameZone.getWidth(), gameZone.getHeight()));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        menuButton();
        moneyShow();
        priceShow();
        buyReroll();
        buyMultiplier();
        buyContinue();
        buySkins();
        equipSkin();
    }

    private void priceShow() {
        int width = (int) (mainScreen.getWidth() * 0.2);
        int height = (int) (mainScreen.getHeight() * 0.09375);
        int x = (int) (mainScreen.getWidth() * 0.55);
        int y = 0;
        ImageIcon menu = new ImageIcon("src/res/priceList.png");
        ImageIcon resized = new ImageIcon(menu.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JButton menuBut = new JButton(resized);
        menuBut.setBackground(Color.black);
        menuBut.setBounds(x, y, width, height);
        this.add(menuBut);

        menuBut.addActionListener(e -> {
            mainScreen.showCardPanel("PriceList");
            mainScreen.priceList.updateMultiplierPrice();
        });
    }

    private void menuButton() {
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

    protected void moneyShow() {
        if (moneyLabel == null) {
            moneyLabel = new JLabel();
            moneyLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight() * 0.032)));
            moneyLabel.setForeground(Color.BLACK);
            moneyLabel.setBounds((int) (gameZone.getWidth() * 0.02222), (int) (gameZone.getHeight() * 0.00005), (int) (gameZone.getWidth() * 0.855556), (int) (gameZone.getHeight() * 0.1875));
            this.add(moneyLabel);
        } else {
            moneyLabel.setText("Money: " + String.format("%.1f",player.getMoney()));
        }
    }

    private void buyReroll() {
        ImageIcon buyRerollIcon = new ImageIcon("src/res/Buy.png");
        ImageIcon resized = new ImageIcon(buyRerollIcon.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        rerollButton = new JButton(resized);
        rerollButton.setBounds((int) (gameZone.getWidth() * 0.45), (int) (gameZone.getHeight() * 0.15), (int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125));
        rerollLabel = new JLabel(":" + player.getReroll());
        rerollLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight() * 0.05)));
        rerollLabel.setForeground(Color.white);
        rerollLabel.setBounds((int) (gameZone.getWidth() * 0.7), (int) (gameZone.getHeight() * 0.15), (int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125));
        this.add(rerollLabel);
        this.add(rerollButton);

        rerollButton.addActionListener(e -> {
            if (player.getMoney() - 10000 >= 0) {
                player.setMoney(player.getMoney() - 10000);
                player.setReroll(1);
                moneyShow();
                rerollLabel.setText(":" + player.getReroll());
                rerollLabel.revalidate();
                rerollLabel.repaint();
            }else{
                JMenuItem msg = new JMenuItem("You don't have enough money");
                msg.setEnabled(false);
                noMoney.removeAll();
                noMoney.add(msg);
                noMoney.show(rerollButton, 0, rerollButton.getHeight());

            }
        });
    }

    public void buyMultiplier() {
        ImageIcon buyRerollIcon = new ImageIcon("src/res/Buy.png");
        ImageIcon resized = new ImageIcon(buyRerollIcon.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        multiplierButton = new JButton(resized);
        multiplierButton.setBounds((int) (gameZone.getWidth() * 0.45), (int) (gameZone.getHeight() * 0.3), (int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125));
        multipilerLabel = new JLabel(":" + String.format("%.1f", player.getMultiplier()));
        multipilerLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight() * 0.05)));
        multipilerLabel.setForeground(Color.white);
        multipilerLabel.setBounds((int) (gameZone.getWidth() * 0.7), (int) (gameZone.getHeight() * 0.3), (int) (gameZone.getWidth() * 0.4), (int) (gameZone.getHeight() * 0.125));
        this.add(multipilerLabel);
        this.add(multiplierButton);

        multiplierButton.addActionListener(e -> {
            if (player.getMoney() - (player.getMultiplier() * 100000) >= 0) {
                player.setMoney(player.getMoney() - (player.getMultiplier() * 100000));
                player.setMultiplier(0.1);
                moneyShow();
                multipilerLabel.setText(":" + String.format("%.1f", player.getMultiplier()));
                multipilerLabel.revalidate();
                multipilerLabel.repaint();
            }else{
                JMenuItem msg = new JMenuItem("You don't have enough money");
                msg.setEnabled(false);
                noMoney.removeAll();
                noMoney.add(msg);
                noMoney.show(multiplierButton, 0, multiplierButton.getHeight());
            }
        });
    }

    public void buyContinue(){
        ImageIcon buyRerollIcon = new ImageIcon("src/res/Buy.png");
        ImageIcon resized = new ImageIcon(buyRerollIcon.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        continueButton = new JButton(resized);
        continueButton.setBounds((int) (gameZone.getWidth() * 0.45), (int) (gameZone.getHeight() * 0.45), (int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125));
        continueLabel = new JLabel(":" + player.getContinueCount());
        continueLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight() * 0.05)));
        continueLabel.setForeground(Color.white);
        continueLabel.setBounds((int) (gameZone.getWidth() * 0.7), (int) (gameZone.getHeight() * 0.45), (int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125));
        this.add(continueLabel);
        this.add(continueButton);

        continueButton.addActionListener(e -> {
            if (player.getMoney() - 50000 >= 0) {
                player.setMoney(player.getMoney() - 50000);
                player.setContinueCount(1);
                moneyShow();
                continueLabel.setText(":" + player.getContinueCount());
                continueLabel.revalidate();
                continueLabel.repaint();
            }else {
                JMenuItem msg = new JMenuItem("You don't have enough money");
                msg.setEnabled(false);
                noMoney.removeAll();
                noMoney.add(msg);
                noMoney.show(continueButton, 0, continueButton.getHeight());
            }
        });
    }

    public void buySkins(){
        ImageIcon buyRerollIcon = new ImageIcon("src/res/Buy.png");
        ImageIcon resized = new ImageIcon(buyRerollIcon.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        skinsButton = new JButton(resized);
        skinsButton.setBounds((int) (gameZone.getWidth() * 0.45), (int) (gameZone.getHeight() * 0.6), (int) (gameZone.getWidth() * 0.222), (int) (gameZone.getHeight() * 0.125));
        this.add(skinsButton);

        skinsButton.addActionListener(e -> {
            String[] availableSkins = {"bazilisek", "mrazak", "buko", "hever"};
            skins.removeAll();

            for (String skinName : availableSkins) {
                if (!player.getOwnedSkins().contains(skinName)) {
                    JMenuItem item = new JMenuItem("Buy: " + skinName);
                    item.addActionListener(ev -> {
                        if (player.getMoney() > 150000) {
                            player.setMoney(player.getMoney() - 150000);
                            player.addSkin(skinName);
                            moneyShow();
                        } else {
                            JMenuItem msg = new JMenuItem("You don't have enough money");
                            msg.setEnabled(false);
                            noMoney.removeAll();
                            noMoney.add(msg);
                            noMoney.show(skinsButton, 0, skinsButton.getHeight());
                        }
                    });
                    skins.add(item);
                }
            }
            skins.show(skinsButton, 0, skinsButton.getHeight());
        });
    }

    public void equipSkin(){
        ImageIcon buyRerollIcon = new ImageIcon("src/res/equipSkin.png");
        ImageIcon resized = new ImageIcon(buyRerollIcon.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.8), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));

        JButton equipSkinButton = new JButton(resized);
        equipSkinButton.setBounds((int) (gameZone.getWidth() * 0.1), (int) (gameZone.getHeight() * 0.75), (int) (gameZone.getWidth() * 0.8), (int) (gameZone.getHeight() * 0.125));

        this.add(equipSkinButton);
        equipSkinButton.addActionListener(e -> {
            ownedSkins.removeAll();
            for (String skinName : player.getOwnedSkins()) {
                if (player.getOwnedSkins().contains(skinName)) {
                    JMenuItem item = new JMenuItem("Equip: " + skinName);
                    item.addActionListener(ev -> {
                        player.setCurSkin(skinName);
                    });
                    ownedSkins.add(item);
                }
            }
            ownedSkins.show(equipSkinButton, 0, equipSkinButton.getHeight());
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon reroll = new ImageIcon("src/res/rerollinv.png");
        ImageIcon resReroll = new ImageIcon(reroll.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.2222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        g.drawImage(resReroll.getImage(), (int) (gameZone.getWidth() * 0.2), (int) (gameZone.getHeight() * 0.15), this);

        ImageIcon multiplier = new ImageIcon("src/res/multiplier.png");
        ImageIcon resMultiplier = new ImageIcon(multiplier.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.2222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        g.drawImage(resMultiplier.getImage(), (int) (gameZone.getWidth() * 0.2), (int) (gameZone.getHeight() * 0.3), this);

        ImageIcon continueCount = new ImageIcon("src/res/continue.png");
        ImageIcon resContinueCount = new ImageIcon(continueCount.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.2222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        g.drawImage(resContinueCount.getImage(), (int) (gameZone.getWidth() * 0.2), (int) (gameZone.getHeight() * 0.45), this);

        ImageIcon skins = new ImageIcon("src/res/skins.png");
        ImageIcon resSkins = new ImageIcon(skins.getImage().getScaledInstance((int) (gameZone.getWidth() * 0.2222), (int) (gameZone.getHeight() * 0.125), Image.SCALE_SMOOTH));
        g.drawImage(resSkins.getImage(), (int) (gameZone.getWidth() * 0.2), (int) (gameZone.getHeight() * 0.6), this);
    }
}
