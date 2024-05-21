import java.sql.*;

// Classe para Fazer Login como Garçom ou Administrador
public class Login {
    // Método para exibir opções de Login
    public void LoginOpcoes(){
        System.out.println("----------Login----------");
        System.out.println("Insira '1' para Garçom");
        System.out.println("Insira '2' para Administrador");
        System.out.println("Insira '3' para Sair");
        System.out.printf("--> ");
    }

    // Método para criar um Login de Garçom
    public void CriarGarcom (String nomeGarcom, int idadeGarcom, String usuarioGarcom, String senhaGarcom){
        // É recebido os parâmetros e criado as QUERIES para criação do Garçom no banco com os dados do Parâmetro
        String sqlGarcom = "INSERT INTO garcom (nome, idade) VALUES (?, ?)";
        String sqlLogin = "INSERT INTO login (id_garcom, usuario, senha) VALUES (?, ?, ?)";

        // Faz a conexão e prepara as QUERIES nas variáveis "stmt"...
        try (Connection conexao = conectiondb.conectar();
        PreparedStatement stmt1 = conexao.prepareStatement(sqlGarcom, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt2 = conexao.prepareStatement(sqlLogin)){

            // Inserindo informações do Garçom na variável sql "stmt1"
            stmt1.setString(1, nomeGarcom);
            stmt1.setInt(2, idadeGarcom);
            int LinhasAfetadas1 = stmt1.executeUpdate();

            // Obtem o ID do Garçom criado na tabela garcom
            int idGarcomCriado;
            try(ResultSet generetedKeys = stmt1.getGeneratedKeys()){
                if (generetedKeys.next()) {
                    idGarcomCriado = generetedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID do Garçom inserido.");
                }
            }

            // Inserindo na sql "stmt2" as outras informações de Login do Garçom na tabela login, Inclusive o ID do garçom como chave estrangeira
            stmt2.setInt(1, idGarcomCriado);
            stmt2.setString(2, usuarioGarcom);
            stmt2.setString(3, senhaGarcom);

            // Armazena as Linhas afetadas no banco
            int LinhasAfetadas2 = stmt2.executeUpdate();

            // Mostra as Linhas afetadas
            System.out.println("Linhas Afetadas Tabela Garçom: "+ LinhasAfetadas1);
            System.out.println("Linhas Afetadas Tabela Login: "+ LinhasAfetadas2);

            System.out.println("Cadastro Feito!");

        } catch (SQLException e){
            System.out.println("Erro ao inserir dados: "+ e.getMessage());
        }
    }    
    
    // Classe para Realizar a Verificação de Login do Garçom
    public boolean LoginGarcom(String usuarioGarcom, String senhaGarcom){
        // Verifica se a entrada do Usuário se iguala ao Usuário e Senha definidos no Banco de Dados e Retorna True ou False
        boolean LoginFeito = false;
        String sql = "SELECT * FROM login WHERE usuario = ? AND senha = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){
                
            stmt.setString(1, usuarioGarcom);
            stmt.setString(2, senhaGarcom);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                LoginFeito = true;
            }
        } catch (SQLException e){
            System.out.println("Erro ao verificar Login." + e.getMessage());
        }
        return LoginFeito;
    }

    // Classe para Realizar a Verificação de Login do Admin
    public boolean LoginAdmin(String usuarioAdmin, String senhaAdmin){
        // Verifica se a entrada do Usuário se iguala ao Usuário e Senha definidos no Banco de Dados e Retorna True ou False
        boolean LoginFeito = false;
        String sql = "SELECT * FROM administrador WHERE usuario = ? AND senha = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){
                
            stmt.setString(1, usuarioAdmin);
            stmt.setString(2, senhaAdmin);

            ResultSet resultSet = stmt.executeQuery();

            // Retorna true para o Login se encontrado o usuario e senha do admin
            if (resultSet.next()) {
                LoginFeito = true;
            }
        } catch (SQLException e){
            System.out.println("Erro ao verificar Login." + e.getMessage());
        }
        return LoginFeito;
    }
}
