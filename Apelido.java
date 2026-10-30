// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe entidade: Apelido
// ==========================================================================
// Representa o apelido de um time (ex: o Palmeiras e' conhecido como "Verdao").
// ==========================================================================
public class Apelido{

    private String apelido;

    /** Construtor padrao: valor inicial para nunca ficar null. */
    public Apelido(){
        this.apelido = "Apelido nao definido";
    }

    /** Define o apelido (nao pode ser nulo nem vazio). */
    public void setApelido(String apelido) throws TimeException{
        if(apelido == null || apelido.trim().isEmpty()){
            throw new TimeException("Apelido invalido: nao pode ser vazio.");
        }
        this.apelido = apelido.trim();
    }

    /** Devolve o apelido. */
    public String getApelido(){
        return this.apelido;
    }

}
