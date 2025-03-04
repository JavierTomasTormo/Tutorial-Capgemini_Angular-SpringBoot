import { environment } from '../../../enviroments/enviroment';

export const API_ROUTES = {
    CATEGORIES: {
        GET_ALL: `${environment.apiBaseUrl}/categories`,
        CREATE: `${environment.apiBaseUrl}/categories`,
        UPDATE: (categoryId: number) => `${environment.apiBaseUrl}/categories/${categoryId}`,
        DELETE: (categoryId: number) => `${environment.apiBaseUrl}/categories/${categoryId}`,
    },
    CLIENTS: {
        GET_ALL: `${environment.apiBaseUrl}/clients`,
        CREATE: `${environment.apiBaseUrl}/clients`,
        UPDATE: (clientId: number) => `${environment.apiBaseUrl}/clients/${clientId}`,
        DELETE: (clientId: number) => `${environment.apiBaseUrl}/clients/${clientId}`,
    },
    GAMES: {
        GET_ALL: `${environment.apiBaseUrl}/games`,
        GET_ONE: (gameId: number) => `${environment.apiBaseUrl}/games/${gameId}`,
        CREATE: `${environment.apiBaseUrl}/games`,
        UPDATE: (gameId: number) => `${environment.apiBaseUrl}/games/${gameId}`,
        DELETE: (gameId: number) => `${environment.apiBaseUrl}/games/${gameId}`,
    },
    LOANS: {
        GET_ALL: `${environment.apiBaseUrl}/loans`,
        GET_ONE: (loanId: number) => `${environment.apiBaseUrl}/loans/${loanId}`,
        SEARCH: `${environment.apiBaseUrl}/loans/search`,
        CREATE: `${environment.apiBaseUrl}/loans`,
        DELETE: (loanId: number) => `${environment.apiBaseUrl}/loans/${loanId}`,
    }
};
