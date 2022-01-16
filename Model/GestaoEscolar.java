import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class GestaoEscolar extends JFrame {
  Container painel = null;
  JButton botaoIncluir = new JButton("Incluir");
  JButton botaoAlterar = new JButton("Alterar");
  JButton botaoExcluir = new JButton("Excluir");
  DefaultTableModel modeloTabela = null;
  JTable tabelaDados = null;

  public GestaoEscolar() throws SQLException {
      super("Cadastro de Escola");
      painel = getContentPane();
      painel.setLayout(new BoxLayout(painel, 1));

      criaTabelaDados();

      JPanel painelBotoesTabela = new JPanel();
      painelBotoesTabela.setLayout(new FlowLayout());

      painelBotoesTabela.add(botaoIncluir);
      painelBotoesTabela.add(botaoAlterar);
      painelBotoesTabela.add(botaoExcluir);
      painel.add(painelBotoesTabela);

      this.setSize(800, 350);
      this.setVisible(true);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  private void criaTabelaDados() throws SQLException {
    String[] colunasTabela = new String[] {"Id", "Nome", "Endereço"};

    modeloTabela = new DefaultTableModel(null, colunasTabela);
    ArrayList<Escola> dadosEscolas = Escola.readSchoolsData(-1);

    for (Escola escola: dadosEscolas) {
      modeloTabela.addRow(new String[] {
        Integer.toString(escola.getId()),
        escola.getNome(),
        escola.getEndereco()
      });
    }
    tabelaDados = new JTable();
    tabelaDados.setModel(modeloTabela);

    JScrollPane painelScroll = new JScrollPane(tabelaDados);
    painel.add(painelScroll, BorderLayout.CENTER);
  }
  public static void main(String[] args) throws SQLException {
    new GestaoEscolar();
    // new GestaoAluno();
  }
}
