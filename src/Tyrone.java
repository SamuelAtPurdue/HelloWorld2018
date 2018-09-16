package com.hello;

import javax.print.DocFlavor;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Tyrone {

    public static final String botID = "26faee26e9a1140cc964a637e0";

    public static void say(String message) throws Exception {
        URL groupMeURL = new URL("https://api.groupme.com/v3/bots/post?token=J2j1gvULxeVlhPo7axHTYGDUirPUQ7N6kGgtmcWI");
        HttpURLConnection connection = (HttpURLConnection) groupMeURL.openConnection();

        connection.setRequestMethod("POST");

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("bot_id", botID);
        parameters.put("text", message);

        // Send parameters to GroupMe
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        connection.getInputStream();
    }public static void postImage(String message) throws Exception{
        URL groupMeURL = new URL("https://api.groupme.com/v3/bots/post?token=J2j1gvULxeVlhPo7axHTYGDUirPUQ7N6kGgtmcWI");
        HttpURLConnection connection = (HttpURLConnection) groupMeURL.openConnection();

        connection.setRequestMethod("POST");

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("bot_id", botID);
        parameters.put("text", message);
        parameters.put("picture_url", "https://i.imgur.com/WBBXEzD.png?fb");

        // Send parameters to GroupMe
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        connection.getInputStream();
    }

}