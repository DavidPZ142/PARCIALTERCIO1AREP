package edu.escuelaing.arem;
import java.net.*;
import java.io.*;
public class HTPPServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            String outputLine;
            boolean firstline = true;
            String file = "";

            while ((inputLine = in.readLine()) != null) {
                if (firstline) {
                    file = inputLine.split(" ")[1];
                    firstline = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            String[] lista = file.split("=");
            if (file.contains("/clima")) {
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Conten-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Clima</title>\n"
                        + "</head>"
                        + "<body>"
                        + "<h1 >Clima</h1>"
                        +" <label> Ciudad:    </label>"
                        +"<input type=\"text\" id = \"ingresado\">"
                        +"<button"
                        + "</body>"
                        + "</html>";
            } else if (file.contains("/consulta?lugar=")) {

                Clima clima = new Clima();
                String resp = Clima.getClima(lista[1]);

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Conten-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Consulta</title>\n"
                        + "</head>"
                        + "<body>"
                        + resp
                        + "</body>"
                        + "</html>";
            }
            else {

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Conten-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "My Web Site"
                        + "</body>"
                        + "</html>";
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
            serverSocket.close();

    }


    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set
    }

}



