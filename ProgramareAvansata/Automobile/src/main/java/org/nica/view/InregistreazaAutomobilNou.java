package org.nica.view;

import org.nica.Sarcini;
import org.nica.model.Model;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class InregistreazaAutomobilNou extends JFrame {
    private Automobil automobil;

    JTextField textAnProducere = new JTextField(20);
    JTextField textLunaAnInregistrare = new JTextField(20);
    JTextField textNrInmatriculare = new JTextField(20);
    JTextField textCuloare = new JTextField(20);
    JLabel lblAnProducere = new JLabel("An producere:");
    JLabel lblLunaAnInregistrare = new JLabel("Luna si An inregistrare:");
    JLabel lblNrInmatriculare = new JLabel("Numar Inmatriculare:");
    JLabel lblCuloare = new JLabel("Culoare:");
    Long idModel;

    public InregistreazaAutomobilNou (Automobil automobil)
    {
        super("Selectati modelul, introduceti an producere, luna si an inregistrare, numar inmatriculare, culoare");
        this.automobil  = automobil;

        this.setPreferredSize( new Dimension( 680, 575 ) );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        modelTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(modelTable);
        modelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelTable.setFillsViewportHeight(true);

        JButton btnAdauga = new JButton( "Adaugare" );
        btnAdauga.setActionCommand("adauga");
        btnAdauga.addActionListener(inregistrareListener);

        Container container = this.getContentPane();

        Box box = Box.createVerticalBox();
        Box bx = Box.createHorizontalBox();
        bx.add(lblAnProducere);
        bx.add(textAnProducere);
        box.add(bx);
        bx = Box.createHorizontalBox();
        bx.add(lblLunaAnInregistrare);
        bx.add(textLunaAnInregistrare);
        box.add(bx);
        bx = Box.createHorizontalBox();
        bx.add(lblNrInmatriculare);
        bx.add(textNrInmatriculare);
        box.add(bx);
        bx = Box.createHorizontalBox();
        bx.add(lblCuloare);
        bx.add(textCuloare);
        box.add(bx);
        box.add(btnAdauga);
        container.add(box, BorderLayout.SOUTH );
        container.add(scrollPane);

        Sarcini.extractBmwAudiToSeparateTable();
        updateAutomobile();

        this.pack();
        this.selectFirst();

    }
    JTable modelTable;
    JLabel lblPretMediu;
    private final ActionListener inregistrareListener = new ActionListener (){
        private Automobil autoForm;
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand())
            {
                case "adauga":
                    int idx = modelTable.getSelectionModel().getMinSelectionIndex();
                    int numeIdx = ((AbstractTableModel) modelTable.getModel()).findColumn("Id");
                    Long idModel = Long.parseLong(modelTable.getModel().getValueAt(idx, numeIdx).toString());
                    Model model = Sarcini.getModel(idModel);
                    System.out.println(model);
                    int anProducere = Integer.parseInt(textAnProducere.getText());
                    int anLunaInregistrare = Integer.parseInt(textLunaAnInregistrare.getText());

                    org.nica.model.Automobil automobil = new org.nica.model.Automobil
                        (
                            model, anProducere, anLunaInregistrare, textNrInmatriculare.getText(), textCuloare.getText()
                        );
                    registru.automobil.inregistreazaAutomobil(automobil);



/*
                    System.out.println("sters");
                    if (modelTable.getSelectedRowCount() == 0) return;
                    int numarIdx = ((AbstractTableModel)modelTable.getModel()).findColumn("numar");
                    String[] numere = Arrays.stream(modelTable.getSelectedRows())
                            .mapToObj(row -> modelTable.getModel().getValueAt(row, numarIdx).toString())
                            .toArray(String[]::new);
                    System.out.println("Stergem numerele: " + Arrays.toString(numere));
                    int dialogResult = JOptionPane.showConfirmDialog
                            (null,
                                    "Stergem numerele: " + Arrays.toString(numere) + "?",
                                    "Stergere automobile",
                                    JOptionPane.YES_NO_OPTION);
                    if(dialogResult != 0) return;
                    Arrays.stream(numere).forEach (Sarcini::excludeAutomobil);

                    updateAutomobile();*/
                    return;

            }
        }
        InregistreazaAutomobilNou registru;
        private ActionListener init(Automobil var, InregistreazaAutomobilNou registru){
            this.registru = registru;
            autoForm = var;
            return this;
        }
    }.init(automobil, this);
    public void updateAutomobile() {
        String[] columnNames = {"Id", "Nume", "Pret", "Marca", "Firma", "Tara",};
        final Object[][] rowData = Sarcini.getModele().stream()
            .map(
                 model ->
                     new Object []
                         {
                             model.getId(),
                             model.getNume(),
                             model.getPret(),
                             model.getMarca().getNume(),
                             model.getMarca().getFirma().getNume(),
                             model.getMarca().getFirma().getTara().getNume(),
                         }
            )
            .toArray(Object[][]::new);
        modelTable.setModel (new DefaultTableModel(rowData, columnNames) );
        modelTable.removeColumn(modelTable.getColumnModel().getColumn(0));

    }

    public void selectFirst() {
        modelTable.setRowSelectionInterval(0, 0);
    }

}

