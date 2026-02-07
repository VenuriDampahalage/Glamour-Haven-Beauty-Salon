<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.salon.model.Service" %>
        <%@ page import="java.util.List" %>
            <%-- taglib 'c' provided by header.jspf prelude --%>

                <section class="services" id="services">
                    <h2 class="section-title">Our Services</h2>
                    <div class="services-grid">
                        <% List<Service> services = (List<Service>) request.getAttribute("services");
                                if (services != null && !services.isEmpty()) {
                                for (Service service : services) {
                                String imagePath = service.getImagePath();
                                if (imagePath == null || imagePath.trim().isEmpty()) {
                                imagePath = request.getContextPath() + "/images/default-service.jpg";
                                } else if (!imagePath.startsWith("http") && !imagePath.startsWith("/")) {
                                imagePath = request.getContextPath() + "/" + imagePath;
                                }
                                %>
                                <div class="service-card">
                                    <img src="<%= imagePath %>" alt="<%= service.getName() %>"
                                        onerror="this.src='${pageContext.request.contextPath}/images/default-service.jpg'">
                                    <div class="service-content">
                                        <h3>
                                            <%= service.getName() %>
                                        </h3>
                                        <p>
                                            <%= service.getDescription() !=null ? service.getDescription()
                                                : "Experience our professional " + service.getName() + " service." %>
                                        </p>
                                        <div class="price">
                                            <i class="fas fa-tag"></i>
                                            LKR <%= String.format("%.2f", service.getPrice()) %>
                                        </div>
                                        <a href="ServiceController?action=view&id=<%= service.getId() %>"
                                            class="service-btn">
                                            <i class="fas fa-calendar-plus"></i>
                                            Book Now
                                        </a>
                                    </div>
                                </div>
                                <% } } else { %>
                                    <div class="no-services"
                                        style="text-align: center; grid-column: 1/-1; padding: 2rem;">
                                        <p>No services available at the moment. Please check back later.</p>
                                    </div>
                                    <% } %>
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
                            <p style="text-align:center; margin-top: 1rem; font-size: 1.1rem;">No reviews yet. Be
                                the first to add one!</p>
                        </c:otherwise>
                    </c:choose>
                </section>

                <%-- footer.jspf coda automatically included --%>