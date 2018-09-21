package com.hello;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GroupMeAPI {

    /**
     * Class GroupMeAPI contains the main method and handles much of the connections with the GroupMe API
     */

    private static Bot tyrone;      //Create New Bot Object


    /**
     * Retrieves all groups from groupme api and returns them as a JSONObject()
     * @return new JSONObject containing all the groups the user is a member of
     * @throws Exception Handles a bunch of expceptions thrown by forming a URL and Connection
     */
    public JSONObject getAllGroups() throws Exception {

        URL groupMeURL = new URL("[URL GOES HERE]");                                                       //Creates new URL object that connects to groupme account

        HttpURLConnection connection = (HttpURLConnection) groupMeURL.openConnection();                         //cast groupMeURL.openConnection() as an HttpURLConnection and store it in a new HttpURLConnection
        connection.setRequestMethod("GET");                                                                     //set Http request Method to GET
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));             //BufferedReader created to read inputstream from the get http request
        String inputLine;
        StringBuffer content = new StringBuffer();                                                              //a mutable string? never used this before but cool, turns out its faster to cat than a regular string
        while ((inputLine = in.readLine()) != null) content.append(inputLine);                                  //read the input steam and append it to content
        in.close();                                                                                             //close BR
        return new JSONObject(content.toString());                                                              //new JSONObject with content toString() which is all the groups the user is a part of
    }

    /**
     * Retrieves most recent message in the chosen group chat
     * @return new JSONObject containing the most recent message
     * @throws Exception Handles URL and connection associated exceptions
     */
    public JSONObject getMostRecentMessage() throws Exception {
        URL messageURL = new URL("[URL GOES HERE]");                                                //URL object for the specified group

        HttpURLConnection connection = (HttpURLConnection) messageURL.openConnection();                 //open new HttpURLConnection() to messageURL.openConnection() cast a an HttpURLConnection()
        connection.setRequestMethod("GET");                                                             //set Http method to GET
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));     //new BufferedReader to retrieve from Http request
        String inputLine;                                                                               //input line string
        StringBuffer content = new StringBuffer();                                                      //new string buffer to store input from http request
        while ((inputLine = in.readLine()) != null) content.append(inputLine);                          //read all http request and store it in content
        in.close();                                                                                     //close BR
        return new JSONObject(content.toString());                                                      //return most recent message stored in JSONObject
    }

    public static void main(String[] args) throws Exception {

        //Meme meme = new Meme();
        //Tyrone.postImage();

        GroupMeAPI api = new GroupMeAPI();
        JSONObject response = api.getAllGroups();
        JSONArray array = response.getJSONArray("response");

        //gets groupName and groupID from content
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String groupId = obj.getString("group_id");
            String groupName = obj.getString("name");
            if (groupName.equals("Hello World 2018")) { //set the parameter of .equals() to the name of the group you want
                System.out.println("Group name: " + groupName);
                System.out.println("Group ID: " + groupId);
            }
        }
        while (true) {
            JSONObject messagesResponse = api.getMostRecentMessage();
            JSONObject responseJSON = messagesResponse.getJSONObject("response");
            JSONArray messageArray = responseJSON.getJSONArray("messages");

            //gets message from group messages
            String latestMessage = "";


            JSONObject obj = messageArray.getJSONObject(0);
            latestMessage = obj.getString("text");//<-- use
            tyrone = new Bot(latestMessage);
            if (latestMessage.toLowerCase().contains("@tyrone")) {
                //System.out.printf("%nLatest Message: %s%n", latestMessage);
                tyrone.generalLoop(latestMessage);
            }
            //System.out.printf("The most recent message is: %s", latestMessage);
        }
        //Tyrone sends his message through groupme
        //Tyrone.say("VICTORY!!!"); << output
    }

}