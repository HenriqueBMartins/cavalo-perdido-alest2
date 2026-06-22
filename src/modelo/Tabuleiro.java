package modelo;

/**
 * Representa o mundo do jogo. Contém a grade toroidal e a lógica
 * para validar posições.
 */
public class Tabuleiro {
    private char[][] grade;
    private int linhas;
    private int colunas;
    
    private int linhaSaida;
    private int colunaSaida;

    public Tabuleiro(char[][] grade) {
        this.grade = grade;
        this.linhas = grade.length;
        this.colunas = grade[0].length;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void setSaida(int linhaSaida, int colunaSaida) {
        this.linhaSaida = linhaSaida;
        this.colunaSaida = colunaSaida;
    }

    public boolean isSaida(int l, int c) {
        return l == linhaSaida && c == colunaSaida;
    }

    /**
     * Calcula a nova posição no mundo toroidal (onde as bordas se conectam).
     * @return Um array [novaLinha, novaColuna]
     */
    public int[] calcularPosicaoToroidal(int linha, int coluna, int deltaL, int deltaC) {
        int novaLinha = ((linha + deltaL) % linhas + linhas) % linhas;
        int novaCol   = ((coluna + deltaC) % colunas + colunas) % colunas;
        return new int[]{novaLinha, novaCol};
    }

    /**
     * Verifica se uma casa é válida (ou seja, se NÃO é um obstáculo 'x').
     */
    public boolean isCasaLivre(int l, int c) {
        return grade[l][c] != 'x';
    }
}
