<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.salon.model.Service" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Services - Glamour Haven</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            line-height: 1.6;
            color: #333;
            padding-top: 70px;
            margin: 0;
        }

        .services {
            padding: 5rem 5%;
            background-color: #f9f9f9;
            position: relative;
            overflow: hidden;
        }

        .section-title {
            text-align: center;
            font-family: 'Playfair Display', serif;
            font-size: 2.5rem;
            margin-bottom: 3rem;
            position: relative;
        }

        .section-title::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 3px;
            background: #ff69b4;
        }

        .services-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 2.5rem;
            padding: 1rem;
        }

        .service-card {
            background: #fff;
            border-radius: 15px;
            overflow: hidden;
            transition: all 0.3s ease;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            position: relative;
        }

        .service-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }

        .service-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            transition: transform 0.5s ease;
        }

        .service-card:hover img {
            transform: scale(1.1);
        }

        .service-content {
            padding: 1.5rem;
        }

        .service-card h3 {
            color: #333;
            font-size: 1.5rem;
            margin-bottom: 0.8rem;
            font-weight: 600;
        }

        .service-card p {
            color: #666;
            font-size: 0.95rem;
            margin-bottom: 1rem;
            line-height: 1.6;
        }

        .service-card .price {
            color: #ff69b4;
            font-weight: 600;
            font-size: 1.3rem;
            margin: 0.8rem 0;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .service-card .price i {
            font-size: 1rem;
        }

        .service-btn {
            background-color: #ff69b4;
            color: white;
            padding: 0.8rem 1.5rem;
            border-radius: 25px;
            text-decoration: none;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            font-weight: 500;
        }

        .service-btn:hover {
            background-color: #ff4da6;
            transform: translateY(-2px);
        }

        .service-btn i {
            font-size: 0.9rem;
        }

        /* Reviews Section Styles */
        .reviews-section {
            padding: 4rem 5%;
            background-color: #fff;
        }

        .reviews-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        }

        .review-card {
            background-color: #fff;
            border: 1px solid #eee;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .review-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 16px rgba(0,0,0,0.12);
        }

        .review-card .reviewer-name {
            font-weight: 600;
            color: #ff69b4;
            margin-bottom: 0.5rem;
            font-size: 1.1rem;
        }

        .review-card .review-text {
            font-size: 0.95rem;
            color: #555;
            line-height: 1.6;
        }

        /* Header Styles */
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

        .user-menu {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .welcome-text {
            color: #333;
            font-weight: 500;
        }

        .dashboard-btn, .logout-btn {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            padding: 0.5rem 1rem;
            border-radius: 25px;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .dashboard-btn {
            background-color: #ff69b4;
            color: white;
        }

        .dashboard-btn:hover {
            background-color: #ff4da6;
            transform: translateY(-2px);
        }

        .logout-btn {
            border: 2px solid #ff69b4;
            color: #ff69b4;
        }

        .logout-btn:hover {
            background-color: #ff69b4;
            color: white;
        }
    </style>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <div class="logo">Glamour Haven</div>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
                <a href="#services">Services</a>
                <a href="${pageContext.request.contextPath}/index.jsp#gallery">Gallery</a>
                <a href="${pageContext.request.contextPath}/employeeDetails.jsp">Our Team</a>
                <a href="${pageContext.request.contextPath}/index.jsp#contact">Contact</a>
                <c:if test="${not empty sessionScope.customer}">
                    <div class="user-menu">
                        <span class="welcome-text">Welcome, ${sessionScope.customer.username}</span>
                        <a href="${pageContext.request.contextPath}/CustomerController?action=viewDashboard" class="dashboard-btn">
                            <i class="fas fa-th-large"></i> Dashboard
                        </a>
                        <a href="${pageContext.request.contextPath}/CustomerController?action=logout" class="logout-btn">
                            <i class="fas fa-sign-out-alt"></i> Logout
                        </a>
                    </div>
                </c:if>
            </div>
        </nav>
    </header>

    <section class="services" id="services">
        <h2 class="section-title">Our Services</h2>
        <div class="services-grid">
            <%
                List<Service> services = (List<Service>) request.getAttribute("services");
                if (services != null && !services.isEmpty()) {
                    for (Service service : services) {
                        String imagePath = service.getImagePath();
                        if (imagePath == null || imagePath.trim().isEmpty()) {
                            imagePath = request.getContextPath() + "/images/default-service.jpg";
                        } else if (!imagePath.startsWith("http")) {
                            imagePath = request.getContextPath() + "/" + imagePath.replace("\\", "/");
                        }
                        System.out.println("Service: " + service.getName() + ", Image path: " + imagePath);
            %>
            <div class="service-card">
                <img src="<%= imagePath %>"
                     alt="<%= service.getName() %>"
                     onerror="this.src='<%= request.getContextPath() %>/images/default-service.jpg'">
                <div class="service-content">
                    <h3><%= service.getName() %></h3>
                    <p><%= service.getDescription() != null ? service.getDescription() : "Experience our professional " + service.getName() + " service." %></p>
                    <div class="price">
                        <i class="fas fa-tag"></i>
                        LKR <%= String.format("%.2f", service.getPrice()) %>
                    </div>
                    <a href="ServiceController?action=view&id=<%= service.getId() %>" class="service-btn">
                        <i class="fas fa-calendar-plus"></i>
                        Book Now
                    </a>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <div class="no-services" style="text-align: center; grid-column: 1/-1; padding: 2rem;">
                <p>No services available at the moment. Please check back later.</p>
            </div>
            <%
                }
            %>
        </div>
    </section>

    <section id="customer-reviews" class="reviews-section">
        <h2 class="section-title">What Our Customers Say</h2>
        <c:choose>
            <c:when test="${not empty salonReviews}">
                <div class="reviews-grid">
                    <c:forEach var="review" items="${salonReviews}">
                        <div class="review-card">
                            <p class="reviewer-name">${review[0]} says:</p>
                            <p class="review-text">${review[1]}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p style="text-align:center; margin-top: 1rem; font-size: 1.1rem;">No reviews yet. Be the first to add one!</p>
            </c:otherwise>
        </c:choose>
    </section>

    <%@ include file="includes/footer.jsp" %>
</body>
</html>