package com.hello;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GroupMeAPI {

    private static Bot tyrone;

    public JSONObject getAllGroups() throws Exception {
        URL groupMeURL = new URL(""/*URL to Groupme Group*/);

        HttpURLConnection connection = (HttpURLConnection) groupMeURL.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) content.append(inputLine);
        in.close();
        return new JSONObject(content.toString());
    }

    public JSONObject getMostRecentMessage() throws Exception {
        URL messageURL = new URL(""/*message URL*/);

        HttpURLConnection connection = (HttpURLConnection) messageURL.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) content.append(inputLine);
        in.close();
        return new JSONObject(content.toString());
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
