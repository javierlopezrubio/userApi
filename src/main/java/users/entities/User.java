package users.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
public class User {

    @Id
    private int idu;
    private String name;
    private String email;
    private String birthDate;
    private Address address;

    public User(){}

    public User(String name, String email, String birthDate, Address address){
        setName(name);
        setEmail(email);
        setAddress(address);
        setBirthDate(birthDate);
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int id) {
        this.idu = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
