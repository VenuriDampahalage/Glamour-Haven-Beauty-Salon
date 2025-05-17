//bookingsConfirmatio
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