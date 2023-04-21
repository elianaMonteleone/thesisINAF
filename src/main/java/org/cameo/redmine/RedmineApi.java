package org.cameo.redmine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cameo.element.IssueDTO;
import org.eclipse.emf.ecore.xml.type.internal.DataValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class RedmineApi {

    public RedmineApi() {
    }

    public String getIssues() throws IOException {

        String uri = "https://www.redmine.org/projects/redmine.json";
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String userCredentials = "admin:admin";
        String basicAuth = "Basic " + DataValue.Base64.encode(userCredentials.getBytes());

        connection.setRequestProperty("Authorization", basicAuth);
        // Create an HTTP client object
        HttpClient httpClient = HttpClients.createDefault();

        // Initialize the client with the Redmine API endpoint
        HttpGet httpGet = new HttpGet("http://redmine.example.com/issues.json");

        // Make the HTTP request and obtain the response
        HttpEntity responseEntity = httpClient.execute(httpGet).getEntity();

        // Parse the response data
        // Use the data as required
        return EntityUtils.toString(responseEntity);
    }


    public String getIssue() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.redmine.org/issues.json?/38480");
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
            Logger.getLogger(responseString + "valore della response string");
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
            String issueSubject = jsonObject.get("issue").getAsJsonObject().get("subject").getAsString();
            value = issueSubject;
            System.out.println("Issue Subject: " + value);
            Logger.getLogger(value + "valore della value string");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return value;
        }
    }



    public void postIssue(){

        String issueId = "38481";
        String endPoint = "http://www.redmine.org/issues.json";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(endPoint);
        String auth = "admin:admin";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        postRequest.setHeader("Authorization", authHeader);
        postRequest.addHeader("accept", "application/json");
       // String value = null;
        String responseString = "";
        try {
            List<IssueDTO> params = new ArrayList<IssueDTO>();
            params.add(new IssueDTO(38483,2,"Redmine - Integration Rest API", 1, ""));
            HttpResponse response = httpClient.execute(postRequest);
            HttpEntity entity = response.getEntity();
            /*String responseString = EntityUtils.toString(entity, "UTF-8");
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);*/
           // String issueSubject = jsonObject.get("issue").getAsJsonObject().get("subject").getAsString();
            //value = issueSubject;
            InputStream content = entity.getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String line;

            while ((line = buffer.readLine()) != null) {
                responseString += line;
            }

            //release all resources held by the responseHttpEntity
            EntityUtils.consume(entity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Logger.getLogger(responseString + "responseString");
        }
    }




}
