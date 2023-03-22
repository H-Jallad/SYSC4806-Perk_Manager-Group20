package perkmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PerkTests {

    @Test
    public void testCreatePerk() {
        Perk perk = new Perk();
        assertNotNull(perk);
    }

    @Test
    public void testSetName() {
        Perk perk = new Perk();
        perk.setPerkName("50% off Ipad");
        assertEquals("50% off Ipad", perk.getPerkName());
    }

    @Test
    public void testSetDescription() {
        Perk perk = new Perk();
        perk.setPerkDescription("Get 50% off Any Ipad");
        assertEquals("Get 50% off Any Ipad", perk.getPerkDescription());
    }

}
