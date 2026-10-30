// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe de Controle: ControleTorcedores
// ==========================================================================
// Guarda o VETOR de torcedores e faz tudo o que envolve Torcedor: cadastro, definicao do time
// e listagem. TODA entrada e TODA saida de torcedores ficam aqui.
// ASSOCIACAO: cada Torcedor guarda uma referencia a um objeto Times real (do vetor do ControleTimes).
// INJECAO POR SETTER: ControleEntrada e ControleTimes sao recebidos por setters (nunca por construtor).
// ==========================================================================
public class ControleTorcedores{

    private static final int MAX_TORCEDORES = 20;

    private Torcedor[] torcedores;        // Posicoes nao usadas ficam null
    private int qtdTorcedores;
    private int proximoId;                // Proximo id automatico
    private ControleEntrada entrada;
    private ControleTimes controleTimes;

    /** Construtor padrao. */
    public ControleTorcedores(){
        this.torcedores = new Torcedor[MAX_TORCEDORES];
        this.qtdTorcedores = 0;
        this.proximoId = 1;
        this.entrada = null;
        this.controleTimes = null;
    }

    /** Injeta o controle de entrada. */
    public void setEntrada(ControleEntrada entrada){
        this.entrada = entrada;
    }

    /** Injeta o controle de times. */
    public void setControleTimes(ControleTimes controleTimes){
        this.controleTimes = controleTimes;
    }

    /** Devolve quantos torcedores estao cadastrados. */
    public int getQtdTorcedores(){
        return this.qtdTorcedores;
    }

    // ---- Pesquisa e cadastro no vetor ----

    /** Procura um torcedor pelo id. Lanca TimeException se nao encontrar (usado tambem pelo ControleVendas). */
    public Torcedor buscar(int id) throws TimeException{
        for(int i = 0; i < this.qtdTorcedores; i++){
            if(this.torcedores[i].getId() == id){
                return this.torcedores[i];
            }
        }
        throw new TimeException("Nenhum torcedor foi encontrado com o id " + id + ".");
    }

    /** Coloca um torcedor no vetor. Lanca TimeException se for null ou se o vetor estiver cheio. */
    private void adicionarTorcedor(Torcedor t) throws TimeException{
        if(t == null){
            throw new TimeException("Nao e possivel cadastrar um torcedor inexistente (null).");
        }
        if(this.qtdTorcedores >= this.torcedores.length){
            throw new TimeException("Nao ha mais espaco para cadastrar torcedores (limite: " + this.torcedores.length + ").");
        }
        this.torcedores[this.qtdTorcedores] = t;
        this.qtdTorcedores++;
    }

    /** Monta um torcedor so com setters e guarda no vetor (usado pelos dados iniciais). */
    private void criarTorcedor(String nome, String email, Times time) throws TimeException{
        Torcedor t = new Torcedor();
        t.setId(this.proximoId);
        t.setNome(nome);
        t.setEmail(email);
        t.setTime(time);
        adicionarTorcedor(t);
        this.proximoId++;
    }

    // ---- Saida ----

    /** Imprime UM torcedor (texto montado por Torcedor.toString()). */
    //Sobrecarga
    private void saiDados(Torcedor t){
        entrada.saiMensagem("  " + t.toString());
    }

    /** Imprime os primeiros "qtd" torcedores de um vetor (ou um aviso se nao houver nenhum). */
    //Sobrecarga
    private void saiDados(Torcedor[] vet, int qtd){
        if(qtd == 0){
            entrada.saiMensagem("  (nenhum torcedor cadastrado)");
            return;
        }
        for(int i = 0; i < qtd; i++){
            saiDados(vet[i]);
        }
    }

    /** Mostra os torcedores e pergunta o id de um (usado tambem pelo ControleVendas). */
    public Torcedor escolherTorcedor() throws TimeException, EntradaInvalidaException{
        if(this.qtdTorcedores == 0){
            throw new TimeException("Nenhum torcedor cadastrado. Use a opcao 3 primeiro.");
        }
        entrada.saiMensagem("Torcedores cadastrados:");
        saiDados(this.torcedores, this.qtdTorcedores);
        return buscar(entrada.lerInteiro("\nId do torcedor (int): "));
    }

    // ---- Dados iniciais ----

    /** Cria 3 torcedores de exemplo (o time vem do ControleTimes). */
    public void carregarDadosIniciais() throws TimeException{
        Times palmeiras = controleTimes.buscar(1);
        Times brasil = controleTimes.buscar(6);
        criarTorcedor("Ana Souza", "ana@email.com", palmeiras);
        criarTorcedor("Bruno Lima", "bruno@email.com", palmeiras); // Mesmo Palmeiras: varios torcedores, um time
        criarTorcedor("Carla Dias", "carla@email.com", brasil);
    }

    // ---- Entrada de dados ----

    /** Define (ou troca) o time do torcedor. Lanca TimeException se for o mesmo time de agora. */
    private void definirTime(Torcedor t) throws TimeException, EntradaInvalidaException{
        Times novo = controleTimes.escolherTime("\nCodigo do time (int): ");
        if(t.getTime() == novo){
            throw new TimeException(t.getNome() + " ja e torcedor do " + novo.getNome() + ".");
        }
        t.setTime(novo);
        entrada.saiMensagem("[OK] " + t.getNome() + " agora e torcedor do " + t.getTime().getNome() + " (" + t.getTime().getTipo() + ")."); //Reflexividade
    }

    /** Opcao 3: cadastra um torcedor e, se o usuario quiser, ja define o time dele. */
    public void cadastrarTorcedor() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("CADASTRO DE TORCEDOR");
        if(this.qtdTorcedores >= this.torcedores.length){
            throw new TimeException("Nao ha mais espaco para cadastrar torcedores (limite: " + this.torcedores.length + ").");
        }
        Torcedor novo = new Torcedor();
        novo.setId(this.proximoId);
        novo.setNome(entrada.lerTexto("Nome (String): "));
        novo.setEmail(entrada.lerTexto("Email (String, ex: nome@dominio.com): "));
        adicionarTorcedor(novo);
        this.proximoId++;
        entrada.saiMensagem("\n[OK] Torcedor cadastrado com sucesso! Id: " + novo.getId());
        if(entrada.perguntaSimNao("Deseja definir o time de " + novo.getNome() + " agora?")){
            definirTime(novo); // Erro aqui nao desfaz o cadastro: o torcedor ja esta no vetor
        }
    }

    /** Opcao 4: define o time de um torcedor sem time ou troca o time de quem ja tem. */
    public void definirTimeDoTorcedor() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("DEFINIR OU ALTERAR O TIME DE UM TORCEDOR");
        definirTime(escolherTorcedor());
    }

    // ---- Consultas ----

    /** Opcao 9: lista os torcedores (cada um com o nome do seu time). */
    public void listarTorcedores(){
        entrada.saiCabecalho("LISTA DE TORCEDORES (" + this.qtdTorcedores + " cadastrados)");
        saiDados(this.torcedores, this.qtdTorcedores);
    }

}
