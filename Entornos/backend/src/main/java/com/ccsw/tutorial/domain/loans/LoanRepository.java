package com.ccsw.tutorial.domain.loans;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {
    
    @Query("select l from Loan l where l.game.id = :gameId and ((l.loanDate <= :returnDate and l.returnDate >= :loanDate))")
    List<Loan> findByGameAndDateRange(
                                        @Param("gameId") Long gameId, 
                                        @Param("loanDate") LocalDate loanDate, 
                                        @Param("returnDate") LocalDate returnDate
                                    );
    
    @Query("select l from Loan l where l.client.id = :clientId")
    List<Loan> findByClientId(@Param("clientId") Long clientId);

    @Query("select l from Loan l where l.game.id = :gameId")
    List<Loan> findByGameId(@Param("gameId") Long gameId);
}