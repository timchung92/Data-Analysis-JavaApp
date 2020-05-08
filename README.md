# cit-594-group-final-project
### **Group Final Project using OpenDataPhilly**

In this project, we apply what we’ve learned this semester about data structures, design principles, and design patterns in developing a Java application to read text files as input and perform some analysis.

### **Background**

The [OpenDataPhilly](https://www.opendataphilly.org/) portal makes over 300 data sets, applications, and APIs related to the city of Philadelphia available for free to government officials, researchers, academics, and the general public so that they can analyze and get an understanding of what is happening in this vibrant city (which, as you presumably know, is home to the University of Pennsylvania!). The portal’s data sets cover topics such as the environment, real estate, health and human services, transportation, and public safety.

In this assignment, we design and develop an application that uses a data set from OpenDataPhilly regarding street **parking violations**, e.g. parking in a no-stopping zone, not paying a meter, parking for too long, etc.

Each incident of a parking violation includes various pieces of data such as the date and time at which it occurred, the reason for the violation, and identifying information about the vehicle.

A parking violation also has an associated **fine**, i.e. the money that was charged as penalty to the vehicle, as well as information about the location at which it occurred. For our purposes, we are concerned with the violation’s **ZIP Code**, which is a numerical code used by the US Postal System to indicate a certain zone, e.g. a city or a neighborhood, as in the case of Philadelphia.

Our program also uses a data set of **property values** of houses and other residences in Philadelphia. This data set includes details about each home including its ZIP Code and current **market value**, or estimated dollar value of the home, which is used by the city to calculate property taxes. It also includes the **total livable area **for the home, which measures the size of the home in square feet.


### **Project Report**

<span style="text-decoration:underline;">Additional Feature:</span>

Our additional feature calculates the total market value per capita and then divides it by the average fine amount in that zip code. This figure is an attempt to find some relationship between fines and market values per capita. We do this for each zip code accounted for in the population file and display the answers in a table. For the total market value per capita, we use the total market value method previously defined in the assignment.  For average fine, we obtain the sum of all fines in a zip code and divide by the number of fines. Since these are the only two figures that comprise our calculation, we know dividing them will produce the correct answer.

<span style="text-decoration:underline;">Data Structures:</span>



1. **TreeMap**&lt;Zip Code, Population Count>** **
    1. <span style="text-decoration:underline;">Where it is used</span> - Data structure for Population data. It is populated by PopulationReader and used by PopulationProcessor. It is leveraged for features requiring per capita analysis.
    2. <span style="text-decoration:underline;">Rationale</span> - We chose TreeMap because it offers efficient O(log n) search and the ability to store in a sorted order.  In total market value per capita and our custom feature, the population of the zip code provided by the user is required in the calculation.  This necessitates an efficient way to retrieve population by zip code (key). Additionally, for total fines per capita, we need to print in ascending order and the TreeMap offers a way to retrieve all elements by ascending keys (zip code) without additional sorting required.
    3. <span style="text-decoration:underline;">Alternatives</span> - We considered HashMap and ArrayList. Even though HashMap offers quicker O(1) retrieval of elements based on key (zip code), there is not an efficient way to sort the elements within a HashMap. We would have to copy the elements into an array or List and then perform a sort. Although arrays provide efficient sorts O(n log n), retrieval of an element based on an attribute is slow O(n).
2. **HashMap**&lt;Zip Code, LinkedList&lt;ParkingViolation>>
    1. <span style="text-decoration:underline;">Where it is used -</span>  Data structure for ParkingViolation data. It is populated by ParkingViolationReader and used by ParkingViolationProcessor. It is leveraged for features one and six, which require ParkingViolation data based off of zip code.
    2. <span style="text-decoration:underline;">Rationale</span> - We require retrieval of all ParkingViolation instances with a given zip code. The need for quick search based on a key led us to choose a HashMap, which offers O(1) get operation assuming reasonable load factor. Our use of HashMap allows for collisions since each key (zip code) can be tied to one or many parking violations, however, since we are not retrieving individual objects but rather a list of objects, the collisions do not affect our search/retrieval time complexity.
    3. <span style="text-decoration:underline;">Alternatives -</span> We considered a Tree implementation like TreeMap. The tree offers efficient search but not as efficient as a HashMap and lacks other advantages over a HashMap for our purposes.
3. HashMap&lt;Zip Code, **LinkedList&lt;PropertyValue>**>
    1. <span style="text-decoration:underline;">Where it is used -</span>  We will focus on the LinkedList sub data structure since the decision for HashMap is similar to the previous example. The LinkedList is used to store PropertyValue data. It is populated by PropertyValueReader and used by PropertyValueProcessor for features three to six, which require PropertyValue market value and livable area based on zip code.
    2. <span style="text-decoration:underline;">Rationale </span>- We chose a LinkedList as our data structure because it offers efficient dynamic resizing. The total number of residences (i.e. instances of PropertyValue) per zip code is highly variable so it needs fast resizing.
    3. <span style="text-decoration:underline;">Alternatives </span>- We considered an array or ArrayList. Arrays were not suitable since they are not dynamically resizable. ArrayList was a good alternative, but it offers amortized O(1) for adding elements, which is not as good as the guaranteed O(1) for LinkedList. We also considered using a HashSet, and while we found that it more accurately described our data (a set of unique entries) we opted for the slight speed advantage that LinkedList offers when adding new elements. Since we were already assuming our data contained no duplicates, per: the project specs, since we were never using a `.contains` function to seek out a specific object, and since our main bottle neck was the actual reading in of the data and adding it to the data structure we didn't want to incur the extra costs of dynamic resizing that our HashSet would inevitably have due load balancing an unknown number of entries from our data file(s).