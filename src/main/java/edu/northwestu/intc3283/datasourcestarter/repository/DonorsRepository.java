package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.reports.*;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DonorsRepository extends CrudRepository<Donor, Long> {

    List<Donor>  findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    List<Donor> findTop10ByOrderByCreatedAtDesc();

    @Query("""
            
                        SELECT
                              d.first_name AS first_name,
                              d.last_name AS last_name,
                              d.email AS email,
                              YEAR(dn.created_at) AS year,
                              MONTH(dn.created_at) AS month,
                              SUM(dn.amount) AS total_donation_amount
                          FROM
                              donors d
                          JOIN
                              donations dn ON d.id = dn.donor_id
                          GROUP BY
                              d.id,
                              YEAR(dn.created_at),
                              MONTH(dn.created_At)
                          ORDER BY
                              YEAR(dn.created_at) DESC,
                              MONTH(dn.created_at) DESC,
                              SUM(dn.amount) DESC
                        LIMIT :limit
            """)
    List<TopDonationReportDTO> findTopDonors(@Param("limit") Integer limit);

    @Query("""
                        SELECT donor_id,
                               first_name AS first_name,
                               last_name AS last_name,
                               address1,
                               address2,
                               city,
                               state,
                               zip_code AS zip_code,
                               phone,
                               SUM(d.amount) AS total_donated,
                               MAX(d.created_at) AS last_donated_on
                        FROM donors RIGHT JOIN donations d ON donors.id = d.donor_id
                        WHERE phone <> ''
                        AND (
                                address1 = ''
                                OR address2 = ''
                                OR city = ''
                                OR state = ''
                        )
                        GROUP BY donor_id
""")
    List<TaxReportDTO> findTaxInfo();

    @Query("""
                        SELECT  DATE_FORMAT(created_at, '%Y-%u')    AS donation_week,
                                SUM(amount)                         AS total_donated
                        FROM donations
                        WHERE created_at BETWEEN :started_at AND :ended_at
                        GROUP BY donation_week
                        ORDER BY donation_week DESC
        """)
    List<WeeklyTrendReportDTO> findWeeklyTrendInfo(@Param("started_at") LocalDate startedAt,
                                                   @Param("ended_at") LocalDate endedAt);

    @Query("""
                        SELECT  DATE_FORMAT(created_at, '%Y-%m')    AS donation_month,
                                SUM(amount)                         AS total_donated
                        FROM donations
                        GROUP BY donation_month
                        ORDER BY donation_month DESC
                        LIMIT :months
    """)
    List<MonthlyTrendReportDTO> findMonthlyTrendInfo(@Param("months") Integer months);

    @Query("""
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
""")
    List<TopEventDonorReportDTO> findTopEventDonorInfo();
}
