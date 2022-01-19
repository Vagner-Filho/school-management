package listeners;

import java.awt.event.*;

public class UpdateSchoolListener implements ActionListener {
  public String nome, endereco;
  public int id;

  public UpdateSchoolListener(String novoNomeDaEscola, String novoEnderecoDaEscola, int idDaEscola) {
    this.nome = novoNomeDaEscola;
    this.endereco = novoEnderecoDaEscola;
    this.id = idDaEscola;
  }

  @Override
  public void actionPerformed(ActionEvent e) {}
  
}
