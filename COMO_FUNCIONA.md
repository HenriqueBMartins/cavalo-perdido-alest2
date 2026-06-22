# Como Funciona

## O Problema

Um cavalo de xadrez está num tabuleiro toroidal — as bordas se conectam. Saiu pela esquerda, entra pela direita. Saiu por cima, entra por baixo. O cavalo começa em **C**, precisa chegar em **S** no menor número de pulos, sem pisar em casas marcadas com **x**.

## O Algoritmo: BFS

BFS (Busca em Largura) explora o tabuleiro em camadas. Primeiro verifica todas as casas alcançáveis em 1 pulo, depois em 2 pulos, e assim por diante. O primeiro caminho que encontra **S** é garantidamente o mais curto.

A estrutura central é uma **fila**: a posição inicial entra com 0 movimentos. A cada iteração, pega a frente da fila, tenta os 8 movimentos do cavalo, e coloca as casas válidas e não visitadas no final da fila com movimentos + 1.

## Tabuleiro Toroidal

O cálculo da nova posição usa módulo duplo:

```
novaLinha = ((linha + deltaL) % linhas + linhas) % linhas
novaCol   = ((coluna + deltaC) % colunas + colunas) % colunas
```

O `+ linhas` antes do segundo `%` garante que valores negativos também fiquem no intervalo correto.

## Estrutura do Código

Tudo está em um arquivo só (`Main.java`):

- `main()` — lê cada arquivo de teste, monta o tabuleiro, roda o BFS, imprime o resultado
- `bfs()` — recebe a grade, dimensões, posição inicial e saída, retorna o menor número de movimentos (-1 se impossível)
- `lerArquivo()` — lê o `.txt`, pula o cabeçalho opcional, retorna a matriz de caracteres

## Compilar e Rodar

```bash
javac src/Main.java -d out
java -cp out Main testes/caso1.txt
```
