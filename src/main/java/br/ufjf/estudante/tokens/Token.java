/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */

package br.ufjf.estudante.tokens;

import java.util.Optional;

public record Token(TokenType type, Optional<Object> value) {
    public String toText() {
        String text = type.label;

        if (value().isPresent()) {
            text += ":" + value().get();
        }
        return text;
    }
}
