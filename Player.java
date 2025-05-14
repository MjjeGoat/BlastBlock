import java.io.*;

public class Player {
    private double money;
    private double multiplier;
    private int rerolls;
    private int highscore;
    private int continueCount;

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

    public void setContinueCount(int amount) {
        int current = getContinueCount();
        continueCount = current + amount;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/continueCount"))) {
            bw.write("continueCount:" + continueCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void setMoney(double amount) {
        money = amount;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/money"))) {
            bw.write("money:" + money);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void setMultiplier(double value) {
        double current = getMultiplier();
        double multiplier = current + value;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/player/multiplier"))) {
            bw.write("multiplier:" + multiplier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void countMoney(GameZone zone) {
        double score = zone.getScore();
        double total = getMoney() + (getMultiplier() * score);
        setMoney(total);
    }

    public void newFiles() {
        fileCreator("src/player/money", "money:0");
        fileCreator("src/player/multiplier", "multiplier:0.1");
        fileCreator("src/player/rerolls", "reroll:0");
        fileCreator("src/player/highScore", "highscore:0");
        fileCreator("src/player/continueCount", "continueCount:0");
    }

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
