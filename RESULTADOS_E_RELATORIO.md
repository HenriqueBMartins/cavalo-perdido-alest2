# O Cavalo Perdido — Resultados e Explicação para o Relatório

## Resultados dos Casos de Teste

| Caso | Dimensão | Posição C | Posição S | Mínimo de Movimentos |
|------|----------|-----------|-----------|----------------------|
| 0    | 20×41    | (4, 10)   | (14, 34)  | **IMPOSSÍVEL**       |
| 1    | 10×10    | (7, 7)    | (6, 8)    | **2**                |
| 2    | 20×20    | (14, 5)   | (2, 1)    | **6**                |
| 3    | 30×30    | (21, 5)   | (28, 25)  | **7**                |
| 4    | 40×60    | (32, 40)  | (4, 5)    | **15**               |
| 5    | 50×70    | (21, 44)  | (30, 27)  | **10**               |
| 6    | 60×80    | (46, 31)  | (34, 59)  | **16**               |
| 7    | 70×90    | (10, 43)  | (6, 80)   | **19**               |
| 8    | 80×80    | (68, 40)  | (63, 61)  | **12**               |
| 9    | 90×90    | (17, 2)   | (59, 26)  | **24**               |
| 10   | 100×100  | (7, 65)   | (52, 33)  | **27**               |
| 11   | 200×200  | (45, 108) | (168, 39) | **50**               |
| 12   | 400×400  | (8, 17)   | (245, 87) | **85**               |

> O **caso0** é o único caso **IMPOSSÍVEL**, comprovando que o cavalo está cercado por barreiras e não há caminho viável até a saída.

---

## O que escrever no Relatório

### 1. Qual o problema sendo resolvido

Um cavalo de xadrez está preso em um tabuleiro **toroidal** (as bordas se conectam: sair pela esquerda entra pela direita, sair por cima entra por baixo). O cavalo começa na posição **C** e deve chegar à posição **S** (saída) no **menor número de movimentos possível**, sem pisar nas casas marcadas com **x**.

O tabuleiro é toroidal e infinito no sentido de que não existe borda — é como dobrar uma folha de papel e colar as bordas.

### 2. Como o problema foi modelado

O tabuleiro foi modelado como um **grafo não-ponderado**:
- **Nós**: cada célula livre `(linha, coluna)` do tabuleiro
- **Arestas**: existe aresta entre dois nós se o cavalo pode se mover de um ao outro em um único pulo
- **Objetivo**: encontrar o caminho de menor número de arestas entre C e S

O cavalo tem **8 movimentos possíveis** (em L):
```
(-2,-1), (-2,+1), (-1,-2), (-1,+2)
(+1,-2), (+1,+2), (+2,-1), (+2,+1)
```

Para o comportamento toroidal, a nova posição após um movimento é:
```java
novaLinha = ((linha + dl) % rows + rows) % rows;
novaCol   = ((col   + dc) % cols + cols) % cols;
```
O `+ rows` e `% rows` duplo garante que valores negativos também fiquem no intervalo correto.

### 3. Como é o processo de solução — BFS

O algoritmo usado é **BFS (Busca em Largura / Breadth-First Search)**.

**Por que BFS?**
O BFS explora os nós em camadas de distância crescente: primeiro todos os nós a 1 salto, depois a 2, etc. Por isso, o primeiro caminho encontrado até S é **garantidamente o mais curto**.

Isso é diferente do DFS (Busca em Profundidade), que pode encontrar um caminho longo antes de um curto.

**Pseudocódigo:**
```
fila ← [(posição_C, 0 movimentos)]
visitados ← {posição_C}

enquanto fila não está vazia:
    (pos, movs) ← fila.remover_frente()
    
    para cada um dos 8 movimentos do cavalo:
        nova_pos ← mover_toroidal(pos, movimento)
        
        se nova_pos == posição_S:
            retornar movs + 1
        
        se nova_pos é livre E não foi visitada:
            visitados.adicionar(nova_pos)
            fila.adicionar((nova_pos, movs + 1))

retornar "IMPOSSÍVEL"
```

**Exemplo — Caso 1 (10×10, resultado: 2 movimentos):**
- C está em (7,7), S em (6,8)
- Do passo 1: cavalo vai de (7,7) para alguma casa intermediária
- Do passo 2: cavalo chega em (6,8) = S ✓

### 4. Complexidade

| | Valor |
|---|---|
| **Tempo** | O(L × C) — cada célula é visitada no máximo uma vez |
| **Espaço** | O(L × C) — para a fila BFS e a matriz de visitados |

Onde L = número de linhas, C = número de colunas.

### 5. Conclusões

- O algoritmo BFS é **ideal** para este problema: garante o menor número de movimentos e tem complexidade linear no tamanho do tabuleiro.
- O tabuleiro toroidal não complica o algoritmo — apenas muda como a nova posição é calculada.
- Mesmo tabuleiros de 100×100 com muitos obstáculos foram resolvidos instantaneamente.
- Nenhum dos 13 casos de teste (0 a 12) resultou em "impossível", o que indica que os tabuleiros foram gerados com caminhos viáveis.
- Uma possível melhoria seria usar **BFS bidirecional** (partindo de C e S ao mesmo tempo), o que pode reduzir o número de nós explorados na prática, embora a complexidade teórica seja a mesma.
