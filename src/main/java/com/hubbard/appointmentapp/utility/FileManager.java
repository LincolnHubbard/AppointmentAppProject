package com.hubbard.appointmentapp.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * This class is used to output user log-in attempts to the login_activity.txt file
 * @author Lincoln Hubbard
 */
public class FileManager {

    /**
     * Outputs user login activity to login_activity.txt on a new line
     * @param user The username input on log-in
     * @param pass The password input on log-in
     * @param logSuccess If the log-in was successful or not
     * @throws IOException If the file to be written to cannot be found
     */
    public static void printLogAttempt(String user, String pass, boolean logSuccess) throws IOException{
        String filename = "login_activity.txt";
        FileWriter fw = new FileWriter( filename, true );
        PrintWriter output = new PrintWriter( fw );
        Timestamp logTime = Timestamp.from( Instant.now() );
        if (logSuccess){
            output.println(user + " " + pass + " " + logTime + " Log-in successful");
        }else {
            output.println(user + " " + pass + " " + logTime + " Log-in failed");
        }
        output.close();

    }
}
