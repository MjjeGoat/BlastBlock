import java.io.*;

public class Player {
    private double money;
    private double multiplier;
    BufferedReader br;
    BufferedWriter bw;

    {
        try {
            bw = new BufferedWriter(new FileWriter("src/player/atributes"));
            br = new BufferedReader(new FileReader("src/player/atributes"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getMoney() {
        try {
            String line = br.readLine();
            while (line != null) {
                if (line.contains("money:")) {
                    money = Double.parseDouble(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return money;
    }

    public void setMoney(double money) {
        try {
            bw.write(String.valueOf(money + this.money));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getMultiplier() {
        try {
            String line = br.readLine();
            while (line != null) {
                if (line.contains("multiplier:")) {
                    multiplier = Double.parseDouble(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        try {
            bw.write(String.valueOf(multiplier + this.multiplier));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void countMoney(GameZone zone) {
        setMoney(getMoney());
    }
}
