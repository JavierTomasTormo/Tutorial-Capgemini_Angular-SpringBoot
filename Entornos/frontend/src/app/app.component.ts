import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/layout/header/header.component';
import { FooterComponent } from './shared/components/layout/footer/footer.component';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, HeaderComponent, FooterComponent],
    template: `
        <div>
            <app-header></app-header>
                <div>
                    <router-outlet></router-outlet>
                </div>
            <app-footer></app-footer>
        </div>
    `,
})
export class AppComponent {
    title = 'Javier Tomás Tormo';
}