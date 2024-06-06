# Hotel Management System

## Introduction

The Hotel Management System is a comprehensive suite designed to facilitate the management of hotel operations. It encompasses a wide array of services for handling reservations, room management, client interactions, and financial transactions.

## Functional Overview

### Reservations

- Reservation Management: Guests can book rooms based on their preferences, and the system will handle reservation logistics, including room availability, booking confirmation, and cancellation policies.
- Room Cart Management: Potential guests can add desired rooms to a cart before finalizing their reservations, similar to an e-commerce shopping cart system.

### Room and Services

- Room Handling: Administrative users can create, update, and delete room listings, as well as manage room statuses for maintenance and availability.
- Category Management: Rooms and services are categorized for better organization and searching capabilities, allowing guests to book according to room types or service tiers.
- Item Management: Individual room items can be tracked and managed, ensuring that all accommodations are up to standard and properly inventoried.

### Financial Transactions

- Payment Processing: The system integrates secure payment processing, supporting various payment methods and currencies, issuing invoices, and managing receipts.
- Financial Records: Maintain comprehensive records of all financial transactions, including payments, refunds, and other billing activities.

### User Interactions

- User Management: Manage user accounts, including guest profiles, administrative users, and staff, with appropriate role-based access control.
- Authentication and Security: Secure user data with robust authentication mechanisms and ensure that sensitive information is encrypted and protected.

### Support and Services

- Customer Service: Facilitate guest services such as inquiries, special requests, and support tickets through an integrated customer service portal.

## Conclusion

The Hotel Management System aims to provide a seamless and efficient experience for both hotel staff and guests, streamlining operations, enhancing the guest experience, and optimizing the overall management of the hotel.

# UML Class Diagram
![HotelReserv](https://private-user-images.githubusercontent.com/126002721/337229179-26aecc19-452f-4607-8211-dee7be098ccd.jpeg?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc2NzA0NDIsIm5iZiI6MTcxNzY3MDE0MiwicGF0aCI6Ii8xMjYwMDI3MjEvMzM3MjI5MTc5LTI2YWVjYzE5LTQ1MmYtNDYwNy04MjExLWRlZTdiZTA5OGNjZC5qcGVnP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI0MDYwNiUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNDA2MDZUMTAzNTQyWiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9ZDZmMzhjZjdkMDRmZmI0YTRmOGQ2MjlhM2QzMjBiM2E5OTg4MDRiNjQ2NThjODdjNjJiYWYzYmEwNTU0M2M0ZiZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmYWN0b3JfaWQ9MCZrZXlfaWQ9MCZyZXBvX2lkPTAifQ.FKUoTkNSbzDWZgRjLZqYiqoWHA-1n5OtLXSFi0IXNLk)


**Project Summary:**

1. **Docker Utilization:** Successfully incorporated Docker for containerization, ensuring a consistent and isolated environment for the application.

2. **Criteria API Implementation:** Employed Criteria API for constructing programmatically the query criteria, providing a more dynamic and robust approach to querying data than static JPQL (Java Persistence Query Language).

3. **Metamodel API Usage:** Leveraged Metamodel API, which offers a type-safe way to create queries in Criteria API, thereby reducing runtime errors and improving query accuracy.

4. **Selective Cascading Operations:** Implemented cascading persist, update, merge, and remove operations. These operations were applied selectively across the application to ensure data integrity and manage entity state transitions efficiently.

5. **Comprehensive HTTP Request and TestData Set:** Developed a comprehensive set of HTTP requests and corresponding test data. This set can be utilized to thoroughly test the application's REST interfaces, ensuring functionality and reliability. These test cases are designed to be easily executed in API testing tools like Postman.

---

Project Limitations - Payment Processing:

This project incorporates a specific limitation concerning the payment processing mechanism. It is designed with the assumption that the actual payment execution will be outsourced to external services. Consequently, the project's scope does not encompass the direct handling or processing of payments within its system architecture. Instead, it calculates and prepares payment details, which are then expected to be managed and executed by third-party payment services. This approach streamlines the project's focus on its core functionalities while relying on specialized external systems for secure and efficient payment transactions.
