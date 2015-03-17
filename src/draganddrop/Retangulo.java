/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draganddrop;

import com.sun.javafx.geom.Point2D;
import controllers.TelaPrincipalController;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Messias
 */
public class Retangulo extends Rectangle {

    private double centroX;
    private double centroY;
    private double initX;
    private double initY;
    private Point2D ponto;
    private boolean mousePorCima = false;

    public Retangulo(double largura, double altura) {
        setWidth(largura);
        setHeight(altura);
        setStroke(Color.BLACK);
        setStrokeWidth(2);
        setFill(Color.GREEN);        
    }

    public void addListeners(Pane principal) {
        setCursor(Cursor.HAND);
        setOnMouseDragged(mouse -> {
            double dragX = mouse.getSceneX() - ponto.x;
            double dragY = mouse.getSceneY() - ponto.y;
            //CALCULA A NOVA POSICAO DO CIRCULO
            double novaPosicaoX = initX + dragX;
            double novaPosicaoY = initY + dragY;
            //COLOCANDO NA NOVA POSICAO
            if ((novaPosicaoX >= this.getWidth() / 2) && (novaPosicaoX <= principal.getWidth() - this.getWidth() / 2)) {
                setTranslateX(novaPosicaoX);
            }
            if ((novaPosicaoY >= this.getHeight() / 2) && (novaPosicaoY <= principal.getHeight() - this.getHeight() / 2)) {
                this.setTranslateY(novaPosicaoY);
            }
        });
        setOnMousePressed(mouse -> {
            ObservableList<Node> lista = principal.getChildren();
            this.setStroke(Color.BLUE);
            this.setStrokeWidth(2);
            for (Node n : lista) {
                if (!((Shape) n).equals(this)) {
                    ((Shape) n).setStroke(Color.BLACK);
                    ((Shape) n).setStrokeWidth(2);
                }
            }
            TelaPrincipalController.setNode(this);
            //QUANDO O MOUSE EH PRESSIONADO, GUARDA A POSICAO INICIAL
            initX = this.getTranslateX();
            initY = this.getTranslateY();
            ponto = new Point2D((float) mouse.getSceneX(), (float) mouse.getSceneY());
        });
        setOnMouseEntered(event -> {
            //this.toFront();
            this.mousePorCima = true;
        });
        setOnMouseExited(event -> {
            this.mousePorCima = false;
        });
        setOnMouseClicked(event -> {
            //TODO
        });
    }

    public void removeListeners() {
        setCursor(null);
        setOnMouseDragged(null);
        setOnMousePressed(null);
        setOnMouseEntered(null);
        setOnMouseExited(null);
        setOnMouseClicked(null);
    }

    public boolean isMousePorCima() {
        return mousePorCima;
    }

    public void setMousePorCima(boolean mousePorCima) {
        this.mousePorCima = mousePorCima;
    }
        
}
