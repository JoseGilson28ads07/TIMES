// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe entidade: Titulos
// ==========================================================================
public class Titulos{

    private String nomeTitulo;
    private int quantidade;

    /** Construtor padrao: valores neutros. */
    public Titulos(){
        this.nomeTitulo = "Titulo nao definido";
        this.quantidade = 0;
    }

    /** Define o nome do titulo (nao pode ser nulo nem vazio). */
    public void setNomeTitulo(String nomeTitulo) throws TimeException{
        if(nomeTitulo == null || nomeTitulo.trim().isEmpty()){
            throw new TimeException("Nome do titulo invalido: nao pode ser vazio.");
        }
        this.nomeTitulo = nomeTitulo.trim();
    }

    /** Devolve o nome do titulo. */
    public String getNomeTitulo(){
        return this.nomeTitulo;
    }

    /** Define quantas vezes o titulo foi conquistado (maior que 0). */
    public void setQuantidade(int quantidade) throws TimeException{
        if(quantidade <= 0){
            throw new TimeException("Quantidade de titulos invalida: deve ser maior que 0.");
        }
        this.quantidade = quantidade;
    }

    /** Devolve a quantidade de conquistas. */
    public int getQuantidade(){
        return this.quantidade;
    }

}
