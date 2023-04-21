package org.cameo.redmine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RedmineAPIClient {

    private static final String REDMINE_URL = "http://YOUR_REDMINE_URL";
    private static final String API_KEY = "dd47afec1184c56a16e9ac1102fcc1b73ae32f61";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";


    public String getResponseBody() {
        // prepare HTTP client with authentication
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(getCredentialsProvider())
                .build();

        // prepare GET request to fetch Redmine issues
        HttpGet getRequest = new HttpGet(REDMINE_URL + "/issues.json");
        String responseBody = "";

        try {
            // execute HTTP request and get response
            HttpResponse response = httpClient.execute(getRequest);

            // handle response
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // parse response body as string
                responseBody = EntityUtils.toString(entity);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // release HTTP connection resources
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    private static UsernamePasswordCredentials getCredentials() {
        return new UsernamePasswordCredentials(API_KEY + ":x");
    }

    private static AuthScope getAuthScope() {
        return new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
    }

    private static String getEncodedCredentials() {
        return Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultCredentialsProvider(getCredentialsProvider())
                .build();
    }

    private static CredentialsProvider getCredentialsProvider() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(getAuthScope(), getCredentials());
        return provider;
    }

}
