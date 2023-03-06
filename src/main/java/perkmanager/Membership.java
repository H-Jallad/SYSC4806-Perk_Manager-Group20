package perkmanager;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Membership {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Perk> perkList;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<User> userList;
    private String name;

    public Membership(){
        perkList = new ArrayList<>();
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addPerk(Perk perk) {
        this.perkList.add(perk);
    }

    public void createPerk(String name, String description) {
        Perk perk = new Perk();
        perk.setName(name);
        perk.setDescriptions(description);
        addPerk(perk);
    }

    public List<Perk> getPerkList(){
        return perkList;
    }

    public void addUser(User user) {
        this.userList.add(user);
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Membership: ").append(name).append("\n");
        sb.append("perkList=").append(perkList);
        return sb.toString();
    }
}
