# · WRK2025-ORDERS – API Schemas

This file documents the data models used in request and response bodies across the API.  
Each schema defines the structure and types of JSON objects used by the endpoints.

---

## · Order

Represents a complete user entity with all related fields.

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
  ],
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
  ]
}

```
---