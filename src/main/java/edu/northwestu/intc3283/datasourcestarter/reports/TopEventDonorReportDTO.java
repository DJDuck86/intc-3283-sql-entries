package edu.northwestu.intc3283.datasourcestarter.reports;

public class TopEventDonorReportDTO {

    /*
    SELECT d.id,
               d.first_name,
               d.last_name,
               d.phone,
               SUM(donation.amount) as amount_given
        FROM nu.donors d
        JOIN nu.donations donation
        WHERE donation.donor_id = d.id AND
              donation.created_at >= NOW() - TIMESTAMP(LAST_DAY(NOW() - INTERVAL 1 MONTH) + INTERVAL 1 DAY)
        GROUP BY d.id
        ORDER BY amount_given DESC
        LIMIT 5;
     */

    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private int amountGiven;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAmountGiven() {
        return amountGiven;
    }

    public void setAmountGiven(int amountGiven) {
        this.amountGiven = amountGiven;
    }

}
