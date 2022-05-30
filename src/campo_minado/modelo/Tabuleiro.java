package campo_minado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;



public class Tabuleiro implements CampoObservador {

    private final int linhas;
    private final int colunas;
    private final int minas;
    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {

        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();

    }
    // Get and Set
    public void paraCadaCampo(Consumer<Campo> funcao){
        campos.forEach(funcao);
    }
    public int getLinhas(){
        return this.linhas;
    }
    public int getColunas(){
        return this.colunas;
    }

    public List<Campo> getCampos() {
        return campos;
    }
    //Observador
    public void registrarObservador(Consumer<ResultadoEvento> obsevador){
        observadores.add(obsevador);
    }
    private void notificarObservadores(Boolean resultado){
        observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
    }

    //Evento
    public void eventoOcorreu(Campo campo,CampoEvento evento){
        if(evento == CampoEvento.EXPLODIR){
            mostrarMinas();
            notificarObservadores(false);
        }else if(objetivoAlcancado()){
            notificarObservadores(true);
        }
    }



    /**
     * Metodo para criação de campo de acordo com quantidade
     * dos atributos linhas e colunas
     */
    private void gerarCampos() {
        for (int l = 0; l < this.linhas; l++) {
            for (int c = 0; c < this.colunas; c++) {
                Campo campo = new Campo(l, c);
                campo.registrarObservadores(this);
                campos.add(campo);
            }
        }

    }
    /**
     * Metodo para percorrer lista de campos associando cada campo com vizinho.
     */
    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adiconarVizinho(c2);
            }
        }
    }
    /** Metedo para gerar minas de maneira aletoria percorrendo a lista de campos */
    private void sortearMinas() {
        long minasArmadas = 0;
        Random random = new Random();
        while(minasArmadas < this.minas){
            
            campos.get(random.nextInt(campos.size())).minar();
            minasArmadas++;
        }
    }
    /**
     * Metodo que verifica se todos os campos da lista estão com objetivo alcançado.
     * @see {@code Campo.objetivoAlcancado()}
     * 
     * 
     * @return {@code true} para caso afirmativo.<br>
     * {@code false} para caso negativo
     */
    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }
    /**
     * Metodo que percorre toda campos da lista e reinicia usando 
     * metodo da classe <b>Campo</b>.
     * @see {@code Campo.reiniciar()}
     */
    public void reniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }
    /**
     * Metodo que percorrer lista de campos verificando se parametros recebidos batem com valor 
     * linhas e coluna do campo e caso encontrar ele abre campo atual.
     * <p>Caso acha um exceção {@code ExplosaoException} este metodo iria abrir todos campos e lançar exceção novamente.
     * @see {@code Campo.abrir()}
     * @param linha
     * @param coluna
     */
    public void abrir(int linha ,int coluna){
        
            int cont = 0;
        for (int l = 0; l < this.linhas; l++) {
            for (int c = 0; c < this.colunas; c++) {
                if(campos.get(cont).getLinha() == linha && campos.get(cont).getColuna() == coluna){
                    campos.get(cont).abrir();
                }
                cont++;
            }
        }
        
        
        
    }
    private void mostrarMinas(){
        campos.stream().filter(c -> c.isMinado()).filter(c -> !c.isMarcado()).forEach(c -> c.setAberto(true));
    }
    /**
     * Metodo que percorrer lista de campos verificando se parametros recebidos batem com valor 
     * linhas e coluna do campo e caso encontrar ele marca campo atual.
     * @see {@code Campo.alterarMarcacao())}
     * @param linha
     * @param coluna
     */
    public void marcar(int linha ,int coluna){
        int cont = 0;
        for (int l = 0; l < this.linhas; l++) {
            for (int c = 0; c < this.colunas; c++) {
                if(campos.get(cont).getLinha() == linha && campos.get(cont).getColuna() == coluna){
                    campos.get(cont).alterarMarcacao();
                }
                cont++;
            }
        }
        
    }

    


}
