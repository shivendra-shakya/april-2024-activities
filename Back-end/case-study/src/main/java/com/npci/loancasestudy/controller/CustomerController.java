package com.npci.loancasestudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.npci.loancasestudy.model.Customer;
import com.npci.loancasestudy.model.Loan;
import com.npci.loancasestudy.model.LoanApplication;
import com.npci.loancasestudy.service.CustomerService;
import com.npci.loancasestudy.service.LoanApplicationService;
import com.npci.loancasestudy.service.LoanService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanApplicationService loanApplicationService;
    

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(registeredCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String emailId = credentials.get("emailId");
        String password = credentials.get("password");

        Optional<Customer> customer = customerService.findByEmailAndPassword(emailId, password);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    @GetMapping("/getAllLoan")
    public ResponseEntity<List<Loan>> getAllLoans(){
    	return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/loanApplications")
    public ResponseEntity<List<LoanApplication>> viewLoans() {
        return ResponseEntity.ok(customerService.getAllLoanApplication());
    }

    @PostMapping("/applyLoan")
    public ResponseEntity<LoanApplication> applyForLoan(@RequestBody LoanApplication application) {
    	if (application.getCustomer().getCustomerId() == null || application.getLoan().getLoanId() == null) {

        }
        LoanApplication appliedLoan = loanService.applyForLoan(application);
        return ResponseEntity.ok(appliedLoan);
    }

    @GetMapping("/loanStatusOfCustomer/{customerId}")
    public ResponseEntity<List<LoanApplication>> viewLoanStatus(@PathVariable Long customerId) {
        return ResponseEntity.ok(loanApplicationService.getApplicationsByCustomerId(customerId));
    }
}