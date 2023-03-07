package perkmanager;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Long>{

    @Override
    List<Person> findAll();

    Optional<Person> findById(Long id);
}
