import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Material;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class App extends Application {

        private ComboBox<Impressora3D> cbbImpressora = new ComboBox<>();
        private ComboBox<MaterialImpressao> cbbMaterial = new ComboBox<>();

        @Override
        public void start(Stage stage) {

                cbbImpressora.getItems().addAll(
                        new Impressora3D("Ender 3", 1500, 350, "Impressora 3D de entrada muito popular entre iniciantes e entusiastas. Possui baixo custo, boa qualidade de impressão e ampla comunidade para suporte e modificações.", "ender3.png"),
                        new Impressora3D("Creality K1", 3500, 500, "Impressora 3D de alta velocidade projetada para produção rápida. Oferece desempenho avançado, estrutura robusta e excelente qualidade para projetos profissionais.", "k1.png"),
                        new Impressora3D("Bambu Lab A1", 4200, 400, "Impressora 3D moderna com recursos inteligentes e calibração automática. Ideal para usuários que buscam praticidade, precisão e excelente acabamento nas peças.", "bambu_a1.png")
                );
                cbbImpressora.setPromptText("Selecione a impressora 3D desejada");
                cbbMaterial.getItems().addAll(
                        new MaterialImpressao("PLA baixa densidade", "Baixa", 0.08),
                        new MaterialImpressao("PLA média densidade", "Média", 0.12),
                        new MaterialImpressao("PLA alta densidade", "Alta", 0.18)
                );
                cbbMaterial.setPromptText("Selecione o material que vai ser utilizado");


                ImageView imgImpressora = new ImageView();
                imgImpressora.setFitWidth(250);
                imgImpressora.setFitHeight(250);
                imgImpressora.setPreserveRatio(true);


                Label lblModelo = new Label("Nenhum Impressora Selecionada");
                Label lblDescricao = new Label();
                lblDescricao.setWrapText(true);
                lblDescricao.setPrefWidth(300);
                Label lblCalculos = new Label("");


                TextField txfQuantidadeMaterial = new TextField("120");
                txfQuantidadeMaterial.setPromptText("Quantidade de Material em gramas");
                TextField txfHorasUtilizadas = new TextField("5");
                txfHorasUtilizadas.setPromptText("Quantas horas a impressora sera utilizada");
                TextField txfValorKwh = new TextField("0.60");
                txfValorKwh.setPromptText("Quantos reais por cada quilowatt-hora");


                ProjetoImpressao arquivoStl = new ProjetoImpressao("suporte-celular.stl", "Suporte para celular desenvolvido para manter o aparelho em posição confortável sobre mesas e superfícies. Projeto leve, resistente e adequado para uso doméstico ou profissional.");
                TextField nomeObjeto = new TextField(arquivoStl.getNome());
                Label lblProjetoDesc = new Label(arquivoStl.getDesc());
                lblProjetoDesc.setWrapText(true);
                lblProjetoDesc.setPrefWidth(300);


                Button btnCalcular = new Button("Calcular");


                cbbImpressora.setOnAction(e -> {

                        Impressora3D imp = cbbImpressora.getValue();

                        if (imp != null) {

                                lblModelo.setText(
                                        "Modelo: " + imp.getModelo() +
                                        "\nPreço de aquisição: R$ " +  imp.getPreco() +
                                        "\nPotencia: " + imp.getPotencia() + "W"
                                );

                                lblDescricao.setText(
                                        "Descrição:\n" + imp.getDescricao()
                                );

                                Image imagem = new Image(
                                getClass().getResourceAsStream(
                                        "/imagens/" + imp.getImagem()
                                )
                                );

                                imgImpressora.setImage(imagem);
                                
                        }
                });

                btnCalcular.setOnAction(e -> {

                        Impressora3D imp = cbbImpressora.getValue();
                        MaterialImpressao mat = cbbMaterial.getValue();
                        CalculadoraCusto calc = new CalculadoraCusto(
                                imp.getPreco(), 
                                Double.parseDouble(txfQuantidadeMaterial.getText()), 
                                mat.getCustoPorGrama(), 
                                Double.parseDouble(txfHorasUtilizadas.getText()), 
                                4,
                                imp.getPotencia(),
                                0.60
                        );
                        
                        lblCalculos.setText(
                                "\nCusto Material: R$" + String.format("%.2f", calc.getCustoMaterial()) + 
                                "\nCusto da Maquina: R$" + String.format("%.2f", calc.getCustoMaquinaPorHora()) +
                                "\nCusto de Energia: R$" + String.format("%.2f", calc.getCustoEnergia()) +
                                "\nCusto de Mão de Obra: R$" + String.format("%.2f", calc.getCustoMaoDeObra()) +
                                "\nCusto de Manutenção: R$" + String.format("%.2f", calc.getCustoManutenção()) +
                                "\n\nCusto Total: " + String.format("%.2f", calc.getCustoTotal()) +
                                "\nValor de Venda Sugerido: " + String.format("%.2f", calc.getValorSugerido())
                        );

                });

                BorderPane mainLayout = new BorderPane();
                VBox esquerda = new VBox(10);
                VBox direita = new VBox(10);
                esquerda.setPadding(new Insets(20));
                esquerda.getChildren().addAll(
                        cbbImpressora,
                        nomeObjeto,
                        lblProjetoDesc,
                        txfQuantidadeMaterial,
                        txfHorasUtilizadas,
                        cbbMaterial,
                        btnCalcular
                );
                direita.setPadding(new Insets(20));
                ScrollPane scrollDireita = new ScrollPane(direita);
                scrollDireita.setFitToWidth(true);
                scrollDireita.setPrefWidth(350);
                direita.getChildren().addAll(
                        imgImpressora,
                        lblModelo,
                        lblDescricao,
                        lblCalculos
                );
                mainLayout.setPadding(new Insets(10));
                mainLayout.setLeft(esquerda);
                mainLayout.setRight(scrollDireita);

                Scene mainScene = new Scene(mainLayout, 800, 600);

                stage.setTitle("Calculadora de Custos - Impressão 3D");
                stage.setScene(mainScene);
                stage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}