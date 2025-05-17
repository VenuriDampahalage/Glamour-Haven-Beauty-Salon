<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.salon.model.Customer" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management - Glamour Haven</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            background-color: #f5f5f5;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        h1 {
            color: #ff69b4;
            font-size: 2.5rem;
            font-weight: 600;
        }

        .users-table {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            margin-bottom: 2rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #ff69b4;
            color: white;
            padding: 1rem;
            text-align: left;
            font-weight: 500;
        }

        td {
            padding: 1rem;
            border-bottom: 1px solid #eee;
        }

        tr:hover {
            background: #fce8f3;
        }

        .action-cell {
            display: flex;
            gap: 0.5rem;
            align-items: center;
        }

        .form-input {
            padding: 0.5rem;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-family: 'Poppins', sans-serif;
            color: #666;
            margin-right: 0.5rem;
        }

        .update-button, .delete-button {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
            color: white;
        }

        .update-button {
            background: #ff69b4;
        }

        .delete-button {
            background: #dc3545;
        }

        .update-button:hover {
            background: #ff4da6;
            transform: translateY(-1px);
        }

        .delete-button:hover {
            background: #c82333;
            transform: translateY(-1px);
        }

        .success-message {
            position: fixed;
            top: 20px;
            right: 20px;
            background: #d4edda;
            color: #155724;
            padding: 1rem;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: none;
            z-index: 1000;
        }

        .error-message {
            position: fixed;
            top: 20px;
            right: 20px;
            background: #f8d7da;
            color: #721c24;
            padding: 1rem;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: none;
            z-index: 1000;
        }

        .form-group {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .form-group label {
            min-width: 80px;
            color: #666;
        }

        .btn {
            padding: 0.8rem 1.5rem;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 1rem;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            transition: all 0.3s ease;
            color: white;
            background: #ff69b4;
            text-decoration: none;
            font-weight: 500;
        }
        .btn-primary {
            background: #ff69b4;
        }
        .btn:hover {
            opacity: 0.9;
            transform: translateY(-1px);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>User Management</h1>
        </div>

        <div class="users-table">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                <% 
                    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                    if (customers != null) {
                        for (Customer customer : customers) {
                %>
                <tr>
                    <td><%= customer.getId() %></td>
                    <td><%= customer.getUsername() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td class="action-cell">
                        <form class="update-form" onsubmit="updateUser(event, <%= customer.getId() %>)">
                            <div class="form-group">
                                <input type="text" name="username" class="form-input" value="<%= customer.getUsername() %>" placeholder="Username">
                                <input type="text" name="email" class="form-input" value="<%= customer.getEmail() %>" placeholder="Email">
                                <input type="password" name="password" class="form-input" placeholder="New Password">
                                <button type="submit" class="update-button">Update</button>
                            </div>
                        </form>
                        <button onclick="deleteUser(<%= customer.getId() %>)" class="delete-button">Delete</button>
                    </td>
                </tr>
                <% 
                        }
                    }
                %>
            </table>
        </div>
        <div style="display: flex; justify-content: center; margin: 2rem 0;">
            <a href="adminDashboard.jsp" class="btn btn-primary">
                <i class="fas fa-arrow-left"></i>
                Back to Dashboard
            </a>
        </div>
    </div>

    <div id="successMessage" class="success-message">
        Operation completed successfully!
    </div>
    <div id="errorMessage" class="error-message">
        An error occurred. Please try again.
    </div>

    <script>
        function showMessage(messageId, duration = 3000) {
            const message = document.getElementById(messageId);
            message.style.display = 'block';
            setTimeout(() => {
                message.style.display = 'none';
            }, duration);
        }

        function updateUser(event, userId) {
            event.preventDefault();
            const form = event.target;
            const formData = new FormData(form);
            
            fetch('CustomerController', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=updateCustomer&id=${userId}&username=${formData.get('username')}&email=${formData.get('email')}&password=${formData.get('password')}`
            })
            .then(response => {
                if (response.ok) {
                    showMessage('successMessage');
                    // Update the displayed values
                    const row = form.closest('tr');
                    row.cells[1].textContent = formData.get('username');
                    row.cells[2].textContent = formData.get('email');
                    // Clear password field
                    form.querySelector('input[name="password"]').value = '';
                } else {
                    showMessage('errorMessage');
                }
            })
            .catch(error => {
                showMessage('errorMessage');
            });
        }

        function deleteUser(userId) {
            if (confirm('Are you sure you want to delete this user?')) {
                fetch('CustomerController', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `action=deleteCustomer&id=${userId}`
                })
                .then(response => {
                    if (response.ok) {
                        const row = document.querySelector(`tr:has(button[onclick="deleteUser(${userId})"])`);
                        row.remove();
                        showMessage('successMessage');
                    } else {
                        showMessage('errorMessage');
                    }
                })
                .catch(error => {
                    showMessage('errorMessage');
                });
            }
        }
    </script>
</body>
</html>