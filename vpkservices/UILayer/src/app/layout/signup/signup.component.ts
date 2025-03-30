import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.signupForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      acceptTerms: [false, Validators.requiredTrue]
    }, {
      validators: this.passwordMatchValidator
    });
  }

  // Custom Validator: Passwords must match
  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  // Check if a form field is invalid and touched
  isInvalid(controlName: string) {
    const control = this.signupForm.get(controlName);
    return control?.invalid && (control.dirty || control.touched);
  }

  onSubmit() {
    if (this.signupForm.invalid) return;

    const userData = {
      name: this.signupForm.value.name,
      email: this.signupForm.value.email,
      phone: this.signupForm.value.phone
    };

    // this.userList.push(userData); // Store in array
    console.log('User List:', userData); // Log the array
    alert('User added successfully!');

    this.signupForm.reset(); // Reset the form after submission
  }

}
