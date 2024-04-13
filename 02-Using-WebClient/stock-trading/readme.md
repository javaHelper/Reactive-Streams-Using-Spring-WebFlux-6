#

```
curl --location 'http://localhost:8080/stocks' \
--header 'Content-Type: application/json' \
--data '{
    "stockName": "Globomantics2",
    "price": 100.00,
    "currency": "USD"
}'
```

Response:

```
{
    "id": "661a2396db35b8418c30c1cf",
    "price": 100.00,
    "currency": "USD",
    "stockName": "Globomantics2"
}
```

HTTP GET: http://localhost:8080/stocks/661a2396db35b8418c30c1cf

HTTP GET: http://localhost:8080/stocks/661a2396db35b8418c30c1cf?currency=EUR

HTTP GET: http://localhost:8080/stocks/661a2396db35b8418c30c1cf?currency=CAD

```
curl --location 'http://localhost:8080/stocks' \
--header 'Content-Type: application/json' \
--data '{
    "stockName": "Globomantics-1",
    "price": 150.00,
    "currency": "USD"
}'
```

Response-

```
{
    "type": "about:blank",
    "title": "Unable to Create Stock",
    "status": 400,
    "detail": "Stock name contains illegal character '-'",
    "instance": "/stocks"
}
```