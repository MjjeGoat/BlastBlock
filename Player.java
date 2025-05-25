import java.io.*;
import java.util.TreeSet;


/**
 * Represents a player in the game, storing persistent data such as money,
 * multiplier, highscore, rerolls, continue count, and owned skins.
 * Data is saved and loaded from local text files in the {@code src/player} directory.
 */
public class Player {
    private double money;
    private double multiplier;
    private int rerolls;
    private int highscore;
    private int continueCount;
    private String curSkin;

    /**
     * Adds a skin name to the owned skins file.
     * @param skin name of the skin to add
     */
    public void addSkin(String skin) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/player/ownedSkins",true));
            writer.write(skin);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a set of skin names owned by the player.
     * @return TreeSet of owned skin names
     */
    public TreeSet<String> getOwnedSkins() {
        TreeSet<String> ownedSkins = new TreeSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/player/ownedSkins"));
            String line;
            while ((line = br.readLine()) != null){
                ownedSkins.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ownedSkins;
    }

    /**
     * Gets the name of the skin from file which is player using.
     * @return skin name which is player using.
     */
    public String getCurSkin() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/player/currentSkin"))) {
            String line = br.readLine();
                curSkin = line;
        } catch (IOException | NumberFormatException e) {
            curSkin = "";
        }
        return curSkin;
    }

    /**
     * Sets the skin name , which is player using, and saves it to file.
     * @param curSkin the name of the skin player is going to use.
     */
    public void setCurSkin(String curSkin) {
        this.curSkin = curSkin;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/currentSkin"))) {
            bw.write(curSkin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the total number of continues from file.
     * @return continue count
     */
    public int getContinueCount() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/player/continueCount"))) {
            String line = br.readLine();
            if (line != null && line.startsWith("continueCount:")) {
                continueCount = Integer.parseInt(line.substring(14));
            }
        } catch (IOException | NumberFormatException e) {
            continueCount = 0;
        }
        return continueCount;
    }

    /**
     * Adds a specified amount to the continue count and saves it into file.
     * @param amount number of continues to add
     */
    public void setContinueCount(int amount) {
        int current = getContinueCount();
        continueCount = current + amount;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/continueCount"))) {
            bw.write("continueCount:" + continueCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads and returns the high score from file.
     * @return high score
     */
    public int getHighscore() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/player/highScore"))) {
            String line = br.readLine();
            if (line != null && line.startsWith("highscore:")) {
                highscore = Integer.parseInt(line.substring(10));
            }
        } catch (IOException | NumberFormatException e) {
            highscore = 0;
        }
        return highscore;
    }

    /**
     * Updates the high score only if the new score is higher than the current one.
     * @param highscore new score to consider
     */
    public void setHighscore(int highscore) {
        if (highscore > getHighscore()){
            this.highscore = highscore;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/highScore"))) {
                bw.write("highscore:" + highscore);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Reads and returns the numbers of reroll from file.
     * @return number of rerolls
     */
    public int getReroll() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/player/rerolls"))) {
            String line = br.readLine();
            if (line != null && line.startsWith("reroll:")) {
                rerolls = Integer.parseInt(line.substring(7));
            }
        } catch (IOException | NumberFormatException e) {
            rerolls = 0;
        }
        return rerolls;
    }

    /**
     * Adds a specified amount to the reroll count and saves it to file.
     * @param amount number of rerolls to add
     */
    public void setReroll(int amount) {
        int current = getReroll();
        System.out.println(current);
        rerolls = current + amount;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/rerolls"))) {
            bw.write("reroll:" + rerolls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the current money amount from file.
     * @return amount of money
     */
    public double getMoney() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/player/money"))) {
            String line = br.readLine();
            if (line != null && line.startsWith("money:")) {
                money = Double.parseDouble(line.substring(6));
            }
        } catch (IOException | NumberFormatException e) {
            money = 0;
        }
        return money;
    }

    /**
     * Sets the money value and saves it to file.
     * @param amount the new amount of money
     */
    public void setMoney(double amount) {
        money = amount;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/money"))) {
            bw.write("money:" + money);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the current multiplier from file.
     * @return multiplier value
     */
    public double getMultiplier() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/player/multiplier"))) {
            String line = br.readLine();
            if (line != null && line.startsWith("multiplier:")) {
                multiplier = Double.parseDouble(line.substring(11));
            }
        } catch (IOException | NumberFormatException e) {
            multiplier = 0.1;
        }
        return multiplier;
    }

    /**
     * Adds the given value to the current multiplier and saves it to file.
     * @param value multiplier to add
     */
    public void setMultiplier(double value) {
        double current = getMultiplier();
        double multiplier = current + value;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/multiplier"))) {
            bw.write("multiplier:" + multiplier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculates and adds money based on the current score and multiplier.
     * @param zone the current game zone to retrieve score from
     */
    public void countMoney(GameZone zone) {
        double score = zone.getScore();
        double total = getMoney() + (getMultiplier() * score);
        setMoney(total);
    }

    /**
     * Creates new data files for all player stats if they do not already exist.
     */
    public void newFiles() {
        fileCreator("src/player/money", "money:0");
        fileCreator("src/player/multiplier", "multiplier:0.1");
        fileCreator("src/player/rerolls", "reroll:0");
        fileCreator("src/player/highScore", "highscore:0");
        fileCreator("src/player/continueCount", "continueCount:0");
        fileCreator("src/player/currentSkin","basic");
        fileCreator("src/player/ownedSkins","basic");
    }

    /**
     * Helper method to create a file with default text if it does not exist.
     * @param fileName the path to the file
     * @param text default content to write into the file
     */
    private void fileCreator(String fileName, String text) {
        File file = new File(fileName);
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
