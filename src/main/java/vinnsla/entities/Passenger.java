package vinnsla.entities;

public class Passenger {
    private String nationalId;
    private String passportNr;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNr;
    private String address;
    private String city;

    // Constructor
    public Passenger(String nationalId, String passportNr, String firstName, String lastName,
                    String email, String phoneNr, String address, String city) {
        this.nationalId = nationalId;
        this.passportNr = passportNr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNr = phoneNr;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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