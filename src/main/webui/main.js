import './style.css'
import rabbitLogo from '/rabbit1.jpeg'

document.querySelector('#app').innerHTML = `
  <div class="auth-container">
    <div class="auth-card">
      <!-- Auth tabs and content -->
      <div class="auth-tabs">
        <div class="auth-tab active" data-tab="login">Login</div>
        <div class="auth-tab" data-tab="register">Register</div>
      </div>

      <!-- Tab content here -->
    </div>
  </div>
`

// Tab switching functionality
const tabElements = document.querySelectorAll('.auth-tab');
// ... JavaScript implementation
