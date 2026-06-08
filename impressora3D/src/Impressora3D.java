public class Impressora3D {

    private String modelo;
    private double preco;
    private double potencia;
    private String imagem;
    private String descricao;

    public Impressora3D(String modelo, double preco, double potencia, String descricao, String imagem){
        this.modelo = modelo;
        this.preco = preco;
        this.potencia = potencia;
        this.imagem = imagem;
        this.descricao = descricao;
    }

    public String getModelo() { return modelo; }
    public double getPreco() { return preco; }
    public double getPotencia() { return potencia; }
    public String getImagem() { return imagem; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return modelo;
    }
}
