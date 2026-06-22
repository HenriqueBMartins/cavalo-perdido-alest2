# 🎤 Roteiro de Apresentação: O Cavalo Perdido (ALEST II) - Versão OOP

Este roteiro explica a nova versão do código, que agora está toda separada em **Orientação a Objetos**. Você deve começar a apresentação mostrando a estrutura dos pacotes (pastas), que mostra muito profissionalismo!

---

## 1. Introdução e Arquitetura do Projeto
*   **O que fizemos:** O código resolve o problema do "Cavalo Perdido" usando a **Busca em Largura (BFS)**.
*   **A Arquitetura:** Comece mostrando a IDE. Diga: *"Decidimos não fazer tudo em uma classe só. Separamos as responsabilidades aplicando princípios de Engenharia de Software."* Mostre as pastas:
    *   `modelo/`: Onde ficam os dados e regras do tabuleiro.
    *   `algoritmo/`: Onde fica a inteligência (BFS).
    *   `utils/`: Onde fica o trabalho "sujo" de ler o arquivo de texto.

---

## 2. A Classe `Tabuleiro` (O Mundo do Jogo)

Abra a classe `src/modelo/Tabuleiro.java`.
*   **Explicação:** *"Em vez de deixar a matriz solta no código, criamos o objeto `Tabuleiro`. Ele guarda a matriz (`char[][]`) e o tamanho."*
*   **O Pulo do Gato (O Toroidal):** Mostre o método `calcularPosicaoToroidal`.
    *   Diga: *"A melhor parte de separar em classes é o **encapsulamento**. A matemática feia do módulo duplo (`%`) que calcula se o cavalo deu a volta na borda do tabuleiro fica escondida aqui dentro. O algoritmo principal não precisa saber como o mundo é redondo, ele só pede a nova posição para o Tabuleiro."*

---

## 3. A Classe `Posicao` (O Nó do Grafo)

Abra a classe `src/modelo/Posicao.java`.
*   **Explicação:** *"Essa classe representa um estado do cavalo durante a busca. Ela guarda a `linha` e `coluna` atuais, mas **também guarda os movimentos gastos até ali**."*
*   *"Sempre que o cavalo pula para uma casa válida, instanciamos uma nova `Posicao` e colocamos na fila."*

---

## 4. A Inteligência: `ResolvedorBFS`

Abra a classe `src/algoritmo/ResolvedorBFS.java`. Mostre o método principal `resolver`.

*   **A Matriz de Visitados:** Mostre o `boolean[][] visitado`. Diga que isso impede o cavalo de andar em círculos eternamente.
*   **A Fila (Queue):** É a estrutura de dados base da BFS. Colocamos a posição inicial nela.
*   **O Laço (While):** Enquanto a fila tiver posições para avaliar, o cavalo tenta seus 8 movimentos (que estão definidos na constante `MOVIMENTOS`).
*   **A Delegação:** Mostre a linha `tabuleiro.calcularPosicaoToroidal(...)`. Reforce: *"Viu como o código do BFS fica limpo? Ele pede para o Tabuleiro calcular a próxima casa. Se a casa for a saída, achamos o caminho mais curto! Se não for, e for válida, colocamos na fila."*

---

## 5. A Classe Principal: `CavaloPerdido.java` (Main)

Abra a classe `src/CavaloPerdido.java`.
*   **Explicação:** *"Graças à Orientação a Objetos, a nossa classe principal não tem quase nenhum 'if' ou 'while' complexo. Ela serve apenas como um maestro."*
*   Mostre os 3 passos simples que ela faz:
    1.  Pede pro `LeitorArquivo` ler o txt.
    2.  Pede pro `ResolvedorBFS` achar o caminho.
    3.  Imprime a resposta na tela.

---

## 6. Conclusão e Testes na Tela
*   **Por que BFS?** A BFS garante o menor caminho, pois explora em círculos de distância crescente. 
*   **Performance:** *"Apesar de ter várias classes, usamos referências leves, e a complexidade continua O(Linhas x Colunas)."*
*   **Rode no terminal:** `java -cp out CavaloPerdido testes/caso1.txt` para mostrar a resposta imediata. 
