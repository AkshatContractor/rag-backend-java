RAG Backend Java

A Java-based backend service designed as a learning demonstration and practical implementation of Retrieval-Augmented Generation (RAG) pipelines. Built with Spring Boot, this service powers the interactive, context-aware AI features on www.akshatcontractor.in.

🌐 Live Deployment

This service is deployed as a serverless backend running on Google Cloud Run Functions (formerly Cloud Functions). It actively processes queries, retrieves semantic context from a vector database, and interacts with generative LLMs to serve real-time requests on the portfolio website.

🚀 Features

Gemini AI Integration: Connects to Google's Gemini models for generating responses based on retrieved context.

Vector Search with Qdrant: Uses Qdrant for semantic similarity lookups to retrieve relevant document context before querying the LLM.

Document Ingestion: Demonstrates how to parse, chunk, and extract text from files (such as PDFs, Markdown, JSON, or Text).

Metadata Support: Attaches and queries payload metadata (such as source or category) within Qdrant vectors.

REST APIs: Simple endpoints for triggering document ingestion, custom prompt queries, and vector storage indexing.

📂 Project Structure & Architecture

This project isolates the REST endpoints from the core vector database interactions and generative AI orchestrations to provide a clear path for studying RAG concepts in a serverless environment.

rag-backend-java/
├── src/
│   ├── main/
│   │   ├── java/com/akshatcontractor/rag/
│   │   │   ├── config/          # Configuration beans (Qdrant, Gemini, ThreadPools)
│   │   │   ├── controller/      # REST API endpoints (Ingestion & Query APIs)
│   │   │   ├── model/           # Data Transfer Objects (DTOs) and request schemas
│   │   │   ├── service/         # Core logical components
│   │   │   │   ├── DocumentService.java # File parsing & text chunking
│   │   │   │   ├── EmbeddingService.java # Converting text blocks to vectors
│   │   │   │   ├── QdrantService.java    # Operations with Qdrant vector database
│   │   │   │   └── RagService.java       # Coordinates the Query -> Retrieve -> Generate flow
│   │   │   └── RagBackendApplication.java
│   │   └── resources/
│   │       ├── application.yml  # Global configuration parameters
│   │       └── application-dev.yml
│   └── test/                    # Learning tests and verification suites
├── pom.xml                      # Build file containing core framework declarations
└── README.md


Component Breakdown

DocumentService: Demonstrates the ingestion phase. It handles incoming files, parses their text, and splits them into manageable chunks (using strategies like fixed-size windows with overlaps) to prepare them for vectorization.

EmbeddingService: Handles the transition from raw text to numerical representations. It interfaces with embedding models to turn text chunks into dense vectors.

QdrantService: Manages the storage and retrieval aspects. It interacts with the Qdrant vector database, creating collections, upserting vectorized text chunks with associated payload metadata, and performing similarity searches.

RagService: The orchestrator of the RAG pipeline. When a query is received, it directs the similarity search in Qdrant, compiles the retrieved text chunks as context, constructs the enriched prompt, and queries Gemini AI for the final answer.
