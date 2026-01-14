# reSearchForest - Backend 
> **2-depth 의미 기반 키워드 확장**을 통해 논문 검색을 지원하는 서비스  
> 사용자의 검색어가 모호해도, 주변 개념을 탐색하며 연구 주제를 확장할 수 있도록 돕습니다.

 **Capstone Design (2025.02 ~ 2025.06)**  
Team 3 (Backend 1 / Frontend 1 / AI 1)

---

##  Links
- Backend Repo: https://github.com/soopsong/searchforest-be
- Frontend Repo: https://github.com/soopsong/searchforest-fe
- AI Repo: https://github.com/soopsong/searchforest-ai

---

##  Key Features
### 1) 2-depth Semantic Keyword Recommendation
- 사용자가 입력한 **Root Keyword**를 기준으로
- **1-depth / 2-depth 키워드 트리**를 생성하여 시각적으로 탐색 가능
- 단순 문자열 기반 연관검색어가 아닌 **의미 기반 추천**

### 2) Keyword-based Paper Recommendation + Summary
- 선택한 키워드에 대해 **관련 논문 리스트** 제공
- 논문 메타데이터(제목/저자/초록/인용수 등)와 함께  
  **TL;DR 요약(사전 생성된 요약)** 을 제공하여 빠른 탐색 지원

### 3) Search History / Session Tracking
- 검색 흐름을 **세션 단위로 저장**
- Root → 클릭한 키워드 흐름 및 논문 클릭 기록을 관리하여
- 사용자가 탐색한 검색 여정을 복원할 수 있도록 지원

---

##  System Architecture (3-Tier)
reSearchForest는 **Frontend / Backend / AI Server**로 구성된 3-tier 구조입니다.

- **Frontend**: React + TypeScript + D3.js (Keyword Graph Visualization)
- **Backend (This Repo)**: Spring Boot 기반 REST API 서버
- **AI Server**: FastAPI 기반 추론 서버 (M3E + FAISS)  
- **Database**: MySQL (유저/세션/검색 기록/논문 메타데이터 관리)

> 전체 구조는 “Frontend ↔ Backend ↔ AI Server” 흐름으로 동작합니다.

---

## 🛠 Tech Stack
**Backend**
- Java 17
- Spring Boot / Spring MVC
- Spring Security (JWT / OAuth2 / Session)
- JPA (Hibernate)

**Database**
- MySQL

**Infra / DevOps**
- AWS EC2
- Docker / Docker Compose

**Testing & Logging**
- JUnit5 / Mockito / SLF4J

**Tools**
- Git / GitHub
- Swagger(OpenAPI) / Postman

---

##  My Role (Backend Developer)
본 프로젝트에서 백엔드를 단독으로 담당하며, 다음을 수행했습니다.

- **REST API 설계 및 구현**
  - 키워드 추천 요청/응답 구조 설계
  - 논문 리스트 조회 및 데이터 전달 API 구현
  - 응답 포맷 표준화

- **DB 설계 및 검색 이력(Session) 저장 구조 구현**
  - 검색 세션 단위로 Root Keyword → 클릭 키워드 흐름 저장
  - 세션별 논문 클릭 기록 저장 및 조회 API 구현

- **AI 서버 연동 및 데이터 파이프라인 연결**
  - AI 서버에서 전달받은 키워드 트리/논문 ID 기반으로
  - 백엔드에서 클라이언트에 필요한 형태로 가공하여 제공

- **배포 환경 구성**
  - Docker 기반 컨테이너 구성 및 서버 운영 환경 세팅(AWS EC2)

---

##  API Overview (Example)
> 실제 엔드포인트는 프로젝트 구현에 맞게 구성되어 있으며, 아래는 흐름 이해를 위한 예시입니다.

### Keyword Recommendation
- `POST /api/keywords/recommend`
- Request: `{ "query": "deep learning" }`
- Response:
  - keyword_tree (2-depth 트리)
  - kw2pids (키워드 → 논문 ID 매핑)

### Paper List
- `GET /api/papers?keyword=...`
- 선택한 키워드 기반 논문 리스트 반환

### History
- `GET /api/history`
- 세션 기반 검색 흐름 복원

---
