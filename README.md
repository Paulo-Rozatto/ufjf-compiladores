# Trabalho de Compiladores

A primeira parte deste projeto consistiu na implementação de um analisador léxico para a linguagem lang.
A segunda parte consistiu em criar um analisador sintático e um interpretador para a linguagem lang.

## Pré-requisitos

- Java JDK 17
- Maven

## Instalação

### Clonar o Repositório
Para começar, clone o repositório para obter o código-fonte mais recente:

```bash
git clone https://github.com/Paulo-Rozatto/ufjf-compiladores.git
```


### Compilar o Projeto
Navegue até a pasta do projeto clonado e execute o arquivo de build.sh:

```bash
cd ufjf-compiladores/
./build.sh
```


## Execução

Depois de compilar o projeto, execute o analisador léxico com o seguinte comando:

```bash
java -jar target/ufjf-compiladores-jar-with-dependencies.jar -i src/test/resources/data/semantic/right/teste0.lan
```

## Sobre o Projeto

Este projeto foi desenvolvido utilizando Java 17. Certifique-se de que você tem a versão correta do Java JDK instalada e configurada no seu ambiente de desenvolvimento para evitar problemas de compatibilidade.

## Implementação

O analisador léxico foi implementado usando a biblioteca JFlex e o analisador sintático foi implementado com JCup.
