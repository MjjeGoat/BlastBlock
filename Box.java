import javax.swing.*;
import java.awt.*;

public class Box extends JPanel {
    private ImageIcon imageb = new ImageIcon("src/res/box.png");
    private int height = 50;
    private int width = 50;
    private boolean isOn;

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public boolean isOn() {
        return isOn;
    }

    public ImageIcon getImageb() {
        return imageb;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImageb(ImageIcon imageb) {
        this.imageb = imageb;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Box() {
        this.setPreferredSize(new Dimension(50,50));
    }


}
