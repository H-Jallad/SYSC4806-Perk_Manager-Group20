package perkmanager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private PerkRepository perkRepository;

//    @PostConstruct
//    public void parseMembershipFile() throws IOException{
//        //Create an ObjectMapper instance
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        //Read the JSON file as a JsonNode
//        File jsonFile;
//        JsonNode jsonNode;
//
//        jsonFile = new ClassPathResource("memberships.json").getFile();
//        jsonNode = objectMapper.readTree(jsonFile);
//
//        //Get the array of memberships
//        JsonNode membershipsArray = jsonNode.get("membership");
//
//        //Iterate over the array and convert each element to a Membership object
//        for (JsonNode membershipNode : membershipsArray) {
//            Membership membership = objectMapper.convertValue(membershipNode, Membership.class);
//            //Save the membership into your repository
//            membershipRepository.save(membership);
//        }
//    }

    public List<Membership> findAll() {
        return membershipRepository.findAll();
    }

    public Membership findById(Long id) {
        return membershipRepository.findById(id).orElse(null);
    }

    public Membership findByName(String name) {
        return membershipRepository.findByName(name);
    }

    public List<Perk> findAllPerks() {
        return perkRepository.findAll();
    }

    public Perk findByPerkId(Long id) {
        return perkRepository.findById(id).orElse(null);
    }

    public Perk findByPerkName(String perkName) {
        return perkRepository.findByPerkName(perkName);
    }

    public void updateMembership(String membershipName, String perkName, String perkDescription) {
        Membership membership = findByName(membershipName);
        Perk perk = membership.createPerk(perkName, perkDescription);
        membershipRepository.save(membership);
        perkRepository.save(perk);
    }

    public void deleteById(Long id) {
        membershipRepository.deleteById(id);
    }

    public List<Perk> sortPerksByExpirationDate(List<Perk> perks) {
        int length = perks.size();
        //Bubble sort algorithm
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (comparePerkExpirationDate(perks.get(j), perks.get(j + 1))) {
                    Perk tempPerk = perks.get(j);
                    perks.set(j, perks.get(j + 1));
                    perks.set(j + 1, tempPerk);
                }
            }
        }
        return perks;
    }

    private boolean comparePerkExpirationDate(Perk perk1, Perk perk2) {
        if (perk2.getExpirationYear() < perk1.getExpirationYear()) {
            return true;
        }
        else if (perk2.getExpirationYear() == perk1.getExpirationYear()) {
            if (perk2.getExpirationMonth() < perk1.getExpirationMonth()) {
                return true;
            }
            else if (perk2.getExpirationMonth() == perk1.getExpirationMonth()) {
                if (perk2.getExpirationDay() < perk1.getExpirationDay()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Perk> sortPerksByUsefulness(List<Perk> perks) {
        int length = perks.size();
        //Bubble sort algorithm
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (perks.get(j).getUseful() < perks.get(j + 1).getUseful()) {
                    Perk tempPerk = perks.get(j);
                    perks.set(j, perks.get(j + 1));
                    perks.set(j + 1, tempPerk);
                }
            }
        }
        return perks;
    }
}
