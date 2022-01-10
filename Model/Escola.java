
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexao.ConexaoMysql;

public class Escola {
  private int id = 0;
  private String nome = "";
  private String endereco = "";
  private Connection conexao = null;

  public Escola(int id, String n, String e) {
    this.id = id;
    this.nome = n;
    this.endereco = e;
    new ConexaoMysql();
    this.conexao = ConexaoMysql.conectar();
  }
  public int getId() {
    return this.id;
  }
  public String getNome() {
    return this.nome;
  }
  public String getEndereco() {
    return this.endereco;
  }
  public void setId(int novoId) {
    this.id = novoId;
  }
  public void setNome(String novoNome) {
    this.nome = novoNome;
  }
  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }
  public boolean createSchool() {
    PreparedStatement pstmt;
    try {
      String sqlQuery = "INSERT INTO escola ("
      +"escola_id, escola_nome, escola_endereco)"
      + "VALUES(default, ?, ?)";

      pstmt = conexao.prepareStatement(sqlQuery);
      pstmt.setString(1, this.nome);
      pstmt.setString(2, this.endereco);

      if (pstmt.executeUpdate() > 0) {
        System.out.println("Escola inserida com sucesso!");
        return true;
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("Escola não inserida.");
    return false;
  }
  public boolean updateSchool() {
    PreparedStatement pstmt;
    try {
      String sqlQuery = "UPDATE escola SET escola_nome = ?, "
      + "escola_endereco = ? WHERE escola_id = ?";

      pstmt = conexao.prepareStatement(sqlQuery);
      pstmt.setString(1, this.nome);
      pstmt.setString(2, this.endereco);
      pstmt.setInt(3, this.id);

      if (pstmt.executeUpdate() > 0) {
        System.out.println("Escola atualizada com sucesso!");
        return true;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Escola não foi atualizada.");
    return false;
  }
  public boolean deleteSchool() {
    PreparedStatement pstmt;
    try {
      String sqlQuery = "DELETE FROM escola"
      + "WHERE escola_id = ?";

      pstmt = conexao.prepareStatement(sqlQuery);
      pstmt.setInt(1, this.id);

      if (pstmt.executeUpdate() > 0) {
        System.out.println("Escola deletada com sucesso!");
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Não foi possível deletar a escola.");
    return false;
  }
  public static ArrayList<Escola> readSchoolsData(int idEscola) throws SQLException {
    ArrayList<Escola> dadosEscolas = new ArrayList<Escola>();
    new ConexaoMysql();
    Connection conexao = ConexaoMysql.conectar();
    String sqlQuery = "";
    PreparedStatement pstmt;

    if (idEscola > 0) {
      sqlQuery = "SELECT escola_id, escola_nome, "
      + "escola_endereco FROM escola WHERE escola_id = ?";
      pstmt = conexao.prepareStatement(sqlQuery);
      pstmt.setInt(1, idEscola);
    } else {
      sqlQuery = "SELECT escola_id, escola_nome, "
      + "escola_endereco FROM escola";
      pstmt = conexao.prepareStatement(sqlQuery);
    }

    ResultSet rs = pstmt.executeQuery();

    while(rs.next()) {
      int id = rs.getInt("escola_id");
      String nome = rs.getString("escola_nome");
      String endereco = rs.getString("escola_endereco");

      Escola escola = new Escola (id, nome, endereco);
      dadosEscolas.add(escola);
    }
    return dadosEscolas;
  }
}