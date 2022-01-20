package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;

import listeners.UpdateStudentListener;
import model.Aluno;

public class GestaoAluno extends JFrame implements MouseInputListener {
  Container painel = null;
  JButton botaoIncluir = new JButton("Incluir");
  JButton botaoAlterar = new JButton("Alterar");
  JButton botaoExcluir = new JButton("Excluir");
  JButton botaoBuscar = new JButton("Buscar");
  DefaultTableModel modeloTabela = null;
  JTable tabelaDados = null;
  Aluno aluno = new Aluno();
  int rowId;

  public GestaoAluno() throws SQLException {
    super("Gerenciamento de Aluno");
    painel = getContentPane();
    painel.setLayout(new BoxLayout(painel, 1));

    criaTabelaDados();

    JPanel painelBotoesTabela = new JPanel();
    painelBotoesTabela.setLayout(new FlowLayout());

    setAction(botaoIncluir, "cadastrar");
    setAction(botaoAlterar, "alterar");
    setAction(botaoExcluir, "excluir");
    setAction(botaoBuscar, "buscar");

    painelBotoesTabela.add(botaoIncluir);
    painelBotoesTabela.add(botaoAlterar);
    painelBotoesTabela.add(botaoExcluir);
    painelBotoesTabela.add(botaoBuscar);
    painel.add(new JLabel("Para alterar ou excluir, clique em um aluno na lista e em seguida no botão desejado"));
    painel.add(painelBotoesTabela);

    this.setSize(800, 600);
    this.setVisible(true);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }
  private void criaTabelaDados() throws SQLException {
    String[] colunasTabela = new String[] {"Id", "Nome", "CPF", "Matricula", "Email", "Escola Id"};

    modeloTabela = new DefaultTableModel(null, colunasTabela);
    ArrayList<Aluno> dadosAlunos = Aluno.readStudentsData(-1);

    for (Aluno aluno: dadosAlunos) {
      modeloTabela.addRow(new String[] {
        Integer.toString(aluno.getId()),
        aluno.getNome(),
        aluno.getCpf(),
        aluno.getMatricula(),
        aluno.getEmail(),
        Integer.toString(aluno.getEscolaId())
      });
    }
    tabelaDados = new JTable();
    tabelaDados.setModel(modeloTabela);
    tabelaDados.addMouseListener(this);

    JScrollPane painelScroll = new JScrollPane(tabelaDados);
    painel.add(painelScroll, BorderLayout.CENTER);
  }
  private void setAction(JButton btn, String action) {
    switch (action) {
      case "cadastrar":
      btn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JFrame frame = new JFrame();
          frame.setTitle("Cadastro Aluno");
          frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          frame.setSize(800, 300);
          frame.setLayout(new FlowLayout(FlowLayout.CENTER));       

          JPanel alunoPainel = new JPanel();
          alunoPainel.setLayout(new GridLayout(0, 1));
          
          JLabel labelAluno = new JLabel("Novo Aluno");
          JButton cadastraAluno = new JButton("Cadastrar Aluno");

          cadastraAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              JLabel status = new JLabel();
              if (aluno.getNome().length() < 2 || aluno.getCpf().length() < 11 || aluno.getEmail().length() < 5 || aluno.getMatricula().length() < 1 || aluno.getEscolaId() < 0) {
                status.setText("Reveja os dados e tente novamente.");
              } else {
                Aluno novoAluno = new Aluno(0, aluno.getNome(), aluno.getCpf(), aluno.getMatricula(), aluno.getEmail(), aluno.getEscolaId());
                if (novoAluno.createStudent()) {
                  status.setText("Aluno Cadastrado!");
                  try {
                    Aluno newAluno = Aluno.readStudent(novoAluno.getNome());
                    modeloTabela.addRow(new String [] {
                      Integer.toString(newAluno.getId()),
                      newAluno.getNome(),
                      newAluno.getCpf(),
                      newAluno.getMatricula(),
                      newAluno.getEmail(),
                      Integer.toString(newAluno.getEscolaId())
                    });
                    modeloTabela.fireTableDataChanged();
                  } catch (SQLException ex) {
                    ex.printStackTrace();
                  }
                } else {
                  status.setText("Lembre-se de matricular o aluno em uma escola existente.");
                }
              }
              status.setLayout(new FlowLayout(FlowLayout.CENTER));
              JFrame frame = new JFrame();
              frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
              frame.setSize(300, 300);
              frame.setLayout(new FlowLayout(FlowLayout.CENTER));
              frame.add(status); 
              frame.setVisible(true);
            }
          });

          alunoPainel.setAlignmentX(CENTER_ALIGNMENT);
          alunoPainel.add(labelAluno);
          alunoPainel.add(conjuntoFormulario("Id", "", 20, "alunoId"));
          alunoPainel.add(conjuntoFormulario("Nome completo", "", 20, "nome"));
          alunoPainel.add(conjuntoFormulario("CPF", "", 20, "cpf"));
          alunoPainel.add(conjuntoFormulario("Matricula", "", 20, "matricula"));
          alunoPainel.add(conjuntoFormulario("E-mail", "", 20, "email"));
          alunoPainel.add(conjuntoFormulario("Escola do aluno", "", 20, "escola"));
          alunoPainel.add(cadastraAluno);

          JScrollPane painelScroll = new JScrollPane(alunoPainel);
          
          frame.add(painelScroll);
          frame.setVisible(true);
        }
      });
        break;
      case "alterar":
        btn.addActionListener(new UpdateStudentListener(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getMatricula(), aluno.getEmail(), aluno.getEscolaId()) {
          @Override
          public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame();
            frame.setTitle("Alterar Aluno");
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setSize(800, 300);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER));

            JPanel alunoPainel = new JPanel();
            alunoPainel.setLayout(new GridLayout(0, 1));
            
            JLabel labelAluno = new JLabel(aluno.getNome());
            JButton alterarAluno = new JButton("Alterar Aluno");

            alterarAluno.addActionListener(new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                Aluno newAluno = new Aluno(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getMatricula(), aluno.getEmail(), aluno.getEscolaId());
                if (newAluno.updateStudent()) {
                  modeloTabela.setValueAt(Integer.toString(newAluno.getId()), rowId, 0);
                  modeloTabela.setValueAt(newAluno.getNome(), rowId, 1);
                  modeloTabela.setValueAt(newAluno.getCpf(), rowId, 2);
                  modeloTabela.setValueAt(newAluno.getMatricula(), rowId, 3);
                  modeloTabela.setValueAt(newAluno.getEmail(), rowId, 4);
                  modeloTabela.setValueAt(Integer.toString(newAluno.getEscolaId()), rowId, 5);
                  modeloTabela.fireTableDataChanged();
                }
              }
            });

            alunoPainel.setAlignmentX(CENTER_ALIGNMENT);
            alunoPainel.add(labelAluno);
            alunoPainel.add(conjuntoFormulario("Id", Integer.toString(aluno.getId()), 20, "alunoId"));
            alunoPainel.add(conjuntoFormulario("Nome completo", aluno.getNome(), 20, "nome"));
            alunoPainel.add(conjuntoFormulario("CPF", aluno.getCpf(), 20, "cpf"));
            alunoPainel.add(conjuntoFormulario("Matricula", aluno.getMatricula(), 20, "matricula"));
            alunoPainel.add(conjuntoFormulario("E-mail", aluno.getEmail(), 20, "email"));
            alunoPainel.add(conjuntoFormulario("Escola do aluno", Integer.toString(aluno.getEscolaId()), 20, "escola"));
            alunoPainel.add(alterarAluno);

            JScrollPane painelScroll = new JScrollPane(alunoPainel);
            
            frame.add(painelScroll);
            frame.setVisible(true);
          }
        });
        break;
      case "excluir":
        btn.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame();
            frame.setTitle("Excluir Aluno");
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setSize(800, 200);
            frame.setLayout(new BorderLayout(10, 10));
  
            JPanel escolaPainel = new JPanel();
            escolaPainel.setLayout(new FlowLayout());
  
            JLabel confirmacao = new JLabel("Deseja excluir o aluno: " + aluno.getNome() + "?");
            JButton btn = new JButton("Excluir");

            btn.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                JLabel status = new JLabel();
                Aluno alunoAExcluir = new Aluno(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getCpf(), aluno.getEmail(), aluno.getEscolaId());
                if (alunoAExcluir.deleteStudent()) {
                  status.setText("Aluno Removido!");
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
        break;
      case "buscar":
        btn.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame();
            JTextField input = new JTextField("Digite o nome do aluno");
            JButton buscar = new JButton("Pesquisar aluno");
            JPanel painel = new JPanel();
            painel.setLayout(new FlowLayout(FlowLayout.CENTER));

            buscar.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                try {
                  ArrayList<Aluno> dadosAlunos = Aluno.readStudentsData(input.getText());
                  String[] colunasTabela = new String[] {"Id", "Nome", "CPF", "Matricula", "Email", "Escola Id"};

                  DefaultTableModel tableModel = null;
                  tableModel = new DefaultTableModel(null, colunasTabela);

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
                  JFrame frame = new JFrame();
                  frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                  frame.setTitle("Consulta Aluno");
                  frame.setLayout(new FlowLayout(FlowLayout.CENTER));

                  if (dadosAlunos.size() > 0) {
                    tabelaDados = new JTable();
                    tabelaDados.setModel(tableModel);
  
                    frame.setSize(800, 300);
                    frame.add(tabelaDados);
                  } else {
                    frame.setSize(500, 300);
                    JLabel lbl = new JLabel("Nenhum aluno encontrado");
                    lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
                    frame.add(lbl);
                  }
                  frame.setVisible(true);     
                } catch (SQLException e1) {
                  e1.printStackTrace();
                }
              }
              
            });

            painel.add(input);
            painel.add(buscar);
            
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setSize(300, 300);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER));
            frame.add(painel); 
            frame.setVisible(true);
          }
        });
      default:
        break;
    }
  }

  private JPanel conjuntoFormulario(String label, String textField, int size, String attr) {
    JPanel conjuntoForm = new JPanel();
    JTextField text = new JTextField(textField, size);
    text.getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void insertUpdate(DocumentEvent e) {
        setValue(attr);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setValue(attr);
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setValue(attr);
      }
      
      private void setValue(String attr) {
        switch (attr) {
          case "alunoId":
            aluno.setId(Integer.parseInt(text.getText()));
            break;
          case "nome":
            aluno.setNome(text.getText());
            break;
          case "cpf":
            aluno.setCpf(text.getText());
            break;
          case "matricula":
            aluno.setMatricula(text.getText());
            break;
          case "email":
            aluno.setEmail(text.getText());
            break;
          case "escola":
            aluno.setEscolaId(Integer.parseInt(text.getText()));
          default:
            break;
        }
      }
    });
    conjuntoForm.setLayout(new FlowLayout(FlowLayout.RIGHT));
    conjuntoForm.add(new JLabel(label));
    conjuntoForm.add(text);
    return conjuntoForm;
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    int row = tabelaDados.rowAtPoint(e.getPoint());
    // Setando a rowId aqui para caso o usuário queira excluir, caso em que é necessário saber o index da row da table, não do objeto
    this.rowId = row;
    Object idEscola = tabelaDados.getValueAt(row, 0);
    Object nome = tabelaDados.getValueAt(row, 1);
    Object cpf = tabelaDados.getValueAt(row, 2);
    Object matricula = tabelaDados.getValueAt(row, 3);
    Object email = tabelaDados.getValueAt(row, 4);
    Object escolaId = tabelaDados.getValueAt(row, 5);
    aluno.setId(Integer.parseInt((String) idEscola));
    aluno.setNome((String) nome);
    aluno.setCpf((String) cpf);
    aluno.setMatricula((String) matricula);
    aluno.setEmail((String) email);
    aluno.setEscolaId(Integer.parseInt((String) escolaId));
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
