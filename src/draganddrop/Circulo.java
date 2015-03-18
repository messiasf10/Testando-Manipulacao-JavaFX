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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Messias
 */
public class Circulo extends Circle {

    private double centroX;
    private double centroY;
    private double initX;
    private double initY;
    private Point2D ponto;
    private boolean mousePorCima = false;

    public Circulo(double raio) {
        setRadius(raio);
        setFill((Paint) Color.CHOCOLATE);
        setStroke(Color.BLACK);
        setStrokeWidth(2);        
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
            if ((novaPosicaoX >= this.getRadius()) && (novaPosicaoX <= principal.getWidth() - this.getRadius())) {
                setTranslateX(novaPosicaoX);
            }
            if ((novaPosicaoY >= this.getRadius()) && (novaPosicaoY <= principal.getHeight() - this.getRadius())) {
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
        setOnMouseExited(mouse -> {
            this.mousePorCima = false;
        });
        setOnMouseClicked(event -> {
            
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
