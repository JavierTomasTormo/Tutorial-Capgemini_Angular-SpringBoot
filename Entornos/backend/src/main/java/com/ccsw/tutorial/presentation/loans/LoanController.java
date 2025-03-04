package com.ccsw.tutorial.presentation.loans;

import com.ccsw.tutorial.application.loans.LoanService;
import com.ccsw.tutorial.domain.loans.Loan;
import com.ccsw.tutorial.presentation.loans.model.LoanDto;
import com.ccsw.tutorial.presentation.loans.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Loan", description = "API de los Loan")
@RequestMapping(value = "/loans")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "getAll de los prestamos filtrados")
    @PostMapping("/search")
    public List<LoanDto> find(@RequestBody LoanSearchDto dto) {
        List<Loan> loans = this.loanService.findByArgs(dto);
        
        return loans.stream()
                .map(loan -> {
                    LoanDto loanDto = new LoanDto();
                    loanDto.setId(loan.getId());
                    loanDto.setGameId(loan.getGame().getId());
                    loanDto.setClientId(loan.getClient().getId());
                    loanDto.setLoanDate(loan.getLoanDate());
                    loanDto.setReturnDate(loan.getReturnDate());
                    return loanDto;
                })
                .collect(Collectors.toList());
    }

    @Operation(summary = "Save", description = "Update del prestamos")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody LoanDto dto) {
        try {
            this.loanService.save(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete", description = "Eliminar prestamo")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            this.loanService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}