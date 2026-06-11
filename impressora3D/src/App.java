import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

/**
 * Classe principal da aplicação JavaFX.
 * Monta a interface gráfica e gerencia os eventos de interação do usuário.
 */
public class App extends Application {

    // ===== COMPONENTES GLOBAIS =====
    // Declarados como campos para acesso compartilhado entre métodos
    private ComboBox<Impressora3D>      cbbImpressora = new ComboBox<>();
    private ComboBox<MaterialImpressao> cbbMaterial   = new ComboBox<>();

    // Largura padrão aplicada a todos os campos de entrada e resultados
    private static final double COL_WIDTH = 400;

    // Texto exibido no painel de resultados antes de qualquer cálculo
    private static final String RESULTADO_PADRAO =
        "PROJETO: —\n" +
        "────────────────────────────────\n" +
        "MATERIAL PREVISTO:   — g\n" +
        "MATERIAL REAL:       — g\n" +
        "────────────────────────────────\n" +
        "CUSTO MATERIAL:      R$ 0,00\n" +
        "CUSTO DA MÁQUINA:    R$ 0,00\n" +
        "CUSTO DE ENERGIA:    R$ 0,00\n" +
        "CUSTO MÃO DE OBRA:   R$ 0,00\n" +
        "CUSTO MANUTENÇÃO:    R$ 0,00\n" +
        "────────────────────────────────\n" +
        "CUSTO TOTAL:         R$ 0,00\n" +
        "VALOR DE VENDA:      R$ 0,00";

