COMPLETE ECOMMERCE PROJECT EXPLANATION
Detailed Breakdown

PROJECT ARCHITECTURE
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND (Browser)                       │
│              HTML + CSS + JavaScript (VS Code)                   │
│  index.html | login.html | register.html | cart.html | orders   │
└─────────────────────┬───────────────────────────────────────────┘
                      │
                      │ HTTP Requests
                      │ (fetch API)
                      ↓
┌─────────────────────────────────────────────────────────────────┐
│                   BACKEND (Spring Boot)                          │
│            IntelliJ IDEA (Java Code & APIs)                      │
│    Controllers → Services → Repositories → Database              │
└─────────────────────┬───────────────────────────────────────────┘
                      │
                      │ SQL Queries
                      ↓
┌─────────────────────────────────────────────────────────────────┐
│                 DATABASE (MySQL Workbench)                       │
│          Tables: users, products, cart, orders, etc              │
└─────────────────────────────────────────────────────────────────┘

TOOLS:
1. IntelliJ IDEA (Backend Development)
What it is: An IDE (Integrated Development Environment) for writing Java code
Location: Left panel shows your project structure
What you write: Spring Boot Java files

2. VS Code (Frontend Development)
What it is: A code editor for writing HTML, CSS, JavaScript
Location: Your ecommerce-frontend folder on Desktop
What you write: HTML pages, CSS styling, JavaScript logic

3. MySQL Workbench (Database Management)
What it is: A tool to manage your MySQL database
Location: Separate application
What you do: Create tables, insert data, view data

4. Postman (API Testing)
What it is: A tool to test your backend APIs without using frontend
Location: Separate application
What you do: Send requests to your backend and see responses

5. Browser (Viewing Frontend)
What it is: Chrome, Firefox, Edge - displays your website
What you do: Open index.html and use your ecommerce website

BACKEND FILES IN INTELLIJ (Spring Boot)
Folder Structure:
com.example.demo/
├── DemoApplication.java          ← Main app starter
├── Product.java                  ← Product entity/model
├── ProductRepository.java        ← Database queries for products
├── ProductController.java        ← API endpoints for products
├── User.java                     ← User entity/model
├── UserRepository.java           ← Database queries for users
├── UserController.java           ← API endpoints for users
├── Cart.java                     ← Cart entity/model
├── CartItem.java                 ← Cart item entity/model
├── CartRepository.java           ← Database queries for carts
├── CartItemRepository.java       ← Database queries for cart items
├── CartController.java           ← API endpoints for cart
├── Order.java                    ← Order entity/model
├── OrderItem.java                ← Order item entity/model
├── OrderRepository.java          ← Database queries for orders
├── OrderItemRepository.java      ← Database queries for order items
├── OrderController.java          ← API endpoints for orders
└── CorsConfig.java               ← Allow frontend to talk to backend

DETAILED FILE EXPLANATIONS
1. DemoApplication.java
WHAT IT DOES:
- Starts your entire Spring Boot application
- Entry point of your backend

WHERE IT'S LOCATED:
- src/main/java/com/example/demo/DemoApplication.java

CODE:
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

WHAT IT MEANS:
- @SpringBootApplication = "This is a Spring Boot app"
- main() = When you click the Green Play button in IntelliJ, 
          this main() function runs and starts the app

2. Product.java (Entity/Model)
WHAT IT DOES:
- Defines the structure of a Product
- Tells the database what columns a product has

WHERE IT'S LOCATED:
- com.example.demo/Product.java

CODE STRUCTURE:
@Entity              ← This is a database table
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue    ← Auto-generate ID (1, 2, 3...)
    private Long id;
    
    private String name;        ← Product name like "Laptop"
    private String description; ← Description like "Gaming Laptop"
    private double price;       ← Price like 75000
}

DATABASE EQUIVALENT:
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    price DOUBLE
);

REAL WORLD EXAMPLE:
When you insert a product:
- name = "Laptop"
- description = "Gaming Laptop"
- price = 75000
- id = 1 (auto-generated)

3. ProductRepository.java (Interface)
WHAT IT DOES:
- Provides database query methods for products
- You don't write SQL, this handles it automatically

