package perkmanager;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Perk {

    @Id
    @GeneratedValue
    private Long id;

    private String description; 
    private String name;

    public Perk() {

    }

    public void setDescriptions(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append('\n');
        sb.append(description);
        return sb.toString();
    }
}
