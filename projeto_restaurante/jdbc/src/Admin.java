import java.sql.*;
import java.util.Scanner;

public class Admin {
    Scanner leitor = new Scanner(System.in);
    
    // Método para exibir opções de Admin
    public void MenuAdmin(){
        System.out.println("\n----------Opções----------");
        System.out.println("1: Cadastrar Pratos");
        System.out.println("2: Atualizar Pratos");
        System.out.println("3: Deletar Pratos");
        System.out.println("4: Logout");
        System.out.printf("--> ");
    }
    
    public void InserirPratos(String nomePrato, double valorPrato){
        String sql = "INSERT INTO prato (nome, valor, disponivel) VALUES (?, ?, ?)";
    
        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);
            stmt.setDouble(2, valorPrato);
            stmt.setString(3, "1");

            int LinhasAfetadas = stmt.executeUpdate();
            System.out.println("Linhas Afetadas: "+ LinhasAfetadas);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void AtualizarPratos(){
        while (true) {
            System.out.println("\n----------Atualização----------");
            System.out.println("1: Mudar Nome de um Prato");
            System.out.println("2: Mudar Valor de um Prato");
            System.out.println("3: Mudar Disponibilidade de um Prato");
            System.out.println("4: Sair");
            int opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 1:
                    System.out.printf("Prato no qual deseja mudar o nome: ");
                    String antigoNome = leitor.nextLine();
                    System.out.printf("Insira o Novo Nome: ");
                    String novoNome = leitor.nextLine();

                    String sql = "UPDATE prato SET nome = ? WHERE nome = ?";

                    try (Connection conexao = conectiondb.conectar();
                        PreparedStatement stmt = conexao.prepareStatement(sql)){
        
                        stmt.setString(1, novoNome);
                        stmt.setString(2, antigoNome);
        
                        int LinhasAfetadas = stmt.executeUpdate();
                        System.out.println("Nome do Prato "+ antigoNome +" atualizado para " + novoNome +".");
                        System.out.println("Linhas afetadas: "+ LinhasAfetadas);
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
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