WHERE IT'S LOCATED:
- com.example.demo/ProductRepository.java

CODE:
public interface ProductRepository extends JpaRepository<Product, Long> {
}

WHAT IT MEANS:
- JpaRepository = "Give me all basic database methods"
- <Product, Long> = "I'm working with Product entities, 
                     and their IDs are Long type"

METHODS YOU GET AUTOMATICALLY:
- findAll()           ← Get all products
- findById(1L)        ← Get product with ID 1
- save(product)       ← Insert/update a product
- deleteById(1L)      ← Delete product with ID 1

REAL WORLD EQUIVALENT:
Instead of writing SQL:
❌ SELECT * FROM products;
✅ productRepository.findAll();

4. ProductController.java (API Endpoints)
WHAT IT DOES:
- Receives requests from frontend
- Calls repository to get/save data
- Sends response back to frontend

WHERE IT'S LOCATED:
- com.example.demo/ProductController.java

ENDPOINTS YOU CREATED:
┌─────────────────────────────────────────────────────┐
│ GET    /products              ← Get all products     │
│ GET    /products/{id}         ← Get 1 product       │
│ POST   /products              ← Create new product   │
│ PUT    /products/{id}         ← Update product       │
│ DELETE /products/{id}         ← Delete product       │
└─────────────────────────────────────────────────────┘

CODE BREAKDOWN:
@RestController              ← This handles API requests
@RequestMapping("/products") ← All endpoints start with /products

@GetMapping
public List<Product> getAll() {
    return productRepository.findAll();
}
WHAT IT DOES:
- When frontend calls: GET http://localhost:8080/products
- It fetches all products from database
- Returns as JSON: [{"id":1,"name":"Laptop"...}, ...]

@PostMapping
public Product create(@RequestBody Product product) {
    return productRepository.save(product);
}
WHAT IT DOES:
- When frontend sends: POST http://localhost:8080/products
- With body: {"name":"Phone", "description":"...", "price":25000}
- It saves to database
- Returns the saved product with its new ID

@PutMapping("/{id}")
public Product update(@PathVariable Long id, @RequestBody Product p) {
    Product existing = productRepository.findById(id).get();
    existing.setName(p.getName());
    existing.setPrice(p.getPrice());
    return productRepository.save(existing);
}
WHAT IT DOES:
- When frontend calls: PUT http://localhost:8080/products/1
- With body: {"name":"New Name", "price":80000}
- It updates the product
- Returns updated product

@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
    productRepository.deleteById(id);
}
WHAT IT DOES:
- When frontend calls: DELETE http://localhost:8080/products/1
- It deletes that product from database

5. User.java (Entity/Model)
WHAT IT DOES:
- Defines structure of a User
- Similar to Product.java but for users

DATABASE TABLE:
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255)
);

REAL WORLD DATA:
id=1, name="John", email="john@gmail.com", password="1234"

6. UserRepository.java (Interface)
WHAT IT DOES:
- Database queries for users
- Plus a custom method for finding by email

CODE:
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);  ← Custom method
}

WHY findByEmail()?
When user logs in with email "john@gmail.com", 
we need to find that user by email first!
So we call: User user = userRepository.findByEmail("john@gmail.com");

7. UserController.java (API Endpoints)
WHAT IT DOES:
- Handle user registration and login

ENDPOINTS:
┌──────────────────────────────────────────────┐
│ POST /users/register   ← Create new user    │
│ POST /users/login      ← Login user         │
└──────────────────────────────────────────────┘

REGISTER ENDPOINT:
@PostMapping("/register")
public User register(@RequestBody User user) {
    return userRepository.save(user);
}

WHAT IT DOES:
- Frontend sends: POST http://localhost:8080/users/register
- Body: {"name":"John", "email":"john@gmail.com", "password":"1234"}
- Backend saves user to database
- Returns the user with new ID: {"id":1, "name":"John", ...}

LOGIN ENDPOINT:
@PostMapping("/login")
public String login(@RequestBody User loginUser) {
    User user = userRepository.findByEmail(loginUser.getEmail());
    if (user != null && user.getPassword().equals(loginUser.getPassword())) {
        return "Login Successful. User ID: " + user.getId();
    }
    return "Invalid email or password";
}

