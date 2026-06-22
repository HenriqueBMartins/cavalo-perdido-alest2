package algoritmo;

import modelo.Posicao;
import modelo.Tabuleiro;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Contém a lógica do algoritmo de Busca em Largura (BFS).
 */
public class ResolvedorBFS {

    // Os 8 movimentos possíveis de um cavalo (delta linha, delta coluna)
    private static final int[][] MOVIMENTOS = {
        {-2, -1}, {-2, +1},
        {-1, -2}, {-1, +2},
        {+1, -2}, {+1, +2},
        {+2, -1}, {+2, +1}
    };

    /**
     * Executa BFS no tabuleiro e retorna o menor número de movimentos
     * da posição inicial até a saída.
     *
     * @return número mínimo de movimentos, ou -1 se impossível
     */
    public static int resolver(Tabuleiro tabuleiro, Posicao inicio) {
        
        // Caso especial: cavalo já nasceu na saída
        if (tabuleiro.isSaida(inicio.getLinha(), inicio.getColuna())) {
            return 0;
        }

        // Matriz de visitados para evitar loops
        boolean[][] visitado = new boolean[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        visitado[inicio.getLinha()][inicio.getColuna()] = true;

        // Fila BFS
        Queue<Posicao> fila = new LinkedList<>();
        fila.add(inicio);

        while (!fila.isEmpty()) {
            Posicao atual = fila.poll();

            // Tenta todos os 8 movimentos a partir da posição atual
            for (int[] mov : MOVIMENTOS) {
                // Delega para o tabuleiro a responsabilidade de calcular o wrap toroidal
                int[] novaPos = tabuleiro.calcularPosicaoToroidal(
                    atual.getLinha(), atual.getColuna(), mov[0], mov[1]
                );
                
                int novaLinha = novaPos[0];
                int novaCol = novaPos[1];

                // Chegou na saída? Retorna a resposta imediatamente
                if (tabuleiro.isSaida(novaLinha, novaCol)) {
                    return atual.getMovimentos() + 1;
                }

                // Se for uma posição válida e ainda não visitada
                if (!visitado[novaLinha][novaCol] && tabuleiro.isCasaLivre(novaLinha, novaCol)) {
                    visitado[novaLinha][novaCol] = true;
                    // Adiciona na fila com +1 movimento gasto
                    fila.add(new Posicao(novaLinha, novaCol, atual.getMovimentos() + 1));
                }
            }
        }

        return -1; // Fila vazia e não achou a saída
    }
}
