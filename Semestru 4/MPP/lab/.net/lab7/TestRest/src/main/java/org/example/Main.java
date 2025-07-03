package org.example;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class Main {
    private static String apiBaseUrl = "https://localhost:7140";
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        HttpClient client = HttpClient.newBuilder().sslContext(sslContext).build();
        HttpRequest requestGetAll = HttpRequest.newBuilder()
                .uri(
                        URI.create(apiBaseUrl + "/api/child")
                ).GET().build();
        HttpRequest requestCreateChild = HttpRequest.newBuilder()
                .uri(
                        URI.create(apiBaseUrl + "/api/child")
                )
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {"name": "Paul Paull", "cnp": "999999___"}"""))
                .build();

        try {
            System.out.println("Before adding");
            HttpResponse<String> response = client.send(requestGetAll, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            System.out.println("Adding Paul");
            HttpResponse<String> responseCreateChild = client.send(requestCreateChild, HttpResponse.BodyHandlers.ofString());
            System.out.println(responseCreateChild.body());

            System.out.println("After adding");
            HttpResponse<String> responseAdd = client.send(requestGetAll, HttpResponse.BodyHandlers.ofString());
            System.out.println(responseAdd.body());



        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}