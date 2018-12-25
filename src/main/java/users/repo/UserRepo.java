package users.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import users.entities.User;

public interface UserRepo extends MongoRepository<User,Integer> {
    User findUserByIdu(int id);
    List<User> findAll();
    void deleteByIdu(int id);
    boolean existsByIdu(int id);

}
