// RA: 2678454
// Nome: José Gilson Cavalcante Filho

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

// ==========================================================================
// Classe de Controle: ControleEntrada
// ==========================================================================
// Cuida da ENTRADA de dados (usa a classe utilitaria Leitura) e das mensagens basicas de SAIDA
// (menu, cabecalho, mensagens). Converte erros de digitacao em EntradaInvalidaException.
// ==========================================================================
public class ControleEntrada{

    private boolean encerrada;  // true quando a entrada acabou (fim do teclado): evita laco infinito

    /** Construtor padrao. */
    public ControleEntrada(){
        this.encerrada = false;
    }

    /** Informa se a entrada acabou. */
    public boolean isEncerrada(){
        return this.encerrada;
    }

    /** Forca a saida do console em UTF-8 (corrige acentos no Windows). */
    public void configurarSaida(){
        try{
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch(UnsupportedEncodingException e){
            System.out.println("Aviso: nao foi possivel forcar UTF-8 na saida.");
        }
    }

    /** Le uma linha de texto (sem espacos nas pontas). Lanca excecao se a entrada acabou. */
    //Sobrecarga
    public String lerTexto() throws EntradaInvalidaException{
        try{
            return Leitura.leString().trim();
        } catch(EntradaInvalidaException e){
            this.encerrada = true;
            throw e;
        }
    }

    /** Mostra a mensagem (prompt) e le uma linha de texto. */
    //Sobrecarga
    public String lerTexto(String mensagem) throws EntradaInvalidaException{
        System.out.print(mensagem);
        return lerTexto();
    }

    /** Le uma linha e converte para int. Lanca excecao se nao for um inteiro. */
    //Sobrecarga
    public int lerInteiro() throws EntradaInvalidaException{
        String texto = lerTexto();
        try{
            return Integer.parseInt(texto);
        } catch(NumberFormatException e){ // Excecao nativa inevitavel: texto nao numerico
            throw new EntradaInvalidaException("Entrada invalida: \"" + texto + "\" nao e um numero inteiro.");
        }
    }

    /** Mostra a mensagem (prompt) e le um inteiro. */
    //Sobrecarga
    public int lerInteiro(String mensagem) throws EntradaInvalidaException{
        System.out.print(mensagem);
        return lerInteiro();
    }

    /** Le uma linha e converte para double (use ponto: 199.90). Lanca excecao se nao for um numero. */
    //Sobrecarga
    public double lerDouble() throws EntradaInvalidaException{
        String texto = lerTexto();
        try{
            double valor = Double.parseDouble(texto);
            if(Double.isNaN(valor) || Double.isInfinite(valor)){
                throw new EntradaInvalidaException("Entrada invalida: \"" + texto + "\" nao e um numero decimal.");
            }
            return valor;
        } catch(NumberFormatException e){ // Excecao nativa inevitavel: texto nao numerico
            throw new EntradaInvalidaException("Entrada invalida: \"" + texto + "\" nao e um numero decimal (use ponto, ex: 199.90).");
        }
    }

    /** Mostra a mensagem (prompt) e le um double. */
    //Sobrecarga
    public double lerDouble(String mensagem) throws EntradaInvalidaException{
        System.out.print(mensagem);
        return lerDouble();
    }

    /** Valida se a opcao esta entre minimo e maximo. Lanca excecao se estiver fora do menu. */
    public int converterOpcao(int opcao, int minimo, int maximo) throws EntradaInvalidaException{
        if(opcao < minimo || opcao > maximo){
            throw new EntradaInvalidaException("Opcao invalida: o numero " + opcao + " nao esta no menu. Digite de " + minimo + " a " + maximo + ".");
        }
        return opcao;
    }

    /** Pergunta sim/nao e repete ate o usuario responder S (true) ou N (false). */
    public boolean perguntaSimNao(String pergunta) throws EntradaInvalidaException{
        while(true){
            String resposta = lerTexto("\n" + pergunta + " (String: S ou N): ");
            if(resposta.equalsIgnoreCase("S")){
                return true;
            }
            if(resposta.equalsIgnoreCase("N")){
                return false;
            }
            System.out.println("[!] Resposta invalida. Digite S para sim ou N para nao.");
        }
    }

    /** Mostra uma mensagem em uma linha. */
    public void saiMensagem(String mensagem){
        System.out.println(mensagem);
    }

    /** Mostra um titulo de secao entre linhas decorativas. */
    public void saiCabecalho(String texto){
        System.out.println("\n==================================================");
        System.out.println(texto);
        System.out.println("==================================================");
    }

    /** Mostra o menu principal (ENTRADA DE DADOS e CONSULTAS). */
    public void mostrarMenu(){
        System.out.println("\n==================================================");
        System.out.println("                 SISTEMA DE TIMES");
        System.out.println("==================================================");
        System.out.println("--- ENTRADA DE DADOS ---");
        System.out.println(" 1 - Cadastrar time (comum, clube, selecao ou amador)");
        System.out.println(" 2 - Definir rival de um time");
        System.out.println(" 3 - Cadastrar torcedor");
        System.out.println(" 4 - Definir ou alterar o time de um torcedor");
        System.out.println(" 5 - Cadastrar camisa");
        System.out.println(" 6 - Registrar venda de camisa");
        System.out.println("--- CONSULTAS ---");
        System.out.println(" 7 - Listar times");
        System.out.println(" 8 - Pesquisar time por codigo");
        System.out.println(" 9 - Listar torcedores");
        System.out.println("10 - Listar camisas");
        System.out.println("11 - Listar vendas");
        System.out.println("12 - Consultar compras de um torcedor");
        System.out.println(" 0 - Sair");
    }

    /** Fecha a leitura (chamado uma unica vez, no fim do programa). */
    public void fechar(){
        try{
            Leitura.fechar();
        } catch(EntradaInvalidaException e){
            System.out.println(e.getMessage());
        }
    }

}
