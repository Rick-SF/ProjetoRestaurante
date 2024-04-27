import java.sql.*;

public class Admin {
    int id = 0;
    
    // Método para exibir opções de Admin
    public void MenuAdmin(){
        System.out.println("\n----------Opções----------");
        System.out.println("1: Cadastrar Pratos");
        System.out.println("2: Atualizar Pratos");
        System.out.println("3: Deletar Pratos");
        System.out.printf("--> ");
    }
    
    public void InserirPratos(String nomePrato, double valorPrato){
        String sql = "INSERT INTO prato (id, nome, valor, disponivel) VALUES (?, ?, ?, ?)";
    
        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setInt(1, ++id);
            stmt.setString(2, nomePrato);
            stmt.setDouble(3, valorPrato);
            stmt.setString(4, "1");

            int LinhasAfetadas = stmt.executeUpdate();
            System.out.println("Linhas Afetadas: "+ LinhasAfetadas);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void DeletarPratos(String nomePrato){
        String sql = "DELETE FROM prato WHERE nome = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);

            int LinhasAfetadas = stmt.executeUpdate();
            System.out.println("Linhas Afetadas: " + LinhasAfetadas);
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
