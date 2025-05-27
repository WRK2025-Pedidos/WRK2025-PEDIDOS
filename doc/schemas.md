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
    "value": "string"     
  },
  "totalPrice": {
    "value": "number"      
  },
  "countryTax": {
    "value": "number"      
  },
  "paymentMethod": {
    "value": "number"    
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
        "value": "number"
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

## · UserId

Represents the UUID identifier of a user.

```json
{
  "uuid": "string"
}
```

---

## · NotificationDto

Used to deliver a notification to the user.

```json
{
  "message": "string",
  "createdAt": "2025-05-22T13:45:00Z",
  "important": true
}
```

---