package org.nica.calculator.valuta;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;

@SuppressWarnings({"ClassEscapesDefinedScope", "unused"})
public class Controller {

    View view;
    public void setView(View view){this.view = view;}

    DocumentListener documentListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {System.out.println ("changed update");}
        public void removeUpdate(DocumentEvent e) { view.update(); }
        public void insertUpdate(DocumentEvent e) { view.update(); }
    };


    public void itemFromChanged(ItemEvent e) //controls the selected items
    {
        view.update();
    }

    public void itemToChanged(ItemEvent e) //controls the selected items
    {
        view.update();
    }
}