WHAT IT DOES:
- Frontend sends: POST http://localhost:8080/users/login
- Body: {"email":"john@gmail.com", "password":"1234"}
- Backend finds user by email
- Checks if password matches
- If yes → returns "Login Successful. User ID: 1"
- If no → returns "Invalid email or password"

SECURITY NOTE:
Don't store plain passwords in real apps!
Use password encryption (BCryptPasswordEncoder)

8. Cart.java (Entity/Model)
WHAT IT DOES:
- Represents a shopping cart belonging to a user

DATABASE TABLE:
CREATE TABLE cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT
);

REAL WORLD DATA:
id=1, userId=1  ← Cart for user with ID 1

9. CartItem.java (Entity/Model)
WHAT IT DOES:
- Represents one item in a cart
- Links a product to a cart with quantity

DATABASE TABLE:
CREATE TABLE cart_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cartId INT,
    productId INT,
    quantity INT
);

REAL WORLD DATA:
id=1, cartId=1, productId=1, quantity=2
Meaning: Cart 1 has Product 1 with quantity 2
(User added Laptop twice to their cart)

10. CartRepository.java & CartItemRepository.java
WHAT THEY DO:
- Database queries for carts and cart items

CartRepository:
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);  ← Find cart by user
}

CartItemRepository:
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);  ← Find items in cart
}

WHY THESE METHODS?
- findByUserId() → When user logs in, get their cart
- findByCartId() → Get all items in that cart

11. CartController.java (API Endpoints)
WHAT IT DOES:
- Handle cart operations: add, view, remove items

ENDPOINTS:
┌────────────────────────────────────────────────┐
│ POST   /cart/add                ← Add item    │
│ GET    /cart/{userId}           ← View cart   │
│ DELETE /cart/remove/{itemId}    ← Remove item │
└────────────────────────────────────────────────┘

ADD TO CART:
@PostMapping("/add")
public String addToCart(@RequestParam Long userId, 
                        @RequestParam Long productId, 
                        @RequestParam int quantity) {
    Cart cart = cartRepository.findByUserId(userId);
    if (cart == null) {
        cart = new Cart();
        cart.setUserId(userId);
        cart = cartRepository.save(cart);
    }
    CartItem item = new CartItem();
    item.setCartId(cart.getId());
    item.setProductId(productId);
    item.setQuantity(quantity);
    cartItemRepository.save(item);
    return "Product added to cart successfully!";
}

STEP BY STEP:
1. User with ID 1 clicks "Add to Cart" for Product 1
2. Frontend calls: POST /cart/add?userId=1&productId=1&quantity=1
3. Backend checks: Does user 1 have a cart?
4. If NO → Create new cart for user 1
5. If YES → Use existing cart
6. Create CartItem: cartId=1, productId=1, quantity=1
7. Save to database
8. Return "Product added to cart successfully!"

VIEW CART:
@GetMapping("/{userId}")
public List<CartItem> viewCart(@PathVariable Long userId) {
    Cart cart = cartRepository.findByUserId(userId);
    if (cart == null) return List.of();
    return cartItemRepository.findByCartId(cart.getId());
}

STEP BY STEP:
1. Frontend calls: GET /cart/1
2. Backend finds user 1's cart
3. Gets all items in that cart
4. Returns: [{"id":1, "cartId":1, "productId":1, "quantity":2}, ...]

REMOVE FROM CART:
@DeleteMapping("/remove/{cartItemId}")
public String removeFromCart(@PathVariable Long cartItemId) {
    cartItemRepository.deleteById(cartItemId);
    return "Item removed from cart!";
}

STEP BY STEP:
1. Frontend calls: DELETE /cart/remove/1
2. Backend deletes CartItem with ID 1
3. Returns "Item removed from cart!"

12. Order.java (Entity/Model)
WHAT IT DOES:
- Represents a customer's order

DATABASE TABLE:
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    totalPrice DOUBLE,
    status VARCHAR(255),
    createdAt DATETIME
);

