import java.util.HashMap;
import java.util.Map;

// Classe para Fazer Login como Garçom ou Administrador
public class Login {
    
    // Usando HashMap para realizar o Login dos Usuários
    private Map<String, String> credenciaisGarcom = new HashMap<>();
    private final Map<String, String> credenciaisAdmin = new HashMap<>();

    // Método para exibir opções de Login
    public void LoginOpcoes(){
        System.out.println("----------Login----------");
        System.out.println("Insira '1' para Garçom");
        System.out.println("Insira '2' para Administrador");
        System.out.println("Insira '3' para Sair");
        System.out.printf("--> ");
    }

    // Definindo credenciais de Login para Administrador
    public Login(){
        credenciaisAdmin.put("admin", "admin123");                // Definindo um Usuário e Senha para Admin
    }

    // Criando novo Garçom
    public void CriarGarcom(String GarcomUsuario, String GarcomSenha){
        credenciaisGarcom.put(GarcomUsuario, GarcomSenha);
    }

    // Classe para Realizar a Verificação de Login do Usuário
    public boolean LoginGarcom(String GarcomUsuario, String GarcomSenha){
        // Verifica se a entrada do Usuário se iguala ao Usuário e Senha definidos na classe e Retorna True ou False
        if (credenciaisGarcom.containsKey(GarcomUsuario) && credenciaisGarcom.get(GarcomUsuario).equals(GarcomSenha)) {
            return true;
        }else{
            return false;
        }
    }

    // Classe para Realizar a Verificação de Login do Admin
    public boolean LoginAdmin(String AdminUsuario, String AdminSenha){
        // Verifica se a entrada do Usuário se iguala ao Usuário e Senha definidos na classe e Retorna True ou False
        if (credenciaisAdmin.containsKey(AdminUsuario) && credenciaisAdmin.get(AdminUsuario).equals(AdminSenha)) {
            return true;
        }else{
            return false;
        }
    }
}
