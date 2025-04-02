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
## 🛠️ 🎨 Screenshots 

![image](https://github.com/user-attachments/assets/0ed34203-86bd-46f7-bf65-ae9801440377) 
![image](https://github.com/user-attachments/assets/acb62563-0b51-46ea-bc71-a8f111643d5d) 
![image](https://github.com/user-attachments/assets/7987f613-8b0c-4449-87f2-8ad30ab419b8) 

## **🛠️ Installation & Setup**  

### 1️⃣ Clone the Repository  
1. Open a terminal and run the following command:
2. git clone https://github.com/Logeshwari-Purushothaman/WebshopEcommerceWebsite.git
3. cd WebshopEcommerceWebsite


### 2️⃣ Configure Database  
Create a MySQL database and update the `application.properties` file:  

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce 
spring.datasource.username=root 
spring.datasource.password=yourpassword 
spring.jpa.hibernate.ddl-auto=update 
spring.jpa.show-sql=true

### 3️⃣ Install Dependencies & Run
1. mvn clean install
2.  mvn spring-boot:run
3.   The application will start at http://localhost:8080

------------------------------------------------------

## 📌 Usage  

### 🛒 Open the Product Catalog  
- Navigate to **http://localhost:8080/products/catalog**  
- Browse products with **pagination**  

### ➕ Add a New Product  
- Click on **"Add Product"** from the dashboard  
- Fill in the details and submit  

### 🔍 View Product Details  
- Click on **"View Details"** in the catalog to see full product details  

------------------------------------------------------

## ⚙️ API Endpoints  

| HTTP Method | Endpoint                     | Description                  |
|------------|-----------------------------|------------------------------|
| **GET**    | `/products/catalog`          | View all products            |
| **GET**    | `/products/catalog-paginated?page=0` | View paginated catalog |
| **GET**    | `/products/add`              | Form to add a new product    |
| **POST**   | `/products/add`              | Submit new product           |
| **GET**    | `/products/detail/{id}`      | View product details         |

------------------------------------------------------

## 🔧 Built With  
- **Spring Boot** (Backend Framework)  
- **Thymeleaf** (Template Engine)  
- **MySQL** (Database)  
- **Spring Data JPA** (ORM)  
- **Bootstrap + Custom CSS** (Frontend Styling)  

------------------------------------------------------

## 📌 To Do  
- ✅ Implement Pagination  
- ✅ Improve UI Styling  
- ⏳ Add User Authentication  
- ⏳ Implement Order & Checkout System  

------------------------------------------------------

## 📄 License  
This project is **MIT Licensed**. Feel free to use and modify!  

------------------------------------------------------

## 👩‍💻 Author  
Developed by **Logeshwari Purushothaman** 🚀


## 📞 Contact & Support    

🔗 GitHub: Logeshwari Purushothaman
📧 Email: loge.eshwa@gmail.com

- Feel free to contribute or report issues! 

