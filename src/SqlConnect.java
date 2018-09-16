package com.hello;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by Samuel Hild on 9/15/2018.
 * Connects to the Sqlite database and processes data
 */

public class SqlConnect {

    private final String PATH_URL = "jdbc:sqlite:C:\\Users\\agent\\helloworld.db";  //path to the Database

    private Connection sqliteConnection;                                            //sqlite connection

    /**
     * Constructor automatically calls the connect() function to connect to the database
      */
    public SqlConnect() {
        connect();
    }

    /**
     * retrieve tasks from the Sql database
     * To be run once at the beginning of running the program
     * will put all tasks in their proper locations based on sub-tasks and super-tasks
     * @return Array of tasks ordered in the standard format
     */
    public Task[] retrieveTasks() {
        try {
            Statement requestStatement = sqliteConnection.createStatement();                    //Request Statement
            ResultSet rawSqlRequest = requestStatement.executeQuery(formatRetrieveCommend());   //generate result set from sql query
            //warning gross code below

            //seriously turn back

            Task[] tasks = new Task[0];                                                         //create Task array

            int counter = 0;                                                                    //counter
            while (rawSqlRequest.next()){                                                       //while there is sql data
                int superid;                                                                    //declare superid
                //Create variables for Tasks
                String name = rawSqlRequest.getString("name");
                String user = rawSqlRequest.getString("user");
                int id = rawSqlRequest.getInt("id");
                long start = rawSqlRequest.getLong("start");
                long end = rawSqlRequest.getLong("end");

                if ((superid = rawSqlRequest.getInt("superid")) == -1){               //Check if superid == -1 which indicatss it is the highest super task

                    Task temp = new Task(name, user, end, superid);                             //Create temporary Task
                    temp.setTaskID(id);                                                         //manually set taskid to the id stored in the database
                    temp.setStart(start);                                                       //manually set start data to date stored in the database

                    if(tasks.length != 0) {                                                     //check if tasks has any objects to avoid an array overflow
                        Task[] buffer = tasks;                                                  //create buffer for array creation
                        tasks = new Task[buffer.length + 1];                                    //create new tasks one longer to accomadate next Task
                        for (int i = 0; i < buffer.length; i++) {
                            tasks[i] = buffer[i];
                        }
                        //everything below here I gave up on documenting
                        tasks[counter] = temp;

                        counter++;
                    }else{
                        tasks = new Task[1];
                        tasks[0] = temp;
                    }
                }else{
                    for (int i = 0; i < tasks.length; i++){
                        if (tasks[i].getTaskID() == superid){

                            Task temp = new Task(name, user, end, superid);
                            temp.setTaskID(id);
                            temp.setStart(start);

                            tasks[i].add(temp);

                            break;
                        }
                    }
                }
            }
            return tasks;
        } catch (SQLException sqle) {
            fuck(sqle);                 //fuck
        }
        return null;                    //returns null, cannot be reached but compiler really wants it
    }


    /**
     * connects to sqlite database
     */
    private void connect() {
        try {
            sqliteConnection = DriverManager.getConnection(PATH_URL);           //Creates a SQL Connection
        } catch (SQLException sqle) {                                               //catches SQLException
            fuck(sqle);                                                             //double fuck
        }
    }

    /**
     * inserts task into the database
     * @param task task to insert into the database
     */
    public void insertTask(Task task) {
        try {
            Statement commandStatement = sqliteConnection.createStatement();        //Create new Statement
            commandStatement.execute(formatInsertCommand(task));                    //Executes command formatted with formatInsertCommand()
        } catch (SQLException sqle) {                                               //catches SQLException
            fuck(sqle);                                                             //rest in peace tyrone
        }
    }

    /**
     * Formats command to insert Task into the Database
     * @param task  task to insert into the database
     * @return returns formatted command
     */
    private String formatInsertCommand(Task task) {
        String command;
        //command formatted to insert into table tasks the values associated with a task
        //progress is excluded because it is calculated from the other values
        command = String.format("INSERT INTO tasks VALUES('%s','%s',%d,%d,%d,%d);", task.getName(), task.getUSER(), task.getTaskID(), task.getStart(), task.getEnd(), task.getSuperId());

        return command;
    }

    /**
     * formats command to retrieve data from the sqlite database
     * @return formatted command
     */
    private String formatRetrieveCommend() {
        String command = "";

        command = String.format("SELECT * FROM tasks");     //formats SELECT FROM tasks command

        return command;
    }

    /**
     * Very professional Error handling
     * says oh fuck!
     * @param e Exception caught
     */
    private void fuck(Exception e) {
        System.err.printf("OH FUCK!%n");        //say important error handling information
        e.printStackTrace();                    //say less important but still useful information
        System.exit(-1);                  //exactly what you think
    }


    /**
     * kills connection to sqlite database with error handling
     */
    public void kill() {
        try {
            sqliteConnection.close();           //close connection
        } catch (SQLException sqle) {
            fuck(sqle);                         //error handling in a calm and professional manor
        }
    }
}
