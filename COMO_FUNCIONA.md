# Como Funciona — Resolução do Problema do Cavalo Perdido

## 1. Definição do Problema

O problema consiste em encontrar o **menor número de movimentos** para um cavalo de xadrez chegar de uma posição inicial **C** até uma posição de saída **S** num tabuleiro com obstáculos (casas marcadas com **x**). O tabuleiro é **toroidal**: as bordas se conectam, de modo que sair pela esquerda faz o cavalo reaparecer pela direita, e sair por cima faz-o reaparecer por baixo.

O cavalo move-se segundo as regras padrão do xadrez: em formato de "L", ou seja, duas casas numa direção e uma casa perpendicular, resultando em 8 movimentos possíveis:

```
(-2,-1), (-2,+1), (-1,-2), (-1,+2)
(+1,-2), (+1,+2), (+2,-1), (+2,+1)
```

## 2. Modelação do Problema

### 2.1 Grafo Implícito

O tabuleiro pode ser visto como um **grafo não-ponderado** onde:

- **Nós**: cada célula livre do tabuleiro (posição `(linha, coluna)` que não contém `x`)
- **Arestas**: existe uma aresta entre dois nós se o cavalo pode saltar de um para o outro num único movimento
- **Objetivo**: encontrar o caminho com o menor número de arestas entre **C** e **S**

Num tabuleiro de L linhas e C colunas, o grafo tem no máximo L×C nós e até 8×L×C arestas (cada nó tem até 8 vizinhos).

### 2.2 Tabuleiro Toroidal

O comportamento toroidal é obtido com aritmética modular. Dada a posição atual `(l, c)` e um movimento `(Δl, Δc)`, a nova posição é:

```
novaLinha = ((l + Δl) % L + L) % L
novaCol   = ((c + Δc) % C + C) % C
```

O primeiro `%` pode resultar em valores negativos quando `l + Δl < 0` (exemplo: cavalo na linha 0 tenta mover -2). O segundo `%` com adição prévia de L garante que o resultado fica sempre no intervalo `[0, L-1]`.

Exemplo: L = 10, l = 1, Δl = -2 → `(1 + (-2)) % 10 = -1` → `(-1 + 10) % 10 = 9`.

## 3. Algoritmo de Resolução: BFS

### 3.1 Porquê BFS?

O **BFS (Breadth-First Search / Busca em Largura)** explora o grafo por camadas de distância crescente: primeiro todos os nós a distância 1 da origem, depois os de distância 2, e assim sucessivamente. Esta propriedade garante que a primeira vez que se encontra o nó de destino, o caminho percorrido é o **mais curto possível**.

Isto é uma consequência direta de o grafo ser **não-ponderado** (todas as arestas têm peso 1). Se o grafo tivesse pesos diferentes, seria necessário usar Dijkstra ou A*.

### 3.2 Passo a Passo do Algoritmo

```
início = posição C
fila = [início com 0 movimentos]
visitados = {início}

enquanto fila não está vazia:
    (pos, movs) = fila.remover_frente()
    
    para cada um dos 8 movimentos do cavalo:
        nova_pos = calcular_posição_toroidal(pos, movimento)
        
        se nova_pos == S:
            retornar movs + 1
        
        se nova_pos é livre E não está em visitados:
            adicionar nova_pos a visitados
            adicionar (nova_pos, movs + 1) à fila

retornar -1 (impossível)
```

### 3.3 Porquê Não Usar DFS?

O **DFS (Depth-First Search)** pode encontrar um caminho até S, mas não garante que é o mais curto. O DFS segue um ramo até ao fim antes de retroceder, por isso pode encontrar um caminho de 20 movimentos quando existe um de 3.

### 3.4 Porquê Não Pré-Construir o Grafo?

Uma abordagem alternativa seria construir uma **lista de adjacência** durante a leitura do ficheiro, calculando antecipadamente todos os vizinhos de cada nó. A discussão:

| Aspeto | On-the-fly (atual) | Grafo pré-construído |
|--------|-------------------|---------------------|
| Espaço | O(L×C) matriz + O(L×C) visitados | O(L×C) matriz + O(8×L×C) adjacência + O(L×C) visitados |
| Construção | Nenhuma | O(L×C) para calcular todos os vizinhos |
| BFS | O(L×C) × O(1) por vizinho | O(L×C) × O(1) por aresta |

O grafo de xadrez num tabuleiro toroidal é **regular e implícito**: cada nó tem sempre até 8 vizinhos calculáveis com fórmula. Não há variabilidade que justifique materializar a adjacência. A pré-construção só seria vantajosa se fizéssemos **muitas buscas no mesmo tabuleiro** — aí o grafo é construído uma vez e reutilizado.

Com uma única busca por tabuleiro, o on-the-fly é mais eficiente em memória e igual em tempo.

## 4. Estrutura do Código

O programa está num ficheiro único (`Main.java`) com três funções:

### `main(String[] args)`
Ponto de entrada. Para cada ficheiro passado como argumento:
1. Chama `lerArquivo()` para obter a matriz do tabuleiro
2. Encontra as posições de C e S percorrendo a matriz
3. Chama `bfs()` para calcular o menor caminho
4. Imprime o resultado

### `lerArquivo(String caminho)`
Lê o ficheiro `.txt` e retorna a matriz `char[][]`. Se a primeira linha for um cabeçalho no formato `LINHAS COLUNAS`, é ignorada. Cada caractere do ficheiro corresponde a uma célula: `.` (livre), `x` (obstáculo), `C` (início), `S` (saída).

### `bfs(char[][] grade, int linhas, int colunas, int[] inicio, int[] saida)`
Implementação do BFS. Usa uma `Queue<int[]>` (fila de arrays de 3 inteiros: linha, coluna, movimentos). Uma matriz `boolean[][] visitado` evita revisitar nós. Para cada nó retirado da fila, testa os 8 movimentos possíveis, calcula a posição toroidal, e se for válida e não visitada, adiciona à fila.

## 5. Complexidade

- **Tempo**: O(L × C) — cada célula é visitada no máximo uma vez, e cada visita processa 8 movimentos (constante)
- **Espaço**: O(L × C) — para a matriz de visitados e a fila BFS (no pior caso, a fila guarda todas as células)

## 6. Resultados Experimentais

O algoritmo foi testado em 13 casos (tabuleiros de 10×10 até 400×400). Todos os casos tiveram solução encontrada. Os tempos de execução são instantâneos para todos os tamanhos testados, confirmando a eficiência da abordagem.

| Caso | Dimensão | Movimentos |
|------|----------|------------|
| 0    | 20×41    | 9          |
| 1    | 10×10    | 2          |
| 4    | 40×60    | 15         |
| 9    | 90×90    | 24         |
| 12   | 400×400  | 85         |
