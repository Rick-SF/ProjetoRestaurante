import java.util.HashMap;
import java.util.Map;

// Classe para Fazer Login como Garçom ou Administrador
public class Login {
    
    private Map<String, String> credenciais;

    // Definindo credenciais de Login para Garçom e administrador
    public Login(){
        credenciais = new HashMap<>();                                // instânciando
        credenciais.put("garcom", "garcom123");             // Definindo um usuário e senha para login
    }

    // Classe para Realizar a Verificação de Login do Usuário
    public boolean LoginGarcom(String GarcomUsuario, String GarcomSenha){
        if (credenciais == null) {
            System.out.println("Erro: Mapa de credenciais não foi inicializado corretamente.");
            return false;
        }else{
            // Boolean para credenciais inseridas se estão corretas ou não
            return credenciais.containsKey(GarcomUsuario) && credenciais.get(GarcomUsuario).equals(GarcomSenha);
        }
    }
}
