#ifndef __SUBSCRIBER_H__
#define __SUBSCRIBER_H__
#include <iostream>
#include <fstream>
#include <string>
#include "data.h"
using namespace std;


struct subs
{
    int code = 0;

    std::string name;
    std::string fname;

    data data_nasterii;
    std::string adress;
    char gender = 'm';

};
std::ostream& operator << (std::ostream&, subs&);

struct subs_vector
{
    std::string nume_fisier = "Abonati.txt";
    int alocat = 0, n = 0, cod_max = -1;
    subs* vt = 0;
    data today; //shortcut pentru operatii importante cu date

    void alocare();
    void incarca();
    void adauga(subs&);
    bool introdu();
    int find_cod(int c)
    {
        for (int i = 0; i < n; i++)
            if (vt[i].code == c) return i;
        return -1;
    }
    void sterge(int);
    void salveaza();
    void descarca();
};
std::ostream& operator << (std::ostream&, subs_vector&);



#endif // __SUBSCRIBER_H__

