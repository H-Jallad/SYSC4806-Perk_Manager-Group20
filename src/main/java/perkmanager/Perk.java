package perkmanager;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Perk {

    @Id
    @GeneratedValue
    private Long id;

    private String perkDescription;
    private String perkName;

    public Perk() {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(perkName).append('\n');
        sb.append(perkDescription);
        return sb.toString();
    }
}
