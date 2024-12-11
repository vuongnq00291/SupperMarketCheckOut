# Supermarket checkout

 All the implementations are based on the assumption that casher machine checkout process is only for 1 customer at a time.
 there is no multiple threading running.

### Explanation of Key Components

- **`Cart.java`**: Manages cart operations such as adding and removing items.
- **`CheckoutService.java`**: Handles checkout operations, including order finalization and payment processing.
- **` PriceSampleDataService.java`**: sample price list.
- **`CartTest.java`**: Contains unit tests for the cart operations to ensure functionality.
- **`CheckoutServiceTest.java`**: Contains unit tests for checkout operations.

## What is missing
- global exception handling and logging
- adding CartService to store all carts in cache with Time to live, each cart have a UUID
- design interface for service layer
- using Dependency injection to manage all objects 

