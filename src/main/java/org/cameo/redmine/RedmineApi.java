package org.cameo.redmine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
     * Testing method to retrieve the Subject of the issue
     *
     * @return subject of the issue
     * @throws IOException
     */
    public String getIssue() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.redmine.org/issues.json");
        String auth = "admin:admin";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        httpGet.setHeader("Authorization", authHeader);
        String subject;
        String description;
        String status;

        // send HTTP request and parse JSON response
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String json = EntityUtils.toString(response.getEntity(), ContentType.APPLICATION_JSON.getCharset());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode issue = root.get("issues");

            // extract relevant information and use it in your Java app
            subject = issue.get("subject").asText();
            description = issue.get("description").asText();
            status = issue.get("status").get("name").asText();

            System.out.println("Subject: " + subject);
            System.out.println("Description: " + description);
            System.out.println("Status: " + status);
            System.out.println("Ha letto l'issue");
            return subject;

        }
    }


    /**
     * API to get the description knowing the Id
     *
     * @return Description of the Issue by defined Id
     */

    public String getIssueById() {

        String redmine_api_key = "76cb1a968ce607538b54ba25cb872db2dd2e4972";
        String issueId = "1";
        String url = "http://localhost:3000/projects/redmine-eliana/issues" + issueId + ".json";
        String endPoint = "http://www.redmine.org/issues/" + issueId + ".json";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        //String auth = "admin:admin";
        //byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        //String authHeader = "Basic " + new String(encodedAuth);
        //getRequest.setHeader("Authorization", authHeader);
        getRequest.addHeader("X-Redmine-API-Key", redmine_api_key);
        getRequest.addHeader("accept", "application/json");
        String value = null;
        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Logger.getLogger("Log of Response String: " + responseString);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
            String issueSubject = jsonObject.get("issue").getAsJsonObject().get("description").getAsString();
            value = issueSubject;
            System.out.println("Issue Description: " + value);
            Logger.getLogger("Log of description:" + value);
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


