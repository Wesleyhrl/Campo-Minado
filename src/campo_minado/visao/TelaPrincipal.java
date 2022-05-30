package campo_minado.visao;

import javax.swing.JFrame;

import campo_minado.modelo.Tabuleiro;
/**
 * @author Wesley Henrique de Lima {@code Wesleyhrl} <p>{@link https://github.com/Wesleyhrl}
 * @since 30/05/2022 
 * @version 2.0 Swing Desktop
 * @category <p>Projeto - Campo Minado - Swing Desktop.
 * <p>É um jogo onde usuario tem como objectivo revelar um campo de minas sem que alguma seja detonada. além de tentar localizar minas e marca-las.
 */
public class TelaPrincipal extends JFrame{
    public TelaPrincipal(){
        Tabuleiro tabuleiro = new Tabuleiro(16, 30, 40);
        add(new PainelTabuleiro(tabuleiro));

        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }
    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
