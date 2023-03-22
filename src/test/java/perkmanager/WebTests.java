package perkmanager;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class WebTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MembershipRepository membershipRepository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PerkRepository perkRepository;

    @MockBean
    private MembershipService membershipService;


    @Test
    public void testMembershipView() throws Exception {
        Membership membership1 = new Membership();
        membership1.setId(1L);
        membership1.setName("CAA");
        Membership membership2 = new Membership();
        membership2.setId(2L);
        membership2.setName("AMEX");
        List<Membership> memberships = Arrays.asList(membership1, membership2);

        Mockito.when(membershipRepository.findAll()).thenReturn(memberships);

        mockMvc.perform(MockMvcRequestBuilders.get("/all-perks-content"))
                .andExpect(MockMvcResultMatchers.status().isOk());



    }


}


