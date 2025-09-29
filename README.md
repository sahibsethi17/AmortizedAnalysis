# AmortizedAnalysis

[![GitHub](https://img.shields.io/badge/GitHub-AmortizedAnalysis-blue?logo=github)](https://github.com/sahibsethi17/AmortizedAnalysis)

![amortizedanalysis](https://github.com/user-attachments/assets/example-image-id)


<!-- Tech Stack Badges -->
<p align="center">
  <!-- Backend -->
  <img src="https://img.shields.io/badge/Java-007396?logo=java&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white" alt="PostgreSQL"/>

  <!-- Frontend -->
  <img src="https://img.shields.io/badge/React-20232A?logo=react&logoColor=61DAFB" alt="React"/>
  <img src="https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=white" alt="TypeScript"/>
  <img src="https://img.shields.io/badge/TailwindCSS-06B6D4?logo=tailwindcss&logoColor=white" alt="TailwindCSS"/>

  <!-- Cloud / Infra -->
  <img src="https://img.shields.io/badge/AWS%20S3-569A31?logo=amazons3&logoColor=white" alt="AWS S3"/>
  <img src="https://img.shields.io/badge/AWS%20EC2-FF9900?logo=amazonec2&logoColor=white" alt="AWS EC2"/>
  <img src="https://img.shields.io/badge/AWS%20Bedrock-232F3E?logo=amazonaws&logoColor=white" alt="AWS Bedrock"/>
  <img src="https://img.shields.io/badge/AWS%20SAM-FF4F00?logo=serverless&logoColor=white" alt="AWS SAM"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white" alt="Docker"/>

  <!-- Dev -->
  <img src="https://img.shields.io/badge/GitHub-181717?logo=github&logoColor=white" alt="GitHub"/>
  <img src="https://img.shields.io/badge/CI%2FCD-2088FF?logo=githubactions&logoColor=white" alt="CI/CD"/>
</p>


An AI-powered personal finance platform with receipt uploads, expense tracking, and automated insights. Deployed on AWS with 100% uptime.

## Overview

AmortizedAnalysis is a full-stack AI-driven finance application that automates expense tracking and management. By integrating AI-powered receipt scanning, it provides users with real-time financial insights. Built on Spring Boot and React, with deployment on AWS, it ensures high availability and scalability.

### Key Features

- **AI Receipt Uploads**: Automatically parse and store financial data from receipts  
- **Scalable REST APIs**: Spring Boot backend with PostgreSQL database  
- **Cloud Deployment**: Hosted on AWS (S3, EC2, SAM, Bedrock) with 100% uptime  
- **Interactive Frontend**: React + TailwindCSS for responsive UI  
- **Secure Data Storage**: PostgreSQL for structured financial tracking  
- **CI/CD**: GitHub-based pipelines for continuous integration and deployment

### Technology Stack

- **Backend**: Java, Spring Boot, PostgreSQL  
- **Frontend**: React, TypeScript, TailwindCSS  
- **Infrastructure**: AWS (S3, EC2, SAM, Bedrock), Docker  
- **Development**: GitHub, CI/CD pipelines

## Architecture

The project consists of three main components:

1. **Receipt AI Engine**: Processes and extracts financial data from uploaded receipts  
2. **Web Interface**: Frontend for managing and visualizing finances  
3. **Backend APIs**: REST services handling authentication, transactions, and storage

## Quick Start

### Prerequisites

- Docker Desktop  
- Node.js (for frontend)  
- Java 17+ (for backend)  
- PostgreSQL (for local database testing)  

### Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/sahibsethi17/AmortizedAnalysis.git
   cd AmortizedAnalysis
   ```

2. **Start with Docker (Recommended)**
   ```bash
   docker compose up --build
   ```

3. **Access the application**
   - Frontend: http://localhost:3000  
   - Backend API: http://localhost:8080  

### Alternative Local Development

**Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

## Usage

1. Upload a receipt via the web interface  
2. AI extracts and stores expense data in PostgreSQL  
3. Visualize financial insights on the dashboard  
4. Track spending trends and manage budgets  

## Development Commands

### Clean Rebuild
```bash
docker compose down -v --remove-orphans
rm -rf frontend/node_modules frontend/package-lock.json backend/target
docker compose build --no-cache
docker compose up
```

### Quick Start (Reusing Images)
```bash
docker compose up
```

## Research Context

AmortizedAnalysis explores the integration of AI into financial management platforms. By leveraging AWS Bedrock for AI-powered parsing, the system demonstrates how cloud-native tools can enhance user productivity and provide reliable, automated insights.

### Data Pipeline

The backend pipeline includes:  
- AI-based receipt parsing and data extraction  
- Secure storage in PostgreSQL  
- Financial data APIs for frontend consumption  
- Cloud deployment via AWS services  

## Project Structure

```
AmortizedAnalysis/
├── backend/           # Spring Boot API
├── frontend/          # React + TailwindCSS web interface
├── docker-compose.yml # Multi-container orchestration
└── README.md          # This file
```

## Acknowledgments

- **Contributors**: Core development team and collaborators  
- **Frameworks**: Built with Spring Boot, React, and Docker  
- **Cloud**: AWS (S3, EC2, SAM, Bedrock)  
- **Inspiration**: Automating personal finance management with AI  

---

**Note**: This is an ongoing project focused on integrating AI with personal finance to automate and enhance user experience.  
