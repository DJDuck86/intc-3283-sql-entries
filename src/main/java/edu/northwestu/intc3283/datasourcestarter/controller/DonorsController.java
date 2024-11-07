package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.repository.DonorsRepository;
import edu.northwestu.intc3283.datasourcestarter.util.DataGeneratorService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class DonorsController {

    private final DonorsRepository donorRepository;
    private final DataGeneratorService dataGeneratorService;

    public DonorsController(DonorsRepository donorRepository, DataGeneratorService dataGeneratorService) {
        this.donorRepository = donorRepository;
        this.dataGeneratorService = dataGeneratorService;
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Donor>> searchDonors(@RequestParam("searchTerm") String searchTerm) {
        List<Donor> donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);

        if (donors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(donors);
        }
    }


    @GetMapping("")
    public String getAll(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model) {
        List<Donor> donors;

        // If searchTerm is provided and non-empty, search by name
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
        } else {
            // Otherwise, retrieve the 10 most recent donors
            donors = donorRepository.findTop10ByOrderByCreatedAtDesc();
        }

        model.addAttribute("donors", donors);
        model.addAttribute("searchTerm", searchTerm);
        return "donors/index";
    }

    @GetMapping("/report/janice")
    public String topDonorsReport(Model model, @Param("limit") Integer limit) {
        if (null == limit || limit < 1) {
            limit = 5;
        }
        model.addAttribute("topDonors", donorRepository.findTopDonors(limit));
        return "donors/report-janice";
    }

    @GetMapping("/report/larry")
    public String taxInfoReport(Model model) {
        model.addAttribute("taxInfo", donorRepository.findTaxInfo());
        return "donors/report-larry";
    }

    @GetMapping("/report/jennifer")
    public String weeklyTrendReport(Model model, @Param("weeks") Integer weeks, @Param("months") Integer months) {
        if (null == weeks || weeks < 1) {
            weeks = 5;
        }
        LocalDate startDate = LocalDate.now().minusWeeks(weeks);
        LocalDate endDate = LocalDate.now();
        model.addAttribute("weeklyTrend", donorRepository.findWeeklyTrendInfo(startDate, endDate));

        if (null == months || months < 1) {
            months = 1;
        }
        model.addAttribute("monthlyTrend", donorRepository.findMonthlyTrendInfo(months));

        model.addAttribute("topEventDonors", donorRepository.findTopEventDonorInfo());
        return "donors/report-jennifer";
    }

    @GetMapping("/donors/random")
    public String generateRandomDonors(@RequestParam("numDonors") int numDonors, @RequestParam("maxDonationsPerDonor") int maxDonationsPerDonor) {
        this.dataGeneratorService.generateRandomDonorsAndDonations(numDonors, maxDonationsPerDonor);
        return "redirect:/";
    }
}
