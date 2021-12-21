import java.sql.Connection;

public class Escola {
  private int id = 0;
  private String nome = "";
  private String endereco = "";
  private Connection conexao = null;

  public Escola(int id, String n, String e) {
    this.id = id;
    this.nome = n;
    this.endereco = e;
    this.conexao = new ConexaoMysql().conectar();
  }
  public int getId() {
    return this.id;
  }
  public String getNome() {
    return this.nome;
  }
  public String getEndereco() {
    return this.endereco;
  }
  public void setId(int novoId) {
    this.id = novoId;
  }
  public void setNome(String novoNome) {
    this.nome = novoNome;
  }
  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }
}