REAL WORLD DATA:
id=1, userId=1, totalPrice=150000, status="PLACED", createdAt="2025-03-27 10:00:00"

13. OrderItem.java (Entity/Model)
WHAT IT DOES:
- Represents one product in an order
- Similar to CartItem but for completed orders

DATABASE TABLE:
CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    orderId INT,
    productId INT,
    quantity INT,
    price DOUBLE
);

REAL WORLD DATA:
id=1, orderId=1, productId=1, quantity=2, price=75000
(Order 1 contains Product 1, quantity 2, at price 75000 each)

14. OrderRepository.java & OrderItemRepository.java
WHAT THEY DO:
- Database queries for orders

OrderRepository:
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);  ← Get user's orders
}

OrderItemRepository:
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);  ← Get items in order
}

15. OrderController.java (API Endpoints)
WHAT IT DOES:
- Handle order placement and viewing

ENDPOINTS:
┌──────────────────────────────────────┐
│ POST /orders/place/{userId}   ← Place order  │
│ GET  /orders/{userId}         ← View orders  │
└──────────────────────────────────────┘

PLACE ORDER:
@PostMapping("/place/{userId}")
public String placeOrder(@PathVariable Long userId) {
    // Step 1: Get user's cart
    Cart cart = cartRepository.findByUserId(userId);
    if (cart == null) return "Cart is empty!";
    
    // Step 2: Get all items in cart
    List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
    if (cartItems.isEmpty()) return "Cart is empty!";
    
    // Step 3: Calculate total price
    double totalPrice = 0;
    for (CartItem item : cartItems) {
        Product product = productRepository.findById(item.getProductId()).get();
        totalPrice += product.getPrice() * item.getQuantity();
    }
    
    // Step 4: Create order
    Order order = new Order();
    order.setUserId(userId);
    order.setTotalPrice(totalPrice);
    order = orderRepository.save(order);
    
    // Step 5: Create order items
    for (CartItem item : cartItems) {
        Product product = productRepository.findById(item.getProductId()).get();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(item.getProductId());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItemRepository.save(orderItem);
    }
    
    // Step 6: Clear cart
    cartItemRepository.deleteAll(cartItems);
    
    return "Order placed successfully! Order ID: " + order.getId() + 
           " Total: Rs." + totalPrice;
}

REAL WORLD FLOW:
User adds 2 Laptops (Rs.75000 each) to cart
User adds 1 Phone (Rs.25000) to cart
User clicks "Place Order"

Backend does:
1. Get user's cart
2. Find both items: Laptop (qty 2) and Phone (qty 1)
3. Calculate: (75000*2) + (25000*1) = 175000
4. Create Order: id=1, userId=1, totalPrice=175000
5. Create OrderItems:
   - OrderItem 1: order=1, product=1(Laptop), qty=2, price=75000
   - OrderItem 2: order=1, product=2(Phone), qty=1, price=25000
6. Delete all CartItems
7. Return success message

VIEW ORDERS:
@GetMapping("/{userId}")
public List<Order> getOrders(@PathVariable Long userId) {
    return orderRepository.findByUserId(userId);
}

WHAT IT DOES:
- Frontend calls: GET /orders/1
- Backend returns all orders by user 1

16. CorsConfig.java
WHAT IT DOES:
- Allows your frontend to talk to your backend
- Without this, browser blocks requests from frontend to backend

WHY IS IT NEEDED?
Security issue: Browsers don't allow:
Frontend: http://localhost:3000  → Backend: http://localhost:8080
By default, this is blocked!

CODE:
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}

WHAT IT MEANS:
- addMapping("/**") = "Apply to all endpoints"
- allowedOrigins("*") = "Allow requests from any origin"
- allowedMethods(...) = "Allow these HTTP methods"
- allowedHeaders("*") = "Allow any headers"

SIMPLE TRANSLATION:
"Hey browser, I'm allowing frontend to send requests to my backend. 
It's safe! Don't block it!"

17. application.properties
WHAT IT DOES:
- Configuration file for your Spring Boot app

LOCATION:
src/main/resources/application.properties

CONTENT:
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
↳ Tells Spring: "Connect to MySQL database named 'ecommerce' on localhost"

