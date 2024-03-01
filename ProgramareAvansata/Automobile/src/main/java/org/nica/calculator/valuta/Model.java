package org.nica.calculator.valuta;

import java.util.HashMap;

@SuppressWarnings({"SpellCheckingInspection"})
public class Model{
    // Valutele si cursurile valutare sunt incarcate
    //    din baza de date, sau de pe site-ul BNM
    String[] valute = { " ", "MDL", "USD", "EUR", "XAU", "AUD" };
    float[] cursuri = { -1f, 1f, 17.68f, 19.12f, 12.34f, 14.56f };
    HashMap<String, Float> valuta2curs;

    Model()
    {
        valuta2curs = new HashMap<>();
        for(int i = 1; i < cursuri.length; i++)
            valuta2curs.put(valute[i], cursuri[i]);
    }

    // Transformare sumei din valuta initiala in format independent de valuta
    float mdl2Valuta(String valuta, float suma)
    {
        if (valuta2curs.containsKey(valuta))
            return suma / valuta2curs.get(valuta);
        return -1;
    }

    // Transformarea sumei din format independent de valuta in valuta finala
    float valuta2Mdl(String valuta, float mdl)
    {
        if (valuta2curs.containsKey(valuta))
            return mdl * valuta2curs.get(valuta);
        return -1;
    }
    float valuta2Valuta(String valutaFrom, String valutaTo, float suma)
    {
        float mdl = valuta2Mdl(valutaFrom, suma);
        if (mdl == -1) return -1f;
        return mdl2Valuta(valutaTo, mdl);
    }

    boolean anyNothing(String ... str)
    {
        if(str.length == 0) return true;
        for (String s: str)
            if (s == null || s.isBlank()) return true;
        return false;
    }
    float valuta2Valuta(String valutaFrom, String valutaTo, String strSuma)
    {
        if (anyNothing(valutaFrom, valutaTo, strSuma)) return -1;
        try {
            return valuta2Valuta(valutaFrom, valutaTo, Float.parseFloat(strSuma));
        }catch(NumberFormatException e)
        {
            return -1;
        }
    }


}
