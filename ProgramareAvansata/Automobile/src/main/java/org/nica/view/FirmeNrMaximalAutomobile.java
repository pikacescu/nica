package org.nica.view;

import org.nica.Sarcini;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FirmeNrMaximalAutomobile extends JFrame {

    public FirmeNrMaximalAutomobile ()
    {
        super("Lista firmelor care produc numarul maximal de automobile");

        this.setPreferredSize( new Dimension( 680, 575 ) );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        automobilTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(automobilTable);
        automobilTable.setFillsViewportHeight(true);

        Container container = this.getContentPane();
        container.add(scrollPane);

        Sarcini.extractBmwAudiToSeparateTable();
        updateFirme();

        this.pack();

    }
    JTable automobilTable;

    public void updateFirme() {
        String[] columnNames = {"nume"};
        var firme = Sarcini.getFirmaNumarMaximal();
        this.setTitle(String.format("Numarul maximal de automobile %d a fost produs de firme", firme.b));
        final Object[][] rowData = firme.a.stream()
            .map(
                firma ->
                    new Object []
                        {
                            firma.getNume(),
                        }
            )
            .toArray(Object[][]::new);
        automobilTable.setModel (new DefaultTableModel(rowData, columnNames) );

    }
}
