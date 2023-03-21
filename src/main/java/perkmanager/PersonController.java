package perkmanager;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        return "viewAllPerks";
    }

    @GetMapping("/membership")
    public String showMembershipForm(Model model) {
        List<Membership> memberships = new ArrayList<>();
        Membership membership = new Membership();
        membership.setName("CAA");
        memberships.add(membership);
        model.addAttribute(memberships);
    //model.addAttribute("membershipTypes", Arrays.asList("CAA", "AMEX"));
    //model.addAttribute("membership", new Membership());
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

    @GetMapping("/perk-manager")
    public String addUser(Model model)
    {
        Person newPerson = new Person();
        model.addAttribute("newPerson", newPerson);
        //personRepository.save(newPerson);
        return "LandingPage";
    }

    @PostMapping("/perk-manager/view")
    public String userSubmit(@ModelAttribute Person newPerson, Model model)
    {
        personRepository.save(newPerson);
        model.addAttribute("newPerson", newPerson);
        return "result";
    }


    @GetMapping("/my-memberships-content")
    public String myMembershipsContent(Model model) {
        Person testPerson = new Person();
        List<Membership> memberships = membershipRepository.findAll();
//        List<Membership> memberships = new ArrayList<>();
//        Membership membership1 = new Membership();
//        Membership membership2 = new Membership();
//        Membership membership3 = new Membership();
//        Membership membership4 = new Membership();
//        membership1.setName("CAA");
//        membership1.setImagePath("/img/memberships/CAA.png");
//        membership2.setName("AMEX");
//        membership2.setImagePath("/img/memberships/AMEX.png");
//        membership3.setName("Air Miles");
//        membership3.setImagePath("/img/memberships/Air-Miles.png");
//        membership4.setName("Capital One");
//        membership4.setImagePath("/img/memberships/Capital-One.png");
//
//        memberships.add(membership1);
//        memberships.add(membership2);
//        memberships.add(membership3);
//        memberships.add(membership4);
//        membershipRepository.save(membership1);
//        membershipRepository.save(membership2);
//        membershipRepository.save(membership3);
//        membershipRepository.save(membership4);
        //testPerson.addMembership(membership1);
        //testPerson.addMembership(membership2);
        //testPerson.addMembership(membership3);
        //testPerson.addMembership(membership4);

        model.addAttribute("memberships", memberships);
        personRepository.save(testPerson);
        //model.addAttribute("memberships", memberships);
        // add memberships data to model
        //model.addAttribute("memberships", personRepository.findById(1L).getMembershipList());
        // return view name for Thymeleaf fragment
        return "userMembership :: content";
    }
}
