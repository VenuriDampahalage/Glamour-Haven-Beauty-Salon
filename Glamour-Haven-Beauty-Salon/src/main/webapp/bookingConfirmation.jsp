//bookingsConfirmation
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %> <%-- For number formatting --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation - Glamour Haven</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> 
    <style>
        body {
            padding-top: 70px; /* For fixed header */
            font-family: 'Poppins', sans-serif;
            color: #333;
            background-color: #f9f9f9;
            line-height: 1.6;
        }
        .confirmation-page-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 2rem;
            min-height: calc(100vh - 70px - 70px); /* Full height minus header and footer */
            box-sizing: border-box;
        }
        .confirmation-container {
            max-width: 650px;
            width: 100%;
            margin: 2rem auto;
            padding: 2.5rem;
            background: white;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.08);
            text-align: center;
        }
        .confirmation-icon {
            font-size: 3.5rem;
            color: #28a745; /* Green for success */
            margin-bottom: 1rem;
        }
        .confirmation-container h1 {
            font-family: 'Playfair Display', serif;
            font-size: 2.2rem;
            color: #333;
            margin-bottom: 0.5rem;
        }
        .confirmation-container p.subtitle {
            font-size: 1.1rem;
            color: #555;
            margin-bottom: 2rem;
        }
        .confirmation-details {
            margin-top: 1.5rem;
            padding: 1.5rem;
            background: #f8f9fa;
            border-radius: 8px;
            text-align: left;
            border: 1px solid #e9ecef;
        }
        .detail-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0.9rem 0;
            border-bottom: 1px solid #e9ecef;
        }
        .detail-row:last-child {
            border-bottom: none;
        }
        .detail-label {
            font-weight: 600;
            color: #495057;
            margin-right: 1rem;
        }
        .detail-value {
            color: #212529;
            text-align: right;
        }
        .actions-container {
            margin-top: 2.5rem;
            display: flex;
            flex-direction: column;
            gap: 1rem;
            align-items: center;
        }
        .action-button {
            display: inline-block;
            padding: 0.8rem 1.8rem;
            background-color: #ff69b4; /* Main theme color */
            color: white !important;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 500;
            transition: background-color 0.3s, transform 0.2s;
            min-width: 200px; /* Ensure buttons have a decent width */
        }
        .action-button:hover {
            background-color: #e0559d; /* Darker shade for hover */
            transform: translateY(-2px);
        }
        .action-button.secondary {
            background-color: #6c757d;
        }
        .action-button.secondary:hover {
            background-color: #5a6268;
        }

        /* Standard Header (from userDashboard.jsp for example) */
        .header {
            background-color: #fff;
            position: fixed;
            width: 100%;
            top: 0;
            left: 0;
            z-index: 1000;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem 5%;
            max-width: 1200px;
            margin: 0 auto;
        }
        .logo {
            font-family: 'Playfair Display', serif;
            font-size: 1.8rem;
            color: #ff69b4;
        }
        .nav-links {
            display: flex;
            gap: 2rem;
            align-items: center;
        }
        .nav-links a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
            transition: color 0.3s;
        }
        .nav-links a:hover {
            color: #ff69b4;
        }
        .logout-btn {
            border: 2px solid #ff69b4;
            color: #ff69b4 !important; 
            padding: 0.5rem 1rem;
            border-radius: 25px;
            text-decoration: none;
            transition: all 0.3s ease;
            font-weight: 500;
        }
        .logout-btn:hover {
            background-color: #ff69b4;
            color: white !important;
        }
        /* Simple Footer */
        .simple-footer {
            text-align: center;
            padding: 1.5rem;
            margin-top: auto; /* Pushes footer to bottom in flex container */
            color: #777;
            font-size: 0.9rem;
            width: 100%;
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <div class="logo">Glamour Haven</div>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/HomeController">Home</a>
                <a href="${pageContext.request.contextPath}/ServiceController?action=list#services">Services</a>
                <c:if test="${not empty sessionScope.customer}">
                    <a href="${pageContext.request.contextPath}/CustomerController?action=viewDashboard" style="font-weight: 600;">My Dashboard</a>
                    <a href="${pageContext.request.contextPath}/CustomerController?action=logout" class="logout-btn">Logout</a>
                </c:if>
                <c:if test="${empty sessionScope.customer}">
                     <a href="${pageContext.request.contextPath}/login.jsp">Login</a>
                </c:if>
            </div>
        </nav>
    </header>

    <div class="confirmation-page-container">
        <div class="confirmation-container">
            <div class="confirmation-icon"><i class="fas fa-check-circle"></i></div>
            <h1>Booking Confirmed!</h1>
            <p class="subtitle">Your appointment has been successfully scheduled. Thank you!</p>
            
            <c:if test="${not empty booking && not empty service}">
                <div class="confirmation-details">
                    <div class="detail-row">
                        <span class="detail-label">Booking ID:</span>
                        <span class="detail-value">#<c:out value="${booking.id}"/></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Service:</span>
                        <span class="detail-value"><c:out value="${service.name}"/></span>
                    </div>
                    <c:if test="${not empty bookedEmployee}">
                        <div class="detail-row">
                            <span class="detail-label">With:</span>
                            <span class="detail-value"><c:out value="${bookedEmployee.name}"/></span>
                        </div>
                    </c:if>
                    <div class="detail-row">
                        <span class="detail-label">Date:</span>
                        <span class="detail-value"><c:out value="${booking.date}"/></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Time:</span>
                        <span class="detail-value"><c:out value="${booking.time}"/></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Price:</span>
                        <span class="detail-value">
                            <fmt:setLocale value="en_LK"/>
                            <fmt:formatNumber value="${service.price}" type="currency" currencyCode="LKR"/>
                        </span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Status:</span>
                        <span class="detail-value"><c:out value="${booking.status}"/></span>
                    </div>
                </div>
            </c:if>
            
            <div class="actions-container">
                <a href="${pageContext.request.contextPath}/CustomerController?action=viewDashboard" class="action-button">View My Bookings</a>
                <a href="${pageContext.request.contextPath}/ServiceController?action=list" class="action-button secondary">Book Another Service</a>
            </div>
        </div>
    </div>

    <footer class="simple-footer">
        <p>&copy; <c:set var="year" value="<%= new java.util.Date().getYear() + 1900 %>"/><c:out value="${year}"/> Glamour Haven. All rights reserved.</p>
    </footer>

</body>
</html> 