import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();

    public Object get(Token name) {
        if (values.containsKey(name.lexeme)) return values.get(name.lexeme);
        return null;
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }
}
