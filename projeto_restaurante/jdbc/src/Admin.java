import java.sql.*;
import java.util.Scanner;

public class Admin {
    Scanner leitor = new Scanner(System.in);
    LimparTerminal terminal = new LimparTerminal();
    Login login = new Login();
    
    // Método para exibir opções de Admin
    public void MenuAdmin(){
        System.out.println("----------Opções de Admin----------");
        System.out.println("1: Cadastrar Pratos");
        System.out.println("2: Atualizar Pratos");
        System.out.println("3: Pratos Cadastrados");
        System.out.println("4: Deletar Pratos");
        System.out.println("5: Administrar Garçons");
        System.out.println("6: Logout");
        System.out.printf("--> ");
    }

    // Método para exibir opções de Administrar Garçons
    public void MenuAdminGarcom(){
        System.out.println("----------Garçons----------");
        System.out.println("1: Cadastrar novo Garçom");
        System.out.printf("--> ");
    }

    // Método para exibir opções de Atualizar Pratos
    public void MenuAdminAtualizar(){
        System.out.println("\n----------Atualização----------");
        System.out.println("1: Mudar Nome de um Prato");
        System.out.println("2: Mudar Valor de um Prato");
        System.out.println("3: Mudar Disponibilidade de um Prato");
        System.out.println("4: Voltar");
        System.out.printf("--> ");
    }
    
    // Método para Cadastrar novos Pratos
    public void CadastrarPratos(String nomePrato, double valorPrato){
        String sql = "INSERT INTO prato (nome, valor, disponivel) VALUES (?, ?, ?)";
    
        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);
            stmt.setDouble(2, valorPrato);
            stmt.setString(3, "sim");

