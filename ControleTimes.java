// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe de Controle: ControleTimes
// ==========================================================================
// Guarda o VETOR de times e faz tudo o que envolve Times: cadastro, pesquisa, listagem, rival e titulos.
// TODA entrada (via ControleEntrada) e TODA saida de times ficam aqui; as entidades continuam puras.
// GENERALIZACAO: o vetor e' do tipo-pai Times e guarda Times, Clube, SelecaoNacional e TimeAmador.
// Os dados de cada filha saem pelo toString() sobrescrito.
// INJECAO POR SETTER: o ControleEntrada e' recebido por setEntrada (nunca por construtor com parametros).
// ==========================================================================
public class ControleTimes{

    private static final int MAX_TIMES = 20;

    private Times[] times;            // Posicoes nao usadas ficam null
    private int qtdTimes;
    private ControleEntrada entrada;

    /** Construtor padrao. */
    public ControleTimes(){
        this.times = new Times[MAX_TIMES];
        this.qtdTimes = 0;
        this.entrada = null;
    }

    /** Injeta o controle de entrada. */
    public void setEntrada(ControleEntrada entrada){
        this.entrada = entrada;
    }

    /** Devolve quantos times estao cadastrados. */
    public int getQtdTimes(){
        return this.qtdTimes;
    }

    // ---- Pesquisa e cadastro no vetor ----

    /** Procura um time pelo codigo. Devolve o objeto ou null se nao achar. */
    private Times pesquisa(int codigo){
        for(int i = 0; i < this.qtdTimes; i++){
            if(this.times[i].getCodigo() == codigo){
                return this.times[i];
            }
        }
        return null;
    }

    /** Versao com excecao: lanca TimeException se o time nao existir (quem chama nunca recebe null). */
    public Times buscar(int codigo) throws TimeException{
        Times t = pesquisa(codigo);
        if(t == null){
            throw new TimeException("Nenhum time foi encontrado com o codigo " + codigo + ".");
        }
        return t;
    }

    /** Coloca um time no vetor. Lanca TimeException se for null ou se o vetor estiver cheio. */
    private void adicionarTime(Times t) throws TimeException{
        if(t == null){
            throw new TimeException("Nao e possivel cadastrar um time inexistente (null).");
        }
        if(this.qtdTimes >= this.times.length){
            throw new TimeException("Nao ha mais espaco para cadastrar times (limite: " + this.times.length + ").");
        }
        this.times[this.qtdTimes] = t; // Se for filha, o objeto sobe para o tipo-pai
        this.qtdTimes++;
    }

    /** Adiciona um titulo ao time. Lanca TimeException se o vetor de titulos estiver cheio ou se nome/quantidade forem invalidos. */
    private void addTitulo(Times t, String nomeTitulo, int quantidade) throws TimeException{
        int pos = t.getQtdTitulos();
        if(pos >= ITimes.LIMITE_TITULOS){
            throw new TimeException("Limite de " + ITimes.LIMITE_TITULOS + " titulos atingido para o " + t.getNome() + ".");
        }
        Titulos novo = new Titulos();
        novo.setNomeTitulo(nomeTitulo);  // Se nome ou quantidade forem invalidos, a excecao sai aqui
        novo.setQuantidade(quantidade);  // e o vetor e o contador do time nao sao alterados
        t.setTitulo(pos, novo);
        t.setQtdTitulos(pos + 1); // So avanca o contador se o titulo foi criado com sucesso
    }

    /** Soma a quantidade de todos os titulos do time (calculo no Controle, nao na entidade). */
    public int getTotalConquistas(Times t){
        int total = 0;
        for(int i = 0; i < t.getQtdTitulos(); i++){
            total += t.getTitulos()[i].getQuantidade();
        }
        return total;
    }

    /** Liga dois times como rivais NOS DOIS SENTIDOS (de a para b e de b para a). */
    private void ligarRivais(Times a, Times b) throws TimeException{
        a.setRival(b);
        b.setRival(a);
    }

