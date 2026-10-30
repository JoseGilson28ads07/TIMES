// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe entidade: Clube (filha de Times)
// ==========================================================================
// HERANCA: um Clube "e' um tipo de" Times e acrescenta 2 atributos proprios: cidade e anoFundacao.
// ==========================================================================
public class Clube extends Times{

    private String cidade;
    private int anoFundacao;  // Formato aaaa

    /** Construtor padrao: super() inicializa a parte herdada de Times. */
    public Clube(){
        super();
        this.cidade = "Cidade nao definida";
        this.anoFundacao = 0;
    }

    /** Define a cidade sede (nao pode ser nula nem vazia). */
    public void setCidade(String cidade) throws TimeException{
        if(cidade == null || cidade.trim().isEmpty()){
            throw new TimeException("Cidade invalida: nao pode ser vazia.");
        }
        this.cidade = cidade.trim();
    }

    /** Devolve a cidade. */
    public String getCidade(){
        return this.cidade;
    }

    /** Define o ano de fundacao (maior que 0). */
    public void setAnoFundacao(int anoFundacao) throws TimeException{
        if(anoFundacao <= 0){
            throw new TimeException("Ano de fundacao invalido: deve ser maior que 0.");
        }
        this.anoFundacao = anoFundacao;
    }

    /** Devolve o ano de fundacao. */
    public int getAnoFundacao(){
        return this.anoFundacao;
    }

    /** Versao de Clube do metodo da interface ITimes. */
    @Override
    //Sobrescrita
    public String getTipo(){
        return "Clube";
    }

    /** Acrescenta cidade e fundacao ao texto de Times (nao imprime). */
    @Override
    //Sobrescrita
    public String toString(){
        return super.toString()
             + "CIDADE...: " + this.getCidade() + "\n"
             + "FUNDACAO.: " + this.getAnoFundacao() + "\n";
    }

}
