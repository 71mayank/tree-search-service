# tree-search-service


PREREQUISITE TO RUN THE tree-search-service

    1. Git version control software should be installed in the System
    2. Maven should be installed and setup correctly in environment variable.

STEPS TO RUN ON LOCAL

    1. Clone tree-search-service in your system using git clone https://github.com/71mayank/tree-search-service.git
    2. Navigate to tree-search-service folder open git bash and run command mvn spring-boot:run
    3. Browse url http://localhost:3030/swagger-ui.html
    
REQUIREMENTS FOR REST API

    1.Create a search application where you expose an endpoint for a client 
    to search based on a certain radius for tree related data.
    
    
INPUT

    You have to expose and API endpoint accepting two parameters
    1. A Cartesian Point specifying a center point along the x & y plane
    2. A search radius in meters
    
OUTPUT
   
    You have to retrieve the count of "common name" 
    (please see in the documentation on the same page above which field to refer to) for all 
    the species of trees in that search radius
    - Expected outcome from the api
   ```json
       {
           "red maple": 30,
           "American linden": 1,
           "London planetree": 3
       }
   ```
        
SOLUTION STEPS

    If the circle is placed in the Cartesian plane with the defined Cartesian coordinate system (O,x,y) 
    so that the centre S is located at the origin O, and the radius is r, 
    then an analytic equation of the circle can be derived. 
    x 2 + y 2 = r 2 .(Pythagorean theorem)    
    
    Given 
    Cartesian point P(Xp,Yp) 
    and Radius R
    
    Circle Equation 
    x 2 + y 2 = r 2
    √x 2 + y 2 = r
    
    Step 1 
    In the given data set, for every tree coordinate x_sp and y_sp (There are 1000 records in the json file)  
    Calculate distance between Cartesian point and tree coordinates(x_sp,y_sp) - relative radius.
    √(Xp-Xt)^2+(Yp-Yt)^2 = rr (relative radius)
    
    E.g.   
    Cartesian point P(Xp,Yp) = P(1100000,210000)
    Radius in meters= 23000
    Coordinate of first tree is T(Xt,Yt) = T(1032564.664,217592.9651)
    
    Relative Radius
    √(1100000-1032564.664)^2+(210000-217592.9651)^2 => √4547524541.432896+57653119.00981801 
    => √4605177660.442714 = 67861.45931559912 Relative Radius in Feet = 20684.172799394611502 Meters
    
    Step 2 
    Compare if relative radius in meters in step 1 is less than or equal to Given Radius
    20684.172799394611502 <= 23000
    then count and collect such trees with Name attribute (spc_common)
    
    Step 3  Repeat 1 and 2 for the rest of the trees as per given data set json.
    
REQUEST PAYLOAD
    
    curl -X POST "http://localhost:3030/api/findTreeSpecies" 
    -H "accept: application/json" 
    -H "Content-Type: application/json" 
    -d "{ \"cartesianX\": 1100000, \"cartesianY\": 210000, \"searchRadiusInMeters\": 23000}"


REQUEST URL

    http://localhost:3030/api/findTreeSpecies
    
    
RESPONSE BODY
    
    {
      "speciesSplit": {
        "Sophora": 9,
        "NoNameSpecies": 3,
        "Japanese zelkova": 1,
        "eastern redcedar": 1,
        "Chinese fringetree": 1,
        "sweetgum": 2,
        "silver maple": 7,
        "Norway maple": 47,
        "pin oak": 9,
        "silver linden": 2,
        "red maple": 2,
        "willow oak": 1,
        "American elm": 1,
        "crab apple": 1,
        "honeylocust": 16,
        "Callery pear": 2,
        "ginkgo": 5,
        "tulip-poplar": 2,
        "swamp white oak": 2,
        "London planetree": 14,
        "Amur maple": 4,
        "black cherry": 1,
        "American linden": 1
      },
      "totalSpeciesCount": 134,
      "distinctSpeciesCount": 23,
      "searchOutcome": "Successfully retrieved tree species"
    }
    
RESPONSE HEADERS
    
    content-type: application/json;charset=UTF-8 
    date: Thu, 07 Mar 2019 21:39:25 GMT 
    transfer-encoding: chunked
    
    
APPLICATION DEPENDENCIES

    1. JDK 8
    2. Spring Boot 2.0
    3. Spring Restful Service
    4. Spring fox Swagger 2.0
    5. Lombok
