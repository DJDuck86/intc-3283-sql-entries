package edu.northwestu.intc3283.datasourcestarter.reports;

public class WeeklyTrendReportDTO {

    /*
                            SELECT DATE_FORMAT(created_at, '%Y-%m%')    AS week_donated,
                            SUM(amount)                                 AS total_donated
                            FROM donations
                            WHERE created_at BETWEEN '2024-10-07' AND '2024-10-15'
                            GROUP BY week_donated
                            ORDER BY week_donated ASC
     */

    private String donationWeek;
    private int totalDonated;

    public String getDonationWeek() {
        return donationWeek;
    }

    public void setDonationWeek(String donationWeek) {
        this.donationWeek = donationWeek;
    }


    public int getTotalDonated() {
        return totalDonated;
    }

    public void setTotalDonated(int totalDonated) {
        this.totalDonated = totalDonated;
    }



}
