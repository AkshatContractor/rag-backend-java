# RAG Backend Java

A Java-based backend service that demonstrates and implements Retrieval-Augmented Generation (RAG) concepts using Spring Boot. The project powers the context-aware AI features available on **[www.akshatcontractor.in](http://www.akshatcontractor.in)**, combining semantic search with large language models to generate grounded responses.

## 🌍 Live Deployment

This service runs as a serverless backend on Google Cloud Run and actively powers AI-driven interactions on the portfolio website.

Its responsibilities include:

* Processing user queries
* Retrieving relevant semantic context from a vector database
* Constructing enriched prompts
* Generating responses through Gemini

## ✨ Highlights

### 🤖 Gemini Integration

Connects with Google's Gemini models to generate responses grounded in retrieved knowledge rather than relying solely on model memory.

### 🔎 Semantic Search with Qdrant

Uses vector similarity search to identify the most relevant pieces of information before response generation.

### 📄 Document Ingestion

Supports document parsing and chunking workflows that prepare content for embedding and retrieval.

### 🏷️ Metadata-Aware Retrieval

Associates metadata such as source, category, or document identifiers with stored vectors, enabling richer retrieval strategies.

### 🔌 REST APIs

Provides endpoints for:

* Document ingestion
* Knowledge base indexing
* Context-aware querying

---

## 🏗️ Project Structure

```text
rag-backend-java/
├── src/
│   ├── main/
│   │   ├── java/com/akshatcontractor/rag/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── service/
│   │   │   │   ├── DocumentService.java
│   │   │   │   ├── EmbeddingService.java
│   │   │   │   ├── QdrantService.java
│   │   │   │   └── RagService.java
│   │   │   └── RagBackendApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-dev.yml
│   └── test/
├── pom.xml
└── README.md
```

---

## ⚙️ Architecture

The application separates document processing, vector operations, and AI orchestration into dedicated services to keep the codebase modular and easy to understand.

### 📥 DocumentService

Handles document ingestion and preprocessing.

Responsibilities:

* Reading uploaded files
* Extracting text content
* Splitting content into chunks
* Preparing data for embedding generation

### 🧠 EmbeddingService

Converts text chunks into vector embeddings using the configured embedding model.

Responsibilities:

* Embedding generation
* Batch processing
* Vector preparation for storage

### 🗄️ QdrantService

Acts as the abstraction layer over the vector database.

Responsibilities:

* Collection management
* Vector storage
* Metadata persistence
* Similarity search operations

### 🔄 RagService

Coordinates the complete Retrieval-Augmented Generation workflow.

```text
User Query
    │
    ▼
Generate Query Embedding
    │
    ▼
Search Qdrant
    │
    ▼
Retrieve Relevant Chunks
    │
    ▼
Build Contextual Prompt
    │
    ▼
Query Gemini
    │
    ▼
Return Response
```

---

## 🛠️ Technology Stack

* Java 21
* Spring Boot
* LangChain4j
* Gemini API
* Qdrant
* Maven
* Google Cloud Run

---

## 🎯 Why This Project?

This project serves two purposes:

1. Powering the AI functionality behind **[www.akshatcontractor.in](http://www.akshatcontractor.in)**
2. Acting as a practical reference for developers exploring Retrieval-Augmented Generation in Java

It demonstrates the complete RAG lifecycle—from document ingestion and vectorization to semantic retrieval and context-grounded response generation.
