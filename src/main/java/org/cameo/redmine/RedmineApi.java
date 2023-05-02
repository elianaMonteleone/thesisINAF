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
     * @return Description of the Issue by defined Id
     */

    public String getIssueById(){

        String issueId = "38480";
        String endPoint = "http://www.redmine.org/issues/" + issueId + ".json";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(endPoint);
       String auth = "admin:admin";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        getRequest.setHeader("Authorization", authHeader);
        getRequest.addHeader("accept", "application/json");
        String value = null;
        try {
            HttpResponse response = httpClient.execute(getRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Logger.getLogger("Log of Response String: " +  responseString);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
            String issueSubject = jsonObject.get("issue").getAsJsonObject().get("description").getAsString();
            value = issueSubject;
            System.out.println("Issue Description: " + value);
            Logger.getLogger("Log of description:"  + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return value;
        }
    }


    /**
     * TO IMPROVE: Post method, to create a new Issue
     */


  /*  @PostMapping("/create/issues")
    public ResponseEntity<Issue> createIssue(@RequestParam String username,
                                             @RequestParam String password,
                                             @RequestParam String projectId) {
        try {
            RedmineManager redmineManager = RedmineManagerFactory.createWithUserAuth("http://www.redmine.org/issues.json", username, password);

            Issue issue = IssueFactory.create(Integer.valueOf(projectId));
            issue.setAssigneeId(new Random().nextInt()); // Setting a random id
            issue.setSubject("New issue");
            issue.setDescription("This is a new issue created via API");

            Issue newIssue = redmineManager.getIssueManager().createIssue(issue);

            return ResponseEntity.ok(newIssue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


    public void  createIssue(String subject, String description, int priority, String assignedUser) throws IOException {
        String url = "http://www.redmine.org/issues";
        String username = "admin";
        String password = "admin";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");

        StringEntity payload = new StringEntity(
                "{" +
                        "\"issue\": {" +
                        "\"subject\": \"" + subject + "\"," +
                        "\"description\": \"" + description + "\"," +
                        "\"priority_id\": \"" + priority + "\"," +
                        "\"assigned_to_id\": \"" + assignedUser + "\"" +
                        "}" +
                        "}"
        );
        post.setEntity(payload);

        /*post.addHeader(BasicScheme.authenticate(
                new UsernamePasswordCredentials(username, password), "UTF-8", false));*/
        // add username and password in the Authorization header
        String authHeader = "Basic " + Base64.encodeBase64String((username + ":" + password).getBytes());
        post.setHeader("Authorization", authHeader);

        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            System.out.println("Issue created successfully!");
        } else {
            System.out.println("Error creating issue: " + response.getStatusLine().getReasonPhrase() + "Error code:" + response.getStatusLine().getStatusCode());
        }

    }



}
