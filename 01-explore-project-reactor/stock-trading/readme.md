#

```
curl --location 'http://localhost:8080/stocks' \
--header 'Content-Type: application/json' \
--data '{
    "stockName": "Globomantics",
    "price": 1000,
    "currency": "USD"
}'
```

response

```
{
    "id": "66196e07bf8bcf76911596d4",
    "price": 1000,
    "currency": "USD",
    "stockName": "Globomantics"
}
```

HTTP GET - http://localhost:8080/stocks

HTTP GET - http://localhost:8080/stocks/66196e07bf8bcf76911596d4

HTTP GET - http://localhost:8080/stocks?priceGreaterThan=900

This code has also done exception handlings!

```
curl --location 'http://localhost:8080/stocks' \
--header 'Content-Type: application/json' \
--data '{
    "stockName": "Globomantics",
    "currency": "USD"
}'
```

Response

```
{
    "type": "about:blank",
    "title": "Unable to Create Stock",
    "status": 400,
    "detail": "price is marked non-null but is null",
    "instance": "/stocks"
}
```

http://localhost:8080/stocks/111

```
{
    "type": "about:blank",
    "title": "Stock Not Found",
    "status": 404,
    "detail": "Stock not found with id: 111",
    "instance": "/stocks/111"
}
```