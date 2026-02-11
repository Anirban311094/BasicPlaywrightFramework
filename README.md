# BasicPlaywrightFramework
BasicPlaywrightFramework

## 👨‍💻 Author

**Anirban Chakrabarty** *Quality Engineering Analyst*

* **LinkedIn:** [Anirban Chakrabarty](https://www.linkedin.com/in/anirban-chakrabarty-a4263b1a6/)
* **Email:** [anirban9051673947@gmail.com](mailto:anirban9051673947@gmail.com) | [anirbanc1994@gmail.com](mailto:anirbanc1994@gmail.com)
* **Mobile:** (+91) 9674663035
* **Location:** Kolkata, India

## 🏗️ Framework Overview

This repository features a **Hybrid Automation Framework** designed for high-performance UI and API validation. It is built to address common automation challenges such as environment flakiness, complex synchronization, and fragmented reporting.

### **Core Architecture Components**
* **Hybrid Execution Engine:** Integrated support for both **Playwright UI** and **REST API** testing using a unified configuration, allowing for end-to-end scenarios (e.g., creating data via API and verifying it on the UI).
* **Resilient Navigation Layer:** Features a custom `NavigationUtils` utility that implements a deep-clean retry mechanism. It specifically detects and recovers from **502 Bad Gateway**, **ERR_CONNECTION_TIMED_OUT**, and slow-loading states by clearing browser storage/cookies and re-attempting the handshake.
* **Fluent Page Object Model (POM):** UI components are abstracted into modular Page Classes using Playwright's lazy-loading `Locator` strategy, ensuring thread-safety and reducing maintenance overhead.
* **Smart API Client:** A wrapper around `APIRequestContext` that handles **Bearer Token** lifecycle management, supports multiple payload types (JSON, Form Data), and provides prettified response logging.
* **Centralized Reporting:** Leveraging **Extent Reports** with a custom `TestListener` to aggregate logs, execution status, and Base64-encoded screenshots into a single, shareable HTML dashboard.


