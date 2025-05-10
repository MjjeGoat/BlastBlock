import java.io.*;

public class Player {
    private double money;
    private double multiplier;
    private int reroll;
    private int highscore;

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
                reroll = Integer.parseInt(line.substring(8));
            }
        } catch (IOException | NumberFormatException e) {
            reroll = 0;
        }
        return reroll;
    }

    public void setReroll(int reroll) {
        this.reroll = reroll + this.reroll;

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
            multiplier = 1.0;
        }
        return multiplier;
    }

    public void setMultiplier(double value) {
        multiplier = value;
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
}
