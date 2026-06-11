/**
 * Realiza todos os cálculos de custo de uma impressão 3D.
 * Recebe os parâmetros brutos e expõe os resultados via getters.
 */
public class CalculadoraCusto {

    // ===== CONSTANTES =====
    // Valores fixos de referência usados em todos os cálculos
    private static final double VIDA_UTIL_ANOS        = 2;
    private static final double HORAS_USO_MEDIO_DIA   = 4;    // horas/dia estimadas de uso da máquina
    private static final double CUSTO_MANUTENCAO_HORA = 0.50; // R$/h de manutenção preventiva
    private static final double MARGEM_LUCRO          = 1.30; // 30% de lucro sobre o custo total

    // ===== ATRIBUTOS =====
    // Resultados calculados no construtor e acessados pelos getters
    private double gramasReais;         // gramas após aplicar a margem de erro
    private double custoMaquinaPorHora; // depreciação da impressora pelo tempo de uso
    private double custoMaterial;
    private double energiaKwh;          // energia consumida em kWh (armazenada para referência)
    private double custoEnergia;
    private double custoMaoObra;
    private double custoManutencao;
    private double custoTotal;
    private double valorVenda;          // custo total com margem de lucro aplicada

    // ===== CONSTRUTOR =====
    /**
     * @param precoImpressora  Valor de compra da impressora (R$)
     * @param quantidadeGrama  Material informado pelo usuário, antes da margem de erro
     * @param custoPorGrama    Custo do filamento por grama (R$/g)
     * @param horasUtilizadas  Tempo estimado de impressão (h)
     * @param potenciaWatts    Potência nominal da impressora (W)
     * @param valorKwh         Tarifa de energia elétrica (R$/kWh)
     * @param custoMaoObraHora Custo da mão de obra por hora (R$/h)
     * @param quantidade       Número de cópias a imprimir
     * @param margemErroPct    Percentual de desperdício/falha (ex: 10.0 = 10%)
     */
    CalculadoraCusto(double precoImpressora, double quantidadeGrama, double custoPorGrama,
                     double horasUtilizadas, double potenciaWatts, double valorKwh,
                     double custoMaoObraHora, int quantidade, double margemErroPct) {

        // ===== DEPRECIAÇÃO DA MÁQUINA =====
        // Total de horas de uso previsto durante a vida útil
        double horasTotais = VIDA_UTIL_ANOS * 365 * HORAS_USO_MEDIO_DIA;

        // ===== MATERIAL COM MARGEM DE ERRO =====
        // Multiplica o material informado pelo fator de desperdício
        double fatorErro       = 1.0 + (margemErroPct / 100.0);
        double gramasReaisUnit = quantidadeGrama * fatorErro;

        // ===== CUSTOS UNITÁRIOS (por impressão) =====
        double maquinaUnit    = (precoImpressora / horasTotais) * horasUtilizadas;
        double materialUnit   = gramasReaisUnit * custoPorGrama;
        double energiaKwhUnit = (potenciaWatts / 1000.0) * horasUtilizadas; // converte W → kW
        double energiaUnit    = energiaKwhUnit * valorKwh;
        double maoObraUnit    = horasUtilizadas * custoMaoObraHora;
        double manutencaoUnit = horasUtilizadas * CUSTO_MANUTENCAO_HORA;

        // ===== TOTAIS (multiplicados pela quantidade de impressões) =====
        this.gramasReais         = gramasReaisUnit * quantidade;
        this.custoMaquinaPorHora = maquinaUnit    * quantidade;
        this.custoMaterial       = materialUnit   * quantidade;
        this.energiaKwh          = energiaKwhUnit * quantidade;
        this.custoEnergia        = energiaUnit    * quantidade;
        this.custoMaoObra        = maoObraUnit    * quantidade;
        this.custoManutencao     = manutencaoUnit * quantidade;

        // Soma de todos os custos operacionais
        this.custoTotal = custoMaterial + custoMaquinaPorHora + custoEnergia
                        + custoMaoObra  + custoManutencao;

        // Valor de venda sugerido com margem de lucro de 30%
        this.valorVenda = custoTotal * MARGEM_LUCRO;
    }

    // ===== GETTERS =====
    public double getGramasReais()         { return gramasReais; }
    public double getCustoMaquinaPorHora() { return custoMaquinaPorHora; }
    public double getCustoMaterial()       { return custoMaterial; }
    public double getCustoEnergia()        { return custoEnergia; }
    public double getCustoMaoDeObra()      { return custoMaoObra; }
    public double getCustoManutencao()     { return custoManutencao; }
    public double getCustoTotal()          { return custoTotal; }
    public double getValorSugerido()       { return valorVenda; }
}
