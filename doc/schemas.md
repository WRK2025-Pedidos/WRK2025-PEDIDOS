# · WRK2025-ORDERS – API Schemas

This file documents the data models used in request and response bodies across the Order Microservice.  
Each schema defines the structure and types of JSON objects handled by the /orders endpoints.

---

## · OrderId

Represents the UUID of a specific order.

```json
{
  "uuid": "string"
}

```
---

## · Order

Represents a complete order.

```json
{
  "id": {
    "uuid": "string"       
  },
  "cartId": {
    "uuid": "string"
  },
  "creationDate": {
    "value": "string (date-time)"     
  },
  "totalPrice": {
    "value": "number"      
  },
  "countryTax": {
    "value": "number (double)"      
  },
  "paymentMethod": {
    "value": "number (double)"    
  },
  "orderLines": [
    {
      "product": {
        "uuid": "string"
      },
      "quantity": {
        "value": "integer"
      },
      "lineWeight": {
        "value": "number (double)"
      },
      "productPrice": {
        "value": "number"
      },
      "linePrice": {
        "value": "number"
      }
    }
  ],
  "orderReturn": {
    "value": "boolean"
  },
  "parentOrderId": {
    "value": "string"
  },
  "offers": [
    {
      "uuid": "string"     
    }
  ]
}

```
---

## · OrderLine

Describes a single line in an order (i.e., a product and its quantity).

```json
{
  "product": {
    "uuid": "string"
  },
  "quantity": {
    "value": "integer"
  },
  "lineWeight": {
    "value": "number (double)"
  },
  "productPrice": {
    "value": "number"
  },
  "linePrice": {
    "value": "number"
  }
}


```
---
