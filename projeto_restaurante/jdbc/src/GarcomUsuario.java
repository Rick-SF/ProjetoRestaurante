import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GarcomUsuario {
    Auxilios auxilios = new Auxilios();
    Scanner leitor = new Scanner(System.in);

    public void MenuGarcom(){
        System.out.println("----------Opções de Garçom----------");
        System.out.println("1: Anotar Pedido");
        System.out.println("2: Cardápio");
        System.out.println("3: Pedidos");
        System.out.println("4: Cancelar Pedido");
        System.out.println("5: Imprimir Comanda");
        System.out.println("6: Pagar Conta");
        System.out.println("7: Deslogar");
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
                System.out.printf("--------------------\nPrato n° %d:\nPrato: %s\nValor: R$%.2f\nDisponivel: %s\n",count2 ,prato, valor, disponivel);
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

    public void AnotarNovoPedido(int numMesa, int ocupacao, String pratoPedido, String usuarioGarcom){
        String sqlMesa = "INSERT INTO mesa (numero, ocupação) VALUES (?, ?)";
        String sqlIDgarcom = "SELECT id_garcom FROM login WHERE usuario = ?";
        String sqlIDprato = "SELECT id FROM prato WHERE nome = ?";
        String sqlPedidos = "INSERT INTO pedidos (num_mesa, id_prato, id_garcom) VALUES (?, ?, ?)";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt1 = conexao.prepareStatement(sqlMesa);
            PreparedStatement stmt2 = conexao.prepareStatement(sqlIDgarcom);
            PreparedStatement stmt3 = conexao.prepareStatement(sqlIDprato);
            PreparedStatement stmt4 = conexao.prepareStatement(sqlPedidos)){

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
            
            stmt3.setString(1, pratoPedido);
            ResultSet rs2 = stmt3.executeQuery();
            int IDprato = -1;
            if (rs2.next()) {
                IDprato = rs2.getInt("id");
            }
            if (IDprato == -1) {
                throw new SQLException("Prato não Encontrado");
            }

            stmt4.setInt(1, numMesa);
            stmt4.setInt(2, IDprato);
            stmt4.setInt(3, IDgarcom);
            stmt4.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean MesaExistente(int numMesa){
        boolean MesaExistente = false;
        String sqlMesa = "SELECT COUNT(*) FROM mesa WHERE numero = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt1 = conexao.prepareStatement(sqlMesa)){

            stmt1.setInt(1, numMesa);

            try(ResultSet rs = stmt1.executeQuery()){
                if (rs.next()) {
                    MesaExistente = rs.getInt(1) > 0;
                }
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return MesaExistente;
    }

    public void AnotarPedidoNovamente(int numMesa, String pratoPedido, String usuarioGarcom){
        String sqlIDprato = "SELECT id FROM prato WHERE nome = ?";
        String sqlIDgarcom = "SELECT id_garcom FROM login WHERE usuario = ?";
        String sqlPedido = "INSERT INTO pedidos (num_mesa, id_prato, id_garcom) VALUES (?, ?, ?)";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt1 = conexao.prepareStatement(sqlIDprato);
            PreparedStatement stmt2 = conexao.prepareStatement(sqlIDgarcom);
            PreparedStatement stmt3 = conexao.prepareStatement(sqlPedido)){
                            
            stmt1.setString(1, pratoPedido);
            ResultSet rs1 = stmt1.executeQuery();
            int IDprato = -1;
            if (rs1.next()) {
                IDprato = rs1.getInt("id");
            }
            if (IDprato == -1) {
                throw new SQLException("Prato não Encontrado");
            }

            stmt2.setString(1, usuarioGarcom);
            ResultSet rs2 = stmt2.executeQuery();
            int IDgarcom = -1;
            if (rs2.next()) {
                IDgarcom = rs2.getInt("id_garcom");
            }
            if (IDgarcom == -1) {
                throw new SQLException("Garçom não encontrado");
            }

            stmt3.setInt(1, numMesa);
            stmt3.setInt(2, IDprato);
            stmt3.setInt(3, IDgarcom);
            stmt3.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void ConsultarPedidos(){        
        String sql = "SELECT mesa.numero AS num_mesa, prato.nome AS nome_prato, garcom.nome AS nome_garcom " +
                     "FROM pedidos " +
                     "INNER JOIN mesa ON pedidos.num_mesa = mesa.numero " +
                     "INNER JOIN prato ON pedidos.id_prato = prato.id " +
                     "INNER JOIN garcom ON pedidos.id_garcom = garcom.id";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            // Mapa para armazenar pedidos agrupados por mesa e garçom
            Map<Integer, Map<String, List<String>>> mesaPedidosMap = new HashMap<>();

            while (rs.next()) {
                int numMesa = rs.getInt("num_mesa");
                String nomePrato = rs.getString("nome_prato");
                String nomeGarcom = rs.getString("nome_garcom");

                // Agrupar pedidos por mesa e por garçom
                mesaPedidosMap.putIfAbsent(numMesa, new HashMap<>());
                Map<String, List<String>> garcomPratosMap = mesaPedidosMap.get(numMesa);
                garcomPratosMap.putIfAbsent(nomeGarcom, new ArrayList<>());
                garcomPratosMap.get(nomeGarcom).add(nomePrato);
            }

            // Iterar sobre o mapa para exibir os resultados agrupados
            for (Map.Entry<Integer, Map<String, List<String>>> mesaEntry : mesaPedidosMap.entrySet()) {
                int numMesa = mesaEntry.getKey();
                System.out.println("-------------------\nMESA: " + numMesa);
                for (Map.Entry<String, List<String>> garcomEntry : mesaEntry.getValue().entrySet()) {
                    String nomeGarcom = garcomEntry.getKey();
                    System.out.println("  GARÇOM: " + nomeGarcom);
                    System.out.println("    PEDIDOS:");
                    int i = 1;
                    for (String nomePrato : garcomEntry.getValue()) {
                        System.out.println("    "+i+"°: " + nomePrato);
                        i++;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CancelarPedido(int numMesa){
        String sqlPedidos = "DELETE FROM pedidos WHERE num_mesa = ?";
        String sqlMesa = "DELETE FROM mesa WHERE numero = ?";
        
        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt1 = conexao.prepareStatement(sqlPedidos);
            PreparedStatement stmt2 = conexao.prepareStatement(sqlMesa)){
            
            stmt1.setInt(1, numMesa);
            stmt1.executeUpdate();

            stmt2.setInt(1, numMesa);
            stmt2.executeUpdate();
        
        } catch(SQLException e){
            e.printStackTrace();
        }    

    }

    public void ImprimirComanda(int numMesa){
        String sqlPedidos = "SELECT id_prato FROM pedidos WHERE num_mesa = ?";
        String sqlPrato = "SELECT nome, valor FROM prato WHERE id = ?";

        try (Connection conexao = conectiondb.conectar();
        PreparedStatement stmt1 = conexao.prepareStatement(sqlPedidos);
        PreparedStatement stmt2 = conexao.prepareStatement(sqlPrato)){

        // Obter os IDs dos Pratos da mesa específica
        stmt1.setInt(1, numMesa);
        ResultSet rsPedidos = stmt1.executeQuery();

        // Contabilizar a quantidade de cada prato
        Map<Integer, Integer> pratosContagem = new HashMap<>();
        while (rsPedidos.next()) {
            int idPrato = rsPedidos.getInt("id_prato");
            pratosContagem.put(idPrato, pratosContagem.getOrDefault(idPrato, 0) + 1);
        }

        // Obter detalhes do prato e calcular o valor total
        double total = 0.0;
        System.out.println("Comanda Para a Mesa: "+ numMesa);
        System.out.println("--------------------");
        for(Map.Entry<Integer, Integer> entry : pratosContagem.entrySet()){
            int idPrato = entry.getKey();
            int quantidade = entry.getValue();

            // Obter detalhes do prato
            stmt2.setInt(1, idPrato);
            ResultSet rsPrato = stmt2.executeQuery();
            if (rsPrato.next()) {
                String nomePrato = rsPrato.getString("nome");
                double valorPrato = rsPrato.getDouble("valor");
                double subtotal = quantidade * valorPrato;
                total += subtotal;
                System.out.printf("%-20s x%d  R$%.2f%n", nomePrato, quantidade, subtotal);
            }
        }

        System.out.println("--------------------");
        System.out.printf("Total: R$%.2f%n", total);
         
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean pagarConta(int numMesa) {
        String sqlPedidos = "SELECT id_prato FROM pedidos WHERE num_mesa = ?";
        String sqlPrato = "SELECT nome, valor FROM prato WHERE id = ?";
        String sqlDeletePedidos = "DELETE FROM pedidos WHERE num_mesa = ?";
        String sqlDeleteMesa = "DELETE FROM mesa WHERE numero = ?";
        String sqlInsertComanda = "INSERT INTO comandas (num_mesa, prato, quantidade, valor_unitario, valor_total, metodo_pagamento, valor_pago, data_hora) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = conectiondb.conectar();
             PreparedStatement stmtPedidos = conexao.prepareStatement(sqlPedidos);
             PreparedStatement stmtPrato = conexao.prepareStatement(sqlPrato);
             PreparedStatement stmtDeletePedidos = conexao.prepareStatement(sqlDeletePedidos);
             PreparedStatement stmtDeleteMesa = conexao.prepareStatement(sqlDeleteMesa);
             PreparedStatement stmtInsertComanda = conexao.prepareStatement(sqlInsertComanda)) {

            // Obter todos os id_prato para a mesa específica
            stmtPedidos.setInt(1, numMesa);
            ResultSet rsPedidos = stmtPedidos.executeQuery();

            // Contabilizar a quantidade de cada prato
            Map<Integer, Integer> pratosContagem = new HashMap<>();
            while (rsPedidos.next()) {
                int idPrato = rsPedidos.getInt("id_prato");
                pratosContagem.put(idPrato, pratosContagem.getOrDefault(idPrato, 0) + 1);
            }

            // Obter detalhes dos pratos e calcular o total
            double total = 0.0;
            Map<Integer, String> pratosDetalhes = new HashMap<>();
            Map<Integer, Double> pratosValores = new HashMap<>();

            boolean pagar = true;
            String metodoPagamento = "";
            double valorPago = 0.0;
            double troco = 0.0;
            while (pagar) {
                total = 0.0;
                System.out.println("Comanda para a Mesa: " + numMesa);
                System.out.println("-------------------------------");
                for (Map.Entry<Integer, Integer> entry : pratosContagem.entrySet()) {
                    int idPrato = entry.getKey();
                    int quantidade = entry.getValue();

                    // Obter detalhes do prato
                    stmtPrato.setInt(1, idPrato);
                    ResultSet rsPrato = stmtPrato.executeQuery();
                    if (rsPrato.next()) {
                        String nomePrato = rsPrato.getString("nome");
                        double valorPrato = rsPrato.getDouble("valor");
                        double subtotal = quantidade * valorPrato;
                        total += subtotal;
                        pratosDetalhes.put(idPrato, nomePrato);
                        pratosValores.put(idPrato, valorPrato);
                        System.out.printf("%-20s x%d  R$%.2f%n", nomePrato, quantidade, subtotal);
                    }
                }

                System.out.println("-------------------------------");
                System.out.printf("Total: R$%.2f%n", total);

                
                // Perguntar método de pagamento
            
                System.out.println("\nEscolha o método de pagamento: ");
                System.out.println("1: Espécie");
                System.out.println("2: Pix/Cartão");
                System.out.printf("--> ");
                int metodo = leitor.nextInt();

                if (metodo == 1) {
                    metodoPagamento = "especie";
                    System.out.println("Insira o valor dado: ");
                    valorPago = leitor.nextDouble();
                    if (valorPago > total) {
                        troco = valorPago - total;
                        System.out.printf("Pagamento realizado com sucesso. Troco: R$%.2f%n", troco);
                        auxilios.limparTerminal(3000);
                        break;
                    } else if (valorPago < total) {
                        double faltante = total - valorPago;
                        System.out.printf("Valor insuficiente. Falta: R$%.2f%n", faltante);
                        auxilios.limparTerminal(3000);
                        continue;
                    } else {
                        System.out.println("Pagamento realizado com sucesso. Não há troco.");
                        auxilios.limparTerminal(3000);
                        break;
                    }
                } else if (metodo == 2) {
                    metodoPagamento = "pix/cartao";
                    valorPago = total;
                    System.out.println("Pagamento realizado com sucesso.");
                    auxilios.limparTerminal(3000);
                    break;
                } else {
                    System.out.println("Método de pagamento inválido.");
                    auxilios.limparTerminal(1000);
                }
            }

            // Registrar comanda na tabela comandas
            Timestamp dataHora = new Timestamp(System.currentTimeMillis());
            for (Map.Entry<Integer, Integer> entry : pratosContagem.entrySet()) {
                int idPrato = entry.getKey();
                String prato = pratosDetalhes.get(idPrato);
                int quantidade = entry.getValue();
                double valorUnitario = pratosValores.get(idPrato);

                stmtInsertComanda.setInt(1, numMesa);
                stmtInsertComanda.setString(2, prato);
                stmtInsertComanda.setInt(3, quantidade);
                stmtInsertComanda.setDouble(4, valorUnitario);
                stmtInsertComanda.setDouble(5, total);
                stmtInsertComanda.setString(6, metodoPagamento);
                stmtInsertComanda.setDouble(7, valorPago);
                stmtInsertComanda.setTimestamp(8, dataHora);
                stmtInsertComanda.addBatch();
            }
            stmtInsertComanda.executeBatch();

            // Apagar registros da tabela pedidos
            stmtDeletePedidos.setInt(1, numMesa);
            stmtDeletePedidos.executeUpdate();

            // Apagar registro da tabela mesa
            stmtDeleteMesa.setInt(1, numMesa);
            stmtDeleteMesa.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}