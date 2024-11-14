public class Interpreter implements Expr.Visitor<Object> {

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        switch(expr.operator.type) {
            case BANG: return !isTruthy(right);
            case MINUS: return -(double)right;
        }

        return null; // Unreachable
    }

    private boolean isTruthy(Object object) {
        if(object == null) return false;
        if(object instanceof Boolean) return (boolean)object;
        return true;
    }

    private String stringify(Object object) {
        if(object == null) return "nil";
        if(object instanceof Double) {
            String text = object.toString();
            if(text.endsWith(".0")) {
                text = text.substring(0, text.length()-2);
            }
            return text;
        }
        return object.toString();
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    public Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch(expr.operator.type) {
            case GREATER: return (double)left > (double)right;
            case GREATER_EQUAL: return (double)left >= (double)right;
            case LESS: return (double)left < (double)right;
            case LESS_EQUAL: return (double)left <= (double)right;
            case MINUS: return (double)left - (double)right;
            case PLUS:
                if(left instanceof Double && right instanceof Double) {
                    return (double)left + (double)right;
                }
                if(left instanceof String && right instanceof String) {
                    return (String)left + (String)right;
                }
                break;
            case SLASH: return (double)left / (double)right;
            case STAR: return (double)left * (double)right;
        }

        // Unreachable
        return null;
    }

    public void interpret(Expr expr) {
        Object value = evaluate(expr);
        System.out.println(stringify(value));
    }
}
