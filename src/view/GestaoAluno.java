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

    painelBotoesTabela.add(botaoIncluir);
    painelBotoesTabela.add(botaoAlterar);
    painelBotoesTabela.add(botaoExcluir);
    painelBotoesTabela.add(botaoBuscar);
    painel.add(new JLabel("Para alterar ou excluir, clique em uma escola na lista e em seguida no botão desejado"));
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
              if (aluno.getNome().length() < 2 || aluno.getCpf().length() < 11 || aluno.getEmail().length() < 5 || aluno.getMatricula().length() < 1 || aluno.getEscolaId() < 0) {
                // TODO: Treat
              } else {
                aluno.setId(0);
                if (aluno.createStudent()) {
                  JLabel status = new JLabel("Aluno cadastrado!");
                  status.setLayout(new FlowLayout(FlowLayout.CENTER));
                  JFrame frame = new JFrame();
                  frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                  frame.setSize(300, 300);
                  frame.setLayout(new FlowLayout(FlowLayout.CENTER));
                  frame.add(status); 
                  frame.setVisible(true);
                }
              }
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
  public void mousePressed(MouseEvent e) {}

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
