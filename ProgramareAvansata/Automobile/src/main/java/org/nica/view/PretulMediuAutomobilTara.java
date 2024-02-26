package org.nica.view;

import org.nica.Sarcini;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PretulMediuAutomobilTara extends JFrame {


    public PretulMediuAutomobilTara ()
    {
        super("Pretul Mediu Al Automobilului Pe Tara");

        this.setPreferredSize( new Dimension( 680, 575 ) );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        tariTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(tariTable);
        tariTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tariTable.setFillsViewportHeight(true);
        ListSelectionModel selectionModel = tariTable.getSelectionModel();
        lblPretMediu = new JLabel();
        selectionModel.addListSelectionListener(e -> {
            int idx = tariTable.getSelectionModel().getMinSelectionIndex();
            int numeIdx = ((AbstractTableModel)tariTable.getModel()).findColumn("nume");
            String taraNume = tariTable.getValueAt(idx, numeIdx).toString();
            Double pretMediu =  Sarcini.getPretulMediuDinTara(taraNume);

            lblPretMediu.setText(String.format("Pretul mediu al automobilelor pe %s e %f Euro", taraNume, pretMediu));
        });

        Container container = this.getContentPane();
        Box box = Box.createVerticalBox();
        box.add(lblPretMediu);
        container.add(box, BorderLayout.SOUTH );
        container.add(scrollPane);

        Sarcini.extractBmwAudiToSeparateTable();
        updateAutomobile();

        this.pack();
        this.selectFirst();

    }
    JTable tariTable;
    JLabel lblPretMediu;
    public void updateAutomobile() {
        String[] columnNames = {"nume"};
        final Object[][] rowData = Sarcini.getTari().stream()
                .map(
                        tara ->
                                new Object []
                                        {
                                                tara.getNume(),
                                        }
                )
                .toArray(Object[][]::new);
        tariTable.setModel (new DefaultTableModel(rowData, columnNames) );

    }

    public void selectFirst() {
        tariTable.setRowSelectionInterval(0, 0);
    }
}
