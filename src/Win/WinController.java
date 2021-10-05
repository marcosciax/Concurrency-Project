package Win;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinController {

    @FXML
    Label winner;
    public static String winnerName;

    public void initialize(){
        winner.setText(winnerName);
    }

}
