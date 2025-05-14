import javax.swing.*;
import java.awt.*;

public class GameFeatures {

    private GameZone gameZone;
    private JButton rerollBut;
    private JLabel scoreLabel;
    private JLabel comboLabel;
    private JLabel highScoreLabel;
    private Player player = new Player();
    private JPopupMenu notEnough = new JPopupMenu();

    public GameFeatures(GameZone gameZone) {
        this.gameZone = gameZone;
        this.scoreLabel = new JLabel("Score: " + gameZone.getScore());
        this.comboLabel = new JLabel("Combo: " + gameZone.getCombo());
        this.highScoreLabel = new JLabel("HighScore: " + gameZone.player.getHighscore());
    }

    public void endGameButtons() {

        ImageIcon endGame = new ImageIcon("src/res/endGame.png");
        ImageIcon resEndGame = new ImageIcon(endGame.getImage().getScaledInstance((int) (gameZone.getWidth()*0.2777777777777778)/2, (int) (gameZone.getHeight()*0.0625)/2, Image.SCALE_SMOOTH));
        ImageIcon continueIcon = new ImageIcon("src/res/continue.png");
        ImageIcon resContIcon = new ImageIcon(continueIcon.getImage().getScaledInstance((int) (gameZone.getWidth()*0.2777777777777778), (int) (gameZone.getHeight()*0.0625),Image.SCALE_SMOOTH));

        JButton endGameButton = new JButton(resEndGame);
        JButton continueButton = new JButton(resContIcon);
        endGameButton.setBackground(Color.black);
        endGameButton.setFont(new Font("Arial", Font.BOLD, 12));
        continueButton.setFont(new Font("Arial", Font.BOLD, 12));
        endGameButton.setBounds((int) (gameZone.getWidth()*0.2222), (int) (gameZone.getHeight()*0.5), (int) (gameZone.getWidth()*0.2777777777777778), (int) (gameZone.getHeight()*0.0625));
        continueButton.setBounds((int) (gameZone.getWidth()*0.5), (int) (gameZone.getHeight()*0.5), (int) (gameZone.getWidth()*0.2777777777777778), (int) (gameZone.getHeight()*0.0625));
        gameZone.add(endGameButton);
        gameZone.add(continueButton);

        blockOtherComponents(endGameButton,continueButton);

        continueButton.addActionListener(e -> {
            if (player.getContinueCount()>0){
                gameZone.remove(continueButton);
                gameZone.remove(endGameButton);
                gameZone.inventory();
                ImageIcon box = new ImageIcon("src/res/box.png");
                for (int j = 0; j < 6; j++) {
                    for (int k = 0; k < 6; k++) {
                        gameZone.getGrid()[j][k].setOn(false);
                        gameZone.getGrid()[j][k].setImageb(box);
                    }
                }
                for (Block block : gameZone.getBlocks()) {
                    gameZone.remove(block);
                }
                player.setContinueCount(-1);
                enableAllComponents();
            }else {
                JMenuItem msg = new JMenuItem("You don't have enough of this item");
                msg.setEnabled(false);
                notEnough.removeAll();
                notEnough.add(msg);
                notEnough.show(continueButton, 0, continueButton.getHeight());
            }
        });

        endGameButton.addActionListener(e -> {
            gameZone.gameControl.scoreDisplay();
            gameZone.setHowManyPlaced(0);
            gameZone.setBlockCountCombo(0);
            gameZone.setScore(0);
            gameZone.setCombo(1);
            gameZone.remove(endGameButton);
            gameZone.remove(continueButton);
            gameZone.mainScreen.showCardPanel("GameControl");
            gameZone.gameControl.clearBoard();
            score();
            combo();
            enableAllComponents();
        });
    }
    protected void menuButton() {
        ImageIcon menu = new ImageIcon("src/res/MainMenu.png");
        ImageIcon resized = new ImageIcon(menu.getImage().getScaledInstance((int) (gameZone.getWidth()*0.222), (int) (gameZone.getHeight()*0.09375), Image.SCALE_SMOOTH));
        JButton menuBut = new JButton(resized);
        menuBut.setBounds((int) (gameZone.getWidth()*0.7777777777777778), 0, (int) (gameZone.getWidth()*0.22222), (int) (gameZone.getHeight()*0.09375));
        gameZone.add(menuBut);

        menuBut.addActionListener(e -> {
            gameZone.mainScreen.showCardPanel("MainMenu");
        });
    }

    public void score() {
        scoreLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight()*0.02)));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setVerticalAlignment(SwingConstants.CENTER);
        scoreLabel.setBounds(0, 0, (int) (gameZone.getWidth()*0.4444), (int) (gameZone.getHeight()*0.09375));
        scoreLabel.setText("Score: " + gameZone.getScore());
        scoreLabel.setForeground(Color.white);
        gameZone.add(scoreLabel);
    }
    public void combo() {
        comboLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight()*0.02)));
        comboLabel.setHorizontalAlignment(SwingConstants.CENTER);
        comboLabel.setVerticalAlignment(SwingConstants.CENTER);
        comboLabel.setBounds(0, (int) (gameZone.getHeight()*0.025), (int) (gameZone.getWidth()*0.4444), (int) (gameZone.getHeight()*0.09375));
        comboLabel.setText("Combo: " + gameZone.getCombo());
        comboLabel.setForeground(Color.white);
        gameZone.add(comboLabel);
    }

    public void highScore(){
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, (int) (gameZone.getHeight()*0.02)));
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreLabel.setVerticalAlignment(SwingConstants.CENTER);
        highScoreLabel.setBounds(0, (int) (gameZone.getHeight()*0.05), (int) (gameZone.getWidth()*0.4444), (int) (gameZone.getHeight()*0.09375));
        highScoreLabel.setText("HighScore: " + gameZone.player.getHighscore());
        highScoreLabel.setForeground(Color.white);
        gameZone.add(highScoreLabel);
    }

    public void reroll(){

        int buttonWidth = (int) (gameZone.getWidth() * 0.4);
        int buttonHeight = (int) (gameZone.getHeight() * 0.1);
        int x = (int) (gameZone.getWidth()-buttonWidth)/2;
        int y = (int) (gameZone.getHeight()*0.86);

        ImageIcon reroll = new ImageIcon("src/res/rerollInv.png");
        ImageIcon resReroll = new ImageIcon(reroll.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));


        rerollBut = new JButton(resReroll);
        rerollBut.setBounds(x,y,buttonWidth,buttonHeight);
        gameZone.add(rerollBut);

        rerollBut.addActionListener(e -> {
            if (player.getReroll()>0){
                for (Block block : gameZone.getBlocks()) {
                    gameZone.remove(block);
                }
                gameZone.inventory();
                gameZone.setHowManyPlaced(0);
                player.setReroll(-1);
            }else {
                JMenuItem msg = new JMenuItem("You don't have enough of this item");
                msg.setEnabled(false);
                notEnough.removeAll();
                notEnough.add(msg);
                notEnough.show(rerollBut, 0, rerollBut.getHeight());
            }
        });
    }

    public void blockOtherComponents(JButton button,JButton button2) {
        for (Component component : gameZone.getComponents()) {
            if (component != button&& component != button2) {
                component.setEnabled(false);
            }
        }
    }
    public void enableAllComponents() {
        for (Component component : gameZone.getComponents()) {
            component.setEnabled(true);
        }
    }
}
