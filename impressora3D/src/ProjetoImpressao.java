/**
 * Representa o projeto/objeto que será impresso.
 * Armazena apenas o nome; pode ser expandida futuramente com mais atributos.
 */
public class ProjetoImpressao {

    // ===== ATRIBUTO =====
    private String objeto; // nome identificador do projeto

    // ===== CONSTRUTOR =====
    ProjetoImpressao(String objeto) {
        this.objeto = objeto;
    }

    // ===== GETTER =====
    public String getNome() { return objeto; }

    // Exibido onde a classe for referenciada como texto
    @Override
    public String toString() { return objeto; }
}
