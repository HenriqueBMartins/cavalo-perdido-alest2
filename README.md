# O Cavalo Perdido — ALEST II

## Como compilar e executar

```bash
javac src/Main.java -d out
java -cp out Main testes/caso1.txt testes/caso2.txt ...
```

## Formato dos arquivos de teste

```
..C..
.....
..x..
.....
..S..
```

- `.` → casa livre
- `x` → casa bloqueada
- `C` → posição inicial do cavalo
- `S` → saída

## Documentação

- [COMO_FUNCIONA.md](COMO_FUNCIONA.md) — explicação do algoritmo e do código
- [RESULTADOS_E_RELATORIO.md](RESULTADOS_E_RELATORIO.md) — resultados dos casos de teste e roteiro para o relatório
- [ROTEIRO_APRESENTACAO.md](ROTEIRO_APRESENTACAO.md) — roteiro da apresentação
