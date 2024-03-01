package org.nica.view;

import org.nica.Sarcini;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Automobil extends JFrame {
    static int x = 0;

    public Automobil ()
    {
        super("Lista automobile");

        this.setPreferredSize( new Dimension( 680, 575 ) );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        automobilTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(automobilTable);
        automobilTable.setFillsViewportHeight(true);

        JButton btnAdauga = new JButton( "Adaugare" );
        btnAdauga.setActionCommand("adaugare");
        btnAdauga.addActionListener(automobilListener);

        JButton btnSterge = new JButton( "Sterge" );
        btnSterge.setActionCommand("sterge");
        btnSterge.addActionListener(automobilListener);

        JButton btnExtrageBmwAudi = new JButton( "Extrage BMW si Audi" );
        btnExtrageBmwAudi.setActionCommand("BMWandAudi");
        btnExtrageBmwAudi.addActionListener(automobilListener);

        JButton btnFirmeNrMaximal = new JButton( "Firmele ce au produs numarul maximal de automobile" );
        btnFirmeNrMaximal.setActionCommand("firmeNrMaximal");
        btnFirmeNrMaximal.addActionListener(automobilListener);

        JButton btnAutomobilVerde = new JButton( "Cele mai ieftine si cele mai scumpe automobile de culoare verde" );
        btnAutomobilVerde.setActionCommand("automobilVerde");
        btnAutomobilVerde.addActionListener(automobilListener);

        JButton btnPretMediuTara = new JButton( "Pretul mediu al automobilului te tara" );
        btnPretMediuTara.setActionCommand("pretulMediuPeTara");
        btnPretMediuTara.addActionListener(automobilListener);

        Container container = this.getContentPane();
        Box box = Box.createVerticalBox();
        box.add(btnAdauga);
        box.add(btnExtrageBmwAudi);
        box.add(btnAutomobilVerde);
        box.add(btnPretMediuTara);
        box.add(btnFirmeNrMaximal);
        box.add(btnSterge);

        container.add( box, BorderLayout.SOUTH );
        container.add(scrollPane);


        updateAutomobile();

        this.pack();

    }

    ActionListener automobilListener = new ActionListener() {
        private Automobil autoForm;
        public void actionPerformed(ActionEvent e) {

            String lnfName = e.getActionCommand();
            switch(e.getActionCommand())
            {
                case "sterge":
                    System.out.println("sters");
                    if (automobilTable.getSelectedRowCount() == 0) return;
                    int numarIdx = ((AbstractTableModel)automobilTable.getModel()).findColumn("numar");
                    String[] numere = Arrays.stream(automobilTable.getSelectedRows())
                            .mapToObj(row -> automobilTable.getModel().getValueAt(row, numarIdx).toString())
                            .toArray(String[]::new);
                    System.out.println("Stergem numerele: " + Arrays.toString(numere));
                    int dialogResult = JOptionPane.showConfirmDialog
                            (null,
                                    "Stergem numerele: " + Arrays.toString(numere) + "?",
                                    "Stergere automobile",
                                    JOptionPane.YES_NO_OPTION);
                    if(dialogResult != 0) return;
                    Arrays.stream(numere).forEach (Sarcini::excludeAutomobil);

                    updateAutomobile();
                    return;
                case "adaugare":
                    InregistreazaAutomobilNou inregistreazaAutomobilNou = new InregistreazaAutomobilNou(autoForm);
                    inregistreazaAutomobilNou.setVisible(true);
                    return;
                case "BMWandAudi":
                    JFrame automobilBmwAudi = new AutomobilBmwAudi();
                    automobilBmwAudi.setVisible(true);
                    return;
                case "automobilVerde":
                    JFrame automobilVerde = new AutomobilVerdeIeftinScump();
                    automobilVerde.setVisible(true);
                    return;
                case "firmeNrMaximal":
                    JFrame firmeNrMaximal = new FirmeNrMaximalAutomobile();
                    firmeNrMaximal.setVisible(true);
                    return;
                case "pretulMediuPeTara":
                    PretulMediuAutomobilTara pretulMediuAutomobilTara = new PretulMediuAutomobilTara ();
                    pretulMediuAutomobilTara.setVisible(true);

                    return;
            }
        }
        private ActionListener init(Automobil var){
            autoForm = var;
            return this;
        }
    }.init(this);
    public void inregistreazaAutomobil(org.nica.model.Automobil automobil)
    {
        Sarcini.adaugaAutomobil (automobil);

        updateAutomobile();
        /*
        Object[] rowData =
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
                };
        tableModel.addRow( rowData );//*/
    }
    JTable automobilTable;

    public void updateAutomobile() {
        String[] columnNames = {"nume", "pret", "marca", "firma", "tara", "numar", "culoare", "produs", "cumparat"};
        final Object[][] rowData = Sarcini.getAlfabetic().stream()
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