spring.datasource.username=root
↳ MySQL username

spring.datasource.password=YOUR_PASSWORD
↳ MySQL password

spring.jpa.hibernate.ddl-auto=update
↳ "Automatically update database schema when app starts"
   update = Don't delete existing data, just add new columns
   create = Delete everything and recreate (causes duplicates!)

spring.jpa.show-sql=true
↳ "Print all SQL queries to console for debugging"

📄 FRONTEND FILES IN VS CODE
Folder Structure:
ecommerce-frontend/
├── index.html      ← Home page with product listing
├── login.html      ← Login page
├── register.html   ← Registration page
├── cart.html       ← Shopping cart page
├── orders.html     ← Order history page
└── style.css       ← Styling for all pages

1. index.html (Home Page)
WHAT IT DOES:
- Shows all products
- Shows navbar with login/logout
- Allows users to add items to cart

STRUCTURE:
<nav>              ← Navigation bar
  ShopEasy logo
  Home | Cart | Orders | Login/Logout
  
<div id="products-container">  ← Products will load here
  
<script>           ← JavaScript code
  Load products from backend
  Add to cart functionality
  Check if user is logged in
FLOW:

Page loads
JavaScript fetches: GET http://localhost:8080/products
Shows all products as cards
User clicks "Add to Cart"
JavaScript sends: POST /cart/add?userId=1&productId=1&quantity=1
Shows popup: "Product added to cart successfully!"


2. style.css (Styling)
WHAT IT DOES:
- Colors, fonts, layout for all pages

MAIN STYLES:
.navbar              ← Blue navigation bar
.products-grid      ← 4 columns of products
.product-card       ← Individual product styling
.form-container     ← Login/Register form styling
.cart-item          ← Cart item styling
.order-card         ← Order styling

EXAMPLE:
.navbar {
    background-color: #2874f0;  ← Blue color
    color: white;               ← White text
    padding: 15px 30px;         ← Space inside
    display: flex;              ← Arrange items in row
}

3. login.html (Login Page)
WHAT IT DOES:
- Shows login form
- Takes email and password
- Sends to backend
- Saves user ID in localStorage
- Redirects to home page

FLOW:
1. User enters email and password
2. Clicks "Login" button
3. JavaScript sends: POST /users/login
4. Backend returns: "Login Successful. User ID: 1"
5. JavaScript extracts user ID: 1
6. Saves to localStorage: userId = "1"
7. Redirects to index.html

WHY localStorage?
- Remembers user ID even after page refresh
- Other pages read this ID to know which user is logged in

CODE EXAMPLE:
function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    
    fetch('http://localhost:8080/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: email, password: password })
    })
    .then(res => res.text())
    .then(msg => {
        if (msg.includes('Login Successful')) {
            const userId = msg.split('User ID: ')[1];
            localStorage.setItem('userId', userId);
            window.location.href = 'index.html';
        }
    });
}

STEP BY STEP:
1. Get email and password from input fields
2. Send POST request to backend
3. Get response text
4. If it contains "Login Successful"
5. Extract the user ID from response
6. Save user ID to localStorage
7. Go to home page

4. register.html (Registration Page)
WHAT IT DOES:
- Shows registration form
- Creates new user account
- Redirects to login page

FLOW:
1. User enters name, email, password
2. Clicks "Register" button
3. JavaScript sends: POST /users/register
4. Backend creates user and returns: {"id":1, "name":"John", ...}
5. Shows popup: "Registration Successful!"
6. Redirects to login.html

CODE EXAMPLE:
function register() {
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    
    fetch('http://localhost:8080/users/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password })
    })
    .then(res => res.json())
    .then(user => {
        alert('Registration Successful!');
        window.location.href = 'login.html';
    });
}

5. cart.html (Shopping Cart Page)
WHAT IT DOES:
- Shows items user added to cart
- Shows "Remove" button for each item
- Shows "Place Order" button

FLOW:
1. Page loads
2. Gets userId from localStorage
3. If no userId → redirect to login
4. Fetches: GET /cart/1 (get cart items for user 1)
5. Shows each item with Remove button
6. Shows "Place Order" button

