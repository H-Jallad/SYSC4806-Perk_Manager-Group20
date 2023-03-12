package perkmanager;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PerkRepository extends CrudRepository<Perk, Long> {

    @Override
    List<Perk> findAll();

    Perk findById(long id);

    Perk findByPerkName(String perkName);
}
