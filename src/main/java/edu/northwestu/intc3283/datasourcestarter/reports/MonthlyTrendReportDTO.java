package edu.northwestu.intc3283.datasourcestarter.reports;

public class MonthlyTrendReportDTO {

    /*
                            SELECT  DATE_FORMAT(created_at, '%Y-%m')    AS donation_month,
                                SUM(amount)                         AS total_donated
                        FROM donations
                        GROUP BY donation_month
                        ORDER BY donation_month ASC
     */

    private String donationMonth;
    private int totalDonated;

    public String getDonationMonth() {
        return donationMonth;
    }

    public void setDonationMonth(String donationMonth) {
        this.donationMonth = donationMonth;
    }


    public int getTotalDonated() {
        return totalDonated;
    }

    public void setTotalDonated(int totalDonated) {
        this.totalDonated = totalDonated;
    }



}
