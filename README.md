# Warehouse Management System with POS Analytics

## Overview

The Warehouse Management System (WMS) application is a JavaFX-based solution designed to manage inventory, orders, and customer information efficiently. This application is tailored for use by inventory managers (IMs) in a physical store setting, providing real-time updates on stock levels, order processing, and sales analytics.

## Members and Roles

- [**Huỳnh Ngọc Anh Thư - ITCSIU21034:**](https://github.com/panadolextr91) Developer, Database, Report, Slides.
- [**Nguyễn Minh Đạt - ITDSIU22166:**](https://github.com/29Schiller) Database, Report, Slides.
- [**Nguyễn Lập Thuận - ITCSIU22279:**](https://github.com/HoangDeBongDem) Report, Slides.
- [**Phạm Hoài Nhật Nam - ITDSIU22143:**](https://github.com/jonathandanghokcode) Report, Slides.
- [**Lê Trọng Hiếu - ITDSIU22127:**](https://github.com/thhieu29) Report, Slides.
- [**Hà Anh Khoa - ITDSIU22128:**](https://github.com/Tinntinn060104) Report, Slides.
- [**Trần Khánh Toàn - ITITIU20322:**](https://github.com/Trantoan0106) Report, Slides.
- [**Nguyễn Hoàng Thiện - ITDSIU22131:**](https://github.com/THien2304) Report, Slides.
- [**Nguyễn Hải Phú - ITDSIU22179:**](https://github.com/haiphu241) Report, Slides.

## Features

- **Inventory Management:**
  - Real-time tracking of inventory levels
  - Detailed information on item locations within the warehouse
  - Automated alerts for low stock levels

- **Order Management:**
  - Processing and fulfilling customer orders
  - Real-time updates on order status
  - Generation of bills for customer payments

- **Customer Management:**
  - Adding and updating customer information
  - Managing customer orders and order history

- **Reporting and Analytics:**
  - Comprehensive reports on inventory levels and sales performance
  - Point of Sale (POS) analytics for sales and inventory trends

## Technologies Used

**Programming Language:**
- Java

**Interface Designing and Testing Tool:**
- SceneBuilder

**Frameworks and Libraries:**
- JavaFX (for building the user interface)
- JFoenix (for material design components in JavaFX)
- MySQL Connector/J (for database connectivity)
- Maven (for project management and build automation)
- JUnit 5 (for unit testing)
- JRebel (for hot reloading during development)

**Version Control:**
- Git (for version control and collaboration)

**Database Management Systems (DBMS):**
- MySQL

**Report and Presentation Tools:**
- Google Slides
- Canva
- Microsoft Word
- Microsoft PowerPoint

**IDE and Workspace:**
- IntelliJ IDEA
- MySQL Workbench

## Setup and Installation

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- MySQL Server
- Maven
- IntelliJ IDEA (or your preferred IDE)
- Git

### Installation Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/panadolextra91/PDM_WIMS
   cd PDM_WIMS
   ```
2. **Configure the database:**
- Create a MySQL database named pdm.
- Update the DatabaseConnection class with your MySQL username and password.
4. **Build the project:**
  ```bash
  mvn clean build
  ```
5. **Run the Application:**
- Open the project in IntelliJ IDEA.
- Run the MainApp class.
