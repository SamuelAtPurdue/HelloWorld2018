package com.hello;

import java.util.Date;

// Code by James, Kayla, Sam, and Sam
public class Bot {

    //declare constants
    private static final String HELP_KEY = "@tyrone !help";
    private static final String TASK_KEY = "@tyrone !task";
    private static final String RES_KEY = "@tyrone !res";
    private static final String TODO_KEY = "@tyrone !todo";
    private static final String MEME_KEY = "@tyrone !meme";
    private static final String PROJ_KEY = "@tyrone !proj";
    private static final String DATE_KEY = "@tyrone !date";
    private static final String SPAM_KEY = "@tyrone !spam";


    private static SqlConnect sqlConnect = new SqlConnect();
    private static Task[] tasks;

    /**
     * Function Check the validity of the input received from the user.
     *
     * @param generalScanner input bot receives is checks to see if it is valid input
     * @return boolean says whether the input is valid
     */
    private static boolean isValidInput(String generalScanner) { //Determines if user entered valid command

        switch (generalScanner) {
            case HELP_KEY:
                return true;
            case TASK_KEY:
                return true;
            case RES_KEY:
                return true;
            case TODO_KEY:
                return true;
            case MEME_KEY:
                return true;
            case PROJ_KEY:
                return true;
            case DATE_KEY:
                return true;
            case SPAM_KEY:
                return true;
            default:
                return false;
        }
    }

    static {
        try {
            Tyrone.say(String.format("Hi my name is Tyrone and I am your Bot! To name your project type in: !proj <your project name here> <due date 'mmddyyyy'>%n"));// Bot introduces itself

            tasks = sqlConnect.retrieveTasks();
        } catch (Exception e) {

        }
    }

    public Bot(String message) {
    }

    public void generalLoop(String message) {
        String[] messageArray = message.split(" ");
        String command = messageArray[0] + " " + messageArray[1];


        String output;
        if (!isValidInput(command)) {
            try {
                Tyrone.say(String.format("%s is not a valid command%n", message));
                usage();
            } catch (Exception e) {

            }
        }
        if (isValidInput(command)) {

            switch (command) {
                case HELP_KEY:
                    usage();
                    break;
                case PROJ_KEY:
                    //should add task to string array of tasks
                    try {
                        Task newTask = new Task(messageArray[2], "Tyrone", Integer.parseInt(messageArray[3]), -1);

                        Task[] buffer = tasks;
                        tasks = new Task[buffer.length+1];
                        for (int i = 0; i < buffer.length; i++){
                            tasks[i] = buffer[i];
                        }
                        tasks[tasks.length-1] = newTask;

                        sqlConnect.insertTask(newTask);

                        Tyrone.say("Creating new Task");
                    } catch (Exception e) {

                    }
                    break;
                case TODO_KEY:
                    //should print array of tasks
                    for (Task t: tasks)
                        t.displayTask();
                    break;
                case MEME_KEY:
                    try{
                        Tyrone.say("Memes on the way");
                    }catch(Exception except){
                        System.err.println("Failed to deliver the memes");
                    }
                case SPAM_KEY:
                    try {
                        Tyrone.say("@tyrone !spam");
                    }catch (Exception e){

                    }
            }
        }
    }

    /**
     * Displays Usage Information
     */
    private static void usage() {
        try {
            Tyrone.say(String.format("COMMANDS%n\t!task\t\t\tAdds task to the TODO list%n\t!help\t\t\tDisplays Help Screen%n\t!todo\t\t\tDisplays TODO list%n\t!meme\t\t\tDisplays Memes%n"));

        } catch (Exception except) {

        }
    }

    /**
     * Converts Date to Epoch time
     *
     * @param inDate Input Date
     * @return time since epoch as a long
     */
    private long toEpoch(Date inDate) {
        return inDate.getTime();
    }

    public static String getHelpKey() {
        return HELP_KEY;
    }

    public static String getTaskKey() {
        return TASK_KEY;
    }

    public static String getResKey() {
        return RES_KEY;
    }

    public static String getTodoKey() {
        return TODO_KEY;
    }

    public static String getMemeKey() {
        return MEME_KEY;
    }

    public static String getProjKey() {
        return PROJ_KEY;
    }

    public static String getDateKey() {
        return DATE_KEY;
    }
}
