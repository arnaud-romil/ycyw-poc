import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuardService } from './core/auth-guard.service';
import { ChatComponent } from './pages/chat/chat.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuardService] },
    { path: 'chat/:id', component: ChatComponent, canActivate: [AuthGuardService] }
];
