package com.ccsw.tutorial.domain.loans;

import com.ccsw.tutorial.application.clients.ClientService;
import com.ccsw.tutorial.application.games.GameService;
import com.ccsw.tutorial.application.loans.LoanService;
import com.ccsw.tutorial.domain.clients.Client;
import com.ccsw.tutorial.domain.games.Game;
import com.ccsw.tutorial.presentation.loans.model.LoanDto;
import com.ccsw.tutorial.presentation.loans.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    @Override
    public List<Loan> findAll() {
        return StreamSupport.stream(this.loanRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByArgs(LoanSearchDto dto) {
        return this.loanRepository.findByFilters(dto.getGameId(), dto.getClientId(), dto.getLoanDate(), dto.getReturnDate());
    }

    @Override
    public Loan findById(Long id) throws Exception {
        return this.loanRepository.findById(id).orElse(null);
    }

    @Override
    public void save(LoanDto dto) throws Exception {
        Loan loan = new Loan();

        if (dto.getId() != null) {
            loan.setId(dto.getId());
        }

        Game game = this.gameService.findById(dto.getGameId());
        if (game == null) {
            throw new Exception("Juego no encontrado");
        }

        Client client = this.clientService.findById(dto.getClientId());
        if (client == null) {
            throw new Exception("Cliente no encontrado");
        }

        List<Loan> existingLoans = this.loanRepository.findByGameAndDateRange(
                dto.getGameId(), dto.getLoanDate(), dto.getReturnDate());

        if (dto.getId() != null) {
            existingLoans = existingLoans.stream()
                    .filter(l -> !l.getId().equals(dto.getId()))
                    .collect(Collectors.toList());
        }

        if (!existingLoans.isEmpty()) {
            throw new Exception("El juego ya está prestado en ese rango de fechas");
        }

        List<Loan> clientLoans = this.loanRepository.findByClientId(dto.getClientId());
        if (clientLoans.size() >= 2 && (dto.getId() == null || 
                clientLoans.stream().noneMatch(l -> l.getId().equals(dto.getId())))) {
            throw new Exception("El Cliente ya tiene el número máximo de préstamos");
        }

        loan.setGame(game);
        loan.setClient(client);
        loan.setLoanDate(dto.getLoanDate());
        loan.setReturnDate(dto.getReturnDate());

        this.loanRepository.save(loan);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (!this.loanRepository.existsById(id)) {
            throw new Exception("Préstamo no encontrado");
        }

        this.loanRepository.deleteById(id);
    }
}