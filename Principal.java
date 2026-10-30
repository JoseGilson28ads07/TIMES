// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe Principal: contem o metodo main e o MENU com switch/case.
// ==========================================================================
public class Principal{

    private static final int OPCAO_MAXIMA = 12;

    private static ControleEntrada entrada = new ControleEntrada();
    private static ControleTimes controleTimes = new ControleTimes();
    private static ControleTorcedores controleTorcedores = new ControleTorcedores();
    private static ControleVendas controleVendas = new ControleVendas();

    /** Chama a operacao escolhida no menu (cada case so CHAMA um metodo do Controle) e trata os erros. */
    private static void executar(int opcao){
        try{
            switch(opcao){
                // ENTRADA DE DADOS
                case 1: controleTimes.cadastrarTime(); break;
                case 2: controleTimes.definirRivalMenu(); break;
                case 3: controleTorcedores.cadastrarTorcedor(); break;
                case 4: controleTorcedores.definirTimeDoTorcedor(); break;
                case 5: controleVendas.cadastrarCamisa(); break;
                case 6: controleVendas.registrarVenda(); break;
                // CONSULTAS
                case 7: controleTimes.listarTimes(); break;
                case 8: controleTimes.pesquisarTime(); break;
                case 9: controleTorcedores.listarTorcedores(); break;
                case 10: controleVendas.listarCamisas(); break;
                case 11: controleVendas.listarVendas(); break;
                case 12: controleVendas.consultarComprasDoTorcedor(); break;
            }
        } catch(TimeException e){ // Dado invalido, vetor cheio, time/torcedor/camisa inexistente, estoque...
            entrada.saiMensagem("\n[!] " + e.getMessage());
        } catch(EntradaInvalidaException e){ // Digitacao invalida ou entrada encerrada
            entrada.saiMensagem("\n[!] " + e.getMessage());
        }
    }

    /** Injeta as dependencias, carrega os dados de exemplo e repete o menu ate o usuario sair. */
    public static void main(String[] args){
        entrada.configurarSaida();
        controleTimes.setEntrada(entrada);
        controleTorcedores.setEntrada(entrada);
        controleTorcedores.setControleTimes(controleTimes);
        controleVendas.setEntrada(entrada);
        controleVendas.setControleTimes(controleTimes);
        controleVendas.setControleTorcedores(controleTorcedores);

        try{ // Os setters usados nos dados iniciais podem lancar TimeException
            controleTimes.carregarDadosIniciais();
            controleTorcedores.carregarDadosIniciais();
            controleVendas.carregarDadosIniciais();
        } catch(TimeException e){
            entrada.saiMensagem("Erro ao carregar os dados iniciais: " + e.getMessage());
            return;
        }

        entrada.saiMensagem("==================================================");
        entrada.saiMensagem("Ola! Bem-vindo ao Sistema de Times.");
        entrada.saiMensagem("==================================================");
        entrada.saiMensagem("Times, torcedores e venda de camisas de clubes para os torcedores.");
        entrada.saiMensagem("Ja existem " + controleTimes.getQtdTimes() + " times e " + controleTorcedores.getQtdTorcedores() + " torcedores cadastrados, alem de camisas e vendas de exemplo.");

        boolean continuar = true;
        while(continuar){
            entrada.mostrarMenu();
            int opcao = 0;
            try{
                opcao = entrada.converterOpcao(entrada.lerInteiro("\nDigite uma opcao (int): "), 0, OPCAO_MAXIMA);
            } catch(EntradaInvalidaException e){ // Letra, texto, numero fora do menu ou entrada encerrada
                entrada.saiMensagem("\n[!] " + e.getMessage());
                if(entrada.isEncerrada()){
                    break;
                }
                continue; // Volta ao menu sem perguntar "Deseja continuar?"
            }

            if(opcao == 0){
                continuar = false;
            } else {
                executar(opcao);
                if(entrada.isEncerrada()){ // Teclado encerrado durante a operacao: nao ha como continuar
                    break;
                }
                try{
                    continuar = entrada.perguntaSimNao("Deseja continuar?");
                } catch(EntradaInvalidaException e){
                    entrada.saiMensagem("\n[!] " + e.getMessage());
                    continuar = false;
                }
            }
        }

        entrada.saiMensagem("\n==================================================");
        entrada.saiMensagem("Obrigado por usar o Sistema de Times. Ate logo!");
        entrada.saiMensagem("==================================================");
        entrada.fechar();
    }

}
