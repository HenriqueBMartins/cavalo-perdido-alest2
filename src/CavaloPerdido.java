import java.io.*;
import java.util.*;

/**
 * O Cavalo Perdido
 *
 * Problema: Encontrar o menor número de movimentos de um cavalo de xadrez
 * em um tabuleiro TOROIDAL (bordas conectadas) para sair de C até S,
 * evitando casas marcadas com 'x'.
 *
 * Algoritmo: BFS (Busca em Largura) — garante o menor caminho em grafos
 * não-ponderados.
 *
 * Uso: java CavaloPerdido <arquivo_do_tabuleiro>
 *      java CavaloPerdido            (usa o tabuleiro padrão do enunciado)
 */
public class CavaloPerdido {

    // Os 8 movimentos possíveis de um cavalo (delta linha, delta coluna)
    private static final int[][] MOVIMENTOS = {
        {-2, -1}, {-2, +1},
        {-1, -2}, {-1, +2},
        {+1, -2}, {+1, +2},
        {+2, -1}, {+2, +1}
    };

    // -------------------------------------------------------------------------
    // BFS principal
    // -------------------------------------------------------------------------

    /**
     * Executa BFS no tabuleiro toroidal e retorna o menor número de movimentos
     * do cavalo (posição C) até a saída (posição S).
     *
     * @param tabuleiro  matriz de chars representando o tabuleiro
     * @param startLinha linha inicial do cavalo
     * @param startCol   coluna inicial do cavalo
     * @param endLinha   linha da saída
     * @param endCol     coluna da saída
     * @return número mínimo de movimentos, ou -1 se impossível
     */
    public static int bfs(char[][] tabuleiro, int startLinha, int startCol,
                          int endLinha, int endCol) {
        int linhas = tabuleiro.length;
        int colunas = tabuleiro[0].length;

        // Caso especial: cavalo já está na saída
        if (startLinha == endLinha && startCol == endCol) {
            return 0;
        }

        // Matriz de visitados
        boolean[][] visitado = new boolean[linhas][colunas];
        visitado[startLinha][startCol] = true;

        // Fila BFS: cada elemento é [linha, coluna, movimentos]
        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{startLinha, startCol, 0});

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int linha = atual[0];
            int col   = atual[1];
            int movs  = atual[2];

            // Tenta todos os 8 movimentos do cavalo
            for (int[] mov : MOVIMENTOS) {
                // Calcula nova posição com wrap toroidal
                int novaLinha = ((linha + mov[0]) % linhas + linhas) % linhas;
                int novaCol   = ((col   + mov[1]) % colunas + colunas) % colunas;

                // Chegou na saída?
                if (novaLinha == endLinha && novaCol == endCol) {
                    return movs + 1;
                }

                // Posição válida (não bloqueada, não visitada)?
                if (!visitado[novaLinha][novaCol]
                        && tabuleiro[novaLinha][novaCol] != 'x') {
                    visitado[novaLinha][novaCol] = true;
                    fila.add(new int[]{novaLinha, novaCol, movs + 1});
                }
            }
        }

        return -1; // Impossível chegar em S
    }

    // -------------------------------------------------------------------------
    // Leitura do tabuleiro
    // -------------------------------------------------------------------------

    /**
     * Lê um tabuleiro de um arquivo texto.
     *
     * Formato esperado:
     *   - Primeira linha: "LINHAS COLUNAS" (opcional; se ausente, infere)
     *   - Linhas seguintes: cada caractere representa uma célula
     *     '.' = livre, 'x' = bloqueada, 'C' = cavalo, 'S' = saída
     *
     * @param caminho caminho para o arquivo
     * @return Tabuleiro lido, ou null em caso de erro
     */
    public static char[][] lerTabuleiro(String caminho) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        }
        if (linhas.isEmpty()) return null;

        // Verifica se a primeira linha é "LINHAS COLUNAS"
        int inicio = 0;
        if (linhas.get(0).trim().matches("\\d+\\s+\\d+")) {
            inicio = 1; // pula cabeçalho
        }

        int numLinhas = linhas.size() - inicio;
        int numColunas = linhas.get(inicio).length();
        char[][] tab = new char[numLinhas][numColunas];

        for (int i = 0; i < numLinhas; i++) {
            String row = linhas.get(i + inicio);
            for (int j = 0; j < numColunas; j++) {
                tab[i][j] = (j < row.length()) ? row.charAt(j) : '.';
            }
        }
        return tab;
    }

    // -------------------------------------------------------------------------
    // Resolução de um tabuleiro
    // -------------------------------------------------------------------------

    public static void resolverTabuleiro(char[][] tab, String nomeCaso) {
        int startLinha = -1, startCol = -1;
        int endLinha   = -1, endCol   = -1;

        // Encontra posições C e S
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j] == 'C') { startLinha = i; startCol = j; }
                if (tab[i][j] == 'S') { endLinha   = i; endCol   = j; }
            }
        }

        System.out.println("=== " + nomeCaso + " ===");
        System.out.println("Dimensões: " + tab.length + " x " + tab[0].length);
        System.out.println("Cavalo (C): linha " + startLinha + ", coluna " + startCol);
        System.out.println("Saída  (S): linha " + endLinha   + ", coluna " + endCol);

        if (startLinha == -1 || endLinha == -1) {
            System.out.println("Resultado: ERRO — C ou S não encontrados no tabuleiro.");
            System.out.println();
            return;
        }

        int resultado = bfs(tab, startLinha, startCol, endLinha, endCol);

        if (resultado == -1) {
            System.out.println("Resultado: IMPOSSÍVEL");
        } else {
            System.out.println("Resultado: " + resultado + " movimento(s)");
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║       O  C A V A L O  P E R D I D O      ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();

        if (args.length == 0) {
            // --- Tabuleiro de exemplo do enunciado (simplificado para teste) ---
            System.out.println("Nenhum arquivo fornecido. Usando tabuleiro de exemplo...");
            System.out.println();

            // Exemplo do enunciado: C chega em S em 10 movimentos
            // Tabuleiro 6x6 com movimentos de cavalo padrão
            char[][] exemploSimples = {
                {'.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'S', '.', '.'},
                {'.', '.', '.', '.', '.', '.'},
                {'.', '.', 'C', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'}
            };
            resolverTabuleiro(exemploSimples, "Exemplo simples");

            // Exemplo com obstáculos
            char[][] comObstaculos = {
                {'.', 'x', '.', '.', '.'},
                {'x', '.', 'x', '.', '.'},
                {'.', '.', 'C', 'x', '.'},
                {'.', 'x', '.', '.', 'x'},
                {'.', '.', '.', 'S', '.'}
            };
            resolverTabuleiro(comObstaculos, "Exemplo com obstáculos");

            // Exemplo impossível
            char[][] impossivel = {
                {'C', '.', '.'},
                {'.', '.', '.'},
                {'.', '.', 'S'}
            };
            resolverTabuleiro(impossivel, "Tabuleiro 3x3 (geralmente impossível p/ cavalo)");

        } else {
            // Lê cada arquivo passado como argumento
            for (String arquivo : args) {
                try {
                    char[][] tab = lerTabuleiro(arquivo);
                    if (tab == null) {
                        System.out.println("ERRO: arquivo vazio: " + arquivo);
                    } else {
                        resolverTabuleiro(tab, arquivo);
                    }
                } catch (IOException e) {
                    System.out.println("ERRO ao ler arquivo: " + arquivo);
                    System.out.println("  " + e.getMessage());
                }
            }
        }
    }
}
