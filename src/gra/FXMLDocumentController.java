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
import javafx.scene.control.ToggleButton;
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

    @FXML
    private ToggleButton easy;

    @FXML
    private ToggleButton hard;

    @FXML
    private ToggleButton pcfirst;

    @FXML
    private ToggleButton aifirst;

    Image x = new Image("gra/x.png");
    Image o = new Image("gra/o.png");

    short tab[][] = new short[3][3];
    boolean aiFirst;
    boolean extraHard;

    @FXML
    private void start(ActionEvent event) {
        ImageView obr2;
        aiFirst = aifirst.isSelected();
        extraHard = hard.isSelected();
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
        if (aiFirst) {
            ai();
        }
    }

    private void warn() {
        if (label.getText().equals("Your turn")) {
            label.setText("Your new settings will be applied on new game");
        }
    }

    @FXML
    private void setEasy(ActionEvent event) {
        warn();
        easy.setDisable(true);
        hard.setDisable(false);
    }

    @FXML
    private void setHard(ActionEvent event) {
        warn();
        easy.setDisable(false);
        hard.setDisable(true);
    }

    @FXML
    private void setPC(ActionEvent event) {
        warn();
        pcfirst.setDisable(true);
        aifirst.setDisable(false);
    }

    @FXML
    private void setAI(ActionEvent event) {
        warn();
        pcfirst.setDisable(false);
        aifirst.setDisable(true);
    }

    private List wybory() {
        short i, j, countEm = 0, countAI = 0, indEm = 0;
        List<Short> wybor = new ArrayList();
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
        if (!wybor.isEmpty()) {
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
        if (!wybor.isEmpty()) {
            return wybor;
        }
        if (extraHard) {//punktowanie możliwych ruchów
            countAI = 0;
            countEm = 0;
            Map<Short, Short> punktacja = new TreeMap<>();
            for (i = 0; i < 9; i++) {
                punktacja.put(i, (short) 0);
            }
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    if (tab[i][j] == 0) {
                        countEm++;
                    }
                    if (tab[i][j] != 0) {
                        countAI++;
                    }
                }
                if (countAI == 1 && countEm == 2) {
                    for (j = 0; j < 3; j++) {
                        if (tab[i][j] == 0) {
                            punktacja.put((short) (3 * i + j), (short) (punktacja.get((short) (3 * i + j)) + 1));
                        }
                    }
                }
                countAI = 0;
                countEm = 0;
            }
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    if (tab[j][i] == 0) {
                        countEm++;
                    }
                    if (tab[j][i] != 0) {
                        countAI++;
                    }
                }
                if (countAI == 1 && countEm == 2) {
                    for (j = 0; j < 3; j++) {
                        if (tab[j][i] == 0) {
                            punktacja.put((short) (3 * j + i), (short) (punktacja.get((short) (3 * j + i)) + 1));
                        }
                    }
                }
                countAI = 0;
                countEm = 0;
            }
            for (i = 0; i < 3; i++) {
                if (tab[i][i] == 0) {
                    countEm++;
                }
                if (tab[i][i] != 0) {
                    countAI++;
                }
            }
            if (countAI == 1 && countEm == 2) {
                for (i = 0; i < 3; i++) {
                    if (tab[i][i] == 0) {
                        punktacja.put((short) (4 * i), (short) (punktacja.get((short) (4 * i)) + 1));
                    }
                }
            }
            countAI = 0;
            countEm = 0;
            for (i = 0; i < 3; i++) {
                if (tab[i][2 - i] == 0) {
                    countEm++;
                }
                if (tab[i][2 - i] != 0) {
                    countAI++;
                }
            }
            if (countAI == 1 && countEm == 2) {
                for (i = 0; i < 3; i++) {
                    if (tab[i][2 - i] == 0) {
                        punktacja.put((short) (2 * i + 2), (short) (punktacja.get((short) (2 * i + 2)) + 1));
                    }
                }
            }
            short max = 0;
            for (Short key : punktacja.keySet()) {
                if (punktacja.get(key) == max) {
                    wybor.add(key);
                }
                if (punktacja.get(key) > max) {
                    wybor.clear();
                    wybor.add(key);
                    max = punktacja.get(key);
                }
            }
            if (max > 0) {
                return wybor;
            } else {
                wybor.clear();
            }
        }
        for (i = 0; i < 3; i++) //brak lepszych ruchów
        {
            for (j = 0; j < 3; j++) {
                if (tab[i][j] == 0) {
                    wybor.add((short) (3 * i + j));
                }
            }
        }
        return wybor;

    }

    private boolean checkTie() {
        for (short i = 0; i < 3; i++) {
            for (short j = 0; j < 3; j++) {
                if (tab[i][j] == 0) {
                    return false;
                }
            }
        }
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

    private void ai() {
        label.setText("Your turn");
        Random r = new Random();
        final short colAI, rowAI;
        List<Short> wybor = wybory();
        short ind = wybor.get((short) r.nextInt(wybor.size()));
        rowAI = (short) (ind / 3);
        colAI = (short) (ind % 3);
        tab[rowAI][colAI] = -1;
        FilteredList<Node> pole = board.getChildren().filtered((Node t) -> {
            final String ID = t.getId();
            return ID != null && ID.equals("c" + colAI + rowAI);
        });
        pole.stream().map((n) -> (ImageView) n).forEachOrdered((img) -> {
            img.setImage(o);
        });
        if (checkTie()) {
            board.setDisable(true);
            board.setStyle("-fx-border-color: yellow");
            label.setText("A tie!");
        }
    }

    @FXML
    private void move(MouseEvent event) {
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
                } else if (checkTie()) {
                    board.setDisable(true);
                    board.setStyle("-fx-border-color: yellow");
                    label.setText("A tie!");
                } else {
                    ai();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
