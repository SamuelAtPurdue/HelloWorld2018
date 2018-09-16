package com.hello;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;

/**
 * Created by Samuel Hild on 9/15/2018.
 * Creates Tasks which the user completes
 * Tasks contain a username, progress to completion, start and end dates, and a unique taskID
 */
public class Task {

    public static int nextID = 0;           //static in for keeping track of taskIDs

    private Task[] subtask = new Task[0];   //Array of sub-tasks
    private long end;                       //intended end date
    private String name;                    //name of the task
    private int progress;                   //number from 1-100 to measure percent complete


    private long start;                     //start date calculated on initialization
    private final String USER;              //User who created the task
    private int taskID;                     //Unique taskID calculated on initialization
    private final int SUPER_ID;             //ID of the super task

    /**
     * Task Constructor creates new task object when instantiated
     *
     * @param taskName Name of the task
     * @param username Name of the user
     * @param end      intended end date of project
     * @param superID  Id of the superclass
     */

    public Task(String taskName, String username, long end, int superID) {
        taskID = nextID++;              //increment static next id and assign task id
        USER = username;                //Set USER constant to username
        this.start = currentDate();     //Set Start to current Date if not specified by setStart()
        this.end = end;                 //Set end to intended end date
        this.name = taskName;           //set name of task
        this.progress = 0;              //set progress var to 0
        this.SUPER_ID = superID;        //set SUPER_ID

        this.dumpTask();                //dump task for debug
    }

    /**
     * Method intended to create a subtask
     * TEMPORARY DO NOT USE
     *
     * @param superTask Super task to create a sub task inside
     */
    private static void createSubtask(Task superTask) {
        Task newSub = new Task("Dinoproject", "tempuser", 0, superTask.getTaskID());
        superTask.add(newSub);
    }

    /**
     * add a sub task to a task
     * non-static method adds Task to the task it is called from
     * @param sub Sub task to add
     */
    public void add(Task sub) {
        //extend array by 1 to accommodate new sub task

        Task[] hold = this.subtask;                         //buffer to hold array of sub-tasks
        this.subtask = new Task[hold.length + 1];           //create new longer array

        for (int i = 0; i < hold.length; i++)
            this.subtask[i] = hold[i];                      //loop through finish extending array

        this.subtask[subtask.length - 1] = sub;             //add new sub task to the end of teh array
    }

    /**
     * Add array of sub tasks to a super task
     * UNUSED!
     * @param subs sub tasks to add to super task
     */
    public void addAll(Task[] subs) {
        for (Task sub : subs)
            add(sub);                                       //add sub to super task
    }

    /**
     * returns current date in second since epoch
     * @return Current Date formatted as seconds since epoch
     */
    private long currentDate() {
        long dateCreated = LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset()); //get current date
        return dateCreated;
    }

    /**
     * Dumps all sub tasks by calling dumpTasks
     */
    public void dumpSubTasks() {
        for (Task sub : subtask) {
            dumpTask();                                     //Call dumpTask() for current task
            if (subtask != null) sub.dumpSubTasks();        //Recursively call dumpSubtasks() for each sub task if the task has sub tasks
        }
    }

    /**
     * Simply dumps the Task into terminal
     */
    public void dumpTask() {
        System.out.printf("Name: %s%nStart: %d%nEnd: %d%nUsername: %s%nTaskID: %d%nProgress: %s%n", this.name, this.start, this.end, this.USER, this.taskID, this.getProgress());
    }

    /**
     * returns the progress variable
     * @return progress formatted as an int read from 1-100
     */
    private int calcProgress() {

        long now = currentDate();                                               //Gets current Date formatted as seconds since epoch

        long timeSinceStart = now - this.start;                                 //returns time since start in seconds
        long totalTimeToCompletion = this.end - this.start;                     //returns total time to completion in seconds

        progress = 100 * ((int) (timeSinceStart / totalTimeToCompletion));      //calculates progress as a percent by dividing timeSinceStart by totalTimeToCompletion type-casted as an integer to round to a whole number
                                                                                //WARNING: Always rounds down because of integer typecasting
        return progress;
    }

    /**
     * Depreciated do not use
     * @param sendTask
     */
    private void sendToDatabase(Task sendTask) {
        SqlConnect database = new SqlConnect();
        database.insertTask(sendTask);
        database.kill();
    }

    //Getters and Setter Functions

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskID() {
        return this.taskID;
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

    public int getSuperId() {
        return this.SUPER_ID;
    }

    public void setTaskID(int id) {
        taskID = id;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void setEnd(long endDate) {
        this.end = endDate;
    }
}
