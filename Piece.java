import javax.swing.*;

public class Piece extends JLabel {
    private boolean filled;
    private int x;
    private int y;

    public Piece(int x, int y) {
        this.setIcon(new ImageIcon("src/res/box.png"));
        filled = false;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    public boolean isFilled() {
        return filled;
    }

    @Override
    public int getY() {
        return y;
    }
}
