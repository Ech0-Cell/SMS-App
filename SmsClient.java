import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmsClient {

    public static void main(String[] args) {
        String url = "https://your-sms-api.com/send_sms?phone=1234567890&message=Hello,%20this%20is%20a%20test%20SMS%20message!"; // Will Replace this URL with SMS API endpoint
        
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("SMS sent successfully!");
            } else {
                System.out.println("Error: HTTP response code - " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
