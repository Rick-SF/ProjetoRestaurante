import java.io.IOException;

public class LimparTerminal {
    
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
}
