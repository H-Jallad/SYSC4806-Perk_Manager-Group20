package perkmanager;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Perk> perkList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Person> personList;
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
        perk.setPerkName(name);
        perk.setPerkDescription(description);
        addPerk(perk);
    }

    public List<Perk> getPerkList(){
        return perkList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Membership: ").append(name).append("\n");
        sb.append("perkList=").append(perkList);
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
