package modelo;

/**
 * Representa um estado do cavalo durante a busca (BFS).
 * Guarda a posição atual (linha, coluna) e quantos movimentos
 * foram necessários para chegar até aqui.
 */
public class Posicao {
    private int linha;
    private int coluna;
    private int movimentos;

    public Posicao(int linha, int coluna, int movimentos) {
        this.linha = linha;
        this.coluna = coluna;
        this.movimentos = movimentos;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public int getMovimentos() {
        return movimentos;
    }
}
