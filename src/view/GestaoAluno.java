package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
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
          frame.setSize(800, 200);
          frame.setLayout(new BorderLayout(10, 10));       

          JPanel alunoPainel = new JPanel();
          alunoPainel.setLayout(new GridLayout(0, 1));

          JPanel conjuntoNomeAluno = new JPanel();
          conjuntoNomeAluno.setLayout(new FlowLayout());
          
          JLabel labelAluno = new JLabel("Novo Aluno");
          // JTextField nomeAluno = new JTextField("Nome", 20);
          // JTextField cpfAluno = new JTextField("CPF", 11);
          // JTextField matriculaAluno = new JTextField("Matricula", 11);
          // JTextField emailAluno = new JTextField("Email", 11);
          // JTextField escolaId = new JTextField("Id da Escola", 11);
          JButton cadastraAluno = new JButton("Cadastrar");
          alunoPainel.setAlignmentX(CENTER_ALIGNMENT);
          alunoPainel.add(labelAluno);
          // alunoPainel.add(idAluno);
          alunoPainel.add(conjuntoFormulario("Id", "", 20));
          // alunoPainel.add(nomeAluno);
          alunoPainel.add(conjuntoFormulario("Nome completo", "", 20));
          alunoPainel.add(conjuntoFormulario("CPF", "", 20));
          alunoPainel.add(conjuntoFormulario("Matricula", "", 20));
          alunoPainel.add(conjuntoFormulario("E-mail", "", 20));
          alunoPainel.add(conjuntoFormulario("Escola do aluno", "", 20));
          alunoPainel.add(cadastraAluno);
  
          frame.add(alunoPainel);
          frame.setVisible(true);
        }
      });
        break;
      default:
        break;
    }
  }

  private JPanel conjuntoFormulario(String label, String textField, int size) {
    JPanel conjuntoForm = new JPanel();
    JTextField text = new JTextField(textField, size);
    conjuntoForm.setLayout(new FlowLayout());
    conjuntoForm.add(new JLabel(label));
    conjuntoForm.add(text);
    return conjuntoForm;
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }
  
}
