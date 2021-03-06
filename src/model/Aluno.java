package model;

import java.sql.*;
import java.util.ArrayList;

public class Aluno {
  private int id;
  private String nome;
  private String cpf;
  private String matricula;
  private String email;
  private int escola_id;
  private Connection conexao = null;

  public Aluno(int id, String n, String cpf, String matricula, String email, int escola_id) {
    this.id = id;
    this.nome = n;
    this.cpf = cpf;
    this.matricula = matricula;
    this.email = email;
    this.escola_id = escola_id;
    new ConexaoMysql();
    this.conexao = ConexaoMysql.conectar();
  }
  public Aluno() {};
  public int getId() {
    return this.id;
  }
  public String getNome() {
    return this.nome;
  }
  public String getCpf() {
    return this.cpf;
  }
  public String getMatricula() {
    return this.matricula;
  }
  public String getEmail() {
    return this.email;
  }
  public int getEscolaId() {
    return this.escola_id;
  }
  public void setId(int novoId) {
    this.id = novoId;
  }
  public void setNome(String novoNome) {
    this.nome = novoNome;
  }
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public void setEscolaId(int escolaId) {
    this.escola_id = escolaId;
  }
  public boolean createStudent() {
    PreparedStatement pstmt;
    try {
      String sqlQuery = "INSERT INTO aluno ("
      +"aluno_id, aluno_nome, aluno_cpf, aluno_matricula, aluno_email, aluno_escola_id)"
      + "VALUES(default, ?, ?, ?, ?, ?)";

      pstmt = conexao.prepareStatement(sqlQuery);

      pstmt.setString(1, this.nome);
      pstmt.setString(2, this.cpf);
      pstmt.setString(3, this.matricula);
      pstmt.setString(4, this.email);
      pstmt.setInt(5, this.escola_id);

      if (pstmt.executeUpdate() > 0) {
        System.out.println("Aluno inserido com sucesso!");
        return true;
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("Aluno n??o inserido.");
    return false;
  }
  public boolean updateStudent() {
    PreparedStatement pstmt;
    try {
      String sqlQuery = "UPDATE aluno SET aluno_nome = ?, aluno_cpf = ?, aluno_matricula = ?, aluno_email = ?, aluno_escola_id = ? WHERE aluno_id = ?";

      pstmt = conexao.prepareStatement(sqlQuery);
      pstmt.setString(1, this.nome);
      pstmt.setString(2, this.cpf);
      pstmt.setString(3, this.matricula);
      pstmt.setString(4, this.email);
      pstmt.setInt(5, this.escola_id);
      pstmt.setInt(6, this.id);

      if (pstmt.executeUpdate() > 0) {
        System.out.println("Aluno atualizado com sucesso!");
        return true;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Aluno n??o foi atualizado.");
    return false;
  }
  public boolean deleteStudent() {
    PreparedStatement pstmt;
    try {
      String sqlQuery = "DELETE FROM aluno "
      + "WHERE aluno_id = ?";

      pstmt = conexao.prepareStatement(sqlQuery);
      pstmt.setInt(1, this.id);

      if (pstmt.executeUpdate() > 0) {
        System.out.println("Aluno deletado com sucesso!");
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("N??o foi poss??vel deletar o aluno.");
    return false;
  }
  public static Aluno readStudent(String nomeAluno) throws SQLException {
    new ConexaoMysql();
    Connection conexao = ConexaoMysql.conectar();
    String sqlQuery = "";
    PreparedStatement pstmt;

    sqlQuery = "SELECT * FROM aluno WHERE aluno_nome = ?";

    pstmt = conexao.prepareStatement(sqlQuery);
    pstmt.setString(1, nomeAluno);

    ResultSet rs = pstmt.executeQuery();
    rs.next();
    Aluno aluno = new Aluno(rs.getInt("aluno_id"), rs.getString("aluno_nome"), rs.getString("aluno_cpf"), rs.getString("aluno_matricula"), rs.getString("aluno_email"), rs.getInt("aluno_escola_id"));
    return aluno;
  }
  public static ArrayList<Aluno> readStudentsData(int idAluno) throws SQLException {
    ArrayList<Aluno> dadosAlunos = new ArrayList<Aluno>();
    new ConexaoMysql();
    Connection conexao = ConexaoMysql.conectar();
    String sqlQuery = "";
    PreparedStatement pstmt;

    if (idAluno > 0) {
      sqlQuery = "SELECT aluno_id, aluno_nome, "
      + "aluno_cpf, aluno_matricula, aluno_email, aluno_escola_id FROM aluno WHERE aluno_id = ?";
      pstmt = conexao.prepareStatement(sqlQuery);
      pstmt.setInt(1, idAluno);
    } else {
      sqlQuery = "SELECT aluno_id, aluno_nome, aluno_cpf, aluno_matricula, aluno_email, aluno_escola_id FROM aluno";
      pstmt = conexao.prepareStatement(sqlQuery);
    }

    ResultSet rs = pstmt.executeQuery();

    while(rs.next()) {
      int id = rs.getInt("aluno_id");
      String nome = rs.getString("aluno_nome");
      String cpf = rs.getString("aluno_cpf");
      String matricula = rs.getString("aluno_matricula");
      String email = rs.getString("aluno_email");
      int escola = rs.getInt("aluno_escola_id");

      Aluno aluno = new Aluno (id, nome, cpf, matricula, email, escola);
      dadosAlunos.add(aluno);
    }
    return dadosAlunos;
  }
  public static ArrayList<Aluno> readStudentsData(String nomeAluno) throws SQLException {
    ArrayList<Aluno> dadosAlunos = new ArrayList<Aluno>();
    new ConexaoMysql();
    Connection conexao = ConexaoMysql.conectar();
    String sqlQuery = "";
    PreparedStatement pstmt;

    sqlQuery = "SELECT * FROM aluno WHERE aluno_nome LIKE ?";

    pstmt = conexao.prepareStatement(sqlQuery);
    pstmt.setString(1, "%" + nomeAluno + "%");
    ResultSet rs = pstmt.executeQuery();

    while(rs.next()) {
      int id = rs.getInt("aluno_id");
      String nome = rs.getString("aluno_nome");
      String cpf = rs.getString("aluno_cpf");
      String matricula = rs.getString("aluno_matricula");
      String email = rs.getString("aluno_email");
      int escola = rs.getInt("aluno_escola_id");

      Aluno aluno = new Aluno (id, nome, cpf, matricula, email, escola);
      dadosAlunos.add(aluno);
    }
    return dadosAlunos;
  }
  // public static ArrayList<Aluno> readStudentsPerSchool(int idEscola) throws SQLException {
  //   ArrayList<Aluno> dadosAlunos = new ArrayList<Aluno>();
  //   new ConexaoMysql();
  //   Connection conexao = ConexaoMysql.conectar();
  //   String sqlQuery = "";
  //   PreparedStatement pstmt;

  //   if (idEscola > 0) {
  //     sqlQuery = "SELECT aluno_id, aluno_nome, "
  //     + "aluno_cpf, aluno_matricula, aluno_email, aluno_escola_id FROM aluno WHERE aluno_escola_id = ?";
  //     pstmt = conexao.prepareStatement(sqlQuery);
  //     pstmt.setInt(1, idEscola);
  //   } else {
  //     sqlQuery = "SELECT aluno_id, aluno_nome, aluno_cpf, aluno_matricula, aluno_email, aluno_escola_id FROM aluno GROUP BY aluno_escola_id";
  //     pstmt = conexao.prepareStatement(sqlQuery);
  //   }

  //   ResultSet rs = pstmt.executeQuery();

  //   while(rs.next()) {
  //     int id = rs.getInt("aluno_id");
  //     String nome = rs.getString("aluno_nome");
  //     String cpf = rs.getString("aluno_cpf");
  //     String matricula = rs.getString("aluno_matricula");
  //     String email = rs.getString("aluno_email");
  //     int escola = rs.getInt("aluno_escola_id");

  //     Aluno aluno = new Aluno (id, nome, cpf, matricula, email, escola);
  //     dadosAlunos.add(aluno);
  //   }

  //   return dadosAlunos;
  // }
}
