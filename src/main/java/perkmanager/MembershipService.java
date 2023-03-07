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

    public void updateMembership(String membershipName, String perkName, String perkDescription) {
        Membership membership = findByName(membershipName);
        membership.createPerk(perkName, perkDescription);
        membershipRepository.save(membership);
    }

    public void deleteById(Long id) {
        membershipRepository.deleteById(id);
    }
}
