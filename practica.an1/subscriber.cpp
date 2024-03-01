#include <iostream>
#include <fstream>
#include <string>
#include <limits>
#include <iomanip>
#include "subscriber.h"


std::ostream& operator << (std::ostream& os, subs& s)
{
    os  << s.code << " " << s.name << " " << s.fname << " "
        << s.data_nasterii<< " "
        << s.adress << " " << s.gender;
    return os;
}
std::ostream& operator << (std::ostream& os, subs_vector& sbs)
{
    subs* begin = sbs.vt;
    for (int i = 0; i < sbs.n; i++)
    {
        os<< begin[i];
        if (i < sbs.n - 1) os<< std::endl;
    }
    return os;
}
using namespace std;

void subs_vector::alocare()
{
    if (n > 0 && n + 2 < alocat) return;
    subs* tmp = vt;
    alocat = 2 * n; //dublam numarul de elemente rezervate
    // daca nu e alocat nimic, rezervam 100 elemente pentru start
    if (n <= 0) n = 0;
    if (alocat < 50) alocat = 100; //rezervam 100 elemente pentru inceput

    vt = new subs[alocat];
    for (int i = 0; i < n; i++)
        vt[i] = tmp[i];
    delete[] tmp;
}
void subs_vector::incarca()
{
    ifstream ifs (nume_fisier);
    ifs>> n;

    alocat = 2 * n;
    if (alocat < 50) alocat = 100;
    vt = new subs[alocat];
    for (int i = 0; i < n; i++)
    {
        ifs >> vt[i].code; ifs.ignore(1);
        if(vt[i].code > cod_max) cod_max = vt[i].code;
        getline (ifs, vt[i].name, '\t');
        getline (ifs, vt[i].fname, '\t');
        int dt;
        ifs>> dt;
        vt[i].data_nasterii.from_simple_int(dt);
        ifs.ignore(1);
        getline (ifs, vt[i].adress, '\t');
        ifs >> vt[i].gender;
    }
    if (cod_max == -1) cod_max = 1;
    else cod_max++;
}

void subs_vector::adauga(subs& s)
{
    alocare();
    vt[n++] = s;
}
bool subs_vector::introdu()
{
    subs s;
    //cin >> s.code;
    s.code = cod_max;
    cin.ignore(numeric_limits<std::streamsize>::max(), '\n');
    getline (cin, s.name);
    getline (cin, s.fname);
    bool valid = s.data_nasterii.introdu();
    if (!valid || s.data_nasterii.is_null() || s.data_nasterii.an > (today.an - 7) || s.data_nasterii.an < (today.an - 120))
    {
        cout<< "** EROARE: Data nastere invalid. Introducere esuat."<< endl
            << "           1. Nu poate fi null"<< endl
            << "           2. Trebuie reflecte o varsta nu mai mica de 7 ani"<< endl
            << "           3. Trebuie reflecte o varsta nu mai mare de 120 ani"<< endl;
        return false;
    }
    cin.ignore(numeric_limits<std::streamsize>::max(), '\n');
    getline (cin, s.adress);
    cin >> s.gender;
    if (s.gender != 'm' && s.gender != 'f')
    {
        cout<< "** EROARE: Sexul invalid, valori posibile m, f. Introducere esuat."<< endl;
        return false;
    }
    adauga(s);
    cod_max++;
    return true;
}
void subs_vector::sterge(int i)
{
    if (i >= n) return;
    n--;
    for (; i < n; i++)
        vt[i] = vt[i + 1];

}
void subs_vector::salveaza()
{
    ofstream ofs ("Abonati.txt");
    ofs<< n<< endl;

    for (int i = 0; i < n; i++)
        ofs << vt[i].code  << '\t'
            << vt[i].name       << '\t'<< vt[i].fname  << '\t'
            << vt[i].data_nasterii.to_simple_int() << '\t'<< vt[i].adress << '\t'
            << vt[i].gender     << endl;

}
void subs_vector::descarca()
{
    n = 0;
    alocat = 0;
    delete [] vt;
    vt = 0;
}
