public class CalculadoraCusto {
    private double horas_totais;
    private double custo_maquina_por_hora;
    private double custo_material;
    private double energia_kwh;
    private double custo_energia;
    private double custo_mao_obra;
    private double custo_manutencao;
    private double valor_venda;
    private double custo_total;

    CalculadoraCusto(double precoImpressora, double quantidadeGrama, double custoPorGrama, double horasUtilizadas, double horasPorDia, double potenciaWatts, double valorKwh){
        this.horas_totais = (2 * 365) * 4; //o numero 4 é uma média de uso por dia que bateu com os resultados
        this.custo_maquina_por_hora = (precoImpressora/horas_totais) * horasUtilizadas;
        this.custo_material = quantidadeGrama * custoPorGrama;
        this.energia_kwh = (potenciaWatts / 1000) * horasUtilizadas;
        this.custo_energia = energia_kwh * valorKwh;
        this.custo_mao_obra = horasUtilizadas * 4; //4 representa o valor do custo da mão de obra
        this.custo_manutencao = horasUtilizadas * 0.50; //0.50 representa o valor da manutenção por hora
        this.custo_total = custo_material + custo_maquina_por_hora + custo_energia + custo_mao_obra + custo_manutencao;
        this.valor_venda = custo_total * 1.30;
    }

    public double getCustoMaquinaPorHora() {return this.custo_maquina_por_hora;};
    public double getCustoMaterial() {return this.custo_material;};
    public double getCustoEnergia() {return this.custo_energia;};
    public double getCustoMaoDeObra() {return this.custo_mao_obra;};
    public double getCustoManutenção() {return this.custo_manutencao;};
    public double getCustoTotal() {return this.custo_total;};
    public double getValorSugerido() {return this.valor_venda;};

}


