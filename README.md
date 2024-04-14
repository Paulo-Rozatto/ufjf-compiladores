# Trabalho de Compiladores

Este projeto consiste na implementação de um analisador léxico para a linguagem "lang". O objetivo é desenvolver uma ferramenta capaz de identificar e classificar os componentes léxicos da linguagem conforme especificado nos materiais do curso. 

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
java -jar target/ufjf-compiladores-1.0.jar testes/sintaxe/exemplo1.txt
```

## Sobre o Projeto

Este projeto foi desenvolvido utilizando Java 17. Certifique-se de que você tem a versão correta do Java JDK instalada e configurada no seu ambiente de desenvolvimento para evitar problemas de compatibilidade.

## Implementação

O analisador léxico foi implementado em Java, com o código-fonte disponível no diretório `src` do projeto. A implementação visa realizar a análise léxica, que é a primeira fase de compilação, transformando uma sequência de caracteres em uma sequência de tokens.
