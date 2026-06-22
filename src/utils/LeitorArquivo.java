package utils;

import modelo.Posicao;
import modelo.Tabuleiro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária focada apenas em I/O (leitura de arquivos).
 */
public class LeitorArquivo {

    public static class ResultadoLeitura {
        public Tabuleiro tabuleiro;
        public Posicao inicio;
    }

    /**
     * Lê o arquivo txt e monta os objetos Tabuleiro e Posicao inicial.
     */
    public static ResultadoLeitura ler(String caminho) throws IOException {
        List<String> linhasArquivo = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivo.add(linha);
            }
        }
        
        if (linhasArquivo.isEmpty()) return null;

        // Verifica se a primeira linha é o cabeçalho "LINHAS COLUNAS" e pula
        int inicioIdx = 0;
        if (linhasArquivo.get(0).trim().matches("\\d+\\s+\\d+")) {
            inicioIdx = 1;
        }

        int numLinhas = linhasArquivo.size() - inicioIdx;
        int numColunas = linhasArquivo.get(inicioIdx).length();
        char[][] matriz = new char[numLinhas][numColunas];
        
        ResultadoLeitura resultado = new ResultadoLeitura();

        for (int i = 0; i < numLinhas; i++) {
            String row = linhasArquivo.get(i + inicioIdx);
            for (int j = 0; j < numColunas; j++) {
                char celula = (j < row.length()) ? row.charAt(j) : '.';
                matriz[i][j] = celula;
                
                if (celula == 'C') {
                    resultado.inicio = new Posicao(i, j, 0);
                }
            }
        }

        Tabuleiro tab = new Tabuleiro(matriz);
        
        // Encontra a saída e configura no tabuleiro
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                if (matriz[i][j] == 'S') {
                    tab.setSaida(i, j);
                }
            }
        }
        
        resultado.tabuleiro = tab;
        return resultado;
    }
}
