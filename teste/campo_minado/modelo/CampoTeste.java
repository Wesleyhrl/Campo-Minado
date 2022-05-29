package campo_minado.modelo;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import campo_minado.excecao.ExplosaoException;



public class CampoTeste {
    private Campo campo;
    @BeforeEach  //Para cada novo @Test inicializa este metodo
    void iniciarCampo(){
        campo = new Campo(3,3);
    }
    @Test

    void testeVizinhoReal(){
        Campo vizinho = new Campo(4, 4);
        
        assertTrue(campo.adiconarVizinho(vizinho));
    }
    @Test
    void testeNaoVizinho(){
        Campo vizinho = new Campo(1, 1);
        assertFalse(campo.adiconarVizinho(vizinho));
    }
    @Test
    void testeValorpadraoMarcado(){
        assertFalse(campo.isMarcado());
        
    }
    @Test
    void testeAlterarMarcacao(){
        campo.alterarMarcacao();
        assertTrue(campo.isMarcado());
    }
    @Test 
    void testealeterarMarcacaoNovamente(){
        campo.alterarMarcacao();
        campo.alterarMarcacao();
        assertFalse(campo.isMarcado());
    }
    @Test 
    void testeAbrirNaoMinadoNaoMarcado(){
       assertTrue( campo.abrir());
        
    }
    @Test
    void testeAbrirNaoMinadoMarcado(){
        campo.alterarMarcacao();
        assertTrue(campo.isMarcado());
        assertFalse(campo.abrir());
    }
    @Test
    void testeAbrirMinadoMarcado(){
        campo.alterarMarcacao();
        assertTrue(campo.isMarcado());
        campo.minar();
        assertFalse(campo.abrir());
        
    }
    @Test
    void testeAbrirMinadoNaoMarcado(){
        assertFalse(campo.isMarcado());
        campo.minar();
        // Teste para esperar que seja lançada um excecao, passando tipo de excecao indicando que uma classe e passando uma lambda.
        assertThrows(ExplosaoException.class,() ->{ 
            campo.abrir();
        }); 
    }
    @Test
    void testeAbrirComVizinho(){
        Campo vizinhoDoVizinho = new Campo(1,1);
        Campo vizinho = new Campo(2, 2);
        assertTrue(vizinho.adiconarVizinho(vizinhoDoVizinho));
        
        campo.adiconarVizinho(vizinho);
        campo.abrir();
        assertTrue(vizinho.isAberto() && vizinhoDoVizinho.isAberto()) ;
    }
    @Test
    void testeReiniciar(){
        campo.alterarMarcacao();
        campo.abrir();
        campo.minar();

        assertFalse(campo.isAberto() && campo.isMarcado() && campo.isMinado());
    }
    @Test 
    void testeMinasVizinhanca(){
        Campo vizinho1 = new Campo(3, 4);
        Campo vizinho2 = new Campo(4, 4);
        vizinho1.minar();
        vizinho2.minar();
        campo.adiconarVizinho(vizinho1);
        campo.adiconarVizinho(vizinho2);
        assertEquals(campo.minasNaVizinhanca(), 2);
    }
    @Test
    void testeObjetivo(){
        campo.abrir();
        assertTrue(campo.objetivoAlcancado());
    }
    @Test
    void testeObjetivoProtegido(){
        campo.alterarMarcacao();
        campo.minar();
        assertTrue(campo.objetivoAlcancado());
    }
    
}
