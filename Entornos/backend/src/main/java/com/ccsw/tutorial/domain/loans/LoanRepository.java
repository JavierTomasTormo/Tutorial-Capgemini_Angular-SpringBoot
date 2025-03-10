package com.ccsw.tutorial.domain.loans;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {

    @Query("select l from Loan l where (:gameId is null or l.game.id = :gameId) and (:clientId is null or l.client.id = :clientId) and (:loanDate is null or l.loanDate >= :loanDate) and (:returnDate is null or l.returnDate <= :returnDate)")
    Page<Loan> findByFilters(
            @Param("gameId") Long gameId,
            @Param("clientId") Long clientId,
            @Param("loanDate") LocalDate loanDate,
            @Param("returnDate") LocalDate returnDate,
            Pageable pageable
    );

    @Query("select l from Loan l where l.client.id = :clientId")
    List<Loan> findByClientId(@Param("clientId") Long clientId);

    @Query("select l from Loan l where l.game.id = :gameId")
    List<Loan> findByGameId(@Param("gameId") Long gameId);

    @Query("select l from Loan l where l.game.id = :gameId and l.loanDate <= :endDate and l.returnDate >= :startDate")
    List<Loan> findByGameAndDateRange(
            @Param("gameId") Long gameId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("select l from Loan l where l.client.id = :clientId and l.loanDate <= :endDate and l.returnDate >= :startDate")
    List<Loan> findByClientAndDateRange(
            @Param("clientId") Long clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}