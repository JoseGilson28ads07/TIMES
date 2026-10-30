// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe entidade: TimeAmador (filha de Times)
// ==========================================================================
// HERANCA: um TimeAmador "e' um tipo de" Times (ex: time de varzea) e acrescenta 2 atributos
// proprios: bairro e qtdAtletas.
// ==========================================================================
public class TimeAmador extends Times{

    private String bairro;
    private int qtdAtletas;

    /** Construtor padrao: super() inicializa a parte herdada de Times. */
    public TimeAmador(){
        super();
        this.bairro = "Bairro nao definido";
        this.qtdAtletas = 0;
    }

    /** Define o bairro (nao pode ser nulo nem vazio). */
    public void setBairro(String bairro) throws TimeException{
        if(bairro == null || bairro.trim().isEmpty()){
            throw new TimeException("Bairro invalido: nao pode ser vazio.");
        }
        this.bairro = bairro.trim();
    }

    /** Devolve o bairro. */
    public String getBairro(){
        return this.bairro;
    }

    /** Define a quantidade de atletas (maior que 0). */
    public void setQtdAtletas(int qtdAtletas) throws TimeException{
        if(qtdAtletas <= 0){
            throw new TimeException("Quantidade de atletas invalida: deve ser maior que 0.");
        }
        this.qtdAtletas = qtdAtletas;
    }

    /** Devolve a quantidade de atletas. */
    public int getQtdAtletas(){
        return this.qtdAtletas;
    }
    //Sobrescrita
    public String getTipo(){
        return "Time Amador";
    }
    //Sobrescrita
    public String toString(){
        return super.toString()
             + "BAIRRO...: " + this.getBairro() + "\n"
             + "ATLETAS..: " + this.getQtdAtletas() + "\n";
    }

}
