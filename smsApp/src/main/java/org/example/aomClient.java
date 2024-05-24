package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class aomClient {

    private static final String BASE_URL = "https://aom-vmovtyc2ya-uc.a.run.app/api";

    public static void createSubscriber() {
        try {
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Please enter the following information:");
            System.out.print("MSISDN (Phone Number): ");
            String msisdn = sc.nextLine();
            System.out.print("Package ID: ");
            int packageID = Integer.parseInt(sc.nextLine());
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Surname: ");
            String surname = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();
            System.out.print("Security Key: ");
            String securityKey = sc.nextLine();
            
            // Construct JSON body
            String jsonBody = "{\"msisdn\":\"" + msisdn + "\",\"packageID\":" + packageID + ",\"name\":\"" + name + "\",\"surname\":\"" + surname + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"securitykey\":\"" + securityKey + "\"}";
            System.out.println(jsonBody);

            // Set up HTTP connection
            String url = BASE_URL + "/customer/register";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Write JSON body to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // Get response code
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Subscriber created successfully!");
            } else {
                System.out.println("Error: HTTP response code - " + responseCode);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static String loginSubscriber() {

        Scanner sc = new Scanner(System.in);

        System.out.print("MSISDN (Phone Number): ");
        String msisdn = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        // Construct JSON body
        String jsonBody = "{\"msisdn\":\"" + msisdn + "\",\"password\":\"" + password + "\"}";
        System.out.println(jsonBody);


        // Set up HTTP connection to  login
        try {

            String url = BASE_URL + "/customer/login";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // Get response code
            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK)
                System.out.println("Error: HTTP response code - " + responseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parse the JSON response to get the token
            JSONObject jsonResponse = new JSONObject(response.toString());
            String token = jsonResponse.getString("token");

            System.out.println("Logged in successfully!");
            System.out.println("Token: " + token);

            return token;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showRemainingBalanceList() {
    // First, obtain the token by logging in
    String token = loginSubscriber();

    try {
        // Set up HTTP connection for GET request
        String msisdn = "your_msisdn_here"; // Replace this with the MSISDN you want to use
        String url = BASE_URL + "/balance?msisdn=" + msisdn;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        
        // Add token to the header
        connection.setRequestProperty("Authorization", "Bearer " + token);

        // Get response code
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }
            // Print the JSON response
            System.out.println("Response Body: " + response.toString());
        } else {
            System.out.println("Error: HTTP response code - " + responseCode);
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}


}
