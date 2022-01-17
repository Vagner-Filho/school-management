// import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMysql {
  private static Connection conexao;

  public static Connection conectar() {
    // String nomeDriver = "com.mysql.jdbc.Driver";
    String usuario = "root", senha = "luaazul69";

    try {
      // Class.forName(nomeDriver);

      conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/escola?user=" + usuario + "&password=" + senha);
      System.out.println("Conexão efetuada!");
      
      return conexao;

    } catch (SQLException E) {
      System.err.println("SQLException: " + E.getMessage());

    } catch (Exception E) {
      E.printStackTrace();
    }
    System.out.println("Erro: Conexão com o banco não efetuada.");
    return null;
  }
}
