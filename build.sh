#!/bin/bash

# Verifica se a versão do Java é 17
is17=$(java -version 2>&1 | grep "version \"17")

if [[ $is17 ]]; then
    echo "Java 17 detectado, compilando o projeto..."
    mvn clean package
else
    echo "Instale o Java 17 para compilar o código. No próximo semestre, especifique a versão que você quer, burrão."
fi
