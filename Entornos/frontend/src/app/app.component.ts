import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './core/layout/header/header.component';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, HeaderComponent],
    template: `
        <div>
            <app-header></app-header>
            <div>
                    <router-outlet></router-outlet>
            </div>
        </div>
    `,
    styleUrls: ['./app.component.scss'],
})
export class AppComponent {
    title = 'Javier Tomás Tormo';
}