import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner leitor = new Scanner(System.in);            // Cria um Scanner
        Login login = new Login();

        System.out.println("----------Login----------");
        System.out.println("Insira '1' para Garçom");
        System.out.println("Insira '2' para Administrador");
        System.out.printf("-->: ");

        int opcao = leitor.nextInt();
        leitor.nextLine();

        if (opcao == 1) {
            System.out.printf("\nUsuário: ");
            String GarcomUsuario = leitor.nextLine();
            System.out.printf("\nSenha: ");
            String GarcomSenha = leitor.nextLine();

            if(login.LoginGarcom(GarcomUsuario, GarcomSenha)){
                System.out.println("Login Feito!");
            }else{
                System.out.println("Login Inválido.");
            }
        }

        leitor.close();
    }
}
