package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import listeners.*;
import model.Aluno;
import model.Escola;


public class GestaoEscolar extends JFrame implements MouseInputListener {
  Container painel = null;
  JButton botaoIncluir = new JButton("Incluir");
  JButton botaoAlterar = new JButton("Alterar");
  JButton botaoExcluir = new JButton("Excluir");
  JButton botaoBuscar = new JButton("Buscar alunos da escola");
  DefaultTableModel modeloTabela = null;
  JTable tabelaDados = null;
  int rowId;
  Escola escolaSelecionada = new Escola();

  public GestaoEscolar() throws SQLException {
      super("Gerenciamento de Escola");
      painel = getContentPane();
      painel.setLayout(new BoxLayout(painel, 1));

      criaTabelaDados();

      JPanel painelBotoesTabela = new JPanel();
      painelBotoesTabela.setLayout(new FlowLayout());

      // Comportamento do botão para abrir janela de adicionar nova escola
      botaoIncluir.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JFrame frame = new JFrame();
          frame.setTitle("Cadastro Escola");
          frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          frame.setSize(800, 200);
          frame.setLayout(new BorderLayout(10, 10));

          JPanel escolaPainel = new JPanel();
          escolaPainel.setLayout(new FlowLayout());

          JLabel labelEscola = new JLabel("Nova Escola");
          JTextField idEscola = new JTextField("Id da escola", 8);
          JTextField nomeEscola = new JTextField("Nome da escola", 20);
          JTextField enderecoEscola = new JTextField("Endereço", 20);
          JButton cadastraEscola = new JButton("Cadastrar");

          // Comportamento do botão para cadastrar escola no banco
          cadastraEscola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Escola novaEscola = new Escola(0, nomeEscola.getText(), enderecoEscola.getText());
              JLabel status = new JLabel();
              if (novaEscola.createSchool()) {
                status.setText("Escola cadastrada!");
                try {
                  Escola newEscola = Escola.readSchool(nomeEscola.getText());
                  modeloTabela.addRow(new String[] {
                    Integer.toString(newEscola.getId()),
                    newEscola.getNome(),
                    newEscola.getEndereco()
                  });
                  modeloTabela.fireTableDataChanged();
                } catch (SQLException e1) {
                  e1.printStackTrace();
                }
              } else {
                status.setText("Não foi possível cadastrar a escola.");
              }
              JFrame msg = new JFrame();
              msg.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
              msg.setTitle("Status da Operação");
              msg.setSize(400, 200);
              msg.setLayout(new BoxLayout(msg, 1));
              msg.add(status);
              msg.setVisible(true);
            };
          });

          escolaPainel.add(labelEscola);
          escolaPainel.add(idEscola);
          escolaPainel.add(nomeEscola);
          escolaPainel.add(enderecoEscola);
          escolaPainel.add(cadastraEscola);
          
          frame.add(escolaPainel);
          frame.setVisible(true);
        };
      });
      // Comportamento do botão para alterar escola cadastrada
      botaoAlterar.addActionListener(new UpdateSchoolListener(escolaSelecionada.getNome(), escolaSelecionada.getEndereco(), escolaSelecionada.getId()) {
        @Override
        public void actionPerformed(ActionEvent e) {
          JFrame frame = new JFrame();
          frame.setTitle("Alteração Escola");
          frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          frame.setSize(800, 200);
          frame.setLayout(new BorderLayout(10, 10));

          JPanel escolaPainel = new JPanel();
          escolaPainel.setLayout(new FlowLayout());

          JLabel labelEscola = new JLabel("Id da escola: ");
          JLabel idEscola = new JLabel(Integer.toString(escolaSelecionada.getId()));
          JTextField nomeEscola = new JTextField(escolaSelecionada.getNome(), 20);
          JTextField enderecoEscola = new JTextField(escolaSelecionada.getEndereco(), 20);
          JButton alterarEscola = new JButton("Alterar com os novos dados");

          alterarEscola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Escola escola = new Escola(escolaSelecionada.getId(), nomeEscola.getText(), enderecoEscola.getText());
              if (escola.updateSchool()) {
                modeloTabela.removeRow(rowId);
                modeloTabela.addRow(new String[] {
                  Integer.toString(escola.getId()),
                  escola.getNome(),
                  escola.getEndereco()
                });
                modeloTabela.fireTableDataChanged();
              }
            }
          });

          escolaPainel.add(labelEscola);
          escolaPainel.add(idEscola);
          escolaPainel.add(nomeEscola);
          escolaPainel.add(enderecoEscola);
          escolaPainel.add(alterarEscola);

          frame.add(escolaPainel);
          frame.setVisible(true);
        }
      });
      // Comportamento do botão para excluir escola cadastrada
      botaoExcluir.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JFrame frame = new JFrame();
          frame.setTitle("Excluir Escola");
          frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          frame.setSize(800, 200);
          frame.setLayout(new BorderLayout(10, 10));

          JPanel escolaPainel = new JPanel();
          escolaPainel.setLayout(new FlowLayout());

          JLabel confirmacao = new JLabel("Deseja excluir a escola " + escolaSelecionada.getNome() + "?");
          JButton btn = new JButton("Excluir");

          btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              JLabel status = new JLabel();
              Escola escola = new Escola(escolaSelecionada.getId(), escolaSelecionada.getNome(), escolaSelecionada.getEndereco());
              if (escola.deleteSchool()) {
                status.setText("Escola Removida!");
                modeloTabela.removeRow(rowId);
                modeloTabela.fireTableDataChanged();
              } else {
                status.setText("Não é possível excluir uma escola que possui alunos.");
              }
              JFrame msg = new JFrame();
              msg.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
              msg.setTitle("Status da Operação");
              msg.setSize(400, 200);
              msg.setLayout(new BorderLayout(10, 10));
              msg.add(status);
              msg.setVisible(true);
            }
            
          });
          escolaPainel.add(confirmacao);
          escolaPainel.add(btn);

          frame.add(escolaPainel);
          frame.setVisible(true);
        }
      });
      // Comportamento do botão para buscar alunos matriculados em uma escola
      botaoBuscar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (escolaSelecionada.getId() < 1) {
            JLabel lbl = new JLabel("Selecione uma escola");
            lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            f.setSize(300, 200);
            f.setLayout(new FlowLayout(FlowLayout.CENTER));
            f.add(lbl);
            f.setVisible(true);
          } else {
            try {
              ArrayList<Aluno> dadosAlunos = Escola.readStudentsFromSchool(escolaSelecionada.getId());
              JFrame frame = new JFrame();
              frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
              frame.setLayout(new FlowLayout(FlowLayout.CENTER));
  
              if (dadosAlunos.size() > 0) {
                String[] tableColumns = new String[] {"Id", "Nome", "CPF", "Matricula", "Email", "Escola Id"};
  
                Container panel = null;
                panel = getContentPane();
                panel.setLayout(new BoxLayout(panel, 1));
                DefaultTableModel tableModel = null;
  
                tableModel = new DefaultTableModel(null, tableColumns);
                frame.setTitle("Alunos matriculados na escola: " + escolaSelecionada.getNome());
  
                for (Aluno aluno: dadosAlunos) {
                  tableModel.addRow(new String[] {
                    Integer.toString(aluno.getId()),
                    aluno.getNome(),
                    aluno.getCpf(),
                    aluno.getMatricula(),
                    aluno.getEmail(),
                    Integer.toString(aluno.getEscolaId())
                  });
                }
                JTable dataTable = new JTable();
                tabelaDados.setModel(tableModel);
  
                JScrollPane pScroll = new JScrollPane(dataTable);
                panel.add(pScroll, BorderLayout.CENTER);
                frame.setSize(800, 300);
                frame.add(panel);
              } else {
                frame.setSize(500, 300);
                JLabel lbl = new JLabel("Nenhum aluno matriculado nesta escola");
                lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
                frame.add(lbl);
              }
              frame.setVisible(true); 
            } catch (SQLException e1) {
              e1.printStackTrace();
            }
          }
        }
        
      });
      painelBotoesTabela.add(botaoIncluir);
      painelBotoesTabela.add(botaoAlterar);
      painelBotoesTabela.add(botaoExcluir);
      painelBotoesTabela.add(botaoBuscar);
      painel.add(new JLabel("Para alterar ou excluir, clique em uma escola na lista e em seguida no botão desejado"));
      painel.add(painelBotoesTabela);

      this.setSize(800, 350);
      this.setVisible(true);
      this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
    tabelaDados.addMouseListener(this);

    JScrollPane painelScroll = new JScrollPane(tabelaDados);
    painel.add(painelScroll, BorderLayout.CENTER);
  }
  // public static void main(String[] args) throws SQLException {
  //   new GestaoEscolar();
  //   // new GestaoAluno();
  // }
  @Override
  public void mouseClicked(MouseEvent e) {}
  @Override
  // Método que atribui os valores da row clicada para que sejam acessados por outra janela para realizar a atualização da escola clidada
  public void mousePressed(MouseEvent e) {
    int row = tabelaDados.rowAtPoint(e.getPoint());
    this.rowId = row;
    Object idEscola = tabelaDados.getValueAt(row, 0);
    Object nome = tabelaDados.getValueAt(row, 1);
    Object endereco = tabelaDados.getValueAt(row, 2);
    escolaSelecionada.setId(Integer.parseInt((String) idEscola));
    escolaSelecionada.setNome((String) nome);
    escolaSelecionada.setEndereco((String) endereco);
  }
  @Override
  public void mouseReleased(MouseEvent e) {}
  @Override
  public void mouseEntered(MouseEvent e) {}
  @Override
  public void mouseExited(MouseEvent e) {}
  @Override
  public void mouseDragged(MouseEvent e) {}
  @Override
  public void mouseMoved(MouseEvent e) {}
}
