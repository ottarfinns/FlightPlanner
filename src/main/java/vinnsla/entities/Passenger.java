package vinnsla.entities;

public class Passenger {
    private String nationalId;
    private String passportNr;
    private String name;
    private String email;
    private String phoneNr;
    private String country;
    private String address;
    private String city;

    // Constructor
    public Passenger(String nationalId, String passportNr, String name,
                    String email, String phoneNr, String country, String address, String city) {
        this.nationalId = nationalId;
        this.passportNr = passportNr;
        this.name = name;
        this.email = email;
        this.phoneNr = phoneNr;
        this.country = country;
        this.address = address;
        this.city = city;
    }

    // Getters and setters
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassportNr() {
        return passportNr;
    }

    public void setPassportNr(String passportNr) {
        this.passportNr = passportNr;
    }

    public String getName() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
