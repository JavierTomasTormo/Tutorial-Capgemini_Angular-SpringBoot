package com.ccsw.tutorial.presentation.loans.model;

import java.time.LocalDate;


public class LoanSearchDto {
    
    private Long clientId;
    private Long gameId;
    private LocalDate date;
    
    public LoanSearchDto() {
    }
    
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    
    public Long getGameId() {
        return gameId;
    }
    
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
}