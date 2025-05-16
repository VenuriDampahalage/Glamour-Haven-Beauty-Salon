<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Glamour Haven</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #fce8f3 0%, #ffffff 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem;
        }

        .login-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            width: 100%;
            max-width: 1000px;
            display: flex;
            min-height: 600px;
        }

        .login-image {
            flex: 1;
            background: linear-gradient(rgba(0,0,0,0.3), rgba(0,0,0,0.3)), url('https://images.unsplash.com/photo-1560066984-138dadb4c035?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80') center/cover;
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem;
        }

        .login-image-text {
            color: white;
            text-align: center;
            z-index: 1;
        }

        .login-image-text h2 {
            font-family: 'Playfair Display', serif;
            font-size: 2.5rem;
            margin-bottom: 1rem;
        }

        .login-form-container {
            flex: 1;
            padding: 4rem 3rem;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .login-header {
            text-align: center;
            margin-bottom: 2.5rem;
        }

        .login-header h1 {
            font-family: 'Playfair Display', serif;
            color: #333;
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }

        .login-header p {
            color: #666;
            font-size: 0.9rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
            position: relative;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .form-group i {
            position: static;
            color: #ff69b4;
            font-size: 1.1rem;
            width: 20px;
            text-align: center;
        }

        .form-input {
            flex: 1;
            padding: 15px 20px;
            border: none;
            border-radius: 30px;
            font-size: 1rem;
            transition: all 0.3s ease;
            font-family: 'Poppins', sans-serif;
            background: #f8f9ff;
        }

        .form-input:focus {
            outline: none;
            box-shadow: 0 0 0 2px #ff69b4;
        }

        .form-input::placeholder {
            color: #999;
        }

        .remember-forgot {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
            font-size: 0.9rem;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: #666;
        }

        .remember-me input[type="checkbox"] {
            accent-color: #ff69b4;
        }

        .forgot-password {
            color: #ff69b4;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .forgot-password:hover {
            color: #ff4da6;
        }

        .login-button {
            width: 100%;
            padding: 15px;
            background: #ff69b4;
            color: white;
            border: none;
            border-radius: 30px;
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-bottom: 1.5rem;
        }

        .login-button:hover {
            background: #ff4da6;
            transform: translateY(-2px);
        }

        .register-link {
            text-align: center;
            color: #666;
        }

        .register-link a {
            color: #ff69b4;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .register-link a:hover {
            color: #ff4da6;
        }

        .error-message {
            color: #ff4d4d;
            text-align: center;
            margin-bottom: 1rem;
            font-size: 0.9rem;
        }

        @media (max-width: 768px) {
            .login-container {
                flex-direction: column;
                max-width: 400px;
            }

            .login-image {
                display: none;
            }

            .login-form-container {
                padding: 2rem;
            }
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-image">
            <div class="login-image-text">
                <h2>Welcome Back</h2>
                <p>Experience luxury beauty treatments in a serene environment</p>
            </div>
        </div>
        <div class="login-form-container">
            <div class="login-header">
                <h1>Login to Your Account</h1>
                <p>Please enter your credentials to continue</p>
            </div>
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <form action="CustomerController" method="post">
                <input type="hidden" name="action" value="login">
                <div class="form-group">
                    <i class="fas fa-user"></i>
                    <input type="text" name="username" class="form-input" placeholder="Username" required>
                </div>
                <div class="form-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="password" class="form-input" placeholder="Password" required>
                </div>
                <div class="remember-forgot">
                    <label class="remember-me">
                        <input type="checkbox" name="remember">
                        Remember me
                    </label>
                    <a href="#" class="forgot-password">Forgot Password?</a>
                </div>
                <button type="submit" class="login-button">Login</button>
            </form>
            <div class="register-link">
                Don't have an account? <a href="register.jsp">Sign Up</a>
            </div>
        </div>
    </div>
</body>
</html>