# VirtusLab internship 2022 recruitment task
### Author: Joanna Klimek

## Challenges
* fixed the source code to make the tests pass
* implemented 15% discount
* made both discounts implement Discount interface and extend prepared abstract class for duplicated code
* created Discount Manager that applies discounts in correct order
* added rest-like endpoint to make application accept basket information and return the receipt data
* prepared tests for new discount, manager and endpoints

## Technologies
* Java
* SpringBoot

## Example request
POST /basket/receipt
```
{
    "products": [
        {
            "name": "Apple",
            "type": "FRUITS",
            "price": 2
        },
        {
            "name": "Milk",
            "type": "DAIRY",
            "price": 2.7
        },
        {
            "name": "Milk",
            "type": "DAIRY",
            "price": 2.7
        },
        {
            "name": "Steak",
            "type": "MEAT",
            "price": 50
        }
    ]
}
```

Response:
```
{
    "entries": [
        {
            "product": {
                "name": "Apple",
                "type": "FRUITS",
                "price": 2
            },
            "quantity": 1,
            "totalPrice": 2
        },
        {
            "product": {
                "name": "Steak",
                "type": "MEAT",
                "price": 50
            },
            "quantity": 1,
            "totalPrice": 50
        },
        {
            "product": {
                "name": "Milk",
                "type": "DAIRY",
                "price": 2.7
            },
            "quantity": 2,
            "totalPrice": 5.4
        }
    ],
    "discounts": [
        "TenPercentDiscount"
    ],
    "totalPrice": 51.66
}
```