REMOVE ITEM:
- User clicks "Remove" button
- JavaScript sends: DELETE /cart/remove/1 (delete CartItem 1)
- Page reloads to show updated cart

PLACE ORDER:
- User clicks "Place Order" button
- JavaScript sends: POST /orders/place/1 (place order for user 1)
- Backend calculates total, creates order, clears cart
- Shows popup with order details
- Redirects to orders.html

CODE EXAMPLE:
const userId = localStorage.getItem('userId');

fetch(`http://localhost:8080/cart/${userId}`)
    .then(res => res.json())
    .then(items => {
        // Show each item
        items.forEach(item => {
            // Display item with Remove button
        });
    });

function placeOrder() {
    fetch(`http://localhost:8080/orders/place/${userId}`, {
        method: 'POST'
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        window.location.href = 'orders.html';
    });
}

6. orders.html (Order History Page)
WHAT IT DOES:
- Shows all past orders of logged-in user
- Shows order ID, total price, date, status

FLOW:
1. Page loads
2. Gets userId from localStorage
3. Fetches: GET /orders/1 (get orders for user 1)
4. Shows each order as a card with details

CODE EXAMPLE:
const userId = localStorage.getItem('userId');

fetch(`http://localhost:8080/orders/${userId}`)
    .then(res => res.json())
    .then(orders => {
        orders.forEach(order => {
            // Display Order ID, Total, Date, Status
        });
    });

DISPLAYS:
Order ID: 1
Total: Rs. 150000
Date: 3/27/2025
Status: PLACED ✅

🗄️ DATABASE STRUCTURE (MySQL Workbench)
6 Tables You Created:
1. products
Stores: id, name, description, price

INSERT: Product gets ID auto-generated (1, 2, 3...)
DELETE: When you delete a product from admin panel
UPDATE: When you edit product price
SELECT: When home page loads products
2. users
Stores: id, name, email, password

INSERT: When user registers
SELECT: When user logs in (find by email)
UPDATE: If user changes password
3. cart
Stores: id, userId

PURPOSE: One cart per user
RELATIONSHIP: userId links to users table

Example:
user_id=1 → cart_id=1
user_id=2 → cart_id=2
4. cart_items
Stores: id, cartId, productId, quantity

PURPOSE: Items in a cart
RELATIONSHIP: 
- cartId links to cart table
- productId links to products table

