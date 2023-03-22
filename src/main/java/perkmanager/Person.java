package perkmanager;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 *  This class represents the Users in the Perk Manager system.
 *
 * @author Santhosh Pradeepan
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Membership> membershipList;
    private boolean signedIn; //To be used in a future iteration.

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    private String username;
    private String password;

    private String name;

    /**
     * Constructor for class User.
     */
    public Person() {
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

    public void removeMembership(Optional<Membership> membership) {
        if (membership != null) {
            membershipList.remove(membership);
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
