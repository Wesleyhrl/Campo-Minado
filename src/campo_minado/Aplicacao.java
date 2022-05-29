package campo_minado;

import campo_minado.modelo.Tabuleiro;
import campo_minado.visao.TabuleiroConsole;

/**
 * @author Wesley Henrique de Lima {@code Wesleyhrl}
 *         <p>
 *         {@link https://github.com/Wesleyhrl}
 * @since 28/05/2022
 * @version 1.0
 * 
 * @category <p>Projeto - Campo Minado.
 * É um jogo onde usuario tenta abrir campos que não contém minas além de tentar localizar minas e marca-las.
 */
public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 5);
        new TabuleiroConsole(tabuleiro);
    }
}