    @Override
    public void start(Stage stage) {

        // ===== IMPRESSORAS =====
        // Cada objeto carrega modelo, preço, potência (W), descrição e nome do arquivo de imagem
        cbbImpressora.getItems().addAll(
            new Impressora3D("Ender 3",      1500, 350,
                "Impressora 3D de entrada muito popular entre iniciantes e entusiastas. " +
                "Possui baixo custo, boa qualidade de impressão e ampla comunidade para suporte e modificações.",
                "ender3.png"),
            new Impressora3D("Creality K1",  3500, 500,
                "Impressora 3D de alta velocidade projetada para produção rápida. " +
                "Oferece desempenho avançado, estrutura robusta e excelente qualidade para projetos profissionais.",
                "k1.png"),
            new Impressora3D("Bambu Lab A1", 4200, 400,
                "Impressora 3D moderna com recursos inteligentes e calibração automática. " +
                "Ideal para usuários que buscam praticidade, precisão e excelente acabamento nas peças.",
                "bambu_a1.png")
        );
        cbbImpressora.setPromptText("Selecione a impressora 3D desejada");
        cbbImpressora.setPrefWidth(COL_WIDTH);
        cbbImpressora.setMaxWidth(COL_WIDTH);

        // ===== MATERIAIS =====
        // Custo por grama varia conforme a densidade do filamento PLA
        cbbMaterial.getItems().addAll(
            new MaterialImpressao("PLA baixa densidade", "Baixa", 0.08),
            new MaterialImpressao("PLA média densidade", "Média", 0.12),
            new MaterialImpressao("PLA alta densidade",  "Alta",  0.18)
        );
        cbbMaterial.setPromptText("Selecione o material que vai ser utilizado");
        cbbMaterial.setPrefWidth(COL_WIDTH);
        cbbMaterial.setMaxWidth(COL_WIDTH);

        // ===== IMAGEM DA IMPRESSORA =====
        // Atualizada dinamicamente ao selecionar uma impressora no ComboBox
        ImageView imgImpressora = new ImageView();
        imgImpressora.setFitWidth(COL_WIDTH);
        imgImpressora.setFitHeight(COL_WIDTH);
        imgImpressora.setPreserveRatio(true);

        // ===== LABELS DE INFORMAÇÃO =====
        // Exibem modelo, preço e potência após seleção da impressora
        Label lblModelo = new Label(
            "MODELO:   —\n" +
            "PREÇO:    R$ —\n" +
            "POTÊNCIA: — W"
        );
        lblModelo.setWrapText(true);
        lblModelo.setPrefWidth(COL_WIDTH);
        lblModelo.getStyleClass().add("label-modelo");

        // Altura livre — cresce conforme o texto da descrição
        Label lblDescricao = new Label("DESCRIÇÃO:\n—");
        lblDescricao.setWrapText(true);
        lblDescricao.setPrefWidth(COL_WIDTH);
        lblDescricao.getStyleClass().add("label-descricao");

        // ===== PAINEL DE RESULTADOS =====
        // Label monospace dentro de um VBox estilizado com borda verde
        Label lblCalculos = new Label(RESULTADO_PADRAO);
        lblCalculos.setPrefWidth(COL_WIDTH - 32); // desconta o padding do container
        lblCalculos.setWrapText(false);
        lblCalculos.getStyleClass().add("label-calculos");

        VBox boxResultados = new VBox(lblCalculos);
        boxResultados.setPrefWidth(COL_WIDTH);
        boxResultados.setMaxWidth(COL_WIDTH);
        boxResultados.getStyleClass().add("box-resultados");

        // ===== CAMPOS DO PROJETO — coluna esquerda =====
        TextField txfNomeProjeto = new TextField();
        txfNomeProjeto.setPromptText("Nome do projeto");
        txfNomeProjeto.setPrefWidth(COL_WIDTH);
        txfNomeProjeto.setMaxWidth(COL_WIDTH);

        // Campo de texto multilinhas para descrição livre do projeto
        TextArea txaDescricaoProjeto = new TextArea();
        txaDescricaoProjeto.setPromptText("Descrição do projeto");
        txaDescricaoProjeto.setPrefRowCount(3);
        txaDescricaoProjeto.setWrapText(true);
        txaDescricaoProjeto.setPrefWidth(COL_WIDTH);
        txaDescricaoProjeto.setMaxWidth(COL_WIDTH);
        txaDescricaoProjeto.getStyleClass().add("text-area");

        // ===== CAMPOS DE CÁLCULO — coluna direita =====
        TextField txfQuantidadeImpressoes = new TextField();
        txfQuantidadeImpressoes.setPromptText("Quantidade de impressões");
        txfQuantidadeImpressoes.setPrefWidth(COL_WIDTH);
        txfQuantidadeImpressoes.setMaxWidth(COL_WIDTH);

        TextField txfQuantidadeMaterial = new TextField();
        txfQuantidadeMaterial.setPromptText("Material previsto (gramas)");
        txfQuantidadeMaterial.setPrefWidth(COL_WIDTH);
        txfQuantidadeMaterial.setMaxWidth(COL_WIDTH);

        // Percentual de desperdício aplicado sobre o material previsto
        TextField txfMargemErro = new TextField();
        txfMargemErro.setPromptText("Margem de erro/falha (%) — ex: 10");
        txfMargemErro.setPrefWidth(COL_WIDTH);
        txfMargemErro.setMaxWidth(COL_WIDTH);

        TextField txfHorasUtilizadas = new TextField();
        txfHorasUtilizadas.setPromptText("Horas de impressão");
        txfHorasUtilizadas.setPrefWidth(COL_WIDTH);
        txfHorasUtilizadas.setMaxWidth(COL_WIDTH);

        TextField txfMaoObra = new TextField();
        txfMaoObra.setPromptText("Mão de obra (R$/hora) — sugerido: 4,00");
        txfMaoObra.setPrefWidth(COL_WIDTH);
        txfMaoObra.setMaxWidth(COL_WIDTH);

        // ===== SELETOR DE TAXA DE ENERGIA =====
        // Define o valor de kWh usado no cálculo de custo de energia
        Label lblTaxaEnergia = new Label("Taxa de energia:");
        lblTaxaEnergia.getStyleClass().add("label");

        ToggleGroup grupoTaxa = new ToggleGroup();
        RadioButton rbDiurna  = new RadioButton("Diurna — R$ 0,95/kWh");
        RadioButton rbNoturna = new RadioButton("Noturna — R$ 0,65/kWh");
        rbDiurna.setToggleGroup(grupoTaxa);
        rbNoturna.setToggleGroup(grupoTaxa);
        rbDiurna.setSelected(true); // padrão: tarifa diurna

        HBox boxTaxa = new HBox(20, rbDiurna, rbNoturna);
        boxTaxa.setAlignment(Pos.CENTER_LEFT);
        boxTaxa.setPrefWidth(COL_WIDTH);

        // ===== BOTÕES =====
        Button btnCalcular = new Button("Calcular");
        Button btnLimpar   = new Button("Limpar");
        btnLimpar.getStyleClass().add("button-secondary");

        // ===== AÇÃO: CALCULAR =====
        // Valida campos, instancia CalculadoraCusto e exibe o resultado formatado
        btnCalcular.setOnAction(e -> {
            if (cbbImpressora.getValue() == null || cbbMaterial.getValue() == null ||
                txfQuantidadeMaterial.getText().isBlank()  ||
                txfMargemErro.getText().isBlank()          ||
                txfHorasUtilizadas.getText().isBlank()     ||
                txfMaoObra.getText().isBlank()             ||
                txfQuantidadeImpressoes.getText().isBlank()) {
                lblCalculos.setText("Preencha todos os campos antes de calcular.");
                return;
            }

            try {
                Impressora3D      imp = cbbImpressora.getValue();
                MaterialImpressao mat = cbbMaterial.getValue();

                double gramas     = Double.parseDouble(txfQuantidadeMaterial.getText());
                double margemErro = Double.parseDouble(txfMargemErro.getText());
                double horas      = Double.parseDouble(txfHorasUtilizadas.getText());
                double maoObra    = Double.parseDouble(txfMaoObra.getText());
                int    quantidade = Integer.parseInt(txfQuantidadeImpressoes.getText());

                double kwh = rbDiurna.isSelected() ? 0.95 : 0.65;

                CalculadoraCusto calc = new CalculadoraCusto(
                    imp.getPreco(), gramas, mat.getCustoPorGrama(),
                    horas, imp.getPotencia(), kwh, maoObra, quantidade, margemErro
                );

                String nomeProjeto   = txfNomeProjeto.getText().isBlank() ? "Sem nome" : txfNomeProjeto.getText();
                double gramasReaisUn = calc.getGramasReais() / quantidade;
                String descEnergia   = rbDiurna.isSelected() ? "Diurna  R$ 0,95/kWh" : "Noturna R$ 0,65/kWh";

                lblCalculos.setText(
                    "PROJETO: " + nomeProjeto + "\n" +
                    "────────────────────────────────\n" +
                    "MATERIAL PREVISTO:   " + String.format("%.1f", gramas)        + " g\n" +
                    "MATERIAL REAL:       " + String.format("%.1f", gramasReaisUn) + " g/un\n" +
                    "────────────────────────────────\n" +
                    "CUSTO MATERIAL:      R$ " + String.format("%.2f", calc.getCustoMaterial())       + "\n" +
                    "CUSTO DA MAQUINA:    R$ " + String.format("%.2f", calc.getCustoMaquinaPorHora()) + "\n" +
                    "CUSTO DE ENERGIA:    R$ " + String.format("%.2f", calc.getCustoEnergia())        + "  " + descEnergia + "\n" +
                    "CUSTO MAO DE OBRA:   R$ " + String.format("%.2f", calc.getCustoMaoDeObra())      + "\n" +
                    "CUSTO MANUTENCAO:    R$ " + String.format("%.2f", calc.getCustoManutencao())     + "\n" +
                    "────────────────────────────────\n" +
                    "CUSTO TOTAL:         R$ " + String.format("%.2f", calc.getCustoTotal())          + "\n" +
                    "VALOR DE VENDA:      R$ " + String.format("%.2f", calc.getValorSugerido())
                );

            } catch (NumberFormatException ex) {
                // Capturado quando o usuário digita texto em campo numérico
                lblCalculos.setText("Insira apenas numeros nos campos numericos.");
            }
        });

        // ===== AÇÃO: LIMPAR =====
        // Reseta todos os campos e restaura o painel de resultados ao estado inicial
        btnLimpar.setOnAction(e -> {
            txfNomeProjeto.clear();
            txaDescricaoProjeto.clear();
            txfQuantidadeImpressoes.clear();
            txfQuantidadeMaterial.clear();
            txfMargemErro.clear();
            txfHorasUtilizadas.clear();
            txfMaoObra.clear();
            cbbMaterial.setValue(null);
            cbbMaterial.setPromptText("Selecione o material que vai ser utilizado");
            rbDiurna.setSelected(true);
            lblCalculos.setText(RESULTADO_PADRAO);
        });

        HBox boxBotoes = new HBox(15, btnCalcular, btnLimpar);
        boxBotoes.setAlignment(Pos.CENTER);
        boxBotoes.setPrefWidth(COL_WIDTH);

        // ===== LAYOUT: COLUNA ESQUERDA =====
        // Contém: nome/descrição do projeto, imagem, ComboBox da impressora e seus dados
        VBox boxImagem = new VBox();
        boxImagem.setPrefWidth(COL_WIDTH);
        boxImagem.setMinWidth(COL_WIDTH);
        boxImagem.setPrefHeight(COL_WIDTH);
        boxImagem.setMinHeight(COL_WIDTH);
        boxImagem.setAlignment(Pos.CENTER);
        boxImagem.getStyleClass().add("box-imagem");

        Label lblPlaceholder = new Label("Aguardando selecao");
        lblPlaceholder.getStyleClass().add("placeholder-imagem");
        lblPlaceholder.setAlignment(Pos.CENTER);
        boxImagem.getChildren().add(lblPlaceholder);

        VBox colunaEsquerda = new VBox(15);
        colunaEsquerda.setAlignment(Pos.TOP_CENTER);
        colunaEsquerda.setPadding(new Insets(10));
        colunaEsquerda.setPrefWidth(COL_WIDTH + 20);
        colunaEsquerda.getChildren().addAll(
            txfNomeProjeto, txaDescricaoProjeto,
            boxImagem, cbbImpressora, lblModelo, lblDescricao
        );

        // ===== LAYOUT: COLUNA DIREITA =====
        // Contém: campos numéricos, material, taxa de energia, botões e painel de resultados
        VBox colunaDireita = new VBox(15);
        colunaDireita.setAlignment(Pos.TOP_CENTER);
        colunaDireita.setPadding(new Insets(10));
        colunaDireita.setPrefWidth(COL_WIDTH + 20);
        colunaDireita.getChildren().addAll(
            txfQuantidadeImpressoes, txfQuantidadeMaterial, txfMargemErro,
            txfHorasUtilizadas, txfMaoObra, cbbMaterial,
            lblTaxaEnergia, boxTaxa, boxBotoes, boxResultados
        );

        // ===== EVENTO: SELEÇÃO DE IMPRESSORA =====
        // Atualiza imagem, modelo, preço e potência ao trocar a impressora selecionada
        cbbImpressora.setOnAction(e -> {
            Impressora3D imp = cbbImpressora.getValue();
            if (imp != null) {
                lblModelo.setText(
                    "MODELO:   " + imp.getModelo()                          + "\n" +
                    "PRECO:    R$ " + String.format("%.2f", imp.getPreco()) + "\n" +
                    "POTENCIA: " + imp.getPotencia() + " W"
                );
                lblDescricao.setText("DESCRICAO:\n" + imp.getDescricao());

                // Carrega imagem do classpath em /imagens/
                Image imagem = new Image(
                    getClass().getResourceAsStream("/imagens/" + imp.getImagem())
                );
                imgImpressora.setImage(imagem);
                boxImagem.getChildren().setAll(imgImpressora);
            }
        });

        // ===== MONTAGEM DO LAYOUT PRINCIPAL =====
        HBox mainLayout = new HBox(30);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(colunaEsquerda, colunaDireita);

        // ===== CENA E STAGE =====
        // ScrollPane permite rolagem vertical caso o conteúdo ultrapasse a altura da janela
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        Scene mainScene = new Scene(scrollPane, 1100, 800);
        mainScene.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());

        stage.setTitle("Calculadora de Custos — Impressao 3D");
        stage.setScene(mainScene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
