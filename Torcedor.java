// RA: 2678454
// Nome: José Gilson Cavalcante Filho

// ==========================================================================
// Classe entidade: Torcedor
// ==========================================================================
public class Torcedor{

    private int id;
    private String nome;
    private String email;
    private Times time;    // null = ainda sem time

    /** Construtor padrao: torcedor vazio, sem time. */
    public Torcedor(){
        this.id = 0;
        this.nome = "";
        this.email = "";
        this.time = null;
    }

    /** Define o id (maior que 0). */
    public void setId(int id) throws TimeException{
        if(id <= 0){
            throw new TimeException("Id invalido: deve ser maior que 0.");
        }
        this.id = id;
    }

    /** Devolve o id. */
    public int getId(){
        return this.id;
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

    /** Define o email (so rejeita nulo ou vazio, sem checar o formato). */
    public void setEmail(String email) throws TimeException{
        if(email == null || email.trim().isEmpty()){
            throw new TimeException("Email invalido: nao pode ser vazio.");
        }
        this.email = email.trim();
    }

    /** Devolve o email. */
    public String getEmail(){
        return this.email;
    }

    /** Associa o torcedor a um objeto Times real (null nao e aceito). */
    public void setTime(Times time) throws TimeException{
        if(time == null){
            throw new TimeException("Associacao invalida: o time nao existe (null).");
        }
        this.time = time;
    }

    /** Devolve o time associado (ou null). */
    public Times getTime(){
        return this.time;
    }
    //Sobrescrita
    public String toString(){
        String nomeTime = "sem time";
        if(this.getTime() != null){
            nomeTime = this.getTime().getNome(); //Reflexividade
        }
        return "ID " + this.getId() + " | " + this.getNome() + " | " + this.getEmail() + " | Time: " + nomeTime;
    }

}
