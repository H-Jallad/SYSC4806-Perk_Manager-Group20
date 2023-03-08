package perkmanager;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;


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
public class WebTestPerks {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private PersonRepository personRepository;



    @BeforeEach
    public void setUp() {
        // Reset your database or perform any other necessary setup steps here
        membershipRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void TestViewAllPerks() throws Exception {

        Membership membership = new Membership();
        membership.setName("CAA");
        membershipRepository.save(membership);


        String perkName1 = "Free parking";
        String perkDescription1 = "Get free parking at participating airports.";

        mockMvc.perform(post("/addPerk")
                        .param("name", membership.getName())
                        .param("perkName", perkName1)
                        .param("perkDescription", perkDescription1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(perkName1)))
                .andExpect(content().string(containsString(perkDescription1)));

        String perkName2 = "Free Dinner";
        String perkDescription2 = "Get free parking a Dinner at The Keg.";

        mockMvc.perform(post("/addPerk")
                        .param("name", membership.getName())
                        .param("perkName", perkName2)
                        .param("perkDescription", perkDescription2))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(perkName2)))
                .andExpect(content().string(containsString(perkDescription2)));

        membershipRepository.save(membership);

        mockMvc.perform(get("/perks-view"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("memberships", hasSize(1)))
                .andExpect(model().attribute("memberships", hasItem(
                        allOf(
                                hasProperty("name", is(membership.getName())),
                                hasProperty("perkList", containsInAnyOrder(
                                        hasProperty("perkName", is(perkName1)),
                                        hasProperty("perkName", is(perkName2))
                                ))
                        )
                )));
    }


}
