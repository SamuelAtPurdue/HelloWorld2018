package com.hello;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by Samuel Hild on 9/15/2018.
 */

public class SqlConnect {

    private final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\agent\\helloworld.db";

    private Connection sqliteConnection;

    public SqlConnect() {
        connect();
    }

    public Task[] retrieveTasks() {
        try {
            sqliteConnection = DriverManager.getConnection(DATABASE_URL);
            Statement requestStatement = sqliteConnection.createStatement();
            ResultSet rawSqlRequest = requestStatement.executeQuery(formatRetrieveCommend());
            //warning gross code below

            //seriously turn back

            Task[] tasks = new Task[0];

            int counter = 0;

            while (rawSqlRequest.next()){
                int superid;
                if ((superid = rawSqlRequest.getInt("superid")) == -1){
                    //This reads a bunch of stuff from the data base
                    String name = rawSqlRequest.getString("name");
                    String user = rawSqlRequest.getString("user");
                    int id = rawSqlRequest.getInt("id");
                    long start = rawSqlRequest.getLong("start");
                    long end = rawSqlRequest.getLong("end");


                    Task temp = new Task(name, user, end, superid);
                    temp.setTaskID(id);
                    temp.setStart(start);
                    if(tasks.length != 0) {
                        Task[] buffer = tasks;
                        tasks = new Task[buffer.length + 1];
                        for (int i = 0; i < buffer.length; i++) {
                            tasks[i] = buffer[i];
                        }
                        tasks[counter] = temp;

                        counter++;
                    }else{
                        tasks = new Task[1];
                        tasks[0] = temp;
                    }
                }else{
                    for (int i = 0; i < tasks.length; i++){
                        if (tasks[i].getTaskID() == superid){
                            String name = rawSqlRequest.getString("name");
                            String user = rawSqlRequest.getString("user");
                            int id = rawSqlRequest.getInt("id");
                            long start = rawSqlRequest.getLong("start");
                            long end = rawSqlRequest.getLong("end");

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
            fuck(sqle);
            System.exit(0);
        }
        return null;
    }

    private void connect() {
        try {
            sqliteConnection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException sqle) {
            fuck(sqle);
        }
    }

    public void insertTask(Task task) {
        try {
            Statement commandStatement = sqliteConnection.createStatement();
            commandStatement.execute(formatInsertCommand(task));

        } catch (SQLException sqle) {
            fuck(sqle);
        }
    }

    private String formatInsertCommand(Task task) {
        String command = "";

        command = String.format("INSERT INTO tasks VALUES('%s','%s',%d,%d,%d,%d);", task.getName(), task.getUSER(), task.getTaskID(), task.getStart(), task.getEnd(), task.getSuperId());

        return command;
    }

    private String formatRetrieveCommend() {
        String command = "";

        command = String.format("SELECT * FROM tasks");

        return command;
    }

    /*
        public void connectToAndQueryDatabase(String username, String password) {
            try {
                Connection con = DriverManager.getConnection(
                        "jdbc:myDriver:myDatabase",
                        username,
                        password);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM Table1");

                while (rs.next()) {
                    int x = rs.getInt("a");
                    String s = rs.getString("b");
                    float f = rs.getFloat("c");
                }
            } catch (SQLException e) {

            }
        }*/
    private void fuck(Exception e) {
        System.err.printf("FUCK!%n");
        e.printStackTrace();
    }

    public void kill() {
        try {
            sqliteConnection.close();
        } catch (SQLException sqle) {
            fuck(sqle);
            System.err.println("Failed to close connection");
        }
    }
}
