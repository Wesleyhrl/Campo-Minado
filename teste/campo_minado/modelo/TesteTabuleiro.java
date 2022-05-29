package campo_minado.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/** Teste feito antes da versão final*/
public class TesteTabuleiro {
    private Tabuleiro tab = new Tabuleiro(3, 3, 2);
    @Test
    void testToString(){
        String conteudo = tab.toString();
        
        conteudo = conteudo.replace(" ","");
        assertEquals("012\n0???\n1???\n2???\n",conteudo );
    }
    @Test
    void testReiniciar(){
        Tabuleiro tab2 = new Tabuleiro(3, 3, 0);
        tab2.abrir(1, 1);
        tab2.reniciar();
        String conteudo = tab2.toString();
        conteudo = conteudo.replace(" ","");
        assertEquals("012\n0???\n1???\n2???\n",conteudo );   
    }
    @Test
    void testMarcar(){
        tab.marcar(0, 0);
        String conteudo = tab.toString();
        conteudo = conteudo.replace(" ","");
        assertEquals("012\n0x??\n1???\n2???\n",conteudo );   
    }
    
}
