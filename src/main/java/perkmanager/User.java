package perkmanager;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 *  This class represents the Users in the Perk Manager system.
 *
 * @author Santhosh Pradeepan
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Membership> membershipList;
    private boolean signedIn; //To be used in a future iteration.

    /**
     * Constructor for class User.
     */
    public User() {
        membershipList = new ArrayList();
        signedIn = false;
    }

    /**
     * Function used to create a profile for the user, which includes all the memberships.
     * This function also gets a list of all the available perks associated with the profile.
     *
     * @param memberships The list of all memberships
     */
    public void createProfile(ArrayList<String> memberships) {
        for (String membership : memberships) {
            addMembership(membership);
        }
        signedIn = true;
    }

    /**
     * Function used to add a membership to the user profile.
     *
     * @param membership The membership to be added
     */
    public void addMembership(Membership membership) {
        if (membership != null) {
            if (!membershipList.contains(membership)) {
                membershipList.add(membership);
            }
        }
    }

    /**
     * Function used to add a membership to the user profile.
     *
     * @param name The name of the membership to be added
     */
    public void addMembership(String name) {
        MembershipService membershipService = new MembershipService();
        Membership tempMembership = membershipService.findByName(name);
        addMembership(tempMembership);
    }

    /**
     * Function used to get a list of all the user's memberships.
     *
     * @return The list of all user's memberships
     */
    public List<Membership> getMembershipList() {
        return membershipList;
    }

    public String toString() {
        String temp = String.format(
                "User[id=%d]",
                id);
        for (Membership membership: membershipList) {
            temp += "\n";
            temp += membership.toString();
        }
        return temp;
    }
}
