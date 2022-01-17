import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

      botaoIncluir.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JFrame frame = new JFrame();
          frame.setTitle("Cadastro Escola");
          frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          frame.setSize(800, 200);
          frame.setLayout(new BorderLayout(10, 10));
          Container escolaContainer;
          escolaContainer = getContentPane();
          escolaContainer.setLayout(new BoxLayout(escolaContainer, 1));

          JPanel escolaPainel = new JPanel();
          escolaPainel.setLayout(new FlowLayout());

          JLabel labelEscola = new JLabel("Nova Escola");
          JTextField idEscola = new JTextField("Id da escola", 8);
          JTextField nomeEscola = new JTextField("Nome da escola", 20);
          JTextField enderecoEscola = new JTextField("Endereço", 20);
          JButton cadastraEscola = new JButton("Cadastrar");

          cadastraEscola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Escola novaEscola = new Escola(Integer.parseInt(idEscola.getText()), nomeEscola.getText(), enderecoEscola.getText());
              String createMessage =  novaEscola.createSchool();
              frame.remove(escolaPainel);
              JLabel mensagem = new JLabel();
              mensagem.setText(createMessage);
              frame.add(mensagem);
            };
          });

          escolaPainel.add(labelEscola);
          escolaPainel.add(idEscola);
          escolaPainel.add(nomeEscola);
          escolaPainel.add(enderecoEscola);
          escolaPainel.add(cadastraEscola);
          
          // escolaContainer.add(escolaPainel);
          frame.add(escolaPainel);
          frame.setVisible(true);
        };
      });

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
