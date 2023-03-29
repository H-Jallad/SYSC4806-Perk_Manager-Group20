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
        //List<Perk> perks = membershipService.sortPerksByUsefulness(perkRepository.findAll()); //Used for testing purposes
        List<Perk> perks = membershipService.sortPerksByExpirationDate(perkRepository.findAll());
        model.addAttribute("perks", perks);
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
        List<Perk> sortedPerks = membershipService.sortPerksByExpirationDate(perks);
        model.addAttribute("perks", sortedPerks);
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

    @PostMapping("/addPerk")
    @ResponseBody
    public void addPerk(@RequestBody Map<String, String> payload) {
        Long membershipId = Long.parseLong(payload.get("membershipId"));
        String perkName = payload.get("perkName");
        String perkDescription = payload.get("description");
        String expirationDate = payload.get("expirationDate");

        Membership membership = membershipService.findById(membershipId);
        Perk perk = new Perk();
        perk.setPerkName(perkName);
        perk.setPerkDescription(perkDescription);
        perk.setExpirationDate(expirationDate);
        perk.setMembership(membership);
        membership.addPerk(perk);
        perkRepository.save(perk);
        membershipRepository.save(membership);
    }

    @GetMapping("/viewMembershipPerks/{id}")
    public String viewMembershipPerks(@PathVariable("id") Long membershipId, Model model) {
        List<Perk> perks = membershipService.sortPerksByExpirationDate(membershipRepository.findById(membershipId).get().getPerkList());
        model.addAttribute("perks", perks);
        // return view name for Thymeleaf fragment
        return "allPerks :: content";
    }
}
