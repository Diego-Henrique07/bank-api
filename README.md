
# 🚀 Bank API
A robust REST API built with Spring Boot to manage customers, accounts, transactions, and cards, including real-world financial operations and PostgreSQL persistence.

🔥 Highlights
Full CRUD for Customers and Accounts
Transactions:
  - Deposit
  - Withdraw
  - Transfer
Balance tracking before and after each transaction
Account statement (transaction history)
Card system:
  - Create card
  - Block card
  - Activate card
Global exception handling
PostgreSQL integration (real-world database)

🧠 Business Logic
This system simulates a real banking backend, including:

Balance validation (no negative balance)
Active account validation before operations
Transfer rules (source ≠ target)
Transaction history with balance snapshots
Data consistency between entities
Relationship handling (Customer ↔ Account ↔ Transaction ↔ Card)

🛠️ Tech Stack
Java
Spring Boot
Spring Data JPA
Hibernate
PostgreSQL
Maven

📦 API Endpoints

👤 Customers
POST /customers
GET /customers
GET /customers/{id}
DELETE /customers/{id}

🏦 Accounts
POST /accounts
GET /accounts
GET /accounts/{id}

💸 Transactions
POST /transactions/deposit
POST /transactions/withdraw
POST /transactions/transfer

📊 Statement
GET /accounts/{id}/statement

💳 Cards
POST /cards
GET /cards
GET /cards/{id}
PATCH /cards/{id}/block
PATCH /cards/{id}/activate
