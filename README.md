# Document Service (Quick Guide)

Base URL: `/documents`  port 8081 

This service has **3 APIs**: create document, get document by id, and search documents.

---

## ✅ 1) Create Document
**POST** `/documents`  
Creates a document and attaches tags (tags are reusable across documents).

**Body**
```json
{
  "title": "Deep Learning Intro",
  "content": "Deep learning models such as CNN, RNN...",
  "tags": ["AI", "Machine Learning"]
}
 ```

**Response (example)**
```json
{
  "id": 1,
  "title": "Deep Learning Intro",
  "content": "Deep learning models such as CNN, RNN...",
  "tags": [
    { "id": 10, "name": "AI" },
    { "id": 11, "name": "Machine Learning" }
  ]
}
 ```

## ✅ 2) Get Document by ID
**GET** `/documents/{id}`  
Returns a document and its tags.

**Examples**
```http
GET http://localhost:8081/documents/1
GET http://localhost:8081/documents/2
```

## ✅ 3) Search Documents
GET ` /documents/search?query={query}&mode={mode}`
Searches documents by a user query. mode chooses which field(s) to search.

```Table
mode=title → search in title only

mode=content → search in contenat only

mode=tag → search by tag name

mode=all → search in title OR content OR tag name (default)
```

***Examples:***

```http
Copy code
GET /documents/search?query=Deep&mode=all
GET /documents/search?query=AI&mode=tag
GET /documents/search?query=Spring&mode=title
```
Search is GET → no request body.
## ▶️ Run with Docker Compose

1) Open a terminal and navigate to the project root (the folder that contains `docker-compose.yml`):
```bash
cd path/to/document_service
docker-compose up --build
```

