package org.cameo.redmine;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
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



    /**
     * POST API to create a new Issue
     * @param subject
     * @param area
     * @throws IOException
     */

    public void createIssue(JTextField subject, JTextArea area) throws IOException {
        String url = "http://localhost:3000/projects/redmine-eliana/issues.json";
        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("X-Redmine-API-Key", redmine_api_key);

        String title = subject.getText();
        String description = area.getText();
        String json = "{\"issue\": {\"project_id\":1,\"subject\":\"" + title + "\",\"description\":  \"" + description + "\"," + "\"priority_id\":2,\"tracker_id\":3}}";

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


    
    

    }


