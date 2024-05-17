import java.io.IOException;
import java.util.Scanner;


public class LimparTerminal {
    Scanner leitor = new Scanner(System.in);
    Garcom garcom;
    
    // Método Limpa o terminal usando um comando específico dependente do sistema operacional
    public void limpar(int tempo){
        try {
            Thread.sleep(tempo); // Delay em milissegundos (Ex: 1000 = 1 segundo)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Tentei fazer para impedir entradas vazias (exceto na idade pq é dificil pa karai)
    public String StringNaoVazia(String mensagem) {
        String entrada = "";
        while (entrada == "") {
            System.out.printf(mensagem);
            entrada = leitor.nextLine();
            if (entrada == "") {
                System.out.println("Por favor, insira os dados.");
                limpar(1000);
                if (garcom.getNome() == null) {         // sabosta nao funfa n sei pq
                    System.out.printf("Nome do Garçom: %s\nIdade do Garçom: %d\n", garcom.getNome());
                    break;
                }
                continue;
            } else if (entrada != ""){
                break;
            }
        }
        return entrada;
    }
}