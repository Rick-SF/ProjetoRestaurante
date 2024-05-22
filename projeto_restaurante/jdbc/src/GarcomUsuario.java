import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GarcomUsuario {
    public void MenuGarcom(){
        System.out.println("----------Opções de Garçom----------");
        System.out.println("1: Anotar Pedido");
        System.out.println("2: Cardápio");
        System.out.printf("--> ");
    }

    public boolean Cardápio(){
        String sql = "SELECT nome, valor, disponivel FROM prato";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){
            
            ResultSet rs = stmt.executeQuery();

            int count = 0;    // variável para testar se há pratos cadastrados
            int count2 = 1;

            while (rs.next()) {
                String prato = rs.getString("nome");
                String disponivel = rs.getString("disponivel");
                double valor = rs.getDouble("valor");
                System.out.printf("--------------------\nPrato n°%d:\nPrato: %s\nValor: R$%.2f\nDisponivel: %s\n",count2 ,prato, valor, disponivel);
                count2++;
                count++;
            }

            if (count == 0) {       // Testa se foi encontrado Pratos cadastrados
                return false;       // Retorna False, se não encontrado Pratos
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;                // Retorna True, para se encontrado Pratos
    }

    public void AnotarPedido(int numMesa, int ocupacao, int pratoPedido, String usuarioGarcom){
        String sqlMesa = "INSERT INTO mesa (numero, ocupação) VALUES (?, ?)";
        String sqlIDgarcom = "SELECT id_garcom FROM login WHERE usuario = ?";
        String sqlPedidos = "INSERT INTO pedidos (num_mesa, id_prato, id_garcom) VALUES (?, ?, ?)";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt1 = conexao.prepareStatement(sqlMesa);
            PreparedStatement stmt2 = conexao.prepareStatement(sqlIDgarcom);
            PreparedStatement stmt3 = conexao.prepareStatement(sqlPedidos)){

            stmt1.setInt(1, numMesa);
            stmt1.setInt(2, ocupacao);
            stmt1.executeUpdate();
            
            stmt2.setString(1, usuarioGarcom);
            ResultSet rs = stmt2.executeQuery();
            int IDgarcom = -1;
            if (rs.next()) {
                IDgarcom = rs.getInt("id_garcom");
            }
            if (IDgarcom == -1) {
                throw new SQLException("Garçom não encontrado");
            }
            
            stmt3.setInt(1, numMesa);
            stmt3.setInt(2, pratoPedido);
            stmt3.setInt(3, IDgarcom);
            stmt3.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
