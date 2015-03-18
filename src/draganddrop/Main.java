/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draganddrop;

/**
 *
 * @author Messias
 */
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
*
* @author Messias
*/
public class Main extends Application {

    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Parent pane = FXMLLoader.load(getClass().getResource("/visao/TelaPrincipal.fxml"));
        
        Scene cena = new Scene(pane);
        primaryStage.setScene(cena);
        primaryStage.show();
        
    }
    
}
