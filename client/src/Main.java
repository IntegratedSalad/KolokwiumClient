import java.io.*;
import java.net.Socket;

public class Main {

    private static BufferedReader in;
    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("127.0.0.1", 2137);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String resp = GetResponseFromServer(); // Get Hello
            System.out.println(resp);

            // Get First Question

            // for ...
            // 1. Get Question from server
            // 2. Read input from Client (uczen odpowiada na pytansko)
            // 3. Send answer
            // 4. Get next question (and show the possible answers)
            // loop?

            clientSocket.close();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
    }

    public static String GetResponseFromServer() throws IOException, InterruptedException {
        Thread.sleep(2000);
        String buff = null;
        while (in.ready() && (buff = in.readLine()) != null);
        return buff;
    }

    public static MessageType DecodeResponseFromServer() throws IOException, InterruptedException {

        // Get first character and map it to the MessageType
        return null;
    }
}