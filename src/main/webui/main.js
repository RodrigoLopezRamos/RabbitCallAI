import './style.css'
import quarkusLogo from '/quarkus.svg'
import viteLogo from '/vite.svg'

document.querySelector('#app').innerHTML = `
  <div class="auth-container">
    <div class="header">
      <div class="logo-container">
        <a href="https://vitejs.dev" target="_blank">
          <img src="${viteLogo}" class="logo" alt="Vite logo" />
        </a>
        <a href="https://docs.quarkiverse.io/quarkus-quinoa/dev/web-frameworks.html" target="_blank">
          <img src="${quarkusLogo}" class="logo" alt="Quarkus logo" />
        </a>
      </div>
      <h1>RabbitCall.AI</h1>
    </div>

    <div class="auth-card">
      <div class="auth-tabs">
        <button class="tab-btn active" data-tab="login">Login</button>
        <button class="tab-btn" data-tab="signup">Sign Up</button>
      </div>

      <div class="tab-content">
        <!-- Login Form -->
        <div class="tab-pane active" id="login-pane">
          <form id="loginForm">
            <div class="form-group">
              <label for="loginEmail">Email or Username</label>
              <input type="text" id="loginEmail" name="loginEmail" required>
            </div>

            <div class="form-group">
              <label for="loginPassword">Password</label>
              <input type="password" id="loginPassword" name="loginPassword" required>
              <div class="forgot-password">
                <a href="#" class="text-link">Forgot Password?</a>
              </div>
            </div>

            <div class="form-group checkbox-group">
              <input type="checkbox" id="rememberMe" name="rememberMe">
              <label for="rememberMe">Remember me</label>
            </div>

            <div class="form-actions">
              <button type="submit" class="btn primary-btn full-width">Login</button>
            </div>
          </form>

          <div class="auth-separator">
            <span>OR</span>
          </div>

          <div class="social-login">
            <button class="btn social-btn google-btn">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="#DB4437">
                <path d="M12.24 10.285V14.4h6.806c-.275 1.765-2.056 5.174-6.806 5.174-4.095 0-7.439-3.389-7.439-7.574s3.345-7.574 7.439-7.574c2.33 0 3.891.989 4.785 1.849l3.254-3.138C18.189 1.186 15.479 0 12.24 0c-6.635 0-12 5.365-12 12s5.365 12 12 12c6.926 0 11.52-4.869 11.52-11.726 0-.788-.085-1.39-.189-1.989H12.24z"/>
              </svg>
              <span>Continue with Google</span>
            </button>

            <button class="btn social-btn facebook-btn">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="#1877F2">
                <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
              </svg>
              <span>Continue with Facebook</span>
            </button>
          </div>
        </div>

        <!-- Sign Up Form -->
        <div class="tab-pane" id="signup-pane">
          <form id="signupForm">
            <div class="form-section">
              <div class="form-row">
                <div class="form-group">
                  <label for="firstName">First Name</label>
                  <input type="text" id="firstName" name="firstName" required>
                </div>

                <div class="form-group">
                  <label for="lastName">Last Name</label>
                  <input type="text" id="lastName" name="lastName" required>
                </div>
              </div>

              <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required>
              </div>

              <div class="form-group">
                <label for="phoneNumber">Phone Number</label>
                <input type="tel" id="phoneNumber" name="phoneNumber">
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="password">Password</label>
                  <input type="password" id="password" name="password" required>
                </div>

                <div class="form-group">
                  <label for="confirmPassword">Confirm Password</label>
                  <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
              </div>

              <div class="form-group">
                <label for="userType">Account Type</label>
                <select id="userType" name="userType" required>
                  <option value="">Select Account Type</option>
                  <option value="1">Customer</option>
                  <option value="2">Vendor</option>
                  <option value="3">Delivery Partner</option>
                  <option value="4">Store Owner</option>
                </select>
              </div>
            </div>

            <div id="storeInfoSection" class="form-section hidden">
              <h3>Store Information</h3>

              <div class="form-group">
                <label for="storeName">Store Name</label>
                <input type="text" id="storeName" name="storeName">
              </div>

              <div class="form-group">
                <label for="description">Store Description</label>
                <textarea id="description" name="description" rows="2"></textarea>
              </div>

              <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address">
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="city">City</label>
                  <input type="text" id="city" name="city">
                </div>

                <div class="form-group">
                  <label for="province">Province</label>
                  <input type="text" id="province" name="province">
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="postalCode">Postal Code</label>
                  <input type="text" id="postalCode" name="postalCode">
                </div>

                <div class="form-group">
                  <label for="country">Country</label>
                  <select id="country" name="country">
                    <option value="Canada">Canada</option>
                    <option value="United States">United States</option>
                    <option value="Mexico">Mexico</option>
                    <option value="Other">Other</option>
                  </select>
                </div>
              </div>
            </div>

            <div class="form-group checkbox-group">
              <input type="checkbox" id="terms" name="terms" required>
              <label for="terms">I agree to the <a href="#" class="text-link">Terms of Service</a> and <a href="#" class="text-link">Privacy Policy</a></label>
            </div>

            <div class="form-actions">
              <button type="submit" class="btn primary-btn full-width">Create Account</button>
            </div>
          </form>

          <div class="auth-separator">
            <span>OR</span>
          </div>

          <div class="social-login">
            <button class="btn social-btn google-btn">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="#DB4437">
                <path d="M12.24 10.285V14.4h6.806c-.275 1.765-2.056 5.174-6.806 5.174-4.095 0-7.439-3.389-7.439-7.574s3.345-7.574 7.439-7.574c2.33 0 3.891.989 4.785 1.849l3.254-3.138C18.189 1.186 15.479 0 12.24 0c-6.635 0-12 5.365-12 12s5.365 12 12 12c6.926 0 11.52-4.869 11.52-11.726 0-.788-.085-1.39-.189-1.989H12.24z"/>
              </svg>
              <span>Sign up with Google</span>
            </button>

            <button class="btn social-btn facebook-btn">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="#1877F2">
                <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
              </svg>
              <span>Sign up with Facebook</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="auth-footer">
      <p>&copy; 2025 RabbitCall.AI. All rights reserved.</p>
    </div>
  </div>
`

