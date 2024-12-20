/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.Pair;
import br.ufjf.estudante.util.VisitException;
import br.ufjf.estudante.visitor.Visitor;
import br.ufjf.estudante.visitor.VisitorInterpreter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LValue extends Expression {
  private final String id;
  private final List<Object> modifiers = new ArrayList<>();
  private Map<String, Pair<Type, Literal>> env;
  private Visitor v;
  public LValue(String id, int line) {
    super(line);
    this.id = id;
  }

  public List<Object> getModifiers() {
    return modifiers;
  }

  public void addModifier(String id) {
    modifiers.add(id);
  }

  public void addModifier(Expression exp) {
    modifiers.add(exp);
  }

  public void accept(Visitor v) {
    if (v.getClass() == VisitorInterpreter.class) {
      env = ((VisitorInterpreter) v).getEnv();
    } else {
      env = null;
    }

    this.v = v;

    v.visit(this);
  }

  public void set(Literal value) {
    if (value == null) {
      throw new VisitException("Atribuição inválida!", getLine());
    }

    if (modifiers.isEmpty()) {
      Pair<Type, Literal> ref = env.get(id);
      if (ref == null) {
        ref = new Pair<>(value.getType(), value);
        env.put(id, ref);
      } else if (ref.getSecond().getClass() == value.getClass()) {
        ref.setSecond(value);
      } else {
        throw new VisitException(
            String.format(
                "Não se pode atribuir tipo %s em variável %s",
                value.getClass().getCanonicalName(), ref.getSecond().getClass().getCanonicalName()),
            getLine());
      }
      return;
    }

    Pair<Type, Literal> ref = env.get(id);
    Object currentObject = ref.getSecond();
    Object modifier;

    // For para fazer acessos
    for (int i = 0; i < modifiers.size() - 1; i++) {
      modifier = modifiers.get(i);

      if (modifier instanceof Expression) {
        ((Expression) modifier).accept(v);
        modifier = ((Expression) modifier).evaluate();
      }

      if (modifier instanceof LiteralInt) {
        if (currentObject instanceof LiteralArray) {
          currentObject = ((LiteralArray) currentObject).getArray();
        }
        currentObject = Array.get(currentObject, ((LiteralInt) modifier).getValue());
      } else if (modifier instanceof String) {
        currentObject = ((LiteralCustom) currentObject).getField((String) modifier).getSecond();
      } else {
        throw new VisitException("Modificador invalido: " + modifier, getLine());
      }
    }

    // Codigo repetido para o ultimo modifier para fazer escrita
    modifier = modifiers.get(modifiers.size() - 1);

    if (modifier instanceof Expression) {
      ((Expression) modifier).accept(v);
      modifier = ((Expression) modifier).evaluate();
    }

    if (modifier instanceof LiteralInt) {
      if (currentObject instanceof LiteralArray) {
        currentObject = ((LiteralArray) currentObject).getArray();
      }

      int index = ((LiteralInt) modifier).getValue();
      if (value.getClass() == LiteralArray.class) {
        Array.set(currentObject, index, ((LiteralArray) value).getArray());

      } else {
        if (currentObject instanceof LiteralArray) {
          currentObject = ((LiteralArray) currentObject).getArray();
        }
        Array.set(currentObject, index, value);
      }
    } else if (modifier instanceof String) {
      ((LiteralCustom) currentObject).setField((String) modifier, value);
    }
  }

  @Override
  public Literal evaluate() {
    if (env == null) {
      return null;
    }

    Pair<Type, Literal> var = env.get(id);

    if (var == null) {
      return null;
    }

    if (modifiers.isEmpty()) {
      return var.getSecond();
    }

    Object currentObject = var.getSecond();
    Type type = null;
    for (Object modifier : modifiers) {
      if (modifier instanceof Expression) {
        ((Expression) modifier).accept(v);
        modifier = ((Expression) modifier).evaluate();
      }

      if (modifier instanceof LiteralInt) {
        if (currentObject instanceof LiteralArray) {
          type = ((LiteralArray) currentObject).getType();
          currentObject = ((LiteralArray) currentObject).getArray();
        }

        currentObject = Array.get(currentObject, ((LiteralInt) modifier).getValue());
      } else if (modifier instanceof String) {
        type = ((LiteralCustom) currentObject).getField((String) modifier).getFirst();
        currentObject = ((LiteralCustom) currentObject).getField((String) modifier).getSecond();
      } else {
        throw new VisitException("Modificador invalido: " + modifier, getLine());
      }
    }

    if (currentObject == null) {
      return new LiteralNull(getLine());
    }

    if (currentObject.getClass().isArray()) {
      return new LiteralArray(currentObject, type, getLine());
    }

    return (Literal) currentObject;
  }

  public String getId() {
    return id;
  }

  @Override
  public int getColumn() {
    return -1;
  }
}
