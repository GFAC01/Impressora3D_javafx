public class ProjetoImpressao {
    private String objeto;
    private String descricao;

    ProjetoImpressao(String objeto, String descricao){
        this.objeto = objeto;
        this.descricao = descricao;
    }

    public String getNome() {return this.objeto;};
    public String getDesc() {return this.descricao;};

    @Override
    public String toString() {return objeto;}
}
