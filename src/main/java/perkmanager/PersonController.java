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

    @GetMapping("/addPerk")
    public String showPerkForm(Model model){
        model.addAttribute("membershipType",Arrays.asList("CAA", "AMEX"));
        model.addAttribute("perk", new Perk());
        return "perk";
    }
    @PostMapping("/addPerk")
    public String addPerk(@ModelAttribute Membership membership, @ModelAttribute Perk perk, Model model) {
        Membership existingMembership = membershipRepository.findByName(membership.getName());
        if(existingMembership != null){
            if (!existingMembership.getPerkList().contains(perk)){
                existingMembership.addPerk(perk);
                perkRepository.save(perk);
                membershipRepository.save(existingMembership);
                model.addAttribute("perk", perk);
                model.addAttribute(("membership"),existingMembership);
            }
        }
        return "listOfPerks";
    }
    @GetMapping("/perks-view")
    public String perksView(Model model){
        List<Membership> memberships = membershipRepository.findAll();
        model.addAttribute("memberships", memberships);
        return "allPerks";
    }

    @GetMapping("/membership")
    public String showMembershipForm(Model model) {
        List<Membership> memberships = new ArrayList<>();
        Membership membership = new Membership();
        membership.setName("CAA");
        memberships.add(membership);
        model.addAttribute(memberships);
    model.addAttribute("membershipTypes", Arrays.asList("CAA", "AMEX"));
    model.addAttribute("membership", new Membership());
    return "membership";
    }

    @PostMapping("/membership")
    public String membershipSubmit(@ModelAttribute Membership membership, Model model) {
        Person person = personRepository.findById(1);
        if (!person.getMembershipList().contains(membership)) {
            membershipRepository.save(membership);
            person.addMembership(membership);
            personRepository.save(person);
            model.addAttribute("membership", membership);
        }
        return "display-membership";

    }
    @GetMapping("/memberships-view")
    public String membershipView(Model model){
        Person person = personRepository.findById(1);
        List<Membership> memberships = person.getMembershipList();
        model.addAttribute("memberships", memberships);

        return "personsMemberships";
    }

    @GetMapping("/all-perks-view")
    public String allPerksView(Model model) {
        List<Perk> perks = perkRepository.findAll();
        List<Perk> sortedPerks = membershipService.sortPerks(perks);
        model.addAttribute("perks", sortedPerks);

        return "viewAllPerks";
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


    @GetMapping("/my-memberships-content")
    public String myMembershipsContent(Model model) {
        Person testPerson = new Person();
        List<Membership> memberships = new ArrayList<>();
        Membership membership = new Membership();
        membership.setName("CAA");
        membership.setImagePath("/img/memberships/CAA.png");

        memberships.add(membership);
        testPerson.addMembership(membership);
        personRepository.save(testPerson);
        // add memberships data to model
        model.addAttribute("memberships", personRepository.findById(1L).getMembershipList());
        // return view name for Thymeleaf fragment
        return "userMembership :: content";
    }

    @GetMapping("/all-perks-content")
    public String allPerksContent(Model model) {

        model.addAttribute("perks", perkRepository.findAll());
        // return view name for Thymeleaf fragment
        return "allPerks :: content";
    }

    @PostMapping("/vote/{count}/{id}")
    @ResponseBody
    public Perk votePerk(@PathVariable("count") int count, @PathVariable("id") Long perkId, @RequestBody Map<String, String> payload) {
        String voteType = payload.get("voteType");
        Perk perk = perkRepository.findById(perkId).orElseThrow(() -> new IllegalArgumentException("Invalid perk ID"));
        if (voteType.equals("UPVOTE")) {
            perk.upvote(count);
        } else {
            perk.downvote(count);
        }
        perkRepository.save(perk);
        return perk;
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
