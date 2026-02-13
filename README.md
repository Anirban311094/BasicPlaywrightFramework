# BasicPlaywrightFramework
Basic Playwright Framework with Java for high-performance UI and API validation.

## 👨‍💻 Author

**Anirban Chakrabarty** *Quality Engineering Analyst*

* **LinkedIn:** [Anirban Chakrabarty](https://www.linkedin.com/in/anirban-chakrabarty-a4263b1a6/)
* **Email:** [anirban9051673947@gmail.com](mailto:anirban9051673947@gmail.com) | [anirbanc1994@gmail.com](mailto:anirbanc1994@gmail.com)
* **Mobile:** (+91) 9674663035
* **Location:** Kolkata, India

---

## 🏗️ Framework Overview

This repository features a **Hybrid Automation Framework** designed for high-performance UI and API validation. It is built to address common automation challenges such as environment flakiness, complex synchronization, and fragmented reporting.

### **🚀 Core Architecture Components**

* **Hybrid Execution Engine:** Integrated support for both **Playwright UI** and **REST API** testing using a unified configuration, allowing for end-to-end scenarios.
* **Data-Driven Excellence:** Fully integrated with **Apache POI** and TestNG `@DataProvider` to drive complex test scenarios via **Excel (`AppData.xlsx`)**.
* **Resilient Navigation Layer:** Features a custom `NavigationUtils` utility that implements a deep-clean retry mechanism to specifically detect and recover from **502 Bad Gateway**, **ERR_CONNECTION_TIMED_OUT**, and slow-loading states.
* **Fluent Page Object Model (POM):** UI components are abstracted into modular Page Classes using Playwright's lazy-loading `Locator` strategy with **text-filtering** and **auto-scrolling** capabilities.
* **Smart API Client:** A custom wrapper around `APIRequestContext` that handles **Bearer Token** lifecycle management, supports prettified JSON response logging, and field-level validation.
* **Centralized Reporting:** Leveraging **Extent Reports** with a custom `TestListener` to aggregate logs, execution status, and automated screenshots into a single, shareable HTML dashboard.



---

## 📁 Project Structure

* **`src/main/java/com/framework/api`**: Core API client and DemoQA specific API wrappers.
* **`src/main/java/com/framework/pages`**: Optimized Page Objects for Account, Login, and BookStore modules.
* **`src/test/java/com/framework/tests`**: Consolidated test suites for Account Management and Book Store operations.
* **`src/test/resources/testdata`**: Centralized Excel data storage for parameterized testing.
* **`testng.xml`**: Modular XML files (`bookStoreTestNG.xml`, `accountManagementTestNG.xml`) for targeted test execution.

---

## 🛠️ Technology Stack

* **Language:** Java
* **Engine:** Playwright
* **Test Runner:** TestNG
* **Build Tool:** Maven
* **Reporting:** Extent Reports
* **Utilities:** Apache POI (Excel), Jackson Databind (JSON), Log4j2

---

## ⚙️ Getting Started

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Anirban311094/BasicPlaywrightFramework.git](https://github.com/Anirban311094/BasicPlaywrightFramework.git)