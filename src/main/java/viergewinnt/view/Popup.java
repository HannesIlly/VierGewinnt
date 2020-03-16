package viergewinnt.view;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class Popup extends Stage {

    public Popup(StageStyle style, String title, Window owner) {
        super(style);

        this.setTitle(title);
        this.initModality(Modality.APPLICATION_MODAL);
        this.initOwner(owner);
    }

    public Popup(String title, Window owner) {
        super();

        this.setTitle(title);
        this.initModality(Modality.APPLICATION_MODAL);
        this.initOwner(owner);
    }
}
