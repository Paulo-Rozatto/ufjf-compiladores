package br.ufjf.estudante.singletons;

import java.util.Map;
import java.util.Objects;

public class SCustom extends SType {
  private final String id;
  private Map<String, SType> fields;

  public SCustom(String id) {
    this.id = id;
  }

  public SCustom(String id, Map<String, SType> fields) {
    this.id = id;
    this.fields = fields;
  }

  public String getId() {
    return id;
  }

  public Map<String, SType> getFields() {
    return fields;
  }

  public void setFields(Map<String, SType> fields) {
    this.fields = fields;
  }

  public SType getFieldType(String fieldId) {
    return fields.get(fieldId);
  }

  @Override
  public boolean match(SType value) {
    if (value instanceof SOr) {
      return value.match(this);
    }

    return (value instanceof SError)
        || (value instanceof SCustom) && Objects.equals(((SCustom) value).getId(), this.id);
  }

  @Override
  public String toString() {
    return id;
  }
}
