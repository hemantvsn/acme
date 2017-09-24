# acme
For TURVO coding assignment

#Assumptions:
1. The format chosen for the persistence of GEODATA is geoJSON
2. Since mongo offers great support to geoJson and associated queries, have chosen mongo (Though haven't used any geojson queries)
3. The spring boot framework is used for its ease of building standalone applications
4. A mongoDump of the database used for development has been attached in project root

#Work Flow
1. The basic entities involved are
 a. Truck (Each truck has a reference to its journey)
 b. Journey (LineString in geoJson)
 c. MOvement - PointInTime data received by webservice storing GPS data
 
2. We have mocked the webservice as follows
a. To generate pointInTime data, get a random point from the truck journey and add a random offset to it
So that truck is not always on track

3. Once we get pointInTime data, its saved in DB (movement history - track historical movements of truck when it was journied for specific purposes)

4. Also its sent to DataHandler for determining breach.
5. If DataHandler finds the breach, then its triggers AlertGenerator to generate an alert (mock implementation, which outputs to console)


#Logic to determine breach
1. Journey is a lineString in geoJSON, which is a collection of different points
Eg : [A[lat1, lon1], B[lat2, lon2], C[lat3, lon3]]
Thus this can also be viewed as 2 segments- AB and BC


2. For the given pointInTime data of Truck and each Line segment, 
a.  Set minimum adrift = INFINITY
b.  Iterate through the segments of the journey
c.  If the truck is on the given segment, then return with 0 (Point P is on Line AB, when slope of AP = Slope of AB = Slope of PB). This will exit from iteration/function
d. Else for each iteration, calculate distance of Point P from Line AB (The minimum distance is perpendicular drawn from P to AB)
e. For each iteration, update minimum if calculated value is less than the previous min value

3. Thus at end of all iterations, the minDrift = the shortest distance of truck from the route
4. Compare this minDrift with the threshold value (<code> refer application.properties </code>)
5. If value if greater than threshold, alert needs to be generated


#Running the project
<ul> This is a simple springBoot project. So this will be executed as normal jar.

<li> 1. Clone this project
<li> 2. This is a maven project. Go to the project root (where pom.xml is located)
<li> 3. We have used MongoDB as the primary DB, mainly because of its support for GEOJSON
<li> 4. In the project root, I have created mongodump of the my existing DB. Make sure that mongo dump is restored via mongorestore
<li> 5. The mongo properties in <code>src/main/java/application.properties</code> are specific to my local. So you may want to change it
<li> 6. Execute <code>mvn clean install</code> This will download the essentials and generate the build(JAR)
in the <code> target/ </code> directory 
<li> 7. The jar can be executed directly via <code>java -jar target/acme-1.0-SNAPSHOT.jar</code>


#Future Enhancements
1. Microservice architecture will suit it more
2. We can use Message broker like RabbitMQ, ActiveMQ for sending messages / interservice communication so that they are loosely bound
