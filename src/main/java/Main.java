import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<String, TokenType> tokens = new HashMap<>();

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: ./your_program.sh tokenize <filename>");
            System.exit(1);
        }

        String command = args[0];
        String filename = args[1];

        if (!command.equals("tokenize")) {
            System.err.println("Unknown command: " + command);
            System.exit(1);
        }

        String fileContents = "";
        try {
            fileContents = Files.readString(Path.of(filename));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }

        if (!fileContents.isEmpty()) {
            tokensInit();
            for(char ch : fileContents.toCharArray()) {
                System.out.println(tokens.get(ch+"") + " " + ch + " null");
            }
            System.out.println("EOF  null");
        } else {
            System.out.println("EOF  null");
        }
    }

    private static void tokensInit() {
        tokens.put("(", TokenType.LEFT_PAREN);
        tokens.put(")", TokenType.RIGHT_PAREN);
        tokens.put("{", TokenType.LEFT_BRACE);
        tokens.put("}", TokenType.RIGHT_BRACE);
    }
}
