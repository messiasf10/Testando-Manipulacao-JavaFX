/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draganddrop;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.rmi.CORBA.Util;

/**
 *
 * @author Messias
 */
public class BigCirculo extends Circulo {

    private ObservableList<Node> circulosSelecionados = FXCollections.observableArrayList();
    private Pane pane;

    public BigCirculo(double raio, Pane pane) {
        super(raio);
        this.pane = pane;
        this.setTranslateX(100);
        this.setTranslateY(100);
        Random random = new Random(System.currentTimeMillis());
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        setFill(Color.rgb(red, green, blue, 0.99)); //COR ALEATORIA
        setEffect(new DropShadow(2, Color.BLACK));
        doubleClick();
    }

    public ObservableList<Node> getCirculosSelecionados() {
        return circulosSelecionados;
    }

    public void setCirculosSelecionados(ObservableList<Node> circulosSelecionados) {
        this.circulosSelecionados = circulosSelecionados;
    }

    public void add(Node node) {
        circulosSelecionados.add(node);
    }

    public void remover(Node node) {
        circulosSelecionados.remove(node);
    }

    public void doubleClick() {
        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                relocar();
                for (Node n : circulosSelecionados) {
                    pane.getChildren().add(n);
                }
                circulosSelecionados.clear();
                pane.getChildren().remove(this);
            }
            System.out.println("mouse clicked");
        });
    }

    private void relocar() {
        double menorX = 0, menorY = 0, maiorX = 0, maiorY = 0;
        menorX = circulosSelecionados.get(0).getTranslateX();
        menorY = circulosSelecionados.get(0).getTranslateY();
        maiorX = circulosSelecionados.get(0).getTranslateX();
        maiorY = circulosSelecionados.get(0).getTranslateY();
        for (Node n : circulosSelecionados) {
            if (n.getTranslateX() <= menorX) {
                menorX = n.getTranslateX();
            }
            if (n.getTranslateX() > maiorX) {
                maiorX = n.getTranslateX();
            }
            if (n.getTranslateY() <= menorY) {
                menorY = n.getTranslateY();
            }
            if (n.getTranslateY() > maiorY);
            {
                maiorY = n.getTranslateY();
            }
        }
        double largura = maiorX - menorX;
        double altura = maiorY - menorY;        
        double translateXVelho = menorX + (largura / 2);
        double translateYVelho = menorY + (altura / 2);
        double translateXNovo = this.getTranslateX();
        double translateYNovo = this.getTranslateY();
        for (int i = 0; i < circulosSelecionados.size(); i++) {
            double difX = (translateXVelho - circulosSelecionados.get(i).getTranslateX()) * -1;
            double difY = (translateYVelho - circulosSelecionados.get(i).getTranslateY()) * -1;
            circulosSelecionados.get(i).setTranslateX(translateXNovo + difX);
            circulosSelecionados.get(i).setTranslateY(translateYNovo + difY);
        }
    }

    private double distancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

}
