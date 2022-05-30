package campo_minado.visao;
import java.awt.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import campo_minado.modelo.Tabuleiro;
//JPanel agrupador de componentes
public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLinhas(),tabuleiro.getLinhas())); //GridLayout é definido por linhas e colunas
        tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
        JOptionPane.showMessageDialog(this, "São 40 Bombas Boa Sorte! :) \nClique OK para inciar o jogo");
        tabuleiro.registrarObservador(e -> {
           SwingUtilities.invokeLater(() ->{ //Rendezira depois que toda interface foi atualizada
                if(e.isGanhou()){
                    JOptionPane.showMessageDialog(this, "Ganhou Parabens!!! :)");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Perdeu!!! :( Continue Tentando ");
                }
                tabuleiro.reniciar();
            });
            
        });
    }
    
}
