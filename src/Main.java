import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    private static final int[][] MOVIMENTOS = {
        {-2, -1}, {-2, +1}, {-1, -2}, {-1, +2},
        {+1, -2}, {+1, +2}, {+2, -1}, {+2, +1}
    };

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Main caso1.txt caso2.txt ...");
            return;
        }

        for (String arquivo : args) {
            try {
                char[][] grade = lerArquivo(arquivo);
                if (grade == null) {
                    System.out.println("ERRO: arquivo vazio: " + arquivo);
                    continue;
                }

                int linhas = grade.length;
                int colunas = grade[0].length;
                int[] inicio = null;
                int[] saida = null;

                for (int i = 0; i < linhas; i++) {
                    for (int j = 0; j < colunas; j++) {
                        if (grade[i][j] == 'C') inicio = new int[]{i, j};
                        if (grade[i][j] == 'S') saida = new int[]{i, j};
                    }
                }

                if (inicio == null || saida == null) {
                    System.out.println("ERRO: falta C ou S em: " + arquivo);
                    continue;
                }

                int resultado = bfs(grade, linhas, colunas, inicio, saida);
                System.out.println(arquivo + ": " + (resultado == -1 ? "IMPOSSÍVEL" : resultado + " movimento(s)"));

            } catch (IOException e) {
                System.out.println("ERRO ao ler " + arquivo + ": " + e.getMessage());
            }
        }
    }

    private static int bfs(char[][] grade, int linhas, int colunas, int[] inicio, int[] saida) {
        if (inicio[0] == saida[0] && inicio[1] == saida[1]) return 0;

        boolean[][] visitado = new boolean[linhas][colunas];
        visitado[inicio[0]][inicio[1]] = true;

        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{inicio[0], inicio[1], 0});

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int l = atual[0], c = atual[1], movs = atual[2];

            for (int[] mov : MOVIMENTOS) {
                int nl = ((l + mov[0]) % linhas + linhas) % linhas;
                int nc = ((c + mov[1]) % colunas + colunas) % colunas;

                if (nl == saida[0] && nc == saida[1]) return movs + 1;

                if (!visitado[nl][nc] && grade[nl][nc] != 'x') {
                    visitado[nl][nc] = true;
                    fila.add(new int[]{nl, nc, movs + 1});
                }
            }
        }

        return -1;
    }

    private static char[][] lerArquivo(String caminho) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        }
        if (linhas.isEmpty()) return null;

        int inicio = 0;
        if (linhas.get(0).trim().matches("\\d+\\s+\\d+")) inicio = 1;

        int numLinhas = linhas.size() - inicio;
        int numColunas = linhas.get(inicio).length();
        char[][] matriz = new char[numLinhas][numColunas];

        for (int i = 0; i < numLinhas; i++) {
            String row = linhas.get(i + inicio);
            for (int j = 0; j < numColunas; j++) {
                matriz[i][j] = (j < row.length()) ? row.charAt(j) : '.';
            }
        }
        return matriz;
    }
}
