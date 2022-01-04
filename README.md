# FetchRewards

Link to assessment https://fetch-hiring.s3.us-east-1.amazonaws.com/points.pdf

Steps to run
1. Install Java 11+
2. Install maven (https://maven.apache.org/download.cgi)
2. git clone https://github.com/RahulNagaraj/FetchRewards.git
3. cd FetchRewards
4. mvn spring-boot:run

REST API:
1. POST /api/v1/payer/transactions
  ```
  curl -d  '{ "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" }' -H 'Content-Type: application/json' localhost:8080/api/v1/payer/transactions
  ```
2. POST /api/v1/payer/balances
  ```
  curl -v localhost:8080/api/v1/payer/balances
  ```
3. GET /api/v1/consumer/spend
```
curl -d  '{ "points": 5000 }' -H 'Content-Type: application/json' -X POST localhost:8080/api/v1/consumer/spend
```
