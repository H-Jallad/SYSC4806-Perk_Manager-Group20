package perkmanager;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Long>{

    @Override
    List<Person> findAll();

    Person findById(long id);
}
