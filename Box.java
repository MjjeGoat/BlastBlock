import javax.swing.*;
import java.awt.*;

/**
 * Represents a single cell in the gamezone grid.
 * Each box has a fixed size, an associated image, and an "on" which checks if it is occupied.
 */
public class Box extends JPanel {
    private ImageIcon imageb = new ImageIcon("src/res/box.png");
    private int height = 50;
    private int width = 50;
    private boolean isOn;

    /**
     * Returns the height of this box.
     *
     * @return The height in pixels.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of this box.
     *
     * @return The width in pixels.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Indicates whether this box is currently occupied or clear.
     *
     * @return {@code true} if the box is occupied; {@code false} if the box is clear.
     */
    public boolean isOn() {
        return isOn;
    }

    /**
     * Returns the image associated with this box.
     *
     * @return An {@link ImageIcon} representing the box image.
     */
    public ImageIcon getImageb() {
        return imageb;
    }

    /**
     * Sets the image for this box.
     *
     * @param imageb An {@link ImageIcon} to use as the box image.
     */
    public void setImageb(ImageIcon imageb) {
        this.imageb = imageb;
    }

    /**
     * Sets the active state of this box.
     *
     * @param on {@code true} to mark the box as occupied, {@code false} to mark the box as clear.
     */
    public void setOn(boolean on) {
        isOn = on;
    }
}
