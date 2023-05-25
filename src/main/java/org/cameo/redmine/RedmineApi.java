package org.cameo.redmine;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.cameo.element.Issue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;

import java.sql.Date;

import java.util.logging.Logger;

/**
 * @author Eliana
 */


/**
 * Class to integrate Redmine's APIs
 */


public class RedmineApi {

    public RedmineApi() {
    }



    /**
     * This API will show always the latest created issue
     * Sort by ID and Order by Updated_On values
     * @return description of the issue
     */
    public String getIssueFromList(){
        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";
        String url = "http://localhost:3000/issues.json?sort=id,order_by=updated_on,asc";
        String urlTwo = "http://localhost:3000/issues/" + returnIdFromIssue() + ".json?include=journals";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("X-Redmine-API-Key", redmine_api_key);
        getRequest.addHeader("accept", "application/json");

        //second get of issue's changes
        HttpGet get = new HttpGet(urlTwo);
        get.addHeader("X-Redmine-API-Key", redmine_api_key);
        get.addHeader("accept", "application/json");

        String value = null;
        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Logger.getLogger("Log of Response String: " + responseString);

            //second get of issue's changes
            HttpResponse resp = httpClient.execute(get);
            HttpEntity ent = resp.getEntity();
            String responseNotes = EntityUtils.toString(ent, "UTF-8");

            JSONObject json = new JSONObject(responseString);
            JSONArray issues = json.getJSONArray("issues");


            for (int i = 0; i < issues.length(); i++) {
                    JSONObject issue = issues.getJSONObject(i);
                    String description = issue.getString("description");
                    if(!responseNotes.isEmpty()) {
                        value = description + "\n " + "\n" + "Changes of the ISSUE: " + " " + getNotes(responseNotes);
                    } else {
                        value = description;
                    }

                }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                System.out.println("Issue found successfully!");
            } else {
                System.out.println("Error retrieving issue: " + response.getStatusLine().getReasonPhrase() + "Error code:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }


    /**
     *
     * @param response
     * @return the notes added changes to issue
     * @throws JSONException
     */

    public String getNotes(String response) throws JSONException {
        String notes = "";
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray journals = jsonResponse.getJSONObject("issue").getJSONArray("journals");

        for (int i = 0; i < journals.length(); i++) {
            JSONObject journal = journals.getJSONObject(i);
            notes = journal.getString("notes");
            System.out.println("Changes from issue" + notes);
        }
        return notes;
    }

    /**
     * POST API to create a new Issue
     * @param subject
     * @param area
     * @throws IOException
     */

    public void createIssue(JTextField subject, JTextArea area, JComboBox priority, JComboBox tracker) throws IOException {
        String url = "http://localhost:3000/projects/redmine-eliana/issues.json";
        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("X-Redmine-API-Key", redmine_api_key);

        String title = subject.getText();
        String description = area.getText();
        String json = "{\"issue\": {\"project_id\":1,\"subject\":\"" + title + "\",\"description\":  \"" + description + "\"," + "\"priority_id\": \"" + priority.getSelectedItem() + "\",\"tracker_id\":\"" + tracker.getSelectedItem() + "\" }}";

        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            System.out.println("Issue created successfully!");
        } else {
            System.out.println("Error creating issue: " + response.getStatusLine().getReasonPhrase() + "Error code:" + response.getStatusLine().getStatusCode());
        }

    }

    /**
     * GET to obtain the properties of the ISSUE
     * @return
     */


    public Issue getProperties(){
        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";
        String url = "http://localhost:3000/issues/" + returnIdFromIssue() +".json?/order_by=updated_on,asc";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("X-Redmine-API-Key", redmine_api_key);
        getRequest.addHeader("accept", "application/json");
        Issue issue = new Issue();
        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            issue.setAuthor(new JSONObject(responseString).getJSONObject("issue").getJSONObject("author").getString("name"));
            issue.setStatus(new JSONObject(responseString).getJSONObject("issue").getJSONObject("status").getString("name"));
            issue.setSubject(new JSONObject(responseString).getJSONObject("issue").getString("subject"));
            issue.setPriority(new JSONObject(responseString).getJSONObject("issue").getJSONObject("priority").getString("name"));
            issue.setTracker(new JSONObject(responseString).getJSONObject("issue").getJSONObject("tracker").getString("name"));
            issue.setStartDate(Date.valueOf(new JSONObject(responseString).getJSONObject("issue").getString("start_date")));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                System.out.println("Issue's id found successfully!");
            } else {
                System.out.println("Error retrieving ticket's properties: " + response.getStatusLine().getReasonPhrase() + "Error code:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return issue;
        }

    }


    /**
     * This API will show the issue
     * Sort by subject or priority or author
     * @return description of the issue
     */
    public String getIssueBySpecifiedProperties(JTextField subject, JComboBox priority, JTextField author){
        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";
        String url = "http://localhost:3000/issues.json?subject=" + subject.getText() + "&priority_name=" + priority.getSelectedItem() + "&author_name=" + author.getText();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("X-Redmine-API-Key", redmine_api_key);
        getRequest.addHeader("accept", "application/json");


        String value = null;
        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Logger.getLogger("Log of Response String: " + responseString);


            JSONObject json = new JSONObject(responseString);
            JSONArray issues = json.getJSONArray("issues");


            for (int i = 0; i < issues.length(); i++) {
                JSONObject issue = issues.getJSONObject(i);
                String description = issue.getString("description");
                    value = description;
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                System.out.println("Issue found successfully!");
            } else {
                System.out.println("Error retrieving issue: " + response.getStatusLine().getReasonPhrase() + "Error code:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }



    public Issue getPropertiesByIssue(JTextField subject, JComboBox priorityBox, JTextField author){
        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";
        String url = "http://localhost:3000/issues.json?subject=" + subject.getText() + "&priority_name=" + priorityBox.getSelectedItem() + "&author_name=" + author.getText();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("X-Redmine-API-Key", redmine_api_key);
        getRequest.addHeader("accept", "application/json");
        Issue issue = new Issue();
        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject json = new JSONObject(responseString);
            JSONArray issues = json.getJSONArray("issues");


            for (int i = 0; i < issues.length(); i++) {
                JSONObject issueJson = issues.getJSONObject(i);
                issue.setAuthor(issueJson.getJSONObject("author").getString("name"));
                issue.setStatus(issueJson.getJSONObject("status").getString("name"));
                issue.setSubject(issueJson.getString("subject"));
                issue.setPriority(issueJson.getJSONObject("priority").getString("name"));
                issue.setTracker(issueJson.getJSONObject("tracker").getString("name"));
                issue.setStartDate(Date.valueOf(issueJson.getString("start_date")));
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                System.out.println("Issue's id found successfully!");
            } else {
                System.out.println("Error retrieving ticket's properties: " + response.getStatusLine().getReasonPhrase() + "Error code:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return issue;
        }

    }
    /**
     * GET to retrieve the ID of the issue
     * @return
     */
    public String returnIdFromIssue(){
        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";
        String url = "http://localhost:3000/issues.json?sort=id,order_by=updated_on,asc";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("X-Redmine-API-Key", redmine_api_key);
        getRequest.addHeader("accept", "application/json");
        String value = "";
        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject json = new JSONObject(responseString);
            JSONArray issues = json.getJSONArray("issues");

            for (int i = 0; i < issues.length(); i++) {
                JSONObject issue = issues.getJSONObject(i);
                int id = issue.getInt("id");
                value = String.valueOf(id);
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                System.out.println("Issue's id found successfully!");
            } else {
                System.out.println("Error retrieving issue's id: " + response.getStatusLine().getReasonPhrase() + "Error code:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }

    }





}


