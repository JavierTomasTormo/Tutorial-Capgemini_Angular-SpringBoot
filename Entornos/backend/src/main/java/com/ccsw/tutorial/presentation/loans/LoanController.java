package com.ccsw.tutorial.presentation.loans;

import com.ccsw.tutorial.application.exceptions.BusinessRuleException;
import com.ccsw.tutorial.application.exceptions.EntityNotFoundException;
import com.ccsw.tutorial.application.exceptions.InvalidArgumentException;
import com.ccsw.tutorial.application.loans.LoanService;
import com.ccsw.tutorial.domain.loans.Loan;
import com.ccsw.tutorial.presentation.loans.model.LoanDto;
import com.ccsw.tutorial.presentation.loans.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda exitosa"),
        @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/search")
    public List<LoanDto> find(@RequestBody LoanSearchDto dto) {
        if (dto == null) {
            throw new InvalidArgumentException("Search criteria cannot be null");
        }

        try {
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
        } catch (Exception e) {
            throw new BusinessRuleException("Error searching loans: " + e.getMessage(), e);
        }
    }


    @Operation(summary = "Find By Id", description = "Get One de los prestamos")    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public LoanDto findById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new InvalidArgumentException("ID", id);
        }
        try {
            Loan loan = this.loanService.findById(id);
            
            if (loan == null) {
                throw new EntityNotFoundException("Loan", id);
            }
            
            LoanDto loanDto = new LoanDto();
            loanDto.setId(loan.getId());
            loanDto.setGameId(loan.getGame().getId());
            loanDto.setClientId(loan.getClient().getId());
            loanDto.setLoanDate(loan.getLoanDate());
            loanDto.setReturnDate(loan.getReturnDate());
            
            return loanDto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error finding loan: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Save", description = "Update del prestamos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamo guardado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de préstamo inválidos"),
        @ApiResponse(responseCode = "404", description = "Cliente o juego no encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflicto de negocio (juego ya prestado o cliente con demasiados préstamos)"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void save(@Valid @RequestBody LoanDto dto) {
        if (dto == null) {
            throw new InvalidArgumentException("Loan data cannot be null");
        }
        
        if (dto.getGameId() == null) {
            throw new InvalidArgumentException("Game ID cannot be null");
        }
        
        if (dto.getClientId() == null) {
            throw new InvalidArgumentException("Client ID cannot be null");
        }
        
        if (dto.getLoanDate() == null) {
            throw new InvalidArgumentException("Loan date cannot be null");
        }
        
        if (dto.getReturnDate() == null) {
            throw new InvalidArgumentException("Return date cannot be null");
        }
        
        if (dto.getLoanDate().isAfter(dto.getReturnDate())) {
            throw new InvalidArgumentException("Loan date cannot be after return date");
        }
        
        if (dto.getLoanDate().isBefore(LocalDate.now())) {
            throw new InvalidArgumentException("Loan date cannot be in the past");
        }
        
        try {
            this.loanService.save(dto);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error saving loan: " + e.getMessage(), e);
        }
    }

    @Operation(summary = "Delete", description = "Eliminar prestamo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new InvalidArgumentException("ID", id);
        }
        
        try {
            Loan loan = this.loanService.findById(id);
            if (loan == null) {
                throw new EntityNotFoundException("Loan", id);
            }
            
            this.loanService.delete(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Error deleting loan: " + e.getMessage(), e);
        }
    }
}