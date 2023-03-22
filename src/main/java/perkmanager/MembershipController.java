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
public class MembershipController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MembershipService membershipService;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    PerkRepository perkRepository;

    @GetMapping("/myMemberships-content")
    public String allMyMembershipsContent(Model model) {
        List<Membership> memberships = personRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getMembershipList();
        List<Membership> allMemberships = membershipRepository.findAll();
        model.addAttribute("memberships", memberships);
        model.addAttribute("allMemberships", allMemberships);
        return "allMemberships :: content";
    }

    @PostMapping("/removeMembership/{id}")
    @ResponseBody
    public void removeMembership(@PathVariable("id") Long membershipId) {
        Person person = personRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Membership membership = membershipService.findById(membershipId);
        person.removeMembership(membership);
        personRepository.save(person);
        //membershipService.deleteById(membershipId);
    }

    @PostMapping("/addMembership/{id}")
    @ResponseBody
    public void addMembership(@PathVariable("id") Long membershipId) {
        Person person = personRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Membership> membership = membershipRepository.findById(membershipId);
        person.addMembership(membership.get());
        personRepository.save(person);
    }

}
