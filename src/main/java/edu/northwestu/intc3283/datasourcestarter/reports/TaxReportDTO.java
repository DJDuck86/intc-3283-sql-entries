package edu.northwestu.intc3283.datasourcestarter.reports;

import org.springframework.data.relational.core.mapping.Column;
import java.math.BigDecimal;

public class TaxReportDTO {

    /*
                            SELECT donor_id,
                               first_name AS firstName,
                               last_name AS lastName,
                               address1,
                               address2,
                               city,
                               state,
                               zip_code,
                               phone,
                               SUM(d.amount) AS total_donated,
                               SUM(d.created_at) AS last_donated on
     */

    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String phone;
    private String zipCode;

    private int year;
    private int month;

    private BigDecimal totalDonated;
    private String lastDonatedOn;

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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getTotalDonated() {
        return totalDonated;
    }

    public void setTotalDonated(BigDecimal totalDonated) {
        this.totalDonated = totalDonated;
    }

    public String getLastDonatedOn() {return lastDonatedOn;}

    public void setLastDonatedOn(String lastDonatedOn) {
        this.lastDonatedOn = lastDonatedOn;
    }
}
