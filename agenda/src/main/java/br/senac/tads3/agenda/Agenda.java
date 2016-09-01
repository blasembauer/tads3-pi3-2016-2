package br.senac.tads3.agenda;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Agenda extends ConexaoBD {

    private static Scanner entrada = new Scanner(System.in);

    //Metodo de processmaneto - Listar
    public void listar() {
        PreparedStatement stmt = null;
        Connection conn = null;

        // 1) Abrir conexao 
        String sql = "SELECT NM_CONTATO FROM TB_CONTATO";

        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            ResultSet resultset = stmt.executeQuery();
            // 2) Exectuar SQL 
            while (resultset.next()) {
                String NM_CONTATO = resultset.getString("NM_CONTATO");
                System.out.println(NM_CONTATO);
            }
        } catch (SQLException e) {
            System.out.println("Nao foi possivel executar.");
        } catch (ClassNotFoundException e) {
            System.out.println("Nao foi possivel executar.");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Erro a fechar stmt.");
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Erro a fechar conn.");
                }
            }
        }
    }

    //Metodo de processamento - Deletado
    public void deletar() {
        //Nome no qual sera apagado
        System.out.print("Digite o nome para ser apagado: ");
        String nome = entrada.nextLine();

        PreparedStatement stmt = null;
        Connection conn = null;

        String sql = "DELETE FROM TB_CONTATO WHERE NM_CONTATO = '" + nome + "'";

        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.execute();
            System.out.println("Contato apagado");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel executar.");
        } catch (ClassNotFoundException e) {
            System.out.println("Nao foi possivel executar.");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Erro a fechar stmt.");
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Erro a fechar conn.");
                }
            }
        }

    }

    //Metodo de processamento - Atualizar
    public void atualizar() {
        PreparedStatement stmt = null;
        Connection conn = null;

        // 1) Abrir conexao 
    }

    // Metodo de processamento - Incluir
    public void incluir() {
        System.out.print("Digite o nome completo do contato: ");
        String nome = entrada.nextLine();

        System.out.print("Digite a data de nascimento no formato dd/mm/aaa: ");
        String strDataNasc = entrada.nextLine();

        System.out.print("Digite o e-mail: ");
        String email = entrada.nextLine();

        System.out.print("Digite o telefone no formato 99 99999-9999: ");
        String telefone = entrada.nextLine();

        // 1) Abrir conexao 
        PreparedStatement stmt = null;
        Connection conn = null;

        String sql = "INSERT INTO TB_CONTATO (NM_CONTATO, DT_NASCIMENTO, "
                + "VL_TELEFONE, VL_EMAIL, DT_CADASTRO)"
                + "VALUES (? ,? , ?, ?, ?)";

        try {
            conn = obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);

            DateFormat formatador = new SimpleDateFormat("dd/MM/yyy");
            Date dataNasc = null;
            try {
                dataNasc = formatador.parse(strDataNasc);
            } catch (ParseException ex) {
                System.out.println("Data de nascimento invalida.");
            }
            stmt.setDate(2, new java.sql.Date(dataNasc.getTime()));
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));

            // 2) Exectuar SQL
            stmt.executeUpdate();
            System.out.println("Cotato cadastrado com sucesso");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel executar.");
        } catch (ClassNotFoundException e) {
            System.out.println("Nao foi possivel executar.");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Erro a fechar stmt.");
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Erro a fechar conn.");
                }
            }
        }

    }

    // 3) Fechar conexao
//Funcao de gereciamento - MAIN
    public static void main(String[] args) {
        Agenda instancia = new Agenda();

        do {
            System.out.println("****DIGITE UMA OPCAO****");
            System.out.println("(1) Listar contatos");
            System.out.println("(2) Incluir novo contato");
            System.out.println("(3) Atualizar um contato");
            System.out.println("(4) Deletar um contato");
            System.out.println("(5) Sair");
            System.out.println("Opcao:");

            String srtOpcao = entrada.nextLine();
            int opcao = Integer.parseInt(srtOpcao);

            switch (opcao) {
                case 1:
                    instancia.listar();
                    break;
                case 2:
                    instancia.incluir();
                    break;
                case 3:
                    instancia.atualizar();
                    break;
                case 4:
                    instancia.deletar();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("OPCAO INVALIDA");
            }
        } while (true);
    }

}
