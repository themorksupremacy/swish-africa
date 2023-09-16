package Swish.Backend;

public class Address {

    //private variables
    private int houseNumber;
    private String streetName;
    private String suburb;
    private String city;
    private String province;
    private String country;
    private String zipCode;
    private int userID;
    private int addressID;

    //default constructor
    public Address(){

    }

    //parameterised constructor
    public Address(int houseNumber, String streetName, String suburb, String city, String province, String country, String zipCode, int userID, int addressID) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zipCode = zipCode;
        this.userID = userID;
        this.addressID = addressID;
    }

    //getter
    public int getHouseNumber() { return houseNumber; }

    //setter
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    //getter
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    //getter
    public String getSuburb() {
        return suburb;
    }

    //setter
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    //getter
    public String getCity() {
        return city;
    }

    //setter
    public void setCity(String city) {
        this.city = city;
    }

    //getter
    public String getProvince() {
        return province;
    }

    //setter
    public void setProvince(String province) {
        this.province = province;
    }

    //getter
    public String getCountry() {
        return country;
    }

    //setter
    public void setCountry(String country) {
        this.country = country;
    }

    //getter
    public String getZipCode() {
        return zipCode;
    }

    //setter
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    //getter
    public int getUserID() {
        return userID;
    }

    //setter
    public void setUserID(int userID) {
        this.userID = userID;
    }

    //getter
    public int getAddressID() {
        return addressID;
    }

    //setter
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    //toString method used for displaying addresses.
    public String toString(){
        return "House Number: " + this.houseNumber + "\nStreet: " + this.streetName + "\nSuburb: " + this.suburb + "\nCity: "
                + this.city + "\nProvince: " + this.province + "\nCountry: " + this.country + "\nZip Code: " + this.zipCode;
    }
}
