import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GameControlTest {
    private GameZone zone;
    private MainScreen mainScreen;
    private GameControl control;

    @BeforeEach
    void setUp() {
        mainScreen = new MainScreen();
        zone = new GameZone(mainScreen);
        control = new GameControl(zone, mainScreen);
    }

    @Test
    void testEndGame_TrueWhenNoBlockCanFit() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                zone.getGrid()[i][j].setOn(true);
            }
        }

        Block block = new Block(1,zone);
        ArrayList<String[]> shape = new ArrayList<>();
        shape.add(new String[]{"1"});
        block.setShape(shape);

        zone.getBlocks().clear();
        zone.getBlocks().add(block);

        assertTrue(control.endGame());
    }


    @Test
    void testEndGame_FalseWhenBlockCanFit() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                zone.getGrid()[i][j].setOn(false);
            }
        }

        Block block = new Block(1,zone);
        ArrayList<String[]> shape = new ArrayList<>();
        shape.add(new String[]{"1"});


        zone.getBlocks().clear();
        zone.getBlocks().add(block);

        assertFalse(control.endGame());
    }

    @Test
    void testAllClear_TrueWhenBoardIsEmpty() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                zone.getGrid()[i][j].setOn(false);
            }
        }

        assertTrue(control.allClear());
    }

    @Test
    void testAllClear_FalseWhenSomeBoxIsOn() {
        zone.getGrid()[2][3].setOn(true);

        assertFalse(control.allClear());
    }
    @Test
    void testCanPlaceShapeAt_whenPlaceIsFree() {
        Box[][] grid = new Box[6][6];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                grid[i][j] = new Box();

        ArrayList<String[]> shape = new ArrayList<>();
        shape.add(new String[]{"1", "1"});
        shape.add(new String[]{"1", "0"});

        boolean result = control.canPlaceShapeAt(shape, 0, 0, grid);
        assertTrue(result);
    }

    @Test
    void testCanPlaceShapeAt_whenPlaceIsAlreadyOccupied() {
        Box[][] grid = new Box[6][6];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                grid[i][j] = new Box();

        grid[1][0].setOn(true);

        ArrayList<String[]> shape = new ArrayList<>();
        shape.add(new String[]{"1", "1"});
        shape.add(new String[]{"1", "0"});

        boolean result = control.canPlaceShapeAt(shape, 0, 0, grid);
        assertFalse(result);
    }

    @Test
    void testCanFitAnywhere_whenThereIsSpace() {
        Block block = new Block(1,zone);
        ArrayList<String[]> shape = new ArrayList<>();
        shape.add(new String[]{"1"});
        shape.add(new String[]{"1"});
        block.setShape(shape);

        zone.getBlocks().clear();
        zone.getBlocks().add(block);

        boolean result = control.canFitAnywhere(block);
        assertTrue(result);
    }

    @Test
    void testCanFitAnywhere_FullGrid() {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                zone.getGrid()[i][j].setOn(true);

        Block block = new Block(1,zone);
        ArrayList<String[]> shape = new ArrayList<>();
        shape.add(new String[]{"1"});
        block.setShape(shape);

        zone.getBlocks().clear();
        zone.getBlocks().add(block);

        boolean result = control.canFitAnywhere(block);
        assertFalse(result);
    }
}

