import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static BufferedReader socIn;
    private static PrintWriter socOut;
    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        try {
            Socket clientSocket = new Socket("127.0.0.1", 2137);
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socOut = new PrintWriter(clientSocket.getOutputStream());

            System.out.println("Waiting for hello...");
            String resp = GetResponseFromServer(); // Get Hello
            System.out.println(resp);

            // Get First Question
            String serverResponse = GetResponseFromServer();
            while (serverResponse.charAt(0) == '5') { // if question

                System.out.println("Got question: " + serverResponse);
                System.out.println("Your answer: ");
                String uczenAnswer = stdin.nextLine();
                if (!Objects.equals(uczenAnswer, "")) {
                    System.out.println("You answered: " + uczenAnswer);
                    // Send
                    socOut.println(uczenAnswer);
                    socOut.flush();
                } else {
                    continue;
                }
                serverResponse = GetResponseFromServer();
            }

            switch (serverResponse) {
                // Catch the other message
            }

            // 1. Get Question from server
            // 2. Read input from Client (uczen odpowiada na pytansko)
            // 3. Send answer
            // loop

            clientSocket.close();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
    }

    public static String GetResponseFromServer() throws IOException, InterruptedException {
        Thread.sleep(2000);
        String buff = null;
//        while (socIn.ready() && (buff = socIn.readLine()) != null);
        while ((buff = socIn.readLine()) == null); // wait if there's nothing
        return buff;
    }
}