    // ---- Saida ----

    /** Imprime os dados de UM time (cada objeto usa o toString() da sua classe real). */
    //Sobrecarga
    private void saiDados(Times t){
        entrada.saiMensagem(t.toString());
        if(t.getQtdTitulos() > 0){
            entrada.saiMensagem("TOTAL DE CONQUISTAS: " + getTotalConquistas(t));
        }
    }

    /** Imprime os dados dos primeiros "qtd" times de um vetor. */
    //Sobrecarga
    private void saiDados(Times[] vet, int qtd){
        for(int i = 0; i < qtd; i++){
            saiDados(vet[i]);
        }
    }

    /** Unica listagem de codigos: mostra "codigo - nome [tipo]" de todos os times. */
    private void mostrarCodigos(){
        entrada.saiMensagem("Times cadastrados:");
        for(int i = 0; i < this.qtdTimes; i++){
            entrada.saiMensagem("  " + this.times[i].getCodigo() + " - " + this.times[i].getNome() + " [" + this.times[i].getTipo() + "]");
        }
    }

    /** Mostra a lista de times e pergunta o codigo de um (usado tambem pelos outros Controles). */
    public Times escolherTime(String mensagem) throws TimeException, EntradaInvalidaException{
        mostrarCodigos();
        return buscar(entrada.lerInteiro(mensagem));
    }

    // ---- Dados iniciais ----

    /** Atribui por setters os dados comuns a todo time. */
    private void definirBasico(Times t, int codigo, String nome, String apelido) throws TimeException{
        t.setCodigo(codigo);
        t.setNome(nome);
        t.getApelido().setApelido(apelido); //Reflexividade
    }

    /** Cria os 8 times de exemplo (tudo por setters). */
    public void carregarDadosIniciais() throws TimeException{
        Clube palmeiras = new Clube();
        definirBasico(palmeiras, 1, "Palmeiras", "Verdao");
        palmeiras.setCidade("Sao Paulo");
        palmeiras.setAnoFundacao(1914);
        addTitulo(palmeiras, "Campeonato Brasileiro", 12);
        addTitulo(palmeiras, "Copa Libertadores", 3);

        Clube flamengo = new Clube();
        definirBasico(flamengo, 2, "Flamengo", "Mengao");
        flamengo.setCidade("Rio de Janeiro");
        flamengo.setAnoFundacao(1895);
        addTitulo(flamengo, "Campeonato Brasileiro", 8);
        addTitulo(flamengo, "Copa Libertadores", 3);

        Times gremio = new Times();
        definirBasico(gremio, 3, "Gremio", "Imortal Tricolor");
        addTitulo(gremio, "Copa Libertadores", 3);

        Times santos = new Times();
        definirBasico(santos, 4, "Santos", "Peixe");
        addTitulo(santos, "Campeonato Brasileiro", 8);

        Times saoPaulo = new Times();
        definirBasico(saoPaulo, 5, "Sao Paulo", "Tricolor Paulista");
        addTitulo(saoPaulo, "Campeonato Brasileiro", 6);

        SelecaoNacional brasil = new SelecaoNacional();
        definirBasico(brasil, 6, "Brasil", "Canarinho");
        brasil.setConfederacao("CONMEBOL");
        brasil.setTecnico("Carlo Ancelotti");
        addTitulo(brasil, "Copa do Mundo", 5);

        SelecaoNacional argentina = new SelecaoNacional();
        definirBasico(argentina, 7, "Argentina", "Albiceleste");
        argentina.setConfederacao("CONMEBOL");
        argentina.setTecnico("Lionel Scaloni");
        addTitulo(argentina, "Copa do Mundo", 3);

        TimeAmador vila = new TimeAmador();
        definirBasico(vila, 8, "Unidos da Vila", "Vilao");
        vila.setBairro("Vila Nova");
        vila.setQtdAtletas(22);

        ligarRivais(palmeiras, saoPaulo);
        ligarRivais(brasil, argentina);

        adicionarTime(palmeiras);
        adicionarTime(flamengo);
        adicionarTime(gremio);
        adicionarTime(santos);
        adicionarTime(saoPaulo);
        adicionarTime(brasil);
        adicionarTime(argentina);
        adicionarTime(vila);
    }

