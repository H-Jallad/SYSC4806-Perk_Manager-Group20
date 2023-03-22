package perkmanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    MembershipService membershipService;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    PerkRepository perkRepository;

    @RequestMapping("/users")
    public ResponseEntity<Iterable<Person>> getAllUsers() {
        Iterable<Person> people = personRepository.findAll();
        return ResponseEntity.ok(people);
    }





    @GetMapping("/signup")
    public String addUser(Model model)
    {
        return "signup";
    }

    @PostMapping("/signup")
    public String userSubmit(@ModelAttribute Person newPerson, Model model) {
        // check if a person with the same name already exists
        Optional<Person> existingPerson = Optional.ofNullable(personRepository.findByUsername(newPerson.getUsername()));
        if (existingPerson.isPresent()) {
            // person with the same name already exists, add an error message to the model
            String errorMessage = "A person with the same username already exists";
            model.addAttribute("errorMessage", errorMessage);
            // return to the signup page
            return "signup";
        } else {
            // person with the same name does not exist, save the new person
            personRepository.save(newPerson);
            model.addAttribute("newPerson", newPerson);
            return "login";
        }
    }

    @GetMapping("/LandingPage")
    public String homePage(Authentication authentication, Model model) {
        if(authentication!=null) {
            String username = authentication.getName();
        }
        return "LandingPage";
    }





    @GetMapping("/auth-status")
    @ResponseBody
    public Map<String, Object> getAuthStatus() {
        boolean loggedIn;
        String userName;
        Map<String, Object> result = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName()!="anonymousUser"){
            loggedIn = auth.isAuthenticated();
            userName = auth.getName();
        } else {
            loggedIn = false;
            userName = "";
        }
        result.put("loggedIn", loggedIn);
        result.put("userName", userName);
        return result;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        System.out.println(auth.getName());
        return "LandingPage";
    }

}
