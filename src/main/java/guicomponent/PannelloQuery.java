package guicomponent;


import javax.swing.*;

public class PannelloQuery extends JPanel {
    JPanelQuery jPanelQuery;

    public void setPannello(JPanelQuery jPanelQuery){
        this.jPanelQuery = jPanelQuery;
        jPanelQuery.avvia();
        add(jPanelQuery);
        revalidate();
        repaint();
    }

    public void removePannello(){
        if(jPanelQuery != null)
            remove(jPanelQuery);
        jPanelQuery = null;
        revalidate();
        repaint();
    }
}
