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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
        Random random = new Random(System.currentTimeMillis());
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        setFill(Color.rgb(red,green,blue,0.99)); //COR ALEATORIA
        setEffect(new DropShadow(2, Color.BLACK));
        doubleClick();
    }

    public ObservableList<Node> getCirculosSelecionados() {
        return circulosSelecionados;
    }

    public void setCirculosSelecionados(ObservableList<Node> circulosSelecionados) {
        this.circulosSelecionados = circulosSelecionados;
    }
    
    public void add(Node node){
        circulosSelecionados.add(node);
    }
    
    public void remover(Node node){
        circulosSelecionados.remove(node);
    }
    
    public void doubleClick(){
        setOnMouseClicked(event->{
            if (event.getClickCount()==2){
                for (Node n : circulosSelecionados){
                    pane.getChildren().add(n);
                }
                circulosSelecionados.clear();
                pane.getChildren().remove(this);
            }
            System.out.println("mouse clicked");
        });
    }
    
}
