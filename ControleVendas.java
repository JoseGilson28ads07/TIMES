// RA: 2678454
// Nome: José Gilson Cavalcante Filho

import java.util.Locale;

// ==========================================================================
// Classe de Controle: ControleVendas
// ==========================================================================
public class ControleVendas{

    private static final int MAX_CAMISAS = 20;
    private static final int MAX_VENDAS = 50;

    private Camisa[] camisas;            
    private int qtdCamisas;
    private int proximoCodigoCamisa;     
    private Venda[] vendas;               
    private int qtdVendas;
    private int proximoCodigoVenda;       
    private ControleEntrada entrada;
    private ControleTimes controleTimes;
    private ControleTorcedores controleTorcedores;

    /** Construtor padrao. */
    public ControleVendas(){
        this.camisas = new Camisa[MAX_CAMISAS];
        this.qtdCamisas = 0;
        this.proximoCodigoCamisa = 1;
        this.vendas = new Venda[MAX_VENDAS];
        this.qtdVendas = 0;
        this.proximoCodigoVenda = 1;
        this.entrada = null;
        this.controleTimes = null;
        this.controleTorcedores = null;
    }

    public void setEntrada(ControleEntrada entrada){
        this.entrada = entrada;
    }

    public void setControleTimes(ControleTimes controleTimes){
        this.controleTimes = controleTimes;
    }

    public void setControleTorcedores(ControleTorcedores controleTorcedores){
        this.controleTorcedores = controleTorcedores;
    }

    // ---- Regras de camisa e de venda ----

    private Camisa criarCamisa(Times clube, String modelo, String tamanho, double preco, int estoque) throws TimeException{
        if(this.qtdCamisas >= this.camisas.length){
            throw new TimeException("Nao ha mais espaco para cadastrar camisas (limite: " + this.camisas.length + ").");
        }
        Camisa c = new Camisa();
        c.setCodigo(this.proximoCodigoCamisa);
        c.setClube(clube);
        c.setModelo(modelo);
        c.setTamanho(tamanho);
        c.setPreco(preco);
        c.setEstoque(estoque);
        this.camisas[this.qtdCamisas] = c;
        this.qtdCamisas++;
        this.proximoCodigoCamisa++;
        return c;
    }

    /*
     Unico caminho de venda (usado pela opcao 6 e pelos dados iniciais):
	 */
     private Venda efetuarVenda(Torcedor torcedor, Camisa camisa, int quantidade, String data) throws TimeException{
        if(this.qtdVendas >= this.vendas.length){
            throw new TimeException("Nao ha mais espaco para registrar vendas (limite: " + this.vendas.length + ").");
        }
        if(quantidade > camisa.getEstoque()){
            throw new TimeException("Estoque insuficiente: restam " + camisa.getEstoque() + " unidade(s) da camisa " + camisa.getCodigo() + ".");
        }
        Venda v = new Venda();
        v.setCodigo(this.proximoCodigoVenda);
        v.setData(data);
        v.setTorcedor(torcedor);
        v.setCamisa(camisa);
        v.setQuantidade(quantidade);
        v.setValorTotal(camisa.getPreco() * quantidade); // Calculo no Controle
        camisa.setEstoque(camisa.getEstoque() - quantidade);
        this.vendas[this.qtdVendas] = v;
        this.qtdVendas++;
        this.proximoCodigoVenda++;
        return v;
    }

    private Camisa escolherCamisa() throws TimeException, EntradaInvalidaException{
        if(this.qtdCamisas == 0){
            throw new TimeException("Nenhuma camisa cadastrada. Use a opcao 5 primeiro.");
        }
        entrada.saiMensagem("Camisas cadastradas:");
        saiDados(this.camisas, this.qtdCamisas);
        int codigo = entrada.lerInteiro("\nCodigo da camisa (int): ");
        for(int i = 0; i < this.qtdCamisas; i++){
            if(this.camisas[i].getCodigo() == codigo){
                return this.camisas[i];
            }
        }
        throw new TimeException("Nenhuma camisa foi encontrada com o codigo " + codigo + ".");
    }

    // ---- Saida ----
	
    //Sobrecarga
    private void saiDados(Camisa c){
        entrada.saiMensagem("  " + c.toString());
    }

    //Sobrecarga
    private void saiDados(Camisa[] vet, int qtd){
        if(qtd == 0){
            entrada.saiMensagem("  (nenhuma camisa cadastrada)");
            return;
        }
        for(int i = 0; i < qtd; i++){
            saiDados(vet[i]);
        }
    }

    //Sobrecarga
    private void saiDados(Venda v){
        entrada.saiMensagem(v.toString());
    }

    //Sobrecarga
    private void saiDados(Venda[] vet, int qtd){
        if(qtd == 0){
            entrada.saiMensagem("  (nenhuma venda registrada)");
            return;
        }
        for(int i = 0; i < qtd; i++){
            saiDados(vet[i]);
        }
    }

