import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static BufferedReader socIn;
    private static PrintWriter socOut;
    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Koniec czasu!");
                System.out.println();
                System.exit(-1);
            }
        }, 20*1000);

        try {
            Socket clientSocket = new Socket("127.0.0.1", 2137);
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socOut = new PrintWriter(clientSocket.getOutputStream());

            System.out.println("Your name?:");
            String name = stdin.nextLine();
            socOut.println(name); // TODO: add message type
            socOut.flush();

            System.out.println("Waiting for hello...");
            String resp = GetResponseFromServer(); // Get Hello
            System.out.println(resp);

            // Get First Question
            String serverResponse = GetResponseFromServer();
            while (serverResponse.charAt(0) == '5') { // if question

                System.out.println("Got question: " + serverResponse.substring(1));
                System.out.println("Got answers:");
                String[] answers = GetPossibleAnswersFromResponse(serverResponse);

                for (int i = 0; i < answers.length; i++) {
                    System.out.println("Possible answer " + (i+1) + ": " + answers[i]);
                }

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

            switch (serverResponse.charAt(0)) {
                case '4': {
                    // Quiz completed
                    System.out.println("Zakonczyles test xD");

                    System.out.println("Czekanie na wynik...");
                    String scoreString = GetResponseFromServer().substring(1);
                    System.out.println("Wynik: " + scoreString);
                    break;
                }
                case '3': {
                    // Quiz abandoned
                    System.out.println("Test nie zdany hahahah get shitted!!!1");
                    break;
                }
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
        String buff = null;
        while ((buff = socIn.readLine()) == null); // wait if there's nothing
        return buff;
    }

    public static void SendDataToServer(String data) {
        socOut.println(data);
        socOut.flush();
    }

    public static String[] GetPossibleAnswersFromResponse(String response) {
        final int indexOfAns = response.indexOf("ANS");
        return response.substring(indexOfAns+4).split(" ");
    }
}