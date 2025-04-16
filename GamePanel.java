import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    MainScreen mainScreen;

    public GamePanel(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        this.setPreferredSize(new Dimension(450, 800));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setLayout(null);


        startButton();
        shopButton();
        exitButton();
    }


    private void startButton() {
        int buttonWidth = 200;
        int buttonHeight = 80;

        ImageIcon icon = new ImageIcon("src/res/PlayBut.png");
        ImageIcon resized = new ImageIcon(icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));

        JButton play = new JButton(resized);
        play.setBackground(Color.white);
        play.setBounds((450 - buttonWidth) / 2, 450, buttonWidth, buttonHeight);
        play.setIcon(icon);


        play.addActionListener(e -> {
            mainScreen.showCardPanel("GameZone");
        });

        this.add(play);
    }

    private void shopButton() {
        int buttonWidth = 200;
        int buttonHeight = 80;

        ImageIcon icon = new ImageIcon("src/res/Shop.png");
        ImageIcon resized = new ImageIcon(icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));

        JButton play = new JButton(resized);
        play.setBackground(Color.white);
        play.setBounds((450 - buttonWidth) / 2, 550, buttonWidth, buttonHeight);
        play.setIcon(icon);


        play.addActionListener(e -> {
            mainScreen.showCardPanel("Shop");
        });

        this.add(play);
    }

    private void exitButton() {
        int buttonWidth = 200;
        int buttonHeight = 80;

        ImageIcon icon = new ImageIcon("src/res/Exit.png");
        ImageIcon resized = new ImageIcon(icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT));

        JButton play = new JButton(resized);
        play.setBackground(Color.white);
        play.setBounds((450 - buttonWidth) / 2, 650, buttonWidth, buttonHeight);
        play.setIcon(icon);


        play.addActionListener(e -> {
            mainScreen.dispose();
        });

        this.add(play);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon logo = new ImageIcon("src/res/Logo.png");
        int x = (this.getWidth() - logo.getIconWidth()) / 2;
        int y = (this.getHeight() - logo.getIconHeight()) / 2;
        g.drawImage(logo.getImage(), 75, 200, 300, 300, null);
    }
}
