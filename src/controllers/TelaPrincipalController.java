
package controllers;

import com.sun.javafx.geom.Point2D;
import draganddrop.BigCirculo;
import draganddrop.Circulo;
import draganddrop.Retangulo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    @FXML
    private Accordion acordionDireito;
    @FXML
    private TitledPane titledPaneProp;
    private ToggleGroup grupoA;
    private static double RAIO_CIRCULO = 20, LARG_RET = 45, ALT_RET = 25, RAIO_BIG_CIRCULO = 40;
    private static Node node;
    private double initX = 0;
    private double initY = 0;
    private Point2D ponto;
    private Rectangle retanguloSelecao = new Rectangle();
    private ObservableList<Node> nodesSelecteds = FXCollections.observableArrayList();
    private ContextMenu contextMenu;
    private MenuItem menuItemMesclar;

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
        acordionDireito.setExpandedPane(titledPaneProp);
        contextMenu = new ContextMenu();
        menuItemMesclar = new MenuItem("Mesclar Circulos");
        menuItemMesclar.setOnAction(event -> {
            BigCirculo bigCirculo = new BigCirculo(RAIO_BIG_CIRCULO, panePrincipal);
            //bigCirculo.setCirculosSelecionados(nodesSelecteds);
            for (Node n : nodesSelecteds) {
                bigCirculo.add(n);
                panePrincipal.getChildren().remove(n);                
            }
            nodesSelecteds.clear();
            bigCirculo.setTranslateX(100);
            bigCirculo.setTranslateY(100);
            panePrincipal.getChildren().add(bigCirculo);
        });
        contextMenu.getItems().add(menuItemMesclar);
    }

    private void initListeners() {
        tgoCirculo.setOnAction(event -> {
            panePrincipal.setOnMouseDragged(null);
            panePrincipal.setOnMouseClicked(evento -> {
                if (!mouseSobreShape()) {
                    Circulo circulo = new Circulo(RAIO_CIRCULO);
                    //Circle circulo = circ.criarCirculo(raio, panePrincipal);
                    circulo.setTranslateX(evento.getX());
                    circulo.setTranslateY(evento.getY());
                    circulo.removeListeners();
                    panePrincipal.getChildren().add(circulo);
                }
            });
        });
        tgoRetangulo.setOnAction(event -> {
            panePrincipal.setOnMouseDragged(null);
            panePrincipal.setOnMouseClicked(evento -> {
                if (!mouseSobreShape()) {
                    Retangulo retangulo = new Retangulo(LARG_RET, ALT_RET);
                    retangulo.setTranslateX(evento.getX());
                    retangulo.setTranslateY(evento.getY());
                    retangulo.removeListeners();
                    panePrincipal.getChildren().add(retangulo);
                }
            });
        });
        tgoSelecionar.setOnAction(event -> {
            retanguloSelecao.setStroke(Color.BLUE);
            retanguloSelecao.setStrokeWidth(1);
            retanguloSelecao.setFill(Color.SLATEBLUE);
            retanguloSelecao.setOpacity(0.5);
            retanguloSelecao.toFront();
            panePrincipal.getChildren().remove(retanguloSelecao);
            panePrincipal.getChildren().add(retanguloSelecao);
            panePrincipal.setOnMouseClicked(mouse -> {
                if (MouseButton.SECONDARY.equals(mouse.getButton())) {
                    contextMenu.show(panePrincipal, mouse.getScreenX(), mouse.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });
            panePrincipal.setOnMousePressed(evento -> {
                initX = evento.getX();
                initY = evento.getY();
                ponto = new Point2D((float) evento.getX(), (float) evento.getY());
                //System.out.println("mouse pressed - selecionados: "+nodesSelecteds.size());
            });
            panePrincipal.setOnMouseDragged(mouse -> {
                if (!mouseSobreShape()) {
                    double dragX = mouse.getSceneX() - ponto.x;
                    double dragY = mouse.getSceneY() - ponto.y;
                    //CALCULA A NOVA POSICAO DO CIRCULO
                    double novaPosicaoX = initX + dragX;
                    double novaPosicaoY = initY + dragY;
                    retanguloSelecao.setTranslateX(initX);
                    retanguloSelecao.setTranslateY(initY);
                    retanguloSelecao.setWidth(Math.abs(mouse.getX() - initX));
                    retanguloSelecao.setHeight(Math.abs(mouse.getY() - initY));
                    if ((mouse.getX() <= retanguloSelecao.getTranslateX())) {
                        retanguloSelecao.setTranslateX(mouse.getX());
                    }
                    if ((mouse.getY() <= retanguloSelecao.getTranslateY())) {
                        retanguloSelecao.setTranslateY(mouse.getY());
                    }
                    for (int i = 0; i < panePrincipal.getChildren().size(); i++) {
                        Node node = panePrincipal.getChildren().get(i);
                        if (!node.equals(retanguloSelecao)) {
                            if (retanguloSelecao.getTranslateX() <= node.getTranslateX() - RAIO_CIRCULO
                                    && retanguloSelecao.getTranslateY() <= node.getTranslateY() - RAIO_CIRCULO
                                    && (node.getTranslateX() + RAIO_CIRCULO) <= retanguloSelecao.getTranslateX() + retanguloSelecao.getWidth()
                                    && (node.getTranslateY() + RAIO_CIRCULO) <= retanguloSelecao.getTranslateY() + retanguloSelecao.getHeight()) {
                                //System.out.println("dentro do retangulo");
                                ((Shape) node).setStroke(Color.BLUE);
                                if (!nodesSelecteds.contains(node)) {
                                    nodesSelecteds.add(node);
                                }
                            } else {
                                ((Shape) node).setStroke(Color.BLACK);
                                nodesSelecteds.remove(node);
                                //System.out.println("fora do retangulo");                                
                            }
                        }
                    }
                } /*else {
                 System.out.println("mouse sobre shape");
                 }*/

            });
            panePrincipal.setOnMouseReleased(mouse -> {
                retanguloSelecao.setTranslateX(-1);
                retanguloSelecao.setTranslateY(-1);
                retanguloSelecao.setWidth(0);
                retanguloSelecao.setHeight(0);
            });
            ObservableList<Node> listaCirculos = panePrincipal.getChildren();
            for (Node node : listaCirculos) {
                if (node instanceof BigCirculo) {
                    ((BigCirculo) node).addListeners(panePrincipal);
                    ((BigCirculo) node).doubleClick();
                } else if (node instanceof Circulo) {
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
                if (nodesSelecteds.size() == 0) {
                    node.setOpacity(newValue.doubleValue());
                    return;
                }
                for (Node node : nodesSelecteds) {
                    node.setOpacity(newValue.doubleValue());
                }

            }
        });
        sliderEscala.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (nodesSelecteds.size() == 0) {
                    node.setScaleX(newValue.doubleValue());
                    node.setScaleY(newValue.doubleValue());
                    return;
                }
                for (Node node : nodesSelecteds) {
                    node.setScaleX(newValue.doubleValue());
                    node.setScaleY(newValue.doubleValue());
                }
            }
        });
        colorPick.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                if (nodesSelecteds.size() == 0) {
                    ((Shape) node).setFill(new Color(newValue.getRed(), newValue.getGreen(), newValue.getBlue(), newValue.getOpacity()));
                    return;
                }
                for (Node node : nodesSelecteds) {
                    ((Shape) node).setFill(new Color(newValue.getRed(), newValue.getGreen(), newValue.getBlue(), newValue.getOpacity()));
                }
            }
        });
    }

    public boolean mouseSobreShape() {
        ObservableList<Node> listaCirculos = panePrincipal.getChildren();
        for (Node node : listaCirculos) {
            if (node instanceof Circulo) {
                if (((Circulo) node).isMousePorCima()) {
                    return true;
                }
            } else if (node instanceof Retangulo) {
                if (((Retangulo) node).isMousePorCima()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setNode(Node node) {
        TelaPrincipalController.node = node;
    }

}
