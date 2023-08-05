Title: AppointmentApp
Version: 1.0.0.2
Date: 2023-08-04
Purpose: Allows users to add, update, and delete customers and appointments
Author: Lincoln Hubbard
Contact Info: lhubb94@wgu.edu
Software versions:
    IntelliJ IDEA Community Edition 2023.1.4
    Oracle OpenJDK version 17.0.8
    JavaFX-SDK 17.0.2
    My-Sql-Connector 8.0.25
How To Use:
    - Log in with your provided username and password (from the database)
    - "Manage Appointments" leads to the Appointment View screen, where you may add, update or delete appointments
        -This screen is also where additional reports are accessed (Contact schedule, appointment stats, etc.)
    - "Manage Customers" leads to the Customer View screen, where you may add, update or delete customers
        - ** Customers CANNOT be deleted unless all associated appointments are deleted first! **
    - The USER ID value is gotten from the database based on the user who is currently signed in to the program.
        EX: If the user "test" is logged in, any appointments they add/update will be assigned the user ID "1".
        - This is also checked and an error is thrown if there is no user ID as it is a foreign key in the database
    - All available appointment times are only generated within the time frame of 8AM-10PM EST (business hours) which
        is then converted to the user's time zone. This means it is impossible for users to schedule outside of these
        hours in this application.

Additional Report:
    On the "Appointment View" screen, an additional report is run (in the AppointmentDAO.java class) which notifies the
    user of how many appointments remain that are scheduled today. This is done by getting the user's current date and
    time and comparing these values in a for loop against the start date and time of each appointment in the database.