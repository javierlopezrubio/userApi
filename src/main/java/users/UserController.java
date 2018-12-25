package users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.entities.Address;
import users.entities.User;
import users.repo.AddressRepo;
import users.repo.UserRepo;
import users.services.NextIdService;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private NextIdService nextId;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserRepo userRepo;

    @PostConstruct
    private void generateData(){
    }

    @RequestMapping("/users/getUsers")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<List<?>>(userRepo.findAll(), HttpStatus.OK);
    }

    @PostMapping("/users/createUsers")
    public ResponseEntity<?> addUser(@RequestBody User newUser){
        try {
            if(!addressRepo.existsByIda(newUser.getAddress().getIda())){
                Address newAddress = newUser.getAddress();
                int ida = nextId.getNextId("address_id");
                newAddress.setIda(ida);
                addressRepo.save(newAddress);
            }else{
                Address choosenAddress = addressRepo.findByIda(newUser.getAddress().getIda());
                if(!choosenAddress.equalAddress(newUser.getAddress())) {
                    System.out.println("No es igual");
                    return new ResponseEntity<>("", HttpStatus.METHOD_NOT_ALLOWED);
                }
                System.out.println("Es igual");
            }
            int id = nextId.getNextId("user_id");
            System.out.println(id);
            newUser.setIdu(id);
            User created = userRepo.save(newUser);
            return new ResponseEntity<>(created, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping("/users/getUsersById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        if(!validateNumber(userId))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        else {
            if (!validateId(userId))
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            else {
                int id = Integer.parseInt(userId);
                userRepo.findUserByIdu(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }

    @PutMapping("/users/updateUsersById/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User toUpdate){
        if(!validateNumber(userId))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        else {
            if (!validateId(userId))
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            else {
                int id = Integer.parseInt(userId);
                Address updatedAddress = toUpdate.getAddress();
                if(updatedAddress.getIda()==0) {
                    if(!updatedAddress.equalAddress(userRepo.findUserByIdu(id).getAddress())){
                        int ida = nextId.getNextId("address_id");
                        updatedAddress.setIda(ida);
                        addressRepo.save(updatedAddress);
                    }
                }else{
                    if(!addressRepo.existsByIda(toUpdate.getAddress().getIda())){
                        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
                    }else{
                        int ida = toUpdate.getAddress().getIda();
                        addressRepo.deleteByIda(ida);
                        addressRepo.save(updatedAddress);
                    }
                }
                userRepo.deleteByIdu(id);
                toUpdate.setIdu(id);
                User updated = userRepo.save(toUpdate);
                return new ResponseEntity<>(updated, HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("/users/deleteUsersById/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        if(!validateNumber(userId))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        else {
            if (!validateId(userId))
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            else {
                int id = Integer.parseInt(userId);
                userRepo.deleteByIdu(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }


    private boolean validateId(String str){
        int id = Integer.parseInt(str);
        return userRepo.existsByIdu(id);
    }

    private boolean validateNumber(String str){
        try {
            int id = Integer.parseInt(str);
            return id > 0;
        } catch (NumberFormatException ex){
            return false;
        }
    }
}
