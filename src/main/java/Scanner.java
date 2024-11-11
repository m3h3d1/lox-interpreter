import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while(!isAtEnd()) {
            start = current;
            scanToken();
        }
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char ch = advance();
        switch (ch) {
            case '(' -> addToken(TokenType.LEFT_PAREN);
            case ')' -> addToken(TokenType.RIGHT_PAREN);
            case '{' -> addToken(TokenType.LEFT_BRACE);
            case '}' -> addToken(TokenType.RIGHT_BRACE);
            case ',' -> addToken(TokenType.COMMA);
            case '.' -> addToken(TokenType.DOT);
            case '-' -> addToken(TokenType.MINUS);
            case '+' -> addToken(TokenType.PLUS);
            case ';' -> addToken(TokenType.SEMICOLON);
            case '*' -> addToken(TokenType.STAR);
            case '!' -> addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
            case '=' -> addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
            case '<' -> addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
            case '>' -> addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
            case '/' -> {
                if(match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
            }
            case ' ', '\t' -> { }
            case '\n' -> line++;
            case '"' -> string();
            default -> {
                if(isDigit(ch)) {
                    number();
                } else if(isAlpha(ch)) {
                    identifier();
                }
                else {
                    Lox.error(line, "Unexpected character: " + ch);
                }
            }
        }
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private boolean match(char expected) {
        if(isAtEnd()) return false;
        if(source.charAt(current) != expected) return false;
        current++;
        return true;
    }

    private char peek() {
        if(isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }
        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        // Closing "
        advance();

        // Trim the surrounding quotes
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private void number() {
        while(isDigit(peek())) advance();
        if(peek() == '.' && isDigit(peekNext())) {
            advance();
            while(isDigit(peek())) advance();
        }
        addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext() {
        if(current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void identifier() {
        while(isAlphaNumeric(peek())) advance();
        addToken(TokenType.IDENTIFIER);
    }

    private boolean isAlphaNumeric(char ch) {
        return isAlpha(ch) || isDigit(ch);
    }

    private boolean isAlpha(char ch) {
        return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_');
    }
}
