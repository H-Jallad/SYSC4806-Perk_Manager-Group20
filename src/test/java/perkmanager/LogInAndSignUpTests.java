package perkmanager;

import org.junit.After;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LogInAndSignUpTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonRepository personRepository;


    @Test
    @WithMockUser
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser
    public void testSuccessfulLogin() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "mutaz")
                        .param("password", "mutaz"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/LandingPage")); // Change this to the URL you expect after successful login
    }

    @Test
    @WithMockUser
    public void testUnsuccessfulLogin() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "invalidUser")
                        .param("password", "invalidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    @WithMockUser
    public void testUnsuccessfulLoginUsername() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "invalidUser")
                        .param("password", "mutaz"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    @WithMockUser
    public void testUnsuccessfulLoginPassword() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "mutaz")
                        .param("password", "invalidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }



    @Test
    @WithMockUser
    public void testSignup() throws Exception {

        Person newPerson = new Person();
        newPerson.setUsername("user3");
        newPerson.setPassword("password3");



        mockMvc.perform(post("/signup")
                        .param("username", "user3")
                        .param("password", "password3"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        deletePersonFromDatabase("user3");

    }


    @Test
    @WithMockUser
    public void testSignupWithExistingUsername() throws Exception {

        Person existingPerson = new Person();
        existingPerson.setUsername("mutaz");
        existingPerson.setPassword("mutaz");



        mockMvc.perform(post("/signup")
                        .param("username", "mutaz")
                        .param("password", "mutaz"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attribute("errorMessage", "A person with the same username already exists"));


    }
    @Transactional
    @After
    public void deletePersonFromDatabase(String username) {
        Person personToDelete = personRepository.findByUsername(username);
        if (personToDelete != null) {
            personRepository.delete(personToDelete);
        }
    }
}







