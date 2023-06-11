package net.draglis.webconsole;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class Connection implements Runnable {

    private final String dataFolder;
    private final Socket connectionSocket;
    private final HashMap<String, String> request;
    private final HashMap<String, String> redirect;

    public Connection(Socket connectionSocket, String dataFolder) {
        this.dataFolder = dataFolder;
        this.connectionSocket = connectionSocket;
        this.request = new HashMap<>();
        this.redirect = createRedirectMap();
    }

    private HashMap<String, String> createRedirectMap() {
        HashMap<String, String> redirectMap = new HashMap<>();
        redirectMap.put("/", "/index.html");
        redirectMap.put("/index.htm", "/index.html");
        redirectMap.put("/index", "/index.html");
        return redirectMap;
    }

    private void parseRequest() throws IOException {
        BufferedReader connectionReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        String requestLine = connectionReader.readLine();

        if (requestLine != null) {
            String[] requestLineParams = requestLine.split(" ");
            String requestMethod = requestLineParams[0];
            String requestResource = requestLineParams[1];
            String requestProtocol = requestLineParams[2];

            request.put("Method", requestMethod);
            request.put("Resource", requestResource);
            request.put("Protocol", requestProtocol);

            String headerLine;
            while (!(headerLine = connectionReader.readLine()).isEmpty()) {
                String[] requestParams = headerLine.split(":", 2);
                request.put(requestParams[0], requestParams[1].trim());
            }
        }
    }

    private void sendResponse() throws IOException {
        DataOutputStream outStream = new DataOutputStream(connectionSocket.getOutputStream());
        String resourcePath = request.get("Resource");
        File file = new File(this.dataFolder + "/WebConsole" + resourcePath);

        if (redirect.containsKey(resourcePath)) {
            String redirectLocation = redirect.get(resourcePath);
            outStream.writeBytes("HTTP/1.1 301 Moved Permanently\nLocation: " + redirectLocation);
        } else if (!file.exists()) {
            String http404Response = """
                    HTTP/1.1 404 Not Found\r
                    \r
                    <!DOCTYPE html>\r
                    <html>\r
                    <head>\r
                        <title>Not Found</title>\r
                    </head>\r
                    <body>\r
                        <h1>404 Error: File not found</h1>\r
                    </body>\r
                    </html>
                    """;

            outStream.write(http404Response.getBytes(StandardCharsets.UTF_8));
        } else {
            String contentType = Files.probeContentType(file.toPath());

            outStream.writeBytes("HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\n\r\n");

            FileInputStream fileStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            fileStream.close();
        }

        outStream.close();
    }

    @Override
    public void run() {
        try {
            parseRequest();
            sendResponse();
            connectionSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}