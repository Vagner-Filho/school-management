import java.sql.SQLException;

import view.GestaoEscolar;

public class Main {
  public static void main(String[] args) {
    try {
      new GestaoEscolar();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
