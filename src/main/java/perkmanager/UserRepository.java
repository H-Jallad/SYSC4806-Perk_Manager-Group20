package perkmanager;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    @Override
    List<User> findAll();

    Optional<User> findById(Long id);
}
