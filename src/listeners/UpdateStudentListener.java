package listeners;

import java.awt.event.*;

public class UpdateStudentListener implements ActionListener {
  public String nome, cpf, matricula, email;
  public int id, escola_id;
  public UpdateStudentListener(int id, String nome, String cpf, String matricula, String email, int escola_id) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.matricula = matricula;
    this.email = email;
    this.escola_id = escola_id;
  }
  @Override
  public void actionPerformed(ActionEvent e) {}
  
}
