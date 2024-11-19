import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    Environment() {
        enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public Object get(Token name) {
        if(values.containsKey(name.lexeme)) return values.get(name.lexeme);
        if(enclosing != null) return enclosing.get(name);
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    public void assign(Token name, Object value) { // !
        values.put(name.lexeme, value);
        if(enclosing != null) enclosing.assign(name, value);
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }
}
