package campo_minado.visao;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import campo_minado.modelo.Campo;
import campo_minado.modelo.CampoEvento;
import campo_minado.modelo.CampoObservador;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {
    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);
    private Campo campo;

    private ImageIcon imagem = new ImageIcon(getClass().getResource("bomba1.png"));

    public BotaoCampo(Campo campo) {
        this.campo = campo;
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.registrarObservadores(this);

    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento) {
            case ABRIR:
                aplicarAbrir();
                break;
            case MARCAR:
                aplicarMarcar();
                break;
            case EXPLODIR:
                aplicarExplodir();
                break;
            default:
                aplicarPadrao();
        }
        SwingUtilities.invokeLater(() -> { //Garantir a atualização dos botões
            repaint();
            validate();
        });

    }

    private void aplicarPadrao() {
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(BG_PADRAO);
        setText("");
        setIcon(null);

    }

    private void aplicarExplodir() {
        setBackground(BG_EXPLODIR);
        imagem.setImage(imagem.getImage().getScaledInstance(getWidth(), getHeight(),getWidth()));
        setIcon(imagem);
        

    }

    private void aplicarMarcar() {
        setBackground(BG_MARCAR);
        setText("X");
    }

    private void aplicarAbrir() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (campo.isMinado()) {
            
            imagem.setImage(imagem.getImage().getScaledInstance(getWidth(), getHeight(), getWidth()));
            setIcon(imagem);
            return;
            

        }

        setBackground(BG_PADRAO);

        switch ((int) campo.minasNaVizinhanca()) {
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = !campo.vizinhacaSegura() ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);
    }
    // Interface Eventos Mouse

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {// Botão Esquerdo
            campo.abrir();
        } else {
            campo.alterarMarcacao();
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

}
