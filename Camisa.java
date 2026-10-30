// RA: 2678454
// Nome: José Gilson Cavalcante Filho

import java.util.Locale;

// ==========================================================================
// Classe entidade: Camisa
// ==========================================================================
// Camisa de um CLUBE posta a venda (ex: "Titular 2026", tamanho M).
// ==========================================================================
public class Camisa{

    private int codigo;
    private String modelo;
    private String tamanho;  // P, M, G ou GG
    private double preco;
    private int estoque;
    private Times clube;     // null = ainda sem clube

    /** Construtor padrao: camisa vazia, sem clube. */
    public Camisa(){
        this.codigo = 0;
        this.modelo = "";
        this.tamanho = "";
        this.preco = 0;
        this.estoque = 0;
        this.clube = null;
    }

    /** Define o codigo (maior que 0). */
    public void setCodigo(int codigo) throws TimeException{
        if(codigo <= 0){
            throw new TimeException("Codigo da camisa invalido: deve ser maior que 0.");
        }
        this.codigo = codigo;
    }

    /** Devolve o codigo. */
    public int getCodigo(){
        return this.codigo;
    }

    /** Define o modelo (nao pode ser nulo nem vazio). */
    public void setModelo(String modelo) throws TimeException{
        if(modelo == null || modelo.trim().isEmpty()){
            throw new TimeException("Modelo invalido: nao pode ser vazio.");
        }
        this.modelo = modelo.trim();
    }

    /** Devolve o modelo. */
    public String getModelo(){
        return this.modelo;
    }

    /** Define o tamanho: aceita P, M, G ou GG (maiusculas ou minusculas). */
    public void setTamanho(String tamanho) throws TimeException{
        if(tamanho == null){
            throw new TimeException("Tamanho invalido: use P, M, G ou GG.");
        }
        String t = tamanho.trim().toUpperCase();
        if(!t.equals("P") && !t.equals("M") && !t.equals("G") && !t.equals("GG")){
            throw new TimeException("Tamanho invalido: use P, M, G ou GG.");
        }
        this.tamanho = t;
    }

    /** Devolve o tamanho. */
    public String getTamanho(){
        return this.tamanho;
    }

    /** Define o preco unitario (maior que 0). */
    public void setPreco(double preco) throws TimeException{
        if(preco <= 0){
            throw new TimeException("Preco invalido: deve ser maior que 0.");
        }
        this.preco = preco;
    }

    /** Devolve o preco unitario. */
    public double getPreco(){
        return this.preco;
    }

    /** Define o estoque (0 ou mais). */
    public void setEstoque(int estoque) throws TimeException{
        if(estoque < 0){
            throw new TimeException("Estoque invalido: nao pode ser negativo.");
        }
        this.estoque = estoque;
    }

    /** Devolve o estoque. */
    public int getEstoque(){
        return this.estoque;
    }

    /** Define o clube: so aceita um Times que seja instancia de Clube (instanceof). */
    public void setClube(Times clube) throws TimeException{
        if(clube == null){
            throw new TimeException("Clube invalido: o time nao existe (null).");
        }
        if(!(clube instanceof Clube)){
            throw new TimeException("Clube invalido: " + clube.getNome() + " e " + clube.getTipo() + ". Camisas so existem para Clube.");
        }
        this.clube = clube;
    }

    /** Devolve o clube da camisa. */
    public Times getClube(){
        return this.clube;
    }
    //Sobrescrita
    public String toString(){
        return "CAMISA " + this.getCodigo() + " | " + this.getClube().getNome() //Reflexividade
             + " | " + this.getModelo() + " | Tam: " + this.getTamanho()
             + " | R$ " + String.format(Locale.US, "%.2f", this.getPreco())
             + " | Estoque: " + this.getEstoque();
    }

}
