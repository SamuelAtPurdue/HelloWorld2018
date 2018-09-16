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
        connect("tyrone","password1");
    }

    public Task retrieveTask(String taskName) {
        return null;
    }

    private void connect(String username, String password) {
        try {
            sqliteConnection = DriverManager.getConnection(DATABASE_URL, username, password);
        }catch (SQLException sqle){
            fuck(sqle);
        }
    }
    public void insertTask(Task task){
        try {
            Statement commandStatement = sqliteConnection.createStatement();
            commandStatement.execute(formatInsertCommand(task));

        }catch(SQLException sqle){
            fuck(sqle);
        }
    }
    private String formatInsertCommand(Task task){
        String command = "";

        command = String.format("INSERT INTO tasks VALUES('%s','%s',%d,%d,%d,%d);", task.getName(), task.getUSER(), task.getTaskID(), task.getStart(), task.getEnd(), task.getSuperId());

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
    private void fuck(Exception e){
        System.err.printf("FUCK!%n");
        e.printStackTrace();
    }
    public void kill(){
        try {
            sqliteConnection.close();
        }catch (SQLException sqle){
            fuck(sqle);
            System.err.println("Failed to close connection");
        }
    }
}
