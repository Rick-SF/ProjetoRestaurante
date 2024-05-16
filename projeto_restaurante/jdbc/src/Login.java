import java.util.HashMap;
import java.util.Map;
import java.sql.*;

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
        credenciaisAdmin.put("admin", "admin123");            // Definindo um Usuário e Senha para Admin
    }

    public void CriarGarcom (String nomeGarcom, int idadeGarcom, String usuarioGarcom, String senhaGarcom){
        String sqlGarcom = "INSERT INTO garcom (nome, idade) VALUES (?, ?)";
        String sqlLogin = "INSERT INTO login (id_garcom, usuario, senha) VALUES (?, ?, ?)";

        try (Connection conexao = conectiondb.conectar();
        PreparedStatement stmt1 = conexao.prepareStatement(sqlGarcom, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt2 = conexao.prepareStatement(sqlLogin)){

            // Inserindo informações do Garçom através da variável "stmt1" armazenando a função
            stmt1.setString(1, nomeGarcom);
            stmt1.setInt(2, idadeGarcom);
            int LinhasAfetadas1 = stmt1.executeUpdate();

            // Obter ID do Garçom
            int idGarcomInserido;
            try(ResultSet generetedKeys = stmt1.getGeneratedKeys()){
                if (generetedKeys.next()) {
                    idGarcomInserido = generetedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID do Garçom inserido.");
                }
            }

            // Inserindo Dados na tabela Login
            stmt2.setInt(1, idGarcomInserido);
            stmt2.setString(2, usuarioGarcom);
            stmt2.setString(3, senhaGarcom);

            credenciaisGarcom.put(usuarioGarcom, senhaGarcom);         // Criando novo Login para Garçom

            int LinhasAfetadas2 = stmt2.executeUpdate();

            System.out.println("Linhas Afetadas Tabela Garçom: "+ LinhasAfetadas1);
            System.out.println("Linhas Afetadas Tabela Login: "+ LinhasAfetadas2);

            stmt1.close();
            stmt2.close();
            conexao.close();

            System.out.println("Cadastro Feito!");

        } catch (SQLException e){
            System.out.println("Erro ao inserir dados: "+ e.getMessage());
        }
    }    
    
    // Classe para Realizar a Verificação de Login do Usuário
    public boolean LoginGarcom(String usuarioGarcom, String senhaGarcom){
        // Verifica se a entrada do Usuário se iguala ao Usuário e Senha definidos na classe e Retorna True ou False
        if (credenciaisGarcom.containsKey(usuarioGarcom) && credenciaisGarcom.get(usuarioGarcom).equals(senhaGarcom)) {
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
