import java.io.*;
import com.fasterxml.jackson.core; 
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.caucho.config.types.ResourceRef;
public class HttpSerializationExample {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/serialize-deserialize", new SerializeDeserializeHandler());
        server.start();
        System.out.println("Server is running on port 8080...");
    }

    static class SerializeDeserializeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper mapper = new ObjectMapper();
            ResourceRef VAR = mapper.readValue(requestBody, ResourceRef.class);
            String filename = "serializedJsonNode.bin";
            serialize(VAR, filename);
            JsonNode deserializedNode = (JsonNode) deserialize(filename);
            exchange.sendResponseHeaders(200, 0);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(deserializedNode.toString().getBytes());
            responseBody.close();
        }
    }

    public static void serialize(Object obj, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj);
        }
    }

    public static Object deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return in.readObject();
        }
    }
}
