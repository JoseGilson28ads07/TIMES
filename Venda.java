// RA: 2678454
// Nome: José Gilson Cavalcante Filho

import java.util.Locale;

// ==========================================================================
// Classe entidade: Venda
// ==========================================================================
// Operacao de venda: UM Torcedor compra uma quantidade de UMA Camisa de um clube.
// ==========================================================================
public class Venda{

    private int codigo;
    private String data;        // Formato dd/mm/aaaa (so nao pode ser vazia)
    private Torcedor torcedor;
    private Camisa camisa;
    private int quantidade;
    private double valorTotal;

    /** Construtor padrao: venda vazia, sem torcedor e sem camisa. */
    public Venda(){
        this.codigo = 0;
        this.data = "";
        this.torcedor = null;
        this.camisa = null;
        this.quantidade = 0;
        this.valorTotal = 0;
    }

    /** Define o codigo (maior que 0). */
    public void setCodigo(int codigo) throws TimeException{
        if(codigo <= 0){
            throw new TimeException("Codigo da venda invalido: deve ser maior que 0.");
        }
        this.codigo = codigo;
    }

    /** Devolve o codigo. */
    public int getCodigo(){
        return this.codigo;
    }

    /** Define a data (nao pode ser nula nem vazia). */
    public void setData(String data) throws TimeException{
        if(data == null || data.trim().isEmpty()){
            throw new TimeException("Data invalida: nao pode ser vazia.");
        }
        this.data = data.trim();
    }

    /** Devolve a data. */
    public String getData(){
        return this.data;
    }

    /** Define o torcedor comprador (null nao e aceito). */
    public void setTorcedor(Torcedor torcedor) throws TimeException{
        if(torcedor == null){
            throw new TimeException("Venda invalida: o torcedor nao existe (null).");
        }
        this.torcedor = torcedor;
    }

    /** Devolve o torcedor comprador. */
    public Torcedor getTorcedor(){
        return this.torcedor;
    }

    /** Define a camisa vendida (null nao e aceito). */
    public void setCamisa(Camisa camisa) throws TimeException{
        if(camisa == null){
            throw new TimeException("Venda invalida: a camisa nao existe (null).");
        }
        this.camisa = camisa;
    }

    /** Devolve a camisa vendida. */
    public Camisa getCamisa(){
        return this.camisa;
    }

    /** Define a quantidade de camisas (maior que 0). */
    public void setQuantidade(int quantidade) throws TimeException{
        if(quantidade <= 0){
            throw new TimeException("Quantidade invalida: deve ser maior que 0.");
        }
        this.quantidade = quantidade;
    }

    /** Devolve a quantidade de camisas. */
    public int getQuantidade(){
        return this.quantidade;
    }

    /** Define o valor total da venda (maior que 0). */
    public void setValorTotal(double valorTotal) throws TimeException{
        if(valorTotal <= 0){
            throw new TimeException("Valor total invalido: deve ser maior que 0.");
        }
        this.valorTotal = valorTotal;
    }

    /** Devolve o valor total da venda. */
    public double getValorTotal(){
        return this.valorTotal;
    }
    //Sobrescrita
    public String toString(){
        return "\nVENDA " + this.getCodigo() + " | Data: " + this.getData() + "\n"
             + "   Torcedor.: " + this.getTorcedor().getNome() + "\n" //Reflexividade
             + "   Clube....: " + this.getCamisa().getClube().getNome() + "\n" //Reflexividade
             + "   Camisa...: " + this.getCamisa().getModelo() + " (Tam: " + this.getCamisa().getTamanho() + ")\n" //Reflexividade
             + "   Qtd......: " + this.getQuantidade() + "\n"
             + "   Total....: R$ " + String.format(Locale.US, "%.2f", this.getValorTotal());
    }

}
