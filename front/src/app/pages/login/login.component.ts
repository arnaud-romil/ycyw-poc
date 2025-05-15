import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/auth.service';
import { take } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(private readonly formBuilder: FormBuilder, private readonly authService: AuthService, private readonly router: Router) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).pipe(
        take(1)
      ).subscribe({
        next: () => {
          this.router.navigate(['/home']);
        },
        error: (err) => {
          this.errorMessage = err.status === 401 ? "Identifiants invalides" : "Une erreur s'est produite";
        }
      });
    }
  }



}
