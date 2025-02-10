# Multi-Agent System for AI & GenAI Use Case Generation

## Overview
This project is a **Multi-Agent System** that automates the process of researching industry trends, generating AI & GenAI use cases, collecting relevant datasets, and creating proposal documents. It integrates **OpenAI GPT-3.5, Cohere AI, and Hugging Face** models to generate insights and recommendations based on real-world data.

## Features
- **Industry Trend Analysis:** Fetches recent trends using Google Serper API.
- **AI Use Case Generation:** Uses OpenAI, Cohere, and Hugging Face to generate AI & GenAI use cases.
- **Dataset Collection:** Searches for relevant datasets on Kaggle using Google Serper API.
- **Proposal Generation:** Compiles industry trends, AI use cases, and datasets into a structured proposal document.

## Technologies Used
- **Java** (JDK 11+)
- **OkHttp** (HTTP client for API calls)
- **JSON Processing** (org.json)
- **External APIs:**
  - OpenAI API (GPT-3.5-turbo)
  - Cohere API
  - Hugging Face Transformers API
  - Google Serper API

## Installation & Setup
### 1. Clone the Repository
```sh
 git clone https://github.com/Ronakkedia/multiAgent.git
 cd MultiAgent
```

### 2. Set Up API Keys
Store your API keys securely using **environment variables**:
```sh
export OPENAI_API_KEY="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"
export COHERE_API_KEY="GEpKT3zXX1yoMnVn064Sk6Vgizu2zQg7OgdQyvnr"
export HUGGINGFACE_API_KEY="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"
export SERPER_API_KEY="a104f57ca4bc2ae62eb1fc22db1333a059b83d4b"
```
Alternatively, create a `.env` file and use a Java dotenv library to load environment variables.

### 3. Install Dependencies
Ensure you have Java and Maven installed:
```sh
mvn clean install
```

### 4. Run the Application
Execute the main class to generate a proposal:
```sh
mvn exec:java -Dexec.mainClass="com.example.multiagent.MultiAgentSystem"
```

## Project Structure
```
multiagent-system/
│── src/main/java/com/example/multiagent/
│   ├── MultiAgentSystem.java          # Main entry point
│   ├── ResearchAgent.java             # Fetches industry trends via Google Serper API
│   ├── UseCaseGeneratorAgent.java     # Generates AI & GenAI use cases using OpenAI, Cohere, and Hugging Face
│   ├── DatasetCollectorAgent.java     # Searches for datasets on Kaggle using Google Serper API
│   ├── ProposalGeneratorAgent.java    # Creates proposal documents
│── pom.xml                            # Project dependencies
│── README.md                          # Documentation
```

## API Usage
### **1. Research Agent** (Fetches Industry Trends via Google Serper API)
```java
ResearchAgent researchAgent = new ResearchAgent();
List<String> trends = researchAgent.fetchIndustryTrends("Supply Chain Optimization");
```

### **2. Use Case Generator Agent** (Generates AI Use Cases via OpenAI, Cohere, Hugging Face)
```java
UseCaseGeneratorAgent useCaseAgent = new UseCaseGeneratorAgent();
String useCases = useCaseAgent.generateUseCaseUsingCohere("AI for supply chain optimization");
```

### **3. Dataset Collector Agent** (Finds Relevant Datasets via Google Serper API)
```java
DatasetCollectorAgent datasetAgent = new DatasetCollectorAgent();
List<String> datasets = datasetAgent.searchDatasets(useCases, "Supply Chain");
```

### **4. Proposal Generator Agent** (Creates AI Use Case Proposal)
```java
ProposalGeneratorAgent proposalAgent = new ProposalGeneratorAgent();
String proposal = proposalAgent.generateProposal("Supply Chain Optimization", trends, useCases, datasets);
System.out.println(proposal);
```

## Future Enhancements
- **Implement Logging:** Replace `System.out.println` with SLF4J or Log4j.
- **Optimize API Calls:** Use caching to reduce redundant requests.
- **Expand Dataset Search:** Integrate additional dataset repositories.
- **Web Interface:** Create a frontend to interact with the system.

## Contributors
- **Your Name** - [GitHub](https://github.com/RonakKedia)

## License
This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

