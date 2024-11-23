import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("127.0.0.1", 2137);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            Thread.sleep(2000);
            String buff;

            while ((buff = in.readLine()) != null) {
                System.out.println(buff);
            }

            //System.out

            //System.out.println(in.readLine());
            //System.out.println(new String(cbuff));

            // Jeden watek obsluguje logike odpowiadania na pytania
            // Drugi watek obsluguje wiadomosci od serwera

            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}