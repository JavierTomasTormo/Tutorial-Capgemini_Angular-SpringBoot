package com.ccsw.tutorial.application.loans;

import com.ccsw.tutorial.domain.loans.Loan;
import com.ccsw.tutorial.presentation.loans.model.LoanDto;
import com.ccsw.tutorial.presentation.loans.model.LoanSearchDto;

import java.util.List;

public interface LoanService {

    List<Loan> findAll();

    Loan findById(Long id) throws Exception;

    List<Loan> findByArgs(LoanSearchDto dto) throws Exception;

    void save(LoanDto dto) throws Exception;

    void delete(Long id) throws Exception;
}