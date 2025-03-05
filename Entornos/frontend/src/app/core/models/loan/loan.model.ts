export interface Loan {
    id?: number;
    gameId: number;
    clientId: number;
    loanDate: string;
    returnDate: string;
    gameTitle?: string; 
    clientName?: string; 
}
// export interface Loan {
//     id?: number;
//     gameId: number;
//     clientId: number;
//     loanDate: string;
//     returnDate: string;
//     game?: any; // Ponemos any porque es probable qu evenga del backend el objeto de estos
//     client?: any;  // Ponemos any porque es probable qu evenga del backend el objeto de estos
// }


