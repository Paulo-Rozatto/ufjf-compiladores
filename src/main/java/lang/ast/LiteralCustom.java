/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.Pair;
import br.ufjf.estudante.util.VisitException;
import java.util.HashMap;
import java.util.Map;

public class LiteralCustom extends Literal {
  private final String id;
  private final Map<String, Pair<Type, Literal>> fields = new HashMap<>();

  public LiteralCustom(String id, Map<String, Type> typeMap, int line) {
    super(line);
    this.id = id;

    for (Map.Entry<String, Type> entry : typeMap.entrySet()) {
      fields.put(entry.getKey(), new Pair<>(entry.getValue(), null));
    }
  }

  public void setField(String id, Object value) {
    Pair<Type, Literal> p = fields.get(id);

    if (p == null) {
      throw new VisitException(
          "Esse tipo não contém campo '" + id + "'", ((Literal) value).getLine());
    }

    if (value.getClass() != p.getFirst().getLiteralClass()) {
      throw new VisitException(
          String.format(
              "Esperado tipo %s para campo '%s', obtido tipo %s",
              p.getFirst().getLiteralClass().getCanonicalName(),
              id,
              value.getClass().getCanonicalName()),
          ((Literal) value).getLine());
    }

    p.setSecond((Literal) value);
  }

  public Pair<Type, Literal> getField(String id) {
    return fields.get(id);
  }

  @Override
  public Type getType() {
    return TypeCustom.getType(id);
  }

  @Override
  protected boolean checkArg(Literal arg) {
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(id).append(" {");

    for (Map.Entry<String, Pair<Type, Literal>> entry : fields.entrySet()) {
      sb.append(entry.getKey()).append(": ");
      if (entry.getValue().getSecond() != null) {
        sb.append(entry.getValue().getSecond().toString());
      } else {
        sb.append("null");
      }
      sb.append(", ");
    }

    // remover a virgula e o espaço no final
    if (!fields.isEmpty()) {
      sb.setLength(sb.length() - 2);
    }

    sb.append("}");
    return sb.toString();
  }

  @Override
  public int getColumn() {
    return -1;
  }
}
