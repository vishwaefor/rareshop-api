# RareShop API

RareShop API is the used by hypothetical rareshop.com

### There are three main microservices

-   ##### Listing API
    Used by Admins to publish Product Info (Articles) and Products associated
-   ##### Searching API
    Used by Publish to view Product Info (Articles) and search Products 
-   ##### Pricing API
    Used to calculate final price of the bucket selected by users

### Pricing Engine
    
-    Used by Pricing API 

### Messaging Service
-    Should be implemented with RabbitMQ
-    Listing API is publishing events when a Product Info or Product got Published
-    Published Products and Product Info are available through Searching API
    
        
    

