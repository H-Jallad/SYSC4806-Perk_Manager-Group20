package perkmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PersonTests {




    @Test
    public void testAddMembership() {
        Person person = new Person();
        Membership membership = new Membership();
        membership.setName("Gold");
        person.addMembership(membership);
        List<Membership> memberships = person.getMembershipList();
        assertNotNull(memberships);
        assertEquals(1, memberships.size());
        assertEquals("Gold", memberships.get(0).getName());
    }


    @Test
    public void testGetMembershipList() {
        Person person = new Person();
        Membership membership1 = new Membership();
        membership1.setName("Gold");
        Membership membership2 = new Membership();
        membership2.setName("Silver");
        person.addMembership(membership1);
        person.addMembership(membership2);
        List<Membership> memberships = person.getMembershipList();
        assertNotNull(memberships);
        assertEquals(2, memberships.size());
        assertEquals("Gold", memberships.get(0).getName());
        assertEquals("Silver", memberships.get(1).getName());
    }

    @Test
    public void testToString() {
        Person person = new Person();
        Membership membership1 = new Membership();
        membership1.setName("Gold");
        membership1.createPerk("50% off Ipad", "Get 50% off any Ipad");
        Membership membership2 = new Membership();
        membership2.setName("Silver");
        membership2.createPerk("Boarding Priority", "Get Boarding priority on all flights");
        person.addMembership(membership1);
        person.addMembership(membership2);
        assertEquals("User[id=null]\nMembership: Gold\nperkList=[50% off Ipad\nGet 50% off any Ipad]\nMembership: Silver\nperkList=[Boarding Priority\nGet Boarding priority on all flights]", person.toString());
    }

}
