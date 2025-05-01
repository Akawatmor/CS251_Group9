# API Documentation

This document provides details on how to use the APIs for the backend.

---

## Customer APIs

### Register a Customer
**POST** `/api/customers/register`

**Request Body:**
```json
{
  "uName": "username",
  "dName": "displayName",
  "name": "John",
  "surname": "Doe",
  "uEmail": "john.doe@example.com",
  "uNumber": "1234567890",
  "age": 25,
  "country": "840",
  "password": "password123"
}
```

**Response:**
```json
{
  "userID": 1000000001,
  "uName": "username",
  "dName": "displayName",
  "name": "John",
  "surname": "Doe",
  "uEmail": "john.doe@example.com",
  "uNumber": "1234567890",
  "age": 25,
  "country": "840",
  "money": 0
}
```

---

### Login a Customer
**POST** `/api/customers/login`

**Request Parameters:**
- `uName`: Username
- `password`: Password

**Response:**
```json
{
  "userID": 1000000001,
  "uName": "username",
  "dName": "displayName",
  "name": "John",
  "surname": "Doe",
  "uEmail": "john.doe@example.com",
  "uNumber": "1234567890",
  "age": 25,
  "country": "840",
  "money": 0
}
```

---

## Game APIs

### Add a Game
**POST** `/api/games`

**Request Body:**
```json
{
  "gName": "Game Name",
  "gDesc": "Game Description",
  "gPrice": 50
}
```

**Response:**
```json
{
  "gameID": 2000000001,
  "gName": "Game Name",
  "gDesc": "Game Description",
  "gPrice": 50,
  "gPublishDate": "2023-10-01T00:00:00",
  "rating": 0.0
}
```

---

### Search Games by Name
**GET** `/api/games/search`

**Request Parameters:**
- `name`: Partial or full game name

**Response:**
```json
[
  {
    "gameID": 2000000001,
    "gName": "Game Name",
    "gDesc": "Game Description",
    "gPrice": 50,
    "gPublishDate": "2023-10-01T00:00:00",
    "rating": 0.0
  }
]
```

---

### Upload Game Picture
**POST** `/api/games/{id}/picture/{position}`

**Path Parameters:**
- `id`: Game ID
- `position`: Picture position (1-5)

**Request:**
- Multipart file upload

**Response:**
```json
"Picture uploaded successfully"
```

---

## Wishlist APIs

### Add to Wishlist
**POST** `/api/wishlist/add`

**Request Parameters:**
- `userID`: User ID
- `gameID`: Game ID

**Response:**
```json
{
  "id": {
    "userID": 1000000001,
    "gameID": 2000000001
  },
  "date": "2023-10-01T00:00:00"
}
```

---

### Remove from Wishlist
**DELETE** `/api/wishlist/remove`

**Request Parameters:**
- `userID`: User ID
- `gameID`: Game ID

**Response:**
```json
"Wishlist entry removed successfully"
```

---

## Mod APIs

### Add a Mod
**POST** `/api/mods`

**Request Body:**
```json
{
  "modName": "Mod Name",
  "modInfo": "Mod Description",
  "modType": "Type",
  "game": {
    "gameID": 2000000001
  }
}
```

**Response:**
```json
{
  "modID": 7000000001,
  "modName": "Mod Name",
  "modInfo": "Mod Description",
  "modType": "Type",
  "game": {
    "gameID": 2000000001
  }
}
```

---

## Country APIs

### Get All Countries
**GET** `/api/countries`

**Response:**
```json
[
  {
    "countryCode": "840",
    "countryName": "United States",
    "region": "North America"
  }
]
```

---

## Review APIs

### Add or Update Review
**POST** `/api/reviews`

**Request Parameters:**
- `userID`: User ID
- `gameID`: Game ID
- `comment`: Review comment
- `score`: Review score (1-5)

**Response:**
```json
{
  "id": {
    "userID": 1000000001,
    "gameID": 2000000001
  },
  "comment": "Great game!",
  "score": 5,
  "reviewDate": "2023-10-01T00:00:00"
}
```

---

Additional APIs follow a similar structure. Refer to the respective controllers for more details.
