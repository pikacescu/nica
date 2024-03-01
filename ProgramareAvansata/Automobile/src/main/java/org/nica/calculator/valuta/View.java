package org.nica.calculator.valuta;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings({"DataFlowIssue", "ReassignedVariable", "SpellCheckingInspection"})
class View extends JFrame{
    Model model = new Model();
    Controller controller = new Controller();

    JLabel wtd1 = new JLabel("Convert from"), wtd2= new JLabel("Convert to"),
            sumlb = new JLabel("sum: "), totlb = new JLabel("result: ");

    JTextField sum = new JTextField(10), total = new JTextField(10);

    JComboBox<String> currencyFrom = new JComboBox<>(model.valute), currencyTo = new JComboBox<>(model.valute);

    View()
    {
        super("Triunghiuri tarcate");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        controller.setView(this);

        sum.getDocument().addDocumentListener(controller.documentListener);
        // add ItemListener
        currencyFrom.addItemListener(controller::itemFromChanged);
        currencyTo.addItemListener(controller::itemToChanged);

        // set the checkbox as editable
        currencyFrom.setEditable(true);
        currencyTo.setEditable(true);

        Container container = getContentPane();

        Box boxAll = Box.createVerticalBox();

        Box boxBig = Box.createVerticalBox();
        Box boxSmall = Box.createHorizontalBox();
        boxSmall.add(wtd1);
        boxSmall.add(currencyFrom);
        boxBig.add(boxSmall);
        boxSmall = Box.createHorizontalBox();
        boxSmall.add(wtd2);
        boxSmall.add(currencyTo);
        boxBig.add(boxSmall);
        boxAll.add(boxBig);

        boxBig = Box.createHorizontalBox();
        boxBig.add(sumlb);
        boxBig.add(sum);
        boxBig.add(totlb);
        boxBig.add(total);
        boxAll.add(boxBig);

        container.add( boxAll, BorderLayout.NORTH );

        setSize(400, 400);
        //pack();
    }

    void update()
    {
        sumlb.setText("Sum " + currencyFrom.getSelectedItem() + ":");
        totlb.setText("Result " + currencyTo.getSelectedItem() + ":");
        String valutaFrom = currencyFrom.getSelectedItem().toString(), valutaTo = currencyTo.getSelectedItem().toString();
        float sumaTo = model.valuta2Valuta(valutaFrom, valutaTo, sum.getText());
        if (sumaTo > 0)
            total.setText(String.format("%.2f", sumaTo));
    }

    public static void main(String[] args)
    {
        JFrame f = new View();
        f.setVisible(true);
    }

}