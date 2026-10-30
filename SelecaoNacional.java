// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe entidade: SelecaoNacional (filha de Times)
// ==========================================================================
// HERANCA: uma SelecaoNacional "e' um tipo de" Times e acrescenta 2 atributos proprios:
// confederacao (ex: CONMEBOL) e tecnico.
// ==========================================================================
public class SelecaoNacional extends Times{

    private String confederacao;
    private String tecnico;

    /** Construtor padrao: super() inicializa a parte herdada de Times. */
    public SelecaoNacional(){
        super();
        this.confederacao = "Confederacao nao definida";
        this.tecnico = "Tecnico nao definido";
    }

    /** Define a confederacao (nao pode ser nula nem vazia). */
    public void setConfederacao(String confederacao) throws TimeException{
        if(confederacao == null || confederacao.trim().isEmpty()){
            throw new TimeException("Confederacao invalida: nao pode ser vazia.");
        }
        this.confederacao = confederacao.trim();
    }

    /** Devolve a confederacao. */
    public String getConfederacao(){
        return this.confederacao;
    }

    /** Define o tecnico (nao pode ser nulo nem vazio). */
    public void setTecnico(String tecnico) throws TimeException{
        if(tecnico == null || tecnico.trim().isEmpty()){
            throw new TimeException("Tecnico invalido: nao pode ser vazio.");
        }
        this.tecnico = tecnico.trim();
    }

    /** Devolve o tecnico. */
    public String getTecnico(){
        return this.tecnico;
    }
    //Sobrescrita
    public String getTipo(){
        return "Selecao Nacional";
    }
    //Sobrescrita
    public String toString(){
        return super.toString()
             + "CONFEDERACAO: " + this.getConfederacao() + "\n"
             + "TECNICO..: " + this.getTecnico() + "\n";
    }

}
