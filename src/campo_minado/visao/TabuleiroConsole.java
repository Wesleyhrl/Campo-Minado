package campo_minado.visao;

import campo_minado.modelo.Tabuleiro;

import java.util.Scanner;

import campo_minado.excecao.*;

/** Classe para rodar o projeto em formato de Console */
public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner teclado = new Scanner(System.in);

    /**
     * Metodo Construtor para iniciar o jogo.
     * 
     * @see {@code executarJogo()}
     * @param tabuleiro
     */
    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        executarJogo();
    }

    /**
     * Metodo que criar um Loop para executar e reiniciar o jogo com uma
     * condição de saida digitada pelo usuario.
     * <p>
     * Além de tratar as exceções:
     * <p>
     * {@code SairException} {@code ValorIncorreto} {@code NumberFormatException}
     * 
     * @see {@code cicloJogo()}
     */
    private void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar) {
                cicloJogo();
                System.out.println("Outra partida? (S/n)");
                String resposta = teclado.nextLine();
                if ("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reniciar();
                }
            }

        } catch (SairException e) {
            System.out.println("Jogo Finalizado ;)");
        } catch (ValorIncorreto | NumberFormatException e) {
            System.out.println("Valor Digitado Incorretamente!!!!");
            executarJogo();
        } finally {
            teclado.close();
        }
    }

    /**
     * Metodo que realiza o clico do jogo de acordo com que usuario digita,Além
     * de tratar as exceções de erros digitados pelo usuario.
     * <p>
     * E tratando a exceção de explosão que indica perca do jogo.
     * {@code ExplosaoException}
     */
    private void cicloJogo() {
        try {
            while (!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro);
                String digitado = valorDigitado("Digite (Linha , Coluna):");
                String[] dividirValor = valorCorreto(digitado);
                int l = Integer.parseInt(dividirValor[0]);
                int c = Integer.parseInt(dividirValor[1]);
                digitado = valorDigitado("1 - Abrir ou 2 - Desmarcar/Marcar");
                if ("1".equalsIgnoreCase(digitado)) {
                    tabuleiro.abrir(l, c);
                } else if ("2".equalsIgnoreCase(digitado)) {
                    tabuleiro.marcar(l, c);
                } else {
                    throw new ValorIncorreto();
                }
            }
            System.out.println(tabuleiro);
            System.out.println("Parabéns!!! Você ganhou :)");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro);
            System.out.println("Você perdeu :(");
        }

    }

    /**
     * Metodo que recebe o texto imprimindo para o console e
     * verifica o valor recebido do teclado,Caso seja {@code sair} lança a
     * exceção:{@code SairException}
     * 
     * @param texto
     * @return valor recebido do teclado
     */
    private String valorDigitado(String texto) {
        System.out.println(texto);
        String digitado = teclado.nextLine();
        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }
        return digitado;
    }

    /**
     * Metodo que trata falhas e verifica se valor digitado pelo usuario esta
     * correto,
     * caso não esteja lança exceção {@code ValorIncorreto}
     * 
     * @param valor
     * @return Um vetor com os valores separados por " , ".
     */
    private String[] valorCorreto(String valor) {
        String aux = valor.replace(" ", "");
        String[] dividirValor = aux.split(",");
        if (dividirValor.length == 2) {
            return dividirValor;
        } else {
            throw new ValorIncorreto();
        }

    }

}
