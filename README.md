<h1>Conference Room Booking API</h1>

<h2>Set up environment</h2>

<h3>Steps</h3>
<ol>
<li>Get the <a href="https://github.com/cjclementson/conference-room-booking">source</a>: git clone https://github.com/cjclementson/conference-room-booking.git</li>
<li>Install <a href="https://www.oracle.com/java/technologies/downloads/">JDK 17</a></li>
<li>Insatll <a href="https://maven.apache.org/download.cgi">Maven</a></li>
<li>Make sure maven is on the your path.</li>
</ol>

<h2>Build and run</h2>

<h3>Pre requisites</h3>
<ul>
<li>Source code downloaded.</li>
<li>Maven is installed and on your path.</li>
</ul>

<h3>Steps</h3>
<ol>
<li>From within the root directory of your repository, navigate to the scripts directory.</li>
<li>Windows: run build-run.bat</li>
<li>Linux or Mac: ./build-run.sh</li>
</ol>

<p>By default, the application will run on http://localhost:8000 as configured in the application.yml.</p>

<h2>Using the API</h2>

<h3>Pre requisites</h3>
<p>Curl or Postman is installed.</p>

<h3>Steps</h3>

<p>See the curl commands below for examples on calling the availability end point to see which rooms are available for the specified time range.</p>
<ul>
<li>curl "http://localhost:8000/api/v1/rooms/availability?startTime=08:00&endTime=08:30"</li>
<li>curl "http://localhost:8000/api/v1/rooms/availability?startTime=09:00&endTime=09:30"</li>
<li>curl "http://localhost:8000/api/v1/rooms/availability?startTime=14:00&endTime=15:00"</li>
</ul>

<p>See the curl commands below for examples on calling the booking end point to book a room for the specified time range and number of attendees.</p>
<ul>
<li>curl -d '{"startTime": "07:00", "endTime": "08:00", "attendees": 6}' -H 'Content-Type: application/json' -X POST "http://localhost:8000/api/v1/rooms/booking"</li>
<li>curl -d '{"startTime": "09:00", "endTime": "09:30", "attendees": 6}' -H 'Content-Type: application/json' -X POST "http://localhost:8000/api/v1/rooms/booking"</li>
<li>curl -d '{"startTime": "18:00", "endTime": "19:00", "attendees": 6}' -H 'Content-Type: application/json' -X POST "http://localhost:8000/api/v1/rooms/booking"</li>
</ul>

<h2>Configurable properties</h2>

<h3>Steps</h3>
<p>test</p>
