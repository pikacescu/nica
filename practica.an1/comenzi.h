#ifndef __COMENZI_H__
#define __COMENZI_H__
#include <fstream>
#include <iostream>
#include "data.h"
#include "carte.h"
#include "subscriber.h"

struct comanda
{
    int code = 0, book_code = 0, subs_code = 0;
    data imprumut;
    int term = 0;
    data restituire;
    bool restituit_la_timp = false;
    bool restituit(){return !restituire.is_null();}
};
std::ostream& operator<< (std::ostream&, comanda&);

struct comanda_vector
{
    std::string nume_fisier = "Comenzi.txt";
    int alocat = 0, n = 0, cod_max = -1;
    comanda* vt = 0;
    data today; //shortcut pentru operatii importante cu date si validare
    book_vector* pbks = 0; //shortcut pentru validare
    subs_vector* psbs = 0; //shortcut pentru validare

    void alocare();
    void incarca();
    void adauga(comanda& cm);
    bool introdu();
    void sterge(int);
    comanda find_cod(int c)
    {
        for (int i = 0; i < n; i++)
            if (vt[i].code == c) return vt[i];
        return {};
    }
    void exclude_restituite();

    void salveaza();
    void descarca();
};
std::ostream& operator<< (std::ostream&, comanda_vector&);

#endif // __COMENZI_H__
