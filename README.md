# Crypto Portfolio Tracker - Pricing Module

This branch is responsible for the **Crypto Price Simulation & Valuation Logic** in the Crypto Portfolio Tracker backend project.

## Features

- Simulates real-time crypto prices for coins like `BTC`, `ETH`, `SOL`, `ADA`
- Updates prices automatically every 30–60 seconds using `@Scheduled`
- Stores price data in the database with `timestamp`
- REST API to fetch the latest simulated prices
- Basic valuation logic to compute:
  - `Current Value = quantityHeld × currentPrice`
  - `calcProandLoss = (currentPrice - buyPrice) × quantityHeld`

## Tech Stack

- Java 21
- Spring Boot 3.4.6
- JPA (Hibernate)
- H2 / MySQL (for persistence)
- Lombok
- Scheduled Tasks