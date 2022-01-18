package listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class UpdateSchoolMouseListener implements MouseInputListener {
  public int id;
  public String novoNomeEscola, novoEnderecoEscola;

  public UpdateSchoolMouseListener(int id, String novoNomeEscola, String novoEnderecoEscola) {
    this.id = id;
    this.novoNomeEscola = novoNomeEscola;
    this.novoEnderecoEscola = novoEnderecoEscola;
  }

  public UpdateSchoolMouseListener() {

  }

  public void setListener(int id, String nome, String endereco) {
    this.id = id;
    this.novoNomeEscola = nome;
    this.novoEnderecoEscola = endereco;
  }

  public String toString() {
    return "id=" + Integer.toString(this.id) + "&novoNomeEscola=" + this.novoNomeEscola + "&novoEnderecoEscola=" + this.novoEnderecoEscola;    
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