Example:
cart_id=1, product_id=1, quantity=2
(User 1's cart has Laptop with quantity 2)
5. orders
Stores: id, userId, totalPrice, status, createdAt

PURPOSE: Customer orders
RELATIONSHIP: userId links to users table

Example:
order_id=1, user_id=1, total=150000, status="PLACED", date="2025-03-27 10:00:00"
6. order_items
Stores: id, orderId, productId, quantity, price

PURPOSE: Products in an order
RELATIONSHIP:
- orderId links to orders table
- productId links to products table

Example:
order_id=1, product_id=1, quantity=2, price=75000
(Order 1 has Laptop with quantity 2 at price 75000 each)

📮 POSTMAN (API Testing)
What Postman Does:
Lets you test backend APIs without using the frontend
5 API Endpoints You Tested:
1. Get All Products
METHOD: GET
URL: http://localhost:8080/products

WHAT IT DOES:
- Fetches all products from database
- No body needed

RESPONSE:
[
  {"id":1, "name":"Laptop", "description":"Gaming Laptop", "price":75000},
  {"id":2, "name":"Phone", "description":"Android Phone", "price":25000},
  ...
]

HOW TO TEST IN POSTMAN:
1. Click "+" to new tab
2. Select GET
3. Paste URL
4. Click Send
5. See response below
2. Create User (Register)
METHOD: POST
URL: http://localhost:8080/users/register

BODY (raw JSON):
{
  "name": "John",
  "email": "john@gmail.com",
  "password": "1234"
}

WHAT IT DOES:
- Creates new user in database
- Returns user with auto-generated ID

RESPONSE:
{
  "id": 1,
  "name": "John",
  "email": "john@gmail.com",
  "password": "1234"
}

HOW TO TEST IN POSTMAN:
1. Select POST
2. Paste URL
3. Click Body → raw → JSON
4. Paste the JSON above
5. Click Send
3. Login
METHOD: POST
URL: http://localhost:8080/users/login

BODY (raw JSON):
{
  "email": "john@gmail.com",
  "password": "1234"
}

WHAT IT DOES:
- Checks if email and password match
- Returns "Login Successful" or error

RESPONSE IF CORRECT:
Login Successful. User ID: 1

RESPONSE IF WRONG:
Invalid email or password

HOW TO TEST IN POSTMAN:
1. Select POST
2. Paste URL
3. Click Body → raw → JSON
4. Paste the JSON above
5. Click Send
4. Add to Cart
METHOD: POST
URL: http://localhost:8080/cart/add?userId=1&productId=1&quantity=2

WHAT IT DOES:
- Creates cart for user if doesn't exist
- Adds product to cart with quantity

RESPONSE:
Product added to cart successfully!

HOW TO TEST IN POSTMAN:
1. Select POST
2. Paste URL (URL includes parameters)
3. NO BODY NEEDED
4. Click Send
5. Place Order
METHOD: POST
URL: http://localhost:8080/orders/place/1

WHAT IT DOES:
- Takes all items from user's cart
- Calculates total price
- Creates order
- Creates order items
- Clears cart

RESPONSE:
Order placed successfully! Order ID: 1 Total: Rs.150000.0

HOW TO TEST IN POSTMAN:
1. Select POST
2. Paste URL
3. NO BODY NEEDED
4. Click Send

🔄 DATA FLOW - COMPLETE EXAMPLE
Scenario: User John buys a Laptop and Phone
STEP 1: REGISTRATION
┌─ Frontend (register.html)
│  User enters: name="John", email="john@gmail.com", password="1234"
│
├─→ Postman (or JavaScript fetch)
│   POST http://localhost:8080/users/register
│
├─→ Backend (UserController.register)
│   Call: userRepository.save(user)
│
├─→ Database (MySQL)
│   INSERT INTO users (name, email, password) 
│   VALUES ('John', 'john@gmail.com', '1234')
│
└─→ Backend returns User with ID=1

STEP 2: LOGIN
┌─ Frontend (login.html)
│  User enters: email="john@gmail.com", password="1234"
│
├─→ Backend (UserController.login)
│   Call: userRepository.findByEmail("john@gmail.com")
│
├─→ Database
│   SELECT * FROM users WHERE email="john@gmail.com"
│
└─→ Backend returns "Login Successful. User ID: 1"
    Frontend saves: localStorage.userId = "1"

STEP 3: BROWSE PRODUCTS
┌─ Frontend (index.html)
│  Page loads
│
├─→ JavaScript: fetch('http://localhost:8080/products')
│   GET http://localhost:8080/products
│
├─→ Backend (ProductController.getAll)
│   Call: productRepository.findAll()
│
├─→ Database
│   SELECT * FROM products
│   Returns all 5 products
│
└─→ Frontend shows products as cards

STEP 4: ADD LAPTOP TO CART
┌─ Frontend (index.html)
│  User clicks "Add to Cart" on Laptop (ID=1)
│
├─→ JavaScript: fetch('/cart/add?userId=1&productId=1&quantity=1')
│   POST http://localhost:8080/cart/add?userId=1&productId=1&quantity=1
│
├─→ Backend (CartController.addToCart)
│   1. Find cart for userId=1 → doesn't exist, create new
│   2. Create CartItem: cartId=1, productId=1, quantity=1
│   3. Save CartItem to database
│
├─→ Database
│   INSERT INTO cart (userId) VALUES (1)  ← Gets ID=1
│   INSERT INTO cart_items (cartId, productId, quantity)
│   VALUES (1, 1, 1)
│
└─→ Frontend shows popup: "Product added to cart!"

STEP 5: ADD PHONE TO CART
┌─ Frontend
│  User clicks "Add to Cart" on Phone (ID=2)
│
├─→ Similar flow but productId=2
│
├─→ Database
│   INSERT INTO cart_items (cartId, productId, quantity)
│   VALUES (1, 2, 1)
│
└─→ Frontend popup: "Product added to cart!"

STEP 6: VIEW CART
┌─ Frontend (cart.html)
│  Page loads
│
├─→ JavaScript: fetch('/cart/1')
│   GET http://localhost:8080/cart/1
│
├─→ Backend (CartController.viewCart)
│   1. Find cart for userId=1
│   2. Find all cart_items for cartId=1
│
├─→ Database
│   SELECT * FROM cart_items WHERE cartId=1
│   Returns: 2 items (Laptop qty 1, Phone qty 1)
│
└─→ Frontend shows both items with Remove buttons

STEP 7: PLACE ORDER
┌─ Frontend (cart.html)
│  User clicks "Place Order" button
│
├─→ JavaScript: fetch('/orders/place/1')
│   POST http://localhost:8080/orders/place/1
│
├─→ Backend (OrderController.placeOrder)
│   1. Find cart for userId=1 → cartId=1
│   2. Find all items in cart → 2 items
│   3. Get product details:
│      - Product 1 (Laptop): price=75000, quantity=1
│      - Product 2 (Phone): price=25000, quantity=1
│   4. Calculate total: 75000 + 25000 = 100000
│   5. Create Order: userId=1, totalPrice=100000, status="PLACED"
│   6. Create OrderItems:
│      - OrderItem 1: orderId=1, productId=1, quantity=1, price=75000
│      - OrderItem 2: orderId=1, productId=2, quantity=1, price=25000
│   7. Delete all CartItems
│
├─→ Database
│   INSERT INTO orders (userId, totalPrice, status, createdAt)
│   VALUES (1, 100000, 'PLACED', NOW())  ← Gets ID=1
│   
│   INSERT INTO order_items (orderId, productId, quantity, price)
│   VALUES (1, 1, 1, 75000)
│   
│   INSERT INTO order_items (orderId, productId, quantity, price)
│   VALUES (1, 2, 1, 25000)
│   
│   DELETE FROM cart_items WHERE cartId=1
│
├─→ Backend returns: "Order placed successfully! Order ID: 1 Total: Rs.100000.0"
│
└─→ Frontend shows popup and redirects to orders.html

STEP 8: VIEW ORDERS
┌─ Frontend (orders.html)
│  Page loads
│
├─→ JavaScript: fetch('/orders/1')
│   GET http://localhost:8080/orders/1
│
├─→ Backend (OrderController.getOrders)
│   Find all orders for userId=1
│
├─→ Database
│   SELECT * FROM orders WHERE userId=1
│   Returns: 1 order (ID=1, total=100000, status="PLACED")
│
└─→ Frontend shows order card with all details

🎯 QUICK REFERENCE - WHO TALKS TO WHO
┌─────────────────────────────────────────────────────────────┐
│                  BROWSER (User sees)                         │
│         HTML + CSS + JavaScript Files                        │
│  (index.html, login.html, cart.html, etc.)                  │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  │ HTTP Requests (JSON data)
                  │ fetch() in JavaScript
                  ↓
┌─────────────────────────────────────────────────────────────┐
│              SPRING BOOT BACKEND                             │
│          Receives requests → Processes → Sends response      │
│                                                               │
│  Controllers   (Receive requests)                           │
│      ↓                                                        │
│  Services      (Business logic) [We didn't create these]     │
│      ↓                                                        │
│  Repositories  (Database queries)                           │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  │ SQL Queries
                  ↓
┌─────────────────────────────────────────────────────────────┐
│                 MYSQL DATABASE                               │
│  (products, users, cart, orders, etc. tables)               │
└─────────────────────────────────────────────────────────────┘


FLOW SUMMARY
USER INTERACTION:
1. Opens browser
2. Clicks index.html
3. JavaScript loads all products (calls backend API)
4. User registers → Backend creates user → Saves to MySQL
5. User logs in → Backend checks credentials → Saves ID in browser
6. User adds products to cart → Backend creates cart & items
7. User places order → Backend creates order → Clears cart
8. User views orders → Backend fetches orders from database

BACKGROUND (What you don't see):
- Controllers receive requests
- Repositories perform database queries
- MySQL stores all data
- Responses sent back as JSON
- Frontend displays the data
