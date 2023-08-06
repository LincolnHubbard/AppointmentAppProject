# Appointment Application
<p>Demo of a simple appointment scheduling application completed for a school project. Users can add/update and delete both appointments and customers.
This application uses data access objects to move information for each entity to and from an SQL database. </p>
<p><b>Features:</b></p>
<ul>
  <li>Appointment times are converted between UTC time and the user's time zone</li>
  <li>Regardless of their time zone, users may only schedule appointment between 8AM - 10PM EST</li>
  <li>The log-in screen can be displayed in either English for French depending on user location</li>
  <li>Users are greeted with an alert message if an appointment starts within 15 minutes of them logging in</li>
  <li>When adding or updating an appointment, users are prevented from scheduling overlapping appointments</li>
  <li>User input is validated to prevent entering blank fields, invalid times, etc.</li>
</ul>
<p><b>Some Future Enchancements:</b></p>
<ul>
  <li>Connections to the database should only be open while users are actively reading data from or saving data to the database</li>
  <li>Currently, no service layer is implemented as it was outside the scope of this class/li>
  <li>A DAO interface should be implemented among all DAO classes</li>
</ul>
<p><b>Technologies Used:</b></p>
<ul>
  <li>Java 17.0.8</li>
  <li>MySQL Workbench 8.0 CE</li>
  <li>IntelliJ IDEA Community Edition</li>
  <li>JavaFX</li>
  <li>JavaDoc</li>
</ul>
