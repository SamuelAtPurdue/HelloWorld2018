package com.hello;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;

/**
 * Created by Samuel Hild on 9/15/2018.
 * Creates Tasks which the user completes
 * Tasks contain a username, progress to completion, start and end dates, and a unique taskID
 */
public class Task {

    static int nextID;              //static in for keeping track of taskIDs

    Task[] subtask = new Task[0];   //Array of subtasks
    private long end;               //intended end date
    private String name;            //name of the task
    private int progress;           //number from 1-100 to measure percent complete


    private final long START;       //start date calculated on initialization
    private final String USER;      //User who created the task
    private int TASKID;             //Unique taskID calculated on initialization


    /**
     * Static initializer for initializing nextID for keeping track of ids
     * Sets static ID to 0 only runs on first initialization
     */
    static {
        nextID = 0;
    }

    public Task(String taskName, String username, long end){
        int TASKID = nextID++;
        USER = username;
        this.START = currentDate();
        this.end = end;
        this.name = taskName;
        this.progress = 0;

        this.dumpTask();
    }

    public static void createSubtask(Task superTask){
        Task newSub = new Task("Dinoproject" ,"tempuser", 0);
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
        System.out.printf("Name: %s%nStart: %d%nEnd: %d%nUsername: %s%nTaskID: %d%nProgress: %s%n", this.name, this.START, this.end, this.USER, this.TASKID, this.getProgress());
    }
    private int calcProgress(){

        long now = currentDate();

        long timeSinceStart = now-this.START;
        long totalTimeToCompletion  = this.end - this.START;

        progress = 100*((int) (timeSinceStart/totalTimeToCompletion));

        return progress;
    }
//==========---------- 50%
//==============------ 70%
//====================
 /*   private void sendToDatabase(){

    }
    private Task retrieveTaskByName(String taskName){
        MongoConnect database = new MongoConnect();
        return database.retrieveTask(taskName);
    }
//====================
*/
    public void setName(String name) {
        this.name = name;
    }

    public int getTaskID() {
        return TASKID;
    }

    public long getStart() {
        return START;
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
}
