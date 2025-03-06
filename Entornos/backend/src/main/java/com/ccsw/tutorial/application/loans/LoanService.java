package com.ccsw.tutorial.application.loans;

import com.ccsw.tutorial.domain.loans.Loan;
import com.ccsw.tutorial.presentation.loans.model.LoanDto;
import com.ccsw.tutorial.presentation.loans.model.LoanSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanService {

    List<Loan> findAll();

    Loan findById(Long id) throws Exception;

    Page<Loan> findByArgs(LoanSearchDto dto, Pageable pageable);

    void save(LoanDto dto) throws Exception;

    void delete(Long id) throws Exception;
}