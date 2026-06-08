public class MaterialImpressao {
    private String material;
    private String densidade;
    private double custoPorGrama;

    MaterialImpressao(String material, String densidade, double custoPorGrama){
        this.material = material;
        this.densidade = densidade;
        this.custoPorGrama = custoPorGrama;
    }

    public String getMaterial() {return material; };

    public double getCustoPorGrama() { return custoPorGrama; };

    @Override
    public String toString() {
        return densidade + " (" + custoPorGrama + " R$/g)";
    }
}
