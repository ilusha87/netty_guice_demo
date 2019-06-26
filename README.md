# netty_guice_demo

This simple project aimed to test standard Netty HTTP server functinality along with Guice as a DI framework.<br>
Netty HTTP server receives income POST request with Customer entity and saves it to the database. 
Then prints out all records from Customer database table.<br><br>
Gradle was chosen as as automation tool.<br>
Derby is used as an database - no pre-install needed! Just run an application :)

# Running an application:
com.illuha.netty1.BootstrapApp#main

then make a POST request to localhost:8080 using any HTTP client tool (Postman, etc.). Write in the POST body:
<pre>
{"id":"1",
 "name": "Customer1"
}
</pre>
Of course, use header:
<pre>
Content-Type: application/json
</pre>

and look to the app console output :)

P.S. if your 8080 port is busy with another application, pass another port in app command line parameters:

<pre>
application [host] [port]
<pre>
