package perkmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/createUser")
//    public ResponseEntity<Person> createUser() {
//        Person newPerson = new Person();
//        personRepository.save(newPerson);
//        return new ResponseEntity<>(newPerson, HttpStatus.OK);
//    }

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
