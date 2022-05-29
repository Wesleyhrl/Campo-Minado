package campo_minado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import campo_minado.excecao.ExplosaoException;
import campo_minado.excecao.ValorIncorreto;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;
    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {

        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();

    }
    public List<Campo> getCampos() {
        return campos;
    }
    /**
     * Metodo para criação de campo de acordo com quantidade
     * dos atributos linhas e colunas
     */
    private void gerarCampos() {
        for (int l = 0; l < this.linhas; l++) {
            for (int c = 0; c < this.colunas; c++) {
                campos.add(new Campo(l, c));
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
        try {
            boolean verificador = false;
            int cont = 0;
        for (int l = 0; l < this.linhas; l++) {
            for (int c = 0; c < this.colunas; c++) {
                if(campos.get(cont).getLinha() == linha && campos.get(cont).getColuna() == coluna){
                    verificador = campos.get(cont).abrir();
                }
                cont++;
            }
        }
        if(verificador == false){
            throw new ValorIncorreto();
        }
        } catch (ExplosaoException e) {
            campos.forEach(c -> c.setAberto(true)); // abrir todos campos
            throw e;
        }
        
    }
    /**
     * Metodo que percorrer lista de campos verificando se parametros recebidos batem com valor 
     * linhas e coluna do campo e caso encontrar ele marca campo atual.
     * @see {@code Campo.alterarMarcacao())}
     * @param linha
     * @param coluna
     */
    public void marcar(int linha ,int coluna){
        boolean verificador = false;
        int cont = 0;
        for (int l = 0; l < this.linhas; l++) {
            for (int c = 0; c < this.colunas; c++) {
                if(campos.get(cont).getLinha() == linha && campos.get(cont).getColuna() == coluna){
                    campos.get(cont).alterarMarcacao();
                    verificador = true;
                }
                cont++;
            }
        }
        if(verificador == false){
            throw new ValorIncorreto();
        }
    }

    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < this.colunas; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");
        int cont = 0;
        for (int l = 0; l < this.linhas; l++) {
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < this.colunas; c++){
                sb.append(" ");
                sb.append(campos.get(cont));
                cont++;
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }


}
