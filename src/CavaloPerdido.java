import algoritmo.ResolvedorBFS;
import modelo.Posicao;
import modelo.Tabuleiro;
import utils.LeitorArquivo;

import java.io.IOException;

/**
 * Classe principal responsável por orquestrar a execução do programa.
 */
public class CavaloPerdido {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║  O CAVALO PERDIDO (Versão OOP)   ║");
        System.out.println("╚══════════════════════════════════╝\n");

        if (args.length == 0) {
            System.out.println("Por favor, passe o caminho dos arquivos .txt como argumento.");
            System.out.println("Exemplo: java CavaloPerdido testes/caso1.txt");
            return;
        }

        for (String arquivo : args) {
            try {
                // 1. Lê o arquivo
                LeitorArquivo.ResultadoLeitura leitura = LeitorArquivo.ler(arquivo);
                
                if (leitura == null || leitura.inicio == null) {
                    System.out.println("ERRO: Arquivo vazio ou sem posição inicial em: " + arquivo);
                    continue;
                }

                Tabuleiro tab = leitura.tabuleiro;
                Posicao inicio = leitura.inicio;

                System.out.println("=== " + arquivo + " ===");
                System.out.println("Dimensões: " + tab.getLinhas() + " x " + tab.getColunas());

                // 2. Resolve usando BFS
                int resultado = ResolvedorBFS.resolver(tab, inicio);

                // 3. Imprime resultado
                if (resultado == -1) {
                    System.out.println("Resultado: IMPOSSÍVEL\n");
                } else {
                    System.out.println("Resultado: " + resultado + " movimento(s)\n");
                }

            } catch (IOException e) {
                System.out.println("ERRO ao ler arquivo: " + arquivo);
                System.out.println("  " + e.getMessage());
            }
        }
    }
}
