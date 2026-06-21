# O Cavalo Perdido — ALEST II

## Como compilar e executar

```bash
# 1. Compile
javac src/CavaloPerdido.java -d out

# 2. Execute com os casos de teste
java -cp out CavaloPerdido testes/caso1.txt testes/caso2.txt ...

# 3. Ou rode sem argumentos para ver o exemplo embutido
java -cp out CavaloPerdido
```

## Formato dos arquivos de teste

Cada arquivo `.txt` deve ter o seguinte formato:

```
LINHAS COLUNAS       ← (opcional, pode omitir)
..C..
.....
..x..
.....
..S..
```

Caracteres:
- `.` → casa livre
- `x` → casa bloqueada (não pode pisar)
- `C` → posição inicial do cavalo
- `S` → saída

## Descrição do Algoritmo

**BFS (Busca em Largura)** em um grafo implícito:
- **Nós** = células do tabuleiro (linha, coluna)
- **Arestas** = 8 movimentos do cavalo
- **Toroidal**: `nova = ((pos + delta) % tamanho + tamanho) % tamanho`

O BFS garante o **menor caminho** porque explora os nós por nível (distância).

## Complexidade

- **Tempo**: O(L × C) onde L = linhas, C = colunas
- **Espaço**: O(L × C) para a matriz de visitados e a fila

## Exemplo do enunciado

O enunciado afirma que o cavalo chega à saída em **10 movimentos**.
