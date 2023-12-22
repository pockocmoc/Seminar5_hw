import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {

            Thread.sleep(6000);

            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Thread readerThread = new Thread(new ServerHandler(reader));
            readerThread.start();

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = consoleReader.readLine()) != null) {
                writer.println(message);
                writer.flush();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class ServerHandler implements Runnable {
        private BufferedReader reader;

        public ServerHandler(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received message: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
