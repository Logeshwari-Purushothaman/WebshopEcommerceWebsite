# **E-Commerce Website**  

![image](https://github.com/user-attachments/assets/aa2c9307-a325-4a13-b3e3-635745e1613b)

## **📌 Overview**  
This is a full-stack **E-Commerce Website** built using **Spring Boot (Backend), Thymeleaf (Frontend), and MySQL (Database)**. The platform allows users to browse products, search, view details, and make purchases while supporting pagination for better user experience.

## **🚀 Features**  
✅ Dashboard to add product and to view product catalog 
✅ Product Catalog with Pagination & Search  
✅ Product Detail Page  
✅ Shopping Cart Functionality    
✅ Write Review for a product  
✅ Add a new product to catalog  

---

## **🛠️ Tech Stack**  
### **Frontend:**  
- **Thymeleaf** for dynamic HTML rendering  

### **Backend:**  
- **Spring Boot (Spring MVC, Spring Security, Spring Data JPA)**  
- **Thymeleaf Template Engine**  
- **REST API Endpoints**  

### **Database:**  
- **MySQL**  
- **Hibernate (JPA)**  

### **Tools & Deployment:**  
- **Maven** for project build & dependency management  
- **Docker (Optional) for containerization**  
- **AWS / Heroku / Render (For Hosting - Future Scope)**  

---

## **🛠️ Installation & Setup**  

1️⃣ Clone the Repository
```sh
git clone https://github.com/yourusername/ecommerce-website.git](https://github.com/Logeshwari-Purushothaman/WebshopEcommerceWebsite.git)
cd ecommerce-website

2️⃣ Configure Database
Create a MySQL database and update the application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=yourpassword

3️⃣ Install Dependencies & Run
mvn clean install
mvn spring-boot:run
The application will start at http://localhost:8080

📄 API Endpoints
HTTP_Method	 Endpoint	                     Description
GET	  /products/catalog-paginated?page=0	 View paginated product catalog
GET	  /detail/{id}	                       View product details
POST	/cart/add/{productId}	               Add product to cart
POST	/order/checkout	                     Checkout & place an order

🎨 Screenshots
![image](https://github.com/user-attachments/assets/0ed34203-86bd-46f7-bf65-ae9801440377) 
![image](https://github.com/user-attachments/assets/acb62563-0b51-46ea-bc71-a8f111643d5d) 
![image](https://github.com/user-attachments/assets/7987f613-8b0c-4449-87f2-8ad30ab419b8) 

📞 Contact & Support
🔗 GitHub: Logeshwari Purushothaman
📧 Email: loge.eshwa@gmail.com

Feel free to contribute or report issues! 

