import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MD5HashGenerator {
    public static void main(String[] args) {
try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("input.json")));
       ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(jsonContent);
            JsonNode studentNode = rootNode.get("student");

    if (studentNode != null) {
     String firstName = studentNode.get("first_name").asText().toLowerCase().replace(" ", "");
         String rollNumber = studentNode.get("roll_number").asText().toLowerCase().replace(" ", "");
         String concatenatedString = firstName + rollNumber;

                String hash = generateMD5(concatenatedString);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
                    writer.write(hash);
     }

                System.out.println("MD5 hash stored in output.txt: " + hash);
    } else {
                System.out.println("Invalid JSON structure: 'student' key not found");
     }
} catch (IOException e) {
            e.printStackTrace();
}
}

    private static String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
     md.update(input.getBytes());
    byte[] digest = md.digest();
    StringBuilder sb = new StringBuilder();
    for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
} catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
}
}
}