    // ---- Consultas ----

    /** Opcao 7: lista todos os times (cada um pelo toString() da sua classe real). */
    public void listarTimes(){
        entrada.saiCabecalho("LISTA DE TIMES (" + this.qtdTimes + " cadastrados)");
        saiDados(this.times, this.qtdTimes);
    }

    /** Opcao 8: mostra um time (titulos, rival e dados da filha vem do toString sobrescrito). */
    public void pesquisarTime() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("PESQUISA DE TIME");
        Times t = escolherTime("\nCodigo do time (int): ");
        entrada.saiMensagem("\n[OK] Time encontrado!");
        saiDados(t);
    }

    // ---- Entrada de dados ----

    /** Submenu de tipo de time. Repete ate o usuario digitar uma opcao valida (0 = cancelar). */
    private int escolherTipo() throws EntradaInvalidaException{
        while(true){
            entrada.saiMensagem("\nQue tipo de time deseja cadastrar?\n"
                              + "  1 - Time comum\n"
                              + "  2 - Clube\n"
                              + "  3 - Selecao nacional\n"
                              + "  4 - Time amador\n"
                              + "  0 - Cancelar e voltar ao menu principal");
            try{
                return entrada.converterOpcao(entrada.lerInteiro("Digite o tipo (int, 0 a 4): "), 0, 4);
            } catch(EntradaInvalidaException e){
                if(entrada.isEncerrada()){
                    throw e;
                }
                entrada.saiMensagem("\n[!] " + e.getMessage());
            }
        }
    }

    /** Le e atribui (por setters) os dados comuns a todo time: codigo, nome e apelido. */
    private void lerDadosComuns(Times novo) throws TimeException, EntradaInvalidaException{
        int codigo = entrada.lerInteiro("Codigo (int, maior que 0): ");
        if(pesquisa(codigo) != null){
            throw new TimeException("Ja existe um time com o codigo " + codigo + ". Cadastro cancelado.");
        }
        novo.setCodigo(codigo);
        novo.setNome(entrada.lerTexto("Nome (String): "));
        novo.getApelido().setApelido(entrada.lerTexto("Apelido (String): ")); //Reflexividade
    }

    /** Le os dados de um Clube (comuns + cidade e ano) e o devolve como Times. */
    private Times lerClube() throws TimeException, EntradaInvalidaException{
        Clube c = new Clube();
        lerDadosComuns(c);
        c.setCidade(entrada.lerTexto("Cidade (String): "));
        c.setAnoFundacao(entrada.lerInteiro("Ano de fundacao (int, aaaa): "));
        return c;
    }

    /** Le os dados de uma SelecaoNacional (comuns + confederacao e tecnico) e a devolve como Times. */
    private Times lerSelecao() throws TimeException, EntradaInvalidaException{
        SelecaoNacional s = new SelecaoNacional();
        lerDadosComuns(s);
        s.setConfederacao(entrada.lerTexto("Confederacao (String, ex: UEFA): "));
        s.setTecnico(entrada.lerTexto("Tecnico (String): "));
        return s;
    }

    /** Le os dados de um TimeAmador (comuns + bairro e atletas) e o devolve como Times. */
    private Times lerAmador() throws TimeException, EntradaInvalidaException{
        TimeAmador a = new TimeAmador();
        lerDadosComuns(a);
        a.setBairro(entrada.lerTexto("Bairro (String): "));
        a.setQtdAtletas(entrada.lerInteiro("Quantidade de atletas (int, maior que 0): "));
        return a;
    }

    /** Pergunta e cadastra titulos do time. Titulo invalido so avisa e o laco continua. */
    private void cadastrarTitulos(Times t) throws EntradaInvalidaException{
        while(t.getQtdTitulos() < ITimes.LIMITE_TITULOS && entrada.perguntaSimNao("Deseja cadastrar um titulo para o " + t.getNome() + "?")){
            try{
                addTitulo(t, entrada.lerTexto("Nome do titulo (String): "), entrada.lerInteiro("Quantidade (int, maior que 0): "));
                entrada.saiMensagem("[OK] Titulo cadastrado!");
            } catch(TimeException e){
                entrada.saiMensagem("[!] Titulo nao cadastrado: " + e.getMessage());
            } catch(EntradaInvalidaException e){
                if(entrada.isEncerrada()){
                    throw e;
                }
                entrada.saiMensagem("[!] Titulo nao cadastrado: " + e.getMessage());
            }
        }
        if(t.getQtdTitulos() >= ITimes.LIMITE_TITULOS){
            entrada.saiMensagem("[i] Limite de " + ITimes.LIMITE_TITULOS + " titulos atingido para o " + t.getNome() + ".");
        }
    }

    /** Opcao 1: cadastra Times, Clube, SelecaoNacional ou TimeAmador, com titulos e rival opcionais. */
    public void cadastrarTime() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("CADASTRO DE TIME");
        if(this.qtdTimes >= this.times.length){
            throw new TimeException("Nao ha mais espaco para cadastrar times (limite: " + this.times.length + ").");
        }
        int tipo = escolherTipo();
        if(tipo == 0){
            entrada.saiMensagem("\n[i] Cadastro cancelado.");
            return;
        }
        Times novo; // Variavel do tipo-pai: recebe Times, Clube, SelecaoNacional ou TimeAmador
        switch(tipo){
            case 2: novo = lerClube(); break;
            case 3: novo = lerSelecao(); break;
            case 4: novo = lerAmador(); break;
            default: novo = new Times(); lerDadosComuns(novo); break; // 1 = time comum
        }
        adicionarTime(novo);
        entrada.saiMensagem("\n[OK] Time cadastrado com sucesso!");
        cadastrarTitulos(novo);
        if(entrada.perguntaSimNao("Deseja definir um rival para o " + novo.getNome() + " agora?")){
            definirRival(novo); // Erro aqui nao desfaz o cadastro: o time ja esta no vetor
        }
        entrada.saiMensagem("\nFicha do time cadastrado:");
        saiDados(novo);
    }

    /** Liga o time a OUTRO como rival, nos dois sentidos. Rivalidades antigas dos dois sao desfeitas. */
    private void definirRival(Times t) throws TimeException, EntradaInvalidaException{
        Times rival = escolherTime("\nCodigo do rival do " + t.getNome() + " (int): ");
        if(rival == t){
            throw new TimeException("Rival invalido: um time nao pode ser rival de si mesmo.");
        }
        Times antigoDeT = t.getRival();
        Times antigoDoRival = rival.getRival();
        if(t.getRival() != null){
            t.getRival().setRival(null); //Reflexividade
        }
        if(rival.getRival() != null){
            rival.getRival().setRival(null); //Reflexividade
        }
        ligarRivais(t, rival);
        entrada.saiMensagem("[OK] Rivalidade definida nos dois sentidos: " + t.getNome() + " e " + rival.getNome() + ".");
        if(antigoDeT != null && antigoDeT != rival){
            entrada.saiMensagem("[i] " + antigoDeT.getNome() + " ficou sem rival (a rivalidade antiga foi desfeita).");
        }
        if(antigoDoRival != null && antigoDoRival != t){
            entrada.saiMensagem("[i] " + antigoDoRival.getNome() + " ficou sem rival (a rivalidade antiga foi desfeita).");
        }
    }

    /** Opcao 2: define o rival de um time ja cadastrado. */
    public void definirRivalMenu() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("DEFINIR RIVAL");
        definirRival(escolherTime("\nCodigo do time que tera um rival (int): "));
    }

}
