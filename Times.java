// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe entidade: Times
// ==========================================================================
// Representa o Tipo Abstrato de Dados "Time" (ex: Palmeiras, Flamengo...).
// HERANCA: Times e' a UNICA classe-mae. Clube, SelecaoNacional e TimeAmador sao as 3 filhas
// ("e' um tipo de" Times) e herdam codigo, nome, apelido, titulos e rival.
public class Times implements ITimes{

    private int codigo;
    private String nome;
    private Apelido apelido;     
    private Titulos[] titulos;   
    private int qtdTitulos;      
    private Times rival;         

    /** Construtor padrao: cria o time vazio; os valores reais entram por setters. */
    public Times(){
        this.codigo = 0;
        this.nome = "";
        this.apelido = new Apelido();
        this.titulos = new Titulos[LIMITE_TITULOS];
        this.qtdTitulos = 0;
        this.rival = null;
    }

    /** Define o codigo (maior que 0). */
    public void setCodigo(int codigo) throws TimeException{
        if(codigo <= 0){
            throw new TimeException("Codigo invalido: deve ser maior que 0.");
        }
        this.codigo = codigo;
    }

    /** Devolve o codigo. */
    public int getCodigo(){
        return this.codigo;
    }

    /** Define o nome (nao pode ser nulo nem vazio). */
    public void setNome(String nome) throws TimeException{
        if(nome == null || nome.trim().isEmpty()){
            throw new TimeException("Nome invalido: nao pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    /** Devolve o nome. */
    public String getNome(){
        return this.nome;
    }

    /** Devolve o objeto Apelido (para mudar o texto use getApelido().setApelido(...)). */
    public Apelido getApelido(){
        return this.apelido;
    }

    /** Devolve o vetor de titulos (percorrer so ate getQtdTitulos(); o resto e' null). */
    public Titulos[] getTitulos(){
        return this.titulos;
    }

    /** Guarda um titulo numa posicao do vetor (de 0 ate LIMITE_TITULOS - 1; null libera a posicao). */
    public void setTitulo(int pos, Titulos titulo) throws TimeException{
        if(pos < 0 || pos >= this.titulos.length){
            throw new TimeException("Posicao de titulo invalida: deve estar entre 0 e " + (this.titulos.length - 1) + ".");
        }
        this.titulos[pos] = titulo;
    }

    /** Define quantas posicoes do vetor de titulos estao em uso. */
    public void setQtdTitulos(int qtdTitulos) throws TimeException{
        if(qtdTitulos < 0 || qtdTitulos > this.titulos.length){
            throw new TimeException("Quantidade de titulos invalida: deve estar entre 0 e " + this.titulos.length + ".");
        }
        this.qtdTitulos = qtdTitulos;
    }

    /** Devolve quantos tipos de titulo foram cadastrados. */
    public int getQtdTitulos(){
        return this.qtdTitulos;
    }

    /** Define o rival (guarda so a referencia; null desfaz o vinculo). O ControleTimes liga os dois sentidos. */
    public void setRival(Times rival) throws TimeException{
        if(rival == this){
            throw new TimeException("Rival invalido: um time nao pode ser rival de si mesmo.");
        }
        this.rival = rival;
    }

    /** Devolve o rival (outro objeto Times) ou null. */
    public Times getRival(){
        return this.rival;
    }
    public String getTipo(){
        return "Time comum";
    }
    //Sobrescrita
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nCODIGO...: ").append(this.getCodigo()).append("\n");
        sb.append("TIPO.....: ").append(this.getTipo()).append("\n");
        sb.append("NOME.....: ").append(this.getNome()).append("\n");
        sb.append("APELIDO..: ").append(this.getApelido().getApelido()).append("\n"); //Reflexividade
        sb.append("TITULOS..:\n");
        if(this.qtdTitulos == 0){
            sb.append("   Nenhum titulo cadastrado.\n");
        } else {
            for(int i = 0; i < this.qtdTitulos; i++){
                sb.append("   - ").append(this.getTitulos()[i].getNomeTitulo()).append(" (").append(this.getTitulos()[i].getQuantidade()).append("x)\n"); //Reflexividade
            }
        }
        if(this.getRival() != null){
            // So o NOME do rival: usar rival.toString() faria um chamar o outro sem parar
            sb.append("RIVAL....: ").append(this.getRival().getNome()).append("\n"); //Reflexividade
        } else {
            sb.append("RIVAL....: Sem rival cadastrado\n");
        }
        return sb.toString();
    }

}
