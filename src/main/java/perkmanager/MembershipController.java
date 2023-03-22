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

}
