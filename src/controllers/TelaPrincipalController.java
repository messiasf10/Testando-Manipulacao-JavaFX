/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import draganddrop.Circulo;
import draganddrop.Retangulo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * FXML Controller class
 *
 * @author Messias
 */
public class TelaPrincipalController implements Initializable {

    @FXML
    private Pane panePrincipal;
    @FXML
    private ToggleButton tgoSelecionar, tgoCirculo, tgoRetangulo;
    @FXML
    private Slider sliderOpacidade, sliderEscala;
    @FXML
    private ColorPicker colorPick;
    private ToggleGroup grupoA;
    private static double RAIO_CIRCULO = 20, LARG_RET = 45, ALT_RET = 25;
    private static Node node;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComponents();
        initListeners();
        initListenersProperty();
    }

    private void initComponents() {
        ImageView imgCirculo = new ImageView(new Image(getClass().getResourceAsStream("/imagens/circulo.png")));
        imgCirculo.setFitHeight(20);
        imgCirculo.setFitWidth(20);
        ImageView imgSeta = new ImageView(new Image(getClass().getResourceAsStream("/imagens/seta.png")));
        imgSeta.setFitHeight(20);
        imgSeta.setFitWidth(20);
        ImageView imgRetangulo = new ImageView(new Image(getClass().getResourceAsStream("/imagens/retangulo.png")));
        imgRetangulo.setFitHeight(20);
        imgRetangulo.setFitWidth(20);
        tgoCirculo.setGraphic(imgCirculo);
        tgoRetangulo.setGraphic(imgRetangulo);
        tgoSelecionar.setGraphic(imgSeta);
        grupoA = new ToggleGroup();
        grupoA.getToggles().addAll(tgoCirculo, tgoSelecionar, tgoRetangulo);
    }

    private void initListeners() {
        tgoCirculo.setOnAction(event -> {
            panePrincipal.setOnMouseClicked(evento -> {
                Circulo circulo = new Circulo(RAIO_CIRCULO);
                //Circle circulo = circ.criarCirculo(raio, panePrincipal);
                circulo.setTranslateX(evento.getX());
                circulo.setTranslateY(evento.getY());
                circulo.removeListeners();
                panePrincipal.getChildren().add(circulo);
            });
        });
        tgoRetangulo.setOnAction(event -> {
            panePrincipal.setOnMouseClicked(evento -> {
                Retangulo retangulo = new Retangulo(LARG_RET, ALT_RET);
                //Circle circulo = circ.criarCirculo(raio, panePrincipal);
                retangulo.setTranslateX(evento.getX());
                retangulo.setTranslateY(evento.getY());
                retangulo.removeListeners();
                panePrincipal.getChildren().add(retangulo);
            });
        });
        tgoSelecionar.setOnAction(event -> {
            panePrincipal.setOnMouseClicked(null);
            ObservableList<Node> listaCirculos = panePrincipal.getChildren();
            for (Node node : listaCirculos) {
                if (node instanceof Circulo) {
                    ((Circulo) node).addListeners(panePrincipal);
                } else if (node instanceof Retangulo) {
                    ((Retangulo) node).addListeners(panePrincipal);
                }
            }
        });
    }

    private void initListenersProperty() {
        sliderOpacidade.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                node.setOpacity(newValue.doubleValue());
            }
        });
        sliderEscala.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                node.setScaleX(newValue.doubleValue());
                node.setScaleY(newValue.doubleValue());
            }
        });
        colorPick.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                ((Shape) node).setFill(new Color(newValue.getRed(), newValue.getGreen(), newValue.getBlue(), newValue.getOpacity()));
            }
        });
    }

    public static void setNode(Node node) {
        TelaPrincipalController.node = node;
    }

}
