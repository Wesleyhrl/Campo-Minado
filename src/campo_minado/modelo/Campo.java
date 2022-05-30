package campo_minado.modelo;

import java.util.ArrayList;
import java.util.List;



public class Campo {
    private int linha;
    private int coluna;
    private boolean aberto;
    private boolean minado;
    private boolean marcado;

    private List<Campo> vizinho = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();


    

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;

    }
    //GET E SET

    void setAberto(boolean aberto){
        this.aberto = aberto;
        if(aberto){
            notificarObservadores(CampoEvento.ABRIR);
        }
    }
    public boolean isMarcado(){
        return this.marcado;
    }
    public boolean isAberto(){
        return this.aberto;
    }
    public boolean isMinado(){
        return this.minado;
    }

    public int getLinha() {
        return linha;
    }


    public void setLinha(int linha) {
        this.linha = linha;
    }


    public int getColuna() {
        return coluna;
    }


    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    //Observadores
    public void registrarObservadores(CampoObservador observador){
        observadores.add(observador);
    }
    private void notificarObservadores(CampoEvento evento){
        for (CampoObservador o : observadores) {
            o.eventoOcorreu(this, evento);
        }
    }
    

    /**
     * Método é para adicinar vizinhos a classe campo.
     * Este método faz diferença absoluta do valor da linha e coluna do Campo atual
     * com valor da linha e coluna do Campo recebido por parametro,
     * depois verifica se campo recebido por parametro esta ao seu lado ou se esta
     * em sua diagonal e adiona vizinho a lista de vizinhos do
     * campo atual.
     * 
     * 
     * @param vizinho
     * @return "true" para caso afirmativo de vizinho
     *         <p>
     *         "false" para caso negativo de vizinho
     * 
     */
    boolean adiconarVizinho(Campo vizinho) {
        boolean linhaDif = this.linha != vizinho.linha;
        boolean colunaDif = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDif && colunaDif;
        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int difGeral = deltaColuna + deltaLinha;
        if (difGeral == 1 && diagonal == false) {
            this.vizinho.add(vizinho);
            return true;

        } else if (difGeral == 2 && diagonal == true) {
            this.vizinho.add(vizinho);
            return true;

        }
        return false;
    }

    /**
     * Método que verifica se campo esta fechado para mudar status do campo para
     * marcado
     */
    public void alterarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
        }
        if(marcado){
            notificarObservadores(CampoEvento.MARCAR);
        }else{
            notificarObservadores(CampoEvento.DESMARCAR);
        }
    }

    /**
     * Metodo que verifica se os campo vizinhos estão sem de minas.
     * <p>
     * Ele percorre lista de vizinhos do campo e verifica se algum campo vizinho
     * esta com mina.
     * 
     * @return "true" para vizinhos livre de minas
     *         <p>
     *         "false" exite vizinho com mina
     */
    public boolean vizinhacaSegura() {
        for (Campo v : vizinho) {
            if (v.minado == true) {
                return false;
            }

        }
        return true;
    }

    /**
     * Metodo que vai definir o campo como aberto.
     * <p>Primeiro Verifica se campo esta fechado e não marcado e sem seguida define
     * campo atual para aberto e faz outra vericação se esta minado (caso minado ele
     * lança um exception) e faz outra verificação para chamando metodo "vizinhacaSegura()"
     * para abrir tambem os vizinho seguros.
     * 
     * @return "true" para caso afirmativo de abertura
     * <p> "false" para caso negativo de abertura
     */
    public boolean abrir() {
        if (!aberto && !marcado) {
            aberto = true;
            if (minado) {
                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }
            setAberto(true);

            if (vizinhacaSegura()) {
                vizinho.stream().forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }

    }
    /**Metodo para minar o campo */
    void minar(){
        this.minado = true;
    }
    /**
     * Metodo para verificar se objetivo do campo foi alcançado.
     * <p>existe duas formas de verificação:
     * <p>desvendado: caso campo foi aberto e não continha mina.
     * <p>protegido: caso campo foi marcado e continha mina.
     * @return "true" para caso afirmativo do objetivo.
     * <p> "false" para caso negativo do objetivo.
     */
    boolean objetivoAlcancado(){
        boolean desvendado = aberto && !minado;
        boolean protegido = marcado && minado;
        return desvendado || protegido;
    }
    /**
     * Metodo que verifica quantidade de minas sem campos vizinhos.
     * <p> Utiliza StremAPI para percorrer lista de vizinhos e verificar filtrando pela quantidade de minas.
     * @return quantidade de minas na vizinhança.
     */
    public long minasNaVizinhanca(){
        return vizinho.stream().filter(v -> v.minado).count();
    }
    /**
     * Metodo para reniciar o campo,
     * definindo os atributos de status para "false" 
     */
    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
        notificarObservadores(CampoEvento.REINICIAR);
    }
    




}
