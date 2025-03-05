import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDividerModule } from '@angular/material/divider';
import { ClientService } from '../../../core/services/client/client.service';
import { CategoryService } from '../../../core/services/category/category.service';

import { GameService } from '../../../core/services/game/game.service';
import { LoanService } from '../../../core/services/loan/loan.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
    MatDividerModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  totalGames: number = 0;
  totalClients: number = 0;
  totalLoans: number = 0;
  activeLoans: number = 0;
  loading: boolean = true;
  
  categoryDistribution: any[] = [];
  recentLoans: any[] = [];
  popularGames: any[] = [];
  
  constructor(
    private clientService: ClientService,
    private categoryService: CategoryService,
    private gameService: GameService,
    private loanService: LoanService
  ) { }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.loading = true;

    forkJoin({
      clients: this.clientService.getAll(),
      categories: this.categoryService.getCategories(),
      games: this.gameService.getAll(),
      loans: this.loanService.getAll()
    }).subscribe({
      next: (results) => {
        // console.log('Results from services:', results);
        
        this.totalClients = results.clients.length;
        this.totalGames = results.games.length;
        this.totalLoans = results.loans.length;
        // console.log('Total clients:', this.totalClients);
        // console.log('Total games:', this.totalGames);
        // console.log('Total loans:', this.totalLoans);
        
        const today = new Date();
        this.activeLoans = results.loans.filter(loan => {
          const loanDate = new Date(loan.loanDate);
          const returnDate = new Date(loan.returnDate);
          return loanDate <= today && returnDate >= today;
        }).length;
        console.log('Active loans:', this.activeLoans);
        
        const gameLoanCounts = new Map();
        results.loans.forEach(loan => {
          const gameId = loan.gameId;
          gameLoanCounts.set(gameId, (gameLoanCounts.get(gameId) || 0) + 1);
        });
        // console.log('Category counts:', categoryCounts);
        
        const maxLoans = Math.max(...Array.from(gameLoanCounts.values(), x => x || 0), 1);
        this.popularGames = results.games.map(game => {
          const loanCount = gameLoanCounts.get(game.id) || 0;
          return {
            id: game.id,
            title: game.title,
            age: game.age,
            loanCount: loanCount,
            percentage: Math.floor((loanCount / maxLoans) * 100)
          };
        }).sort((a, b) => b.loanCount - a.loanCount).slice(0, 5);


        const categoryCounts = new Map();
        results.games.forEach(game => {
          const catId = game.categoryId;
          categoryCounts.set(catId, (categoryCounts.get(catId) || 0) + 1);
        });
        
        this.categoryDistribution = results.categories.map(category => {
          const count = categoryCounts.get(category.id) || 0;
          const maxCount = Math.max(...Array.from(categoryCounts.values()));
          return {
            name: category.name,
            count: count,
            percentage: maxCount > 0 ? Math.floor((count / maxCount) * 100) : 0
          };
        }).sort((a, b) => b.count - a.count);


        // console.log('Category distribution:', this.categoryDistribution);
        
        const loanData = results.loans
          .sort((a, b) => new Date(b.loanDate).getTime() - new Date(a.loanDate).getTime())
          .slice(0, 5)
          .map(loan => {
            const game = results.games.find(g => g.id === loan.gameId);
            const client = results.clients.find(c => c.id === loan.clientId);
            return {
              game: game?.title || 'Unknown Game',
              client: client?.name || 'Unknown Client',
              date: loan.loanDate,
              returnDate: loan.returnDate
            };
          });
        
        this.recentLoans = loanData;
        // console.log('Recent loans:', this.recentLoans);
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading dashboard data', error);
        this.loading = false;
      }
    });
  }
}