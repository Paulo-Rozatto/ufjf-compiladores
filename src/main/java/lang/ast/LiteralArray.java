/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import java.lang.reflect.Array;

public class LiteralArray extends Literal {
  private final Object array;
  private final int dimensions;
  private final int lastSize;

  public LiteralArray(Object array, int dimensions, int lastSize, int line) {
    super(line);
    this.array = array;
    this.dimensions = dimensions;
    this.lastSize = lastSize;
  }

  private static void arrayToStringHelper(Object array, StringBuilder sb) {
    if (array != null && array.getClass().isArray()) {
      sb.append("[");
      int length = Array.getLength(array);
      for (int i = 0; i < length; i++) {
        if (i > 0) {
          sb.append(", ");
        }
        arrayToStringHelper(Array.get(array, i), sb);
      }
      sb.append("]");
    } else {
      sb.append(array);
    }
  }

  public Object getArray() {
    return array;
  }

  public int getDimensions() {
    return dimensions;
  }

  public int getLastSize() {
    return lastSize;
  }

  @Override
  public Type getType() {
    return new TypePrimitive(this.getClass(), lineNumber);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    arrayToStringHelper(array, sb);
    return sb.toString();
  }

  @Override
  public int getColumn() {
    return -1;
  }
}
