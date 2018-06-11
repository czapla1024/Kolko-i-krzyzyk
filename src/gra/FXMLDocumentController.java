/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gra;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author admin
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private GridPane board;
    
    @FXML
    private ImageView pic;
    
    @FXML
    private ImageView da;
    
    @FXML
    private Button but;
    
    Image x = new Image("gra/x.png");
    Image o = new Image("gra/o.png");

    short tab[][] = new short[3][3];
    
    @FXML
    private void start(ActionEvent event) {
        ImageView obr2;
        label.setText("Your turn");
        but.setText("New game");
        for(int i=0; i<3;i++)
            for(int j=0; j<3;j++)
                tab[i][j]=0;
        board.setDisable(false);
        for(Node obr : board.getChildren().filtered(new Predicate<Node>() {
            @Override
            public boolean test(Node t) {
                return t.getId()!=null;
            }
        })){
            obr2 = (ImageView)obr;
            obr2.setImage(null);
        }
    }
    
    
    
    private void ai() {
        
    }
    
    private boolean checkWin() {
        short suma=0;
        boolean niepelny=true;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                suma+=tab[i][j];
                if(suma==3)
                    return true;
                
            }suma=0;
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                suma+=tab[j][i];
                if(suma==3)
                    return true;
                
            }suma=0;
        }
        for(int i=0;i<3;i++){
                suma+=tab[i][i];
                if(suma==3)
                    return true;
                
        }
        suma=0;
        for(int i=0;i<3;i++){
                suma+=tab[i][2-i];
                if(suma==3)
                    return true;
                
        }
        return false;

    }
    
    
    @FXML
    private void move(MouseEvent event) {
        ImageView img = (ImageView)event.getTarget();
        img.setImage(x);
        short col=(short) (img.getId().toCharArray()[2]-48);
        short row=(short) (img.getId().toCharArray()[1]-48);
        tab[col][row]=1;
        if(checkWin()){
            board.setDisable(true);
            label.setText("You won!");
        }
        else
            ai();
        
            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        board.setDisable(true);
    }    
    
}
