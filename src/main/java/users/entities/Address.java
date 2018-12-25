package users.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "address")
public class Address {
    @Id
    private int ida;
    private String street;
    private String state;
    private String city;
    private String country;
    private String zip;

    public Address(){}

    public Address(String street, String state, String city, String country, String zip){
        setStreet(street);
        setState(state);
        setCity(city);
        setCountry(country);
        setZip(zip);
    }

    public int getIda() {
        return ida;
    }

    public void setIda(int id) {
        this.ida = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean equalAddress(Address add){
        return ((this.street.equals(add.street)) &&
                (this.state.equals(add.state)) &&
                (this.city.equals(add.city)) &&
                (this.country.equals(add.country)) &&
                (this.zip.equals(add.zip)));
    }
}
