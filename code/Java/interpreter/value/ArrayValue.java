package interpreter.value;

import java.util.Vector;

public class ArrayValue extends Value<Vector<? extends Value<?>>> {

    private Vector<? extends Value<?>> value;

    public ArrayValue(Vector<? extends Value<?>> value) {
        this.value = value;
    }

    public Vector<? extends Value<?>> value() {
        return value;
    }

<<<<<<< Updated upstream
=======
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < value.size(); i++) {
            Value<?> v = value.get(i);
            sb.append(i == 0 ? " " : ", ");
            sb.append(v == null ? "" : v.value().toString());
        }
        sb.append(" ]");
        return sb.toString();
    }
>>>>>>> Stashed changes
}

    // Feito em sala