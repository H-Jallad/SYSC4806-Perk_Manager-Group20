package perkmanager;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  This class represents the Users in the Perk Manager system.
 *
 * @author Santhosh Pradeepan
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;
    private List<Membership> membershipList;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Perk> availablePerks;
    private boolean signedIn; //To be used in a future iteration.

    /**
     * Constructor for class User.
     */
    public User() {
        membershipList = new ArrayList();
        availablePerks = new ArrayList();
    }

    /**
     * Function used to create a profile for the user, which includes all the memberships.
     * This function also gets a list of all the available perks associated with the profile.
     *
     * @param memberships The list of all memberships
     */
    public void createProfile(ArrayList<String> memberships) {
        Perk tempPerk = new Perk();
        for (String membership : memberships) {
            Membership tempMembership = verifyMembership(membership);
            if (tempMembership != null) {
                if (!membershipList.contains(tempMembership)) {
                    membershipList.add(tempMembership);
                }
            }
        }
        availablePerks = tempPerk.retrieveAvailablePerks(membershipList);
    }

    /**
     * Function used to check a potential membership and see if it is a valid membership.
     *
     * @param membership The string containing the membership name
     * @return The official membership if it is valid or null if invalid
     */
    private Membership verifyMembership (String membership) {
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        for (Membership m : Membership.values()) {
            pattern = Pattern.compile(m.getMembershipName(), Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(membership);
            matchFound = matcher.find();
            if(matchFound) {
                return m;
            }
        }
        return null;
    }

    public String toString() {
        String temp = String.format(
                "User[id=%d]",
                id);
        for (Membership membership: membershipList) {
            temp += "\n";
            temp += membership.toString();
        }

        for (Perk perk: availablePerks) {
            temp += "\n";
            temp += perk.toString();
        }
        return temp;
    }

}
