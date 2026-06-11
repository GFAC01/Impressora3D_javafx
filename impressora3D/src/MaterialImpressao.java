/**
 * Representa um filamento de impressão 3D.
 * O custo por grama é o valor central usado no cálculo de custo de material.
 */
public class MaterialImpressao {

    // ===== ATRIBUTOS =====
    private String material;
    private String densidade;      // rótulo exibido no ComboBox (Baixa / Média / Alta)
    private double custoPorGrama;  // R$/g — multiplicado pelos gramas reais no cálculo

    // ===== CONSTRUTOR =====
    MaterialImpressao(String material, String densidade, double custoPorGrama) {
        this.material      = material;
        this.densidade     = densidade;
        this.custoPorGrama = custoPorGrama;
    }

    // ===== GETTERS =====
    public String getMaterial()      { return material; }
    public double getCustoPorGrama() { return custoPorGrama; }

    // Exibido no ComboBox: ex. "Baixa (0.08 R$/g)"
    @Override
    public String toString() {
        return densidade + " (" + custoPorGrama + " R$/g)";
    }
}
