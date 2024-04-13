package br.ufjf.estudante.tokens;

import java.util.Optional;

public record Token(TokenType type, int line, int col, Optional<Object> value) {
    public String toText() {
        String text = type.label;

        if (value().isPresent()) {
            text += ":" + value().get();
        }
        return text;
    }
}
