package perkmanager;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends CrudRepository<Membership, Long>{

    @Override
    List<Membership> findAll();

    Optional<Membership> findById(long id);

    Membership findByName(String name);
}