    // ---- Dados iniciais ----

    public void carregarDadosIniciais() throws TimeException{
        Times palmeiras = controleTimes.buscar(1);
        Times flamengo = controleTimes.buscar(2);
        Camisa titularPalmeiras = criarCamisa(palmeiras, "Titular 2026", "M", 349.90, 15);
        criarCamisa(palmeiras, "Reserva 2026", "G", 329.90, 10);
        Camisa titularFlamengo = criarCamisa(flamengo, "Titular 2026", "GG", 349.90, 8);
        efetuarVenda(controleTorcedores.buscar(1), titularPalmeiras, 2, "10/10/2026");
        efetuarVenda(controleTorcedores.buscar(2), titularFlamengo, 1, "15/10/2026");
    }

    // ---- Entrada de dados ----

    /** Opcao 5: cadastra uma camisa (o clube escolhido precisa ser do tipo "Clube"). */
    public void cadastrarCamisa() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("CADASTRO DE CAMISA");
        if(this.qtdCamisas >= this.camisas.length){
            throw new TimeException("Nao ha mais espaco para cadastrar camisas (limite: " + this.camisas.length + ").");
        }
        Times clube = controleTimes.escolherTime("\nCodigo do clube (int): ");
        if(!(clube instanceof Clube)){ 
            throw new TimeException("Clube invalido: " + clube.getNome() + " e " + clube.getTipo() + ". Camisas so existem para Clube.");
        }
        String modelo = entrada.lerTexto("Modelo (String, ex: Titular 2026): ");
        String tamanho = entrada.lerTexto("Tamanho (String: P, M, G ou GG): ");
        double preco = entrada.lerDouble("Preco (double, use ponto: 199.90): ");
        int estoque = entrada.lerInteiro("Estoque (int, 0 ou mais): ");
        Camisa nova = criarCamisa(clube, modelo, tamanho, preco, estoque);
        entrada.saiMensagem("\n[OK] Camisa cadastrada com sucesso!");
        saiDados(nova);
    }

    /** Opcao 6: escolhe torcedor e camisa, le quantidade e data e registra a venda. */
    public void registrarVenda() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("REGISTRAR VENDA DE CAMISA");
        if(this.qtdVendas >= this.vendas.length){
            throw new TimeException("Nao ha mais espaco para registrar vendas (limite: " + this.vendas.length + ").");
        }
        Torcedor torcedor = controleTorcedores.escolherTorcedor();
        Camisa camisa = escolherCamisa();
        int quantidade = entrada.lerInteiro("Quantidade (int, maior que 0): ");
        String data = entrada.lerTexto("Data da venda (String, dd/mm/aaaa): ");
        Venda venda = efetuarVenda(torcedor, camisa, quantidade, data);
        entrada.saiMensagem("\n[OK] Venda registrada com sucesso!");
        saiDados(venda);
        entrada.saiMensagem("\nClube da venda: " + venda.getCamisa().getClube().getNome() + " | Estoque restante: " + venda.getCamisa().getEstoque()); //Reflexividade
    }

    // ---- Consultas ----

    /** Opcao 10: lista as camisas com o estoque atual. */
    public void listarCamisas(){
        entrada.saiCabecalho("LISTA DE CAMISAS (" + this.qtdCamisas + " cadastradas)");
        saiDados(this.camisas, this.qtdCamisas);
    }

    /** Opcao 11: lista as vendas registradas. */
    public void listarVendas(){
        entrada.saiCabecalho("LISTA DE VENDAS (" + this.qtdVendas + " registradas)");
        saiDados(this.vendas, this.qtdVendas);
    }

    /** Opcao 12: mostra as compras de um torcedor (itens) e o total gasto. */
    public void consultarComprasDoTorcedor() throws TimeException, EntradaInvalidaException{
        entrada.saiCabecalho("COMPRAS DE UM TORCEDOR");
        Torcedor torcedor = controleTorcedores.escolherTorcedor();
        int compras = 0;
        int pecas = 0;
        double totalGasto = 0;
        for(int i = 0; i < this.qtdVendas; i++){
            if(this.vendas[i].getTorcedor() == torcedor){
                saiDados(this.vendas[i]);
                compras++;
                pecas += this.vendas[i].getQuantidade();
                totalGasto += this.vendas[i].getValorTotal();
            }
        }
        if(compras == 0){
            entrada.saiMensagem("\n" + torcedor.getNome() + " ainda nao fez nenhuma compra.");
        } else {
            entrada.saiMensagem("\n" + torcedor.getNome() + ": " + compras + " compra(s), " + pecas + " camisa(s), total gasto R$ " + String.format(Locale.US, "%.2f", totalGasto));
        }
    }

}
