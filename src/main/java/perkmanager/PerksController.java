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
public class PerksController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MembershipService membershipService;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    PerkRepository perkRepository;

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

    @GetMapping("/all-perks-view")
    public String allPerksView(Model model) {
        List<Perk> perks = perkRepository.findAll();
        List<Perk> sortedPerks = membershipService.sortPerks(perks);
        model.addAttribute("perks", sortedPerks);

        return "viewAllPerks";
    }

    @GetMapping("/my-perks-content")
    public String myPerksContent(Model model) {

        // following code is for testing
        List<Perk> perks = new ArrayList<>();

        Perk perk = new Perk();

        perk.setPerkName("TEsting");
        perk.setPerkDescription("This is just a test");
        perk.setExpirationDate("2023-08-09");

        Perk perk2 = new Perk();

        perk2.setPerkName("TEsting2");
        perk2.setPerkDescription("This is just a test2");
        perk2.setExpirationDate("2023-08-10");

        perkRepository.save(perk);
        perkRepository.save(perk2);
        perks.add(perk);
        perks.add(perk2);

//        retrieve current logged in user perk list

        model.addAttribute("perks", perks);
        // return view name for Thymeleaf fragment
        return "allPerks :: content";
    }
}