// Tab switching functionality
const tabButtons = document.querySelectorAll('.tab-btn');
const tabPanes = document.querySelectorAll('.tab-pane');

tabButtons.forEach(button => {
  button.addEventListener('click', () => {
    // Remove active class from all buttons and panes
    tabButtons.forEach(btn => btn.classList.remove('active'));
    tabPanes.forEach(pane => pane.classList.remove('active'));

    // Add active class to clicked button
    button.classList.add('active');

    // Show corresponding pane
    const tabId = button.getAttribute('data-tab');
    document.getElementById(`${tabId}-pane`).classList.add('active');
  });
});

// Show/hide store information based on user type
document.getElementById('userType').addEventListener('change', function() {
  const storeInfoSection = document.getElementById('storeInfoSection');
  if (this.value === '4') { // Store Owner
    storeInfoSection.classList.remove('hidden');
  } else {
    storeInfoSection.classList.add('hidden');
  }
});

// Login form submission
document.getElementById('loginForm').addEventListener('submit', function(event) {
  event.preventDefault();

  const loginData = {
    loginEmail: document.getElementById('loginEmail').value,
    loginPassword: document.getElementById('loginPassword').value,
    rememberMe: document.getElementById('rememberMe').checked
  };

  console.log('Login attempt:', loginData);

  // Example API call (commented out)
  /*
  fetch('/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      email: loginData.loginEmail,
      password: loginData.loginPassword,
      remember: loginData.rememberMe
    })
  })
  .then(response => response.json())
  .then(data => {
    console.log('Success:', data);
    window.location.href = '/dashboard.html';
  })
  .catch(error => {
    console.error('Error:', error);
    alert('Login failed. Please check your credentials and try again.');
  });
  */

  // For demo purposes
  alert('Login successful! Redirecting to dashboard...');
});

// Sign-up form submission
document.getElementById('signupForm').addEventListener('submit', function(event) {
  event.preventDefault();

  // Password validation
  const password = document.getElementById('password').value;
  const confirmPassword = document.getElementById('confirmPassword').value;

  if (password !== confirmPassword) {
    alert('Passwords do not match!');
    return;
  }

  // Collect form data
  const formData = new FormData(this);
  const userData = Object.fromEntries(formData.entries());

  console.log('User registration data:', userData);

  // Example API call (commented out)
  /*
  fetch('/api/users/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData)
  })
  .then(response => response.json())
  .then(data => {
    console.log('Success:', data);
    window.location.href = '/registration-success.html';
  })
  .catch(error => {
    console.error('Error:', error);
    alert('Registration failed. Please try again.');
  });
  */

  // For demo purposes
  alert('Account created successfully! You can now log in.');

  // Switch to login tab
  document.querySelector('.tab-btn[data-tab="login"]').click();
});

