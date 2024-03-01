#ifndef ___CARTE_H___
#define ___CARTE_H___

#include <string>
#include <iostream>
#include "data.h"

struct book
{
    int code = 0, year = 0;
    struct
    {
        std::string name;
        std::string fname;
    } autor;
    std::string title;
    float price = 0;

};
std::ostream& operator << (std::ostream&, book&);

struct book_vector
{
    std::string nume_fisier = "Carti.txt";
    int alocat = 0, n = 0, cod_max = -1;
    book* vt = 0;
    data today; //shortcut pentru operatii importante cu date

    void alocare();
    void incarca();
    float pret_mediu();
    void adauga(book& bk);
    bool introdu();
    void sterge(int);
    int find_cod(int c)
    {
        for (int i = 0; i < n; i++)
            if (vt[i].code == c) return i;
        return -1;
    }
    void salveaza();
    void descarca();
    void sortare_an_denumire();
    void sortare_an_descrescator();
    void sortare_autor_descrescator();
};
std::ostream& operator << (std::ostream&, book_vector&);

#endif // ___CARTE_H___
