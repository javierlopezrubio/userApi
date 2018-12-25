package users.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import users.entities.Address;

public interface AddressRepo extends MongoRepository<Address,Integer> {
    List<Address> findAll();
    Address findByIda(int id);
    boolean existsByIda(int id);
    void deleteByIda(int id);

}