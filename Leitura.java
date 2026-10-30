// RA: 2678454
// Nome: José Gilson Cavalcante Filho

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// ==========================================================================
// Classe utilitaria: Leitura
// ==========================================================================
public class Leitura{

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static String leString() throws EntradaInvalidaException{
        try{
            String linha = br.readLine();
            if(linha == null){
                throw new EntradaInvalidaException("Entrada encerrada: nenhum valor foi digitado.");
            }
            return linha;
        } catch(IOException e){
            throw new EntradaInvalidaException("Erro ao ler a entrada.");
        }
    }
    public static void fechar() throws EntradaInvalidaException{
        try{
            br.close();
        } catch(IOException e){ 
            throw new EntradaInvalidaException("Erro ao fechar a leitura.");
        }
    }

}
