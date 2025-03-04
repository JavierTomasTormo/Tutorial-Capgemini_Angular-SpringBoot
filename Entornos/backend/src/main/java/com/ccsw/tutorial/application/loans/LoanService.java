package com.ccsw.tutorial.application.loans;

import com.ccsw.tutorial.domain.loans.Loan;
import com.ccsw.tutorial.presentation.loans.model.LoanDto;
import com.ccsw.tutorial.presentation.loans.model.LoanSearchDto;

import java.util.List;

public interface LoanService {

    List<Loan> findAll();
    
    List<Loan> findByArgs(LoanSearchDto searchDto);

    Loan findById(Long id) throws Exception;

    void save(LoanDto dto) throws Exception;

    void delete(Long id) throws Exception;
}