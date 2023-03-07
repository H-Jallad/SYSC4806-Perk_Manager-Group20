package perkmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    MembershipService membershipService;

    @Autowired
    MembershipRepository membershipRepository;
    @RequestMapping("/users")
    public ResponseEntity<Iterable<Person>> getAllUsers() {
        Iterable<Person> people = personRepository.findAll();
        return ResponseEntity.ok(people);
    }
    @RequestMapping("/addPerk")
    public ResponseEntity<Membership> addPerk(@RequestParam(value="membership") int membership, @RequestParam(value = "name") String name, @RequestParam(value = "description") String description){
        Membership membership1 = new Membership();
        membership1.setName("AMEX");
        membershipRepository.save(membership1);
        membershipService.updateMembership(membership1.getName(), name, description);

        return ResponseEntity.ok(membership1);
    }

    @GetMapping("/membership")
    public String showMembershipForm(Model model) {
    model.addAttribute("membershipTypes", Arrays.asList("CAA", "AMEX"));
    model.addAttribute("membership", new Membership());
    return "membership";
    }

    @PostMapping("/membership")
    public String membershipSubmit(@ModelAttribute Membership membership, Model model) {
        membershipRepository.save(membership);
        Person person = personRepository.findById(1);
        person.addMembership(membership);
        personRepository.save(person);
        model.addAttribute("membership", membership);
        return "display-membership";
    }

    @GetMapping("/memberships-view")
    public String membershipView(Model model){
        Person person = personRepository.findById(1);
        List<Membership> memberships = person.getMembershipList();
        model.addAttribute("memberships", memberships);

        return "personsMemberships";
    }


    @GetMapping("/perk-manager")
    public String addUser(Model model)
    {
        Person newPerson = new Person();
        model.addAttribute("newPerson", newPerson);
        //personRepository.save(newPerson);
        return "person";
    }

    @PostMapping("/perk-manager/view")
    public String userSubmit(@ModelAttribute Person newPerson, Model model)
    {
        personRepository.save(newPerson);
        model.addAttribute("newPerson", newPerson);
        return "result";
    }

}
