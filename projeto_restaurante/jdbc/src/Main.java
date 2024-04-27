import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);            // Cria Scanner
        Login login = new Login();                          // Instância da Classe Login
        Admin admin = new Admin();                          // Instância da Classe Admin

        // Exibe as Opções de Login
        login.LoginOpcoes();

        int opcao = leitor.nextInt();
        leitor.nextLine();                   // Limpa o Buffer

        switch (opcao) {
            case 1:
                System.out.printf("\nUsuário: ");       // Usuário insere o Usuário de Garçom
                String GarcomUsuario = leitor.nextLine();      // Armazena a entrada Usuário

                System.out.printf("Senha: ");           // Usuário insere a Senha de Garçom
                String GarcomSenha = leitor.nextLine();        // Armazena a entrada Senha

                if (login.LoginGarcom(GarcomUsuario, GarcomSenha)) {    // Faz a Verificação de Usuário e senha pelo Método LoginGarcom
                    System.out.println("Login de Garçom Feito!");             // Retornando True, o Login é confirmado.
                } else {
                    System.out.println("Login Inválido.");          // Retornando False, o Login é impedido
                }
                break;
            case 2:
                System.out.printf("\nUsuário: ");       // Usuário Insere o Usuário de Admin
                String AdminUsuario = leitor.nextLine();       // Armazena a entrada Usuário

                System.out.printf("Senha: ");           // Usuário Insere a Senha de Admin
                String AdminSenha = leitor.nextLine();         // Armazena a entrada Senha

                if (login.LoginAdmin(AdminUsuario, AdminSenha)) {   // Faz a Verificação de Usuário e senha pelo Método LoginAdmin
                    System.out.println("Login de Admin Feito!");        // Retornando True, o Login é confirmado e procede
                    admin.MenuAdmin();                              // Exibe o Menu de Admin
                    int adminopcao = leitor.nextInt();

                    leitor.nextLine();                          // Limpa o Buffer

                    switch (adminopcao) {
                        case 1:
                            System.out.printf("Nome do Prato: ");
                            String nomePrato = leitor.nextLine();
                            System.out.printf("Valor do Prato: ");
                            double valorPrato = leitor.nextDouble();

                            leitor.nextLine();                              // Limpa o Buffer

                            admin.InserirPratos(nomePrato, valorPrato);     // Adiciona um novo prato com os parâmetros informados
                            break;
                        default:
                            break;
                    }
                
                } else {
                    System.out.println("Login Inválido.");              // Retornando False, o Login é impedido.
                }
                break;
            default:
                System.out.println("Opção Inválida.");   // Mensagem de erro para opção inválida
        }

        leitor.close();
    }
}
