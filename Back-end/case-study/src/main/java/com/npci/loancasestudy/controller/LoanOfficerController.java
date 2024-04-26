package com.npci.loancasestudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.npci.loancasestudy.model.CreditScore;
import com.npci.loancasestudy.model.LoanApplication;
import com.npci.loancasestudy.service.CreditScoreService;
import com.npci.loancasestudy.service.LoanApplicationService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/loan-officers")
@CrossOrigin(origins = "*")
public class LoanOfficerController {
    @Autowired
    private LoanApplicationService loanApplicationService;
    @Autowired
    private CreditScoreService creditScoreService;

    @GetMapping("/applications")
    public ResponseEntity<List<LoanApplication>> viewApplications() {
        return ResponseEntity.ok(loanApplicationService.getAllLoanApplications());
    }

    @PutMapping("/update/{applicationId}")
    public ResponseEntity<LoanApplication> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> updateRequest
    ) {
        String status = updateRequest.get("status");
        LoanApplication updatedApplication = loanApplicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(updatedApplication);
    }

    @GetMapping("/credit-score/{pan}")
    public ResponseEntity<CreditScore> viewCreditScore(@PathVariable String pan) {
        Optional<CreditScore> creditScore = creditScoreService.getCreditScoreByPAN(pan);
        return creditScore.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}