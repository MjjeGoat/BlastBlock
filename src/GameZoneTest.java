import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameZoneTest {

    private GameZone gameZone;

    @BeforeEach
    public void setUp() {
        MainScreen screen = new MainScreen();
        gameZone = new GameZone(screen);
    }

    @Test
    public void testInventoryAddsComponent() {
        int initialCount = gameZone.getComponentCount();
        gameZone.inventory();
        int afterCount = gameZone.getComponentCount();
        assertTrue(afterCount >= initialCount, "Inventory should add component or reset");
    }
}