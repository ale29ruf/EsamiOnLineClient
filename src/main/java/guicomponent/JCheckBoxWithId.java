package guicomponent;

import javax.swing.*;

public class JCheckBoxWithId extends JCheckBox {
    private int idScelta;

    public JCheckBoxWithId(String testoScelta, int idScelta) {
        super(testoScelta);
        this.idScelta = idScelta;
    }

    public int getIdScelta() {
        return idScelta;
    }

    public void setIdScelta(int id) {
        this.idScelta = id;
    }
}
