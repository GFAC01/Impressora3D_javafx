/**
 * Representa uma impressora 3D com seus dados técnicos e visuais.
 * Utilizada nos cálculos de depreciação e custo de energia.
 */
public class Impressora3D {

    // ===== ATRIBUTOS =====
    private String modelo;
    private double preco;      // valor de compra — base para o cálculo de depreciação
    private double potencia;   // potência em watts — usada no cálculo de energia
    private String imagem;     // nome do arquivo de imagem em /imagens/
    private String descricao;

    // ===== CONSTRUTOR =====
    public Impressora3D(String modelo, double preco, double potencia, String descricao, String imagem) {
        this.modelo    = modelo;
        this.preco     = preco;
        this.potencia  = potencia;
        this.imagem    = imagem;
        this.descricao = descricao;
    }

    // ===== GETTERS =====
    public String getModelo()    { return modelo; }
    public double getPreco()     { return preco; }
    public double getPotencia()  { return potencia; }
    public String getImagem()    { return imagem; }
    public String getDescricao() { return descricao; }

    // Exibido no ComboBox da interface
    @Override
    public String toString() { return modelo; }
}
