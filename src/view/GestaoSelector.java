package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class GestaoSelector extends JFrame implements ActionListener {
  private JFrame frame = new JFrame("Selecione a área de gestão");
    private JPanel panel = new JPanel();
    private JButton botaoEscola = new JButton("Escola");
    private JButton botaoAluno = new JButton("Aluno");
    public GestaoSelector() {
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        botaoEscola.addActionListener(this);
        botaoAluno.addActionListener(this);
        
        panel.add(botaoAluno);
        panel.add(botaoEscola);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      JButton btn = (JButton) e.getSource();
      if (btn.getText() == "Escola") {
        try {
          new GestaoEscolar();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
      } else {
        try {
          new GestaoAluno();
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    }
}
