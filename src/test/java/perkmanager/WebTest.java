package perkmanager;


import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class WebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void testMembershipFlow() throws Exception {

        Person person = new Person();
        personRepository.save(person);


        Membership membership = new Membership();
        membership.setName("CAA");
        membershipRepository.save(membership);


        person.addMembership(membership);
        personRepository.save(person);


        String perkName = "Free parking";
        String perkDescription = "Get free parking at participating airports.";

        mockMvc.perform(post("/addPerk")
                        .param("name", membership.getName())
                        .param("perkName", perkName)
                        .param("perkDescription", perkDescription))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(perkName)))
                .andExpect(content().string(containsString(perkDescription)));


        Membership updatedMembership = membershipRepository.findByName(membership.getName());
        List<Perk> perks = updatedMembership.getPerkList();
        assertEquals(1, perks.size());
        assertEquals(perkName, perks.get(0).getPerkName());
        assertEquals(perkDescription, perks.get(0).getPerkDescription());
    }



    @Test
    public void testAddUser() throws Exception {
        mockMvc.perform(get("/perk-manager"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("newPerson", instanceOf(Person.class)))
                .andExpect(view().name("person"));
    }




    }
