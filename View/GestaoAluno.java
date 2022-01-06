import aluno.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestaoAluno extends JFrame {
  Container painel = null;
  JButton botaoIncluir = new JButton("Incluir");
  JButton botaoAlterar = new JButton("Alterar");
  JButton botaoExcluir = new JButton("Excluir");
  JButton botaoAlunosPorEscola = new JButton("Alunos por Escola");
  DefaultTableModel modeloTabela = null;
  JTable tabelaDados = null;

  public GestaoAluno() throws SQLException {
      super("Cadastro de Aluno");
      painel = getContentPane();
      painel.setLayout(new BoxLayout(painel, 1));

      criaTabelaDados();

      JPanel painelBotoesTabela = new JPanel();
      painelBotoesTabela.setLayout(new FlowLayout());

      painelBotoesTabela.add(botaoIncluir);
      painelBotoesTabela.add(botaoAlterar);
      painelBotoesTabela.add(botaoExcluir);
      painelBotoesTabela.add(botaoAlunosPorEscola);
      painel.add(painelBotoesTabela);

      this.setSize(800, 350);
      this.setVisible(true);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  private void criaTabelaDados() throws SQLException {
    String[] colunasTabela = new String[] {"Id", "Nome", "Endereço"};

    modeloTabela = new DefaultTableModel(null, colunasTabela);
    ArrayList<Aluno> dadosAlunos = Aluno.readStudentsData(-1);

    for (Aluno aluno: dadosAlunos) {
      modeloTabela.addRow(new String[] {
        Integer.toString(aluno.getId()),
        aluno.getNome(),
        aluno.getCpf(),
        Integer.toString(aluno.getEscolaId())
      });
    }
    tabelaDados = new JTable();
    tabelaDados.setModel(modeloTabela);

    JScrollPane painelScroll = new JScrollPane(tabelaDados);
    painel.add(painelScroll, BorderLayout.CENTER);
  }
  
}
