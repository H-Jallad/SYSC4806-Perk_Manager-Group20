package perkmanager;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Perk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String perkDescription;
    private String perkName;

    private int useful;

    private String expirationDate;

    private int expirationYear;

    private int expirationMonth;

    private int expirationDay;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Membership membership;

    public Perk() {
        useful = 0;
    }

    public void setPerkDescription(String description){
        this.perkDescription = description;
    }

    public String getPerkDescription(){
        return perkDescription;
    }

    public void setPerkName(String name){
        this.perkName = name;
    }

    public String getPerkName(){
        return perkName;
    }

    public void upvote(int count) {
        useful += count;
    }

    public void downvote(int count) {
        useful -= count;
    }

    public int getUseful() {
        return useful;
    }

    public void setExpirationDate(String date) {
        expirationDate = date;
        String[] dateArray = date.split("-");
        expirationYear = Integer.parseInt(dateArray[0]);
        expirationMonth = Integer.parseInt(dateArray[1]);
        expirationDay = Integer.parseInt(dateArray[2]);
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationDay() {
        return  expirationDay;
    }

    public Membership getMembership() {
        return membership;
    }

    public Long getId() {
        return id;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(perkName).append('\n');
        sb.append(perkDescription).append('\n');
        sb.append(expirationDate);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Perk that = (Perk) o;
        return Objects.equals(perkName, that.perkName) && Objects.equals(perkDescription, that.perkDescription);
    }

    // Override hashCode() method to generate a hash code for Membership objects
    @Override
    public int hashCode() {
        return Objects.hash(perkName, perkDescription);
    }
}
