package com.hello;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;

/**
 * Created by Samuel Hild on 9/15/2018.
 * Creates Tasks which the user completes
 * Tasks contain a username, progress to completion, start and end dates, and a unique taskID
 */
public class Task {

    static int nextID = 0;              //static in for keeping track of taskIDs

    Task[] subtask = new Task[0];   //Array of subtasks
    private long end;               //intended end date
    private String name;            //name of the task
    private int progress;           //number from 1-100 to measure percent complete


    private long start;       //start date calculated on initialization
    private final String USER;      //User who created the task
    private int taskID;             //Unique taskID calculated on initialization
    private final int SUPER_ID;


    public Task(String taskName, String username, long end, int superID){
        int taskID = nextID+=1;
        USER = username;
        this.start = currentDate();
        this.end = end;
        this.name = taskName;
        this.progress = 0;
        this.SUPER_ID = superID;

        this.dumpTask();
    }
    public static void createSubtask(Task superTask){
        Task newSub = new Task("Dinoproject" ,"tempuser", 0, superTask.getTaskID());
        superTask.add(newSub);
    }

    public void add(Task sub){
        Task[] hold = this.subtask;
        this.subtask = new Task[hold.length+1];
        for (int i = 0; i < hold.length; i++)
            this.subtask[i] = hold[i];

        this.subtask[subtask.length-1] = sub;
    }
    public void addAll(Task[] subs){
        for (Task sub : subs)
            add(sub);
    }

    public void setEnd (long endDate){
        this.end = endDate;
    }

    private long currentDate(){
        long dateCreated = LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset());
        return dateCreated;
    }

    public void dumpSubtasks(){
        for (Task sub : subtask) {
            dumpTask();
            if(subtask != null)sub.dumpSubtasks();
        }
    }
    public void dumpTask(){
        System.out.printf("Name: %s%nStart: %d%nEnd: %d%nUsername: %s%nTaskID: %d%nProgress: %s%n", this.name, this.start, this.end, this.USER, this.taskID, this.getProgress());
    }
    private int calcProgress(){

        long now = currentDate();

        long timeSinceStart = now-this.start;
        long totalTimeToCompletion  = this.end - this.start;

        progress = 100*((int) (timeSinceStart/totalTimeToCompletion));

        return progress;
    }

    public void sendToDatabase(Task sendTask){
        SqlConnect database = new SqlConnect();
        database.insertTask(sendTask);
        database.kill();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskID() {
        return taskID;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }

    public int getProgress() {
        return progress = calcProgress();
    }

    public String getUSER() {
        return USER;
    }

    public int getSuperId(){
        return this.SUPER_ID;
    }public void setTaskID(int id){
        taskID = id;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
