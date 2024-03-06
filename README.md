# Uso de la API
## Petición POST
#### Para realizar una petición POST, utiliza la siguiente URL:
```
   http://localhost:8080/api/searchevent
```

A continuación, se muestra un ejemplo de un JSON de entrada para la petición POST:

```
{
  "hotelId": "8234aBc",
  "checkIn": "05/12/2023",
  "checkOut": "31/12/2023",
  "ages": [
    30,
    29,
    1,
    3
  ]
} 
```
Cuando crear Retorna el valor de searchId, ejemplo:

```
{
    "searchId": "5a579cee-b8a2-4b54-bdd2-58988753d392"
}
```

## Consulta con método GET
Para realizar una consulta con el método GET, utiliza la URL con parámetros. 
```
http://localhost:8080/count?searchId={VALOR_SEARCHID}
```
Aquí tienes un ejemplo:
```
http://localhost:8080/count?searchId=5a579cee-b8a2-4b54-bdd2-58988753d392
```
esto retorna todo el objeto de lo que estas consultando:

```{
    "searchId": "5a579cee-b8a2-4b54-bdd2-58988753d392",
    "search": {
        "hotelId": "8234aBc",
        "checkIn": "05/12/2023",
        "checkOut": "31/12/2023",
        "ages": [
            30,
            29,
            1,
            3
        ]
    },
    "count": 0
}
```


