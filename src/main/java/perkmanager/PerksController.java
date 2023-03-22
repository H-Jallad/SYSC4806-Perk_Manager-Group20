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

    @GetMapping("/all-perks-content")
    public String allPerksContent(Model model) {

        model.addAttribute("perks", perkRepository.findAll());
        // return view name for Thymeleaf fragment
        return "allPerks :: content";
    }

    @GetMapping("/my-perks-content")
    public String myPerksContent(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Perk> perks = new ArrayList<>();
        for(Membership membership : personRepository.findByUsername(auth.getName()).getMembershipList()){
            perks.addAll(membership.getPerkList());
        }
        model.addAttribute("perks", perks);
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

    @PostMapping("/addPerk/{id}/{name}/{description}/{expirationDate}")
    @ResponseBody
    public void addPerk(@PathVariable("id") Long membershipId, @PathVariable("name") String perkName, @PathVariable("description") String perkDescription, @PathVariable("expirationDate") String expirationDate) {
        Membership membership = membershipService.findById(membershipId);
        Perk perk = new Perk();
        perk.setPerkName(perkName);
        perk.setPerkDescription(perkDescription);
        perk.setExpirationDate(expirationDate);
        perk.setMembership(membership);
        membership.addPerk(perk);
        perkRepository.save(perk);
        membershipRepository.save(membership);

    @GetMapping("/viewMembershipPerks/{id}")
    public String viewMembershipPerks(@PathVariable("id") Long membershipId, Model model) {
        model.addAttribute("perks", membershipRepository.findById(membershipId).get().getPerkList());
        // return view name for Thymeleaf fragment
        return "allPerks :: content";
    }
}