            int LinhasAfetadas = stmt.executeUpdate();
            System.out.printf("\nPrato '%s' pelo valor de R$%.2f cadastrado.\n",nomePrato, valorPrato);
            System.out.println("Linhas Afetadas: "+ LinhasAfetadas);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Método para atualizar pratos existentes
    public void AtualizarPratos(int AtualiOpcao){
        switch (AtualiOpcao) {
            case 1: // Mudar nome de um Prato
                System.out.printf("Prato no qual deseja mudar o nome: ");
                String antigoNome = leitor.nextLine();

                String nomePrato = antigoNome;
                if(VerificarPrato(nomePrato)){         // Verifica se o prato existe
                    // Se sim, continua...
                } else{                                             
                    System.out.println("Prato não cadastrado ou não existe.");
                    terminal.limpar(2000);                                      // Senão, Informa e não continua
                    break;
                }
                
                System.out.printf("Insira o Novo Nome: ");
                String novoNome = leitor.nextLine();

                String sql = "UPDATE prato SET nome = ? WHERE nome = ?";

                try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setString(1, novoNome);
                    stmt.setString(2, antigoNome);
    
                    int LinhasAfetadas = stmt.executeUpdate();
                    System.out.printf("\nNome do Prato '%s' atualizado para '%s'.\n", antigoNome, novoNome);
                    System.out.println("Linhas afetadas: "+ LinhasAfetadas);
                    terminal.limpar(3000);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case 2: // Mudar Valor de um Prato
                System.out.printf("Prato no qual deseja mudar o preço: ");
                nomePrato = leitor.nextLine();
                System.out.printf("Novo preço do prato: ");
                String NovoPreco = leitor.nextLine();

                if (VerificarPrato(nomePrato)) {            // Verifica se o Prato Existe
                    // Se sim, continua...
                }else{
                    System.out.println("Prato não cadastrado ou não existe.");
                    terminal.limpar(2000);                                      // Senão, Informa e não continua
                    break;
                }

                sql = "UPDATE prato SET valor = ? WHERE nome = ?";                  // Query para mudar preço de um Prato

                try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setString(1, NovoPreco);
                    stmt.setString(2, nomePrato);
    
                    int LinhasAfetadas = stmt.executeUpdate();
                    System.out.printf("Preço do Prato '%s' atualizado para R$%.2f.", nomePrato, NovoPreco);
                    System.out.println("Linhas afetadas: "+ LinhasAfetadas);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case 3: // Mudar Disponibilidade de um Prato
                System.out.printf("Prato no qual deseja mudar a disponibilidade: ");
                nomePrato = leitor.nextLine();                          // Armazena o Prato a ser atualizado

                if (VerificarPrato(nomePrato) == true) {                // Verifica se o Prato Existe
                    // Se sim, continua...
                }else{                                              
                    System.out.println("Prato não existe ou não cadastrado.");
                    terminal.limpar(2000);                                      // Senão, informa e não continua
                    break;
                }

                System.out.printf("Disponivel('1') ou Indisponivel('0'): ");
                int disponibilidade = leitor.nextInt();                     // armazena opção de disponibilidade
                leitor.nextLine();                                          // Limpa o Buffer

                sql = "UPDATE prato SET disponivel = ? WHERE nome = ?";             // Query para Atualizar disponibilidade

                if (disponibilidade == 1) {
                    try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setString(1, "sim");
                    stmt.setString(2, nomePrato);
    
                    int LinhasAfetadas = stmt.executeUpdate();
                    System.out.println("Disponibilidade do Prato '"+ nomePrato +"' atualizado para Disponivel.");
                    System.out.println("Linhas afetadas: "+ LinhasAfetadas);
                    terminal.limpar(3000);

                    }catch(SQLException e){
                    e.printStackTrace();
                    }
                } else if(disponibilidade == 0) {
                    try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setString(1, "nao");
                    stmt.setString(2, nomePrato);
    
                    int LinhasAfetadas = stmt.executeUpdate();
                    System.out.println("Disponibilidade do Prato '"+ nomePrato +"' atualizado para Indisponivel.");
                    System.out.println("Linhas afetadas: "+ LinhasAfetadas);
                    terminal.limpar(3000);

                    }catch(SQLException e){
                    e.printStackTrace();
                    }
                }else {
                    System.out.println("Opção Inválida");
                    terminal.limpar(1000);
                    break;
                }
                break;
            default:
                break;
        }
    }

    // Método para retornar os pratos cadastrado no Banco
    public boolean PratosCadastrados(){
        String sql = "SELECT nome, valor, disponivel FROM prato";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){
            
            ResultSet rs = stmt.executeQuery();

            int count = 0;    // variável para testar se há pratos cadastrados
            
            while (rs.next()) {
                String prato = rs.getString("nome");
                String disponivel = rs.getString("disponivel");
                double valor = rs.getDouble("valor");
                System.out.printf("--------------------\nPrato: %s\nValor: R$%.2f\nDisponivel: %s\n",prato, valor, disponivel);
                count++;
            }

            if (count == 0) {       // Testa se foi encontrado Pratos cadastrados
                return false;
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Método para deletar pratos existentes
    public void DeletarPratos(String nomePrato){
        String sql = "DELETE FROM prato WHERE nome = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);

            int LinhasAfetadas = stmt.executeUpdate();
            System.out.println("Prato "+ nomePrato +" Deletado.");
            System.out.println("Linhas Afetadas: " + LinhasAfetadas);
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AdministrarGarcons(int opcaoAdminGarcom){
        switch (opcaoAdminGarcom) {
            case 1: // Cria novo Garçom
                System.out.print("Nome do Garçom: ");
                String nomeGarcom = leitor.nextLine();
                System.out.printf("Idade do Garcom: ");
                int idadeGarcom = leitor.nextInt();
                leitor.nextLine();
                System.out.printf("Usuário do novo Garçom: ");
                String usuarioGarcom = leitor.nextLine();
                System.out.printf("Senha do Novo Garçom: ");
                String senhaGarcom = leitor.nextLine();
                terminal.limpar(500);
                login.CriarGarcom(nomeGarcom, idadeGarcom, usuarioGarcom, senhaGarcom);
                terminal.limpar(3000);
                break;
            default:
                break;
        }
    }

    // Método para verificar se Prato existe no Banco
    public boolean VerificarPrato(String nomePrato){
        String sql = "SELECT COUNT(*) FROM prato WHERE nome = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return true;
                } else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}