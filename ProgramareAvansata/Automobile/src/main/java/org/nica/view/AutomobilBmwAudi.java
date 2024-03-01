package org.nica.view;

import org.nica.Sarcini;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AutomobilBmwAudi extends JFrame {


        public AutomobilBmwAudi ()
        {
            super("Lista automobile BMW si Audi");

            this.setPreferredSize( new Dimension( 680, 575 ) );
            this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

            automobilTable = new JTable();
            JScrollPane scrollPane = new JScrollPane(automobilTable);
            automobilTable.setFillsViewportHeight(true);

            Container container = this.getContentPane();
            container.add(scrollPane);

            Sarcini.extractBmwAudiToSeparateTable();
            updateAutomobile();

            this.pack();

        }
        JTable automobilTable;

        public void updateAutomobile() {
            String[] columnNames = {"nume", "pret", "marca", "firma", "tara", "numar", "culoare", "produs", "cumparat"};
            final Object[][] rowData = Sarcini.getBmwAudi().stream()
                    .map(
                            automobil ->
                                    new Object []
                                            {
                                                    automobil.getModel().getNume(),
                                                    automobil.getModel().getPret(),
                                                    automobil.getModel().getMarca().getNume(),
                                                    automobil.getModel().getMarca().getFirma().getNume(),
                                                    automobil.getModel().getMarca().getFirma().getTara().getNume(),
                                                    automobil.getNrInregistrare(),
                                                    automobil.getCuloare(),
                                                    automobil.getAnProducere(),
                                                    automobil.formatCumparare(),
                                            }
                    )
                    .toArray(Object[][]::new);
            automobilTable.setModel (new DefaultTableModel(rowData, columnNames) );

        }

}
