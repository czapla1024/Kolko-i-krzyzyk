/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gra;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

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
    private Button but;

    Image x = new Image("gra/x.png");
    Image o = new Image("gra/o.png");

    short tab[][] = new short[3][3];

    @FXML
    private void start(ActionEvent event) {
        ImageView obr2;
        label.setText("Your turn");
        but.setText("New game");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tab[i][j] = 0;
            }
        }
        board.setDisable(false);
        board.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        board.setStyle("-fx-border-color: black");
        for (Node obr : board.getChildren().filtered((Node t) -> t.getId() != null)) {
            obr2 = (ImageView) obr;
            obr2.setImage(null);
        }
    }

    private List ai() {
        short i, j, countEm = 0, countAI = 0, indEm = 0;
        List<Short> wybor = new ArrayList();
        Map<Short, Short> linia = new TreeMap<>();
        for (i = 0; i < 3; i++) { //wygrana
            for (j = 0; j < 3; j++) {
                if (tab[i][j] == 0) {
                    indEm = (short) (3 * i + j);
                    countEm++;
                }
                if (tab[i][j] == -1) {
                    countAI++;
                }
            }
            if (countAI == 2 && countEm == 1) {
                wybor.add(indEm);
            }
            countAI = 0;
            countEm = 0;
        }
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (tab[j][i] == 0) {
                    indEm = (short) (3 * j + i);
                    countEm++;
                }
                if (tab[j][i] == -1) {
                    countAI++;
                }
            }
            if (countAI == 2 && countEm == 1) {
                wybor.add(indEm);
            }
            countAI = 0;
            countEm = 0;
        }
        for (i = 0; i < 3; i++) {
            if (tab[i][i] == 0) {
                indEm = (short) (4 * i);
                countEm++;
            }
            if (tab[i][i] == -1) {
                countAI++;
            }
        }
        if (countAI == 2 && countEm == 1) {
            wybor.add(indEm);
        }
        countAI = 0;
        countEm = 0;
        for (i = 0; i < 3; i++) {
            if (tab[i][2 - i] == 0) {
                indEm = (short) (2 * i + 2);
                countEm++;
            }
            if (tab[i][2 - i] == -1) {
                countAI++;
            }
        }
        if (countAI == 2 && countEm == 1) {
            wybor.add(indEm);
        }
        if (!wybor.isEmpty()){
            board.setDisable(true);
            label.setText("You lost!");
            board.setStyle("-fx-border-color: red");
            return wybor;
        }
        countAI = 0;
        countEm = 0;
        for (i = 0; i < 3; i++) {//obrona przed przegraną
            for (j = 0; j < 3; j++) {
                if (tab[i][j] == 0) {
                    indEm = (short) (3 * i + j);
                    countEm++;
                }
                if (tab[i][j] == 1) {
                    countAI++;
                }
            }
            if (countAI == 2 && countEm == 1) {
                wybor.add(indEm);
            }
            countAI = 0;
            countEm = 0;
        }
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (tab[j][i] == 0) {
                    indEm = (short) (3 * j + i);
                    countEm++;
                }
                if (tab[j][i] == 1) {
                    countAI++;
                }
            }
            if (countAI == 2 && countEm == 1) {
                wybor.add(indEm);
            }
            countAI = 0;
            countEm = 0;
        }
        for (i = 0; i < 3; i++) {
            if (tab[i][i] == 0) {
                indEm = (short) (4 * i);
                countEm++;
            }
            if (tab[i][i] == 1) {
                countAI++;
            }
        }
        if (countAI == 2 && countEm == 1) {
            wybor.add(indEm);
        }
        countAI = 0;
        countEm = 0;
        for (i = 0; i < 3; i++) {
            if (tab[i][2 - i] == 0) {
                indEm = (short) (2 * i + 2);
                countEm++;
            }
            if (tab[i][2 - i] == 1) {
                countAI++;
            }
        }
        if (countAI == 2 && countEm == 1) {
            wybor.add(indEm);
        }
        if (!wybor.isEmpty())
            return wybor;
        for (i = 0; i < 3; i++) //brak lepszych ruchów
            for (j = 0; j < 3; j++) 
                if (tab[i][j] == 0)
                    wybor.add((short) (3*i+j));
        return wybor;
        
    }
    private boolean checkTie() {
        for (short i = 0; i < 3; i++) 
            for (short j = 0; j < 3; j++)
                if(tab[i][j]==0)
                    return false;
        return true;
    }
    private boolean checkWin() {
        short i, j, suma = 0;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                suma += tab[i][j];
                if (suma == 3) {
                    return true;
                }

            }
            suma = 0;
        }
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                suma += tab[j][i];
                if (suma == 3) {
                    return true;
                }

            }
            suma = 0;
        }
        for (i = 0; i < 3; i++) {
            suma += tab[i][i];
            if (suma == 3) {
                return true;
            }

        }
        suma = 0;
        for (i = 0; i < 3; i++) {
            suma += tab[i][2 - i];
            if (suma == 3) {
                return true;
            }

        }
        return false;

    }

    @FXML
    private void move(MouseEvent event) {
        Random r = new Random();
        final short colAI, rowAI;
        EventTarget target = event.getTarget();
        if (target instanceof javafx.scene.image.ImageView) {
            ImageView img = (ImageView) event.getTarget();
            short col = (short) (img.getId().toCharArray()[2] - 48);
            short row = (short) (img.getId().toCharArray()[1] - 48);
            if (tab[col][row] == 0) {
                img.setImage(x);
                tab[col][row] = 1;
                if (checkWin()) {
                    board.setDisable(true);
                    board.setStyle("-fx-border-color: lime");
                    label.setText("You won!");
                } else if(checkTie()) {
                    board.setDisable(true);
                    board.setStyle("-fx-border-color: yellow");
                    label.setText("A tie!");
                } else {
                    List<Short> wybor = ai();
                    short ind = wybor.get((short) r.nextInt(wybor.size()));
                    rowAI = (short) (ind / 3);
                    colAI = (short) (ind % 3);
                    tab[rowAI][colAI] = -1;
                    FilteredList<Node> pole = board.getChildren().filtered((Node t) -> {
                        final String ID = t.getId();
                        return ID!=null && ID.equals("c"+colAI+rowAI);
                    });
                    for (Node n : pole) {
                        img = (ImageView) n;
                        img.setImage(o);
                    }

                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