// Add this to your style.css file or create a new style block
const styleElement = document.createElement('style');
styleElement.textContent = `
  /* General styles */
  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    line-height: 1.6;
    color: #333;
    background-color: #f9f9f9;
  }

  .auth-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 2rem;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }

  .header {
    text-align: center;
    margin-bottom: 2rem;
  }

  .logo-container {
    display: flex;
    justify-content: center;
    gap: 1rem;
    margin-bottom: 1rem;
  }

  .logo {
    height: 40px;
  }

  /* Card styles */
  .auth-card {
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    overflow: hidden;
    flex: 1;
  }

  /* Tab styles */
  .auth-tabs {
    display: flex;
    border-bottom: 1px solid #eaeaea;
  }

  .tab-btn {
    flex: 1;
    padding: 1rem;
    border: none;
    background: none;
    font-size: 1rem;
    font-weight: 600;
    color: #666;
    cursor: pointer;
    transition: all 0.3s;
  }

  .tab-btn:hover {
    background-color: #f5f5f5;
  }

  .tab-btn.active {
    color: #4caf50;
    border-bottom: 3px solid #4caf50;
  }

  /* Tab content */
  .tab-content {
    padding: 2rem;
  }

  .tab-pane {
    display: none;
  }

  .tab-pane.active {
    display: block;
  }

  /* Form styles */
  .form-section {
    margin-bottom: 1.5rem;
  }

  .form-section h3 {
    margin-bottom: 1rem;
    font-size: 1.1rem;
    color: #4caf50;
  }

  .form-row {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 1rem;
  }

  @media (max-width: 768px) {
    .form-row {
      grid-template-columns: 1fr;
    }
  }

  .form-group {
    margin-bottom: 1.25rem;
  }

  .form-group label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: 500;
    color: #555;
  }

  .form-group input,
  .form-group textarea,
  .form-group select {
    width: 100%;
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1rem;
    transition: border-color 0.2s;
  }

  .form-group input:focus,
  .form-group textarea:focus,
  .form-group select:focus {
    outline: none;
    border-color: #4caf50;
    box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
  }

  .forgot-password {
    text-align: right;
    margin-top: 0.5rem;
    font-size: 0.85rem;
  }

  .checkbox-group {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }

  .checkbox-group input {
    width: auto;
  }

  .text-link {
    color: #4caf50;
    text-decoration: none;
  }

  .text-link:hover {
    text-decoration: underline;
  }

  /* Button styles */
  .form-actions {
    margin-top: 1.5rem;
  }

  .btn {
    padding: 0.75rem 1.5rem;
    border: none;
    border-radius: 4px;
    font-size: 1rem;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s;
    text-align: center;
  }

  .primary-btn {
    background-color: #4caf50;
    color: white;
  }

  .primary-btn:hover {
    background-color: #43a047;
  }

  .full-width {
    width: 100%;
  }

  /* Social login */
  .auth-separator {
    display: flex;
    align-items: center;
    text-align: center;
    margin: 1.5rem 0;
  }

  .auth-separator::before,
  .auth-separator::after {
    content: '';
    flex: 1;
    border-bottom: 1px solid #eaeaea;
  }

  .auth-separator span {
    padding: 0 1rem;
    color: #888;
    font-size: 0.85rem;
  }

  .social-login {
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
  }

  .social-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0.75rem;
    background-color: #f5f5f5;
    color: #333;
  }

  .social-btn:hover {
    background-color: #ebebeb;
  }

  .google-btn {
    border: 1px solid #ddd;
  }

  .facebook-btn {
    border: 1px solid #ddd;
  }

  /* Footer styles */
  .auth-footer {
    margin-top: 2rem;
    text-align: center;
    color: #888;
    font-size: 0.85rem;
  }

  /* Helper classes */
  .hidden {
    display: none;
  }
`;

document.head.appendChild(styleElement);
