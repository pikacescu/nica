#include <iostream>
#include <fstream>
#include "comenzi.h"

std::ostream& operator<< (std::ostream& os, comanda& c)
{
    os  << c.code    << ' '<< c.book_code     << ' ' << c.subs_code  << ' '
        << c.imprumut<< ' '<< c.term          << ' ' << c.restituire << ' ';
    return os;
}
std::ostream& operator<< (std::ostream& os, comanda_vector& cmds)
{
    comanda* begin = cmds.vt;
    for (int i = 0; i < cmds.n; i++)
    {
        os<< begin[i];
        if (i < cmds.n - 1) os << std::endl;
    }
    return os;
}

using namespace std;

void comanda_vector::alocare()
{
    if (n > 0 && n + 2 < alocat) return; //nu se apropie de limita
    comanda* tmp = vt;
    alocat = 2 * n; //dublam numarul de elemente rezervate

    if (n <= 0) n = 0;
    if (alocat < 50) alocat = 100; //rezervam 100 elemente pentru inceput

    vt = new comanda[alocat];
    for (int i = 0; i < n; i++)
        vt[i] = tmp[i];
    delete[] tmp;
}
void comanda_vector::incarca()
{
    ifstream ifs (nume_fisier);
    ifs>> n;

    alocat = 2 * n;
    if (alocat < 50) alocat = 100;
    vt = new comanda[alocat];
    for (int i = 0; i < n; i++)
    {
        ifs >> vt[i].code; ifs.ignore(1);
        if (vt[i].code > cod_max) cod_max = vt[i].code;
        ifs >> vt[i].book_code; ifs.ignore(1);
        ifs >> vt[i].subs_code; ifs.ignore(1);
        int dt;
        ifs >> dt; vt[i].imprumut.from_simple_int(dt); ifs.ignore(1);
        ifs >> vt[i].term; ifs.ignore(1);
        ifs >> dt; vt[i].restituire.from_simple_int(dt);

        if (vt[i].imprumut.in_termen(vt[i].term, vt[i].restituire))
            vt[i].restituit_la_timp = true; //e restituit la timp
        else if (vt[i].imprumut.in_termen(vt[i].term, today))
            vt[i].restituit_la_timp = true; //nu e restituit, dar nu e datorie
    }
    if (cod_max == -1) cod_max = 1;
    else cod_max++;
}

void comanda_vector::adauga(comanda& cm)
{
    alocare();
    vt[n++] = cm;
}
bool comanda_vector::introdu()
{
    comanda cmd;
    //cin >> cmd.code;
    cmd.code = cod_max;
    cin >> cmd.book_code;
    if (pbks->find_cod(cmd.book_code) == -1)
    {
        cout<< "** EROARE: cod carte inexistent, introducere esuat"<< endl;
        return false;
    }
    cin >> cmd.subs_code;
    if (psbs->find_cod(cmd.subs_code) == -1)
    {
        cout<< "** EROARE: cod carte inexistent, introducere esuat"<< endl;
        return false;
    }
    bool valid = cmd.imprumut.introdu();
    if (!valid || cmd.imprumut.is_null())
    {
        cout<< "** EROARE: data imprumut nu este valida, introducere esuat"<< endl;
        return false;
    }
    cin >> cmd.term;
    if (cmd.term <= 1)
    {
        cout<< "** EROARE: termen imprumut nu este valid, introducere esuat"<< endl;
        return false;
    }
    valid = cmd.restituire.introdu();
    if (!valid)
    {
        cout<< "** EROARE: data restituire nu este valida, introducere esuat"<< endl;
        return false;
    }

    adauga(cmd);
    cod_max++;
    return true;
}
void comanda_vector::sterge(int i)
{
    if (i >= n) return;
    n--;
    for (; i < n; i++)
        vt[i] = vt[i + 1];

}

void comanda_vector::exclude_restituite()
{
    int c = 0;
    for (int i = 0; (i + c) < n; i++)
    {
        if (vt[i].restituit()) c++;
        if (c)
            vt[i] = vt[i + c];
    }
    n -= c;
}

void comanda_vector::salveaza()
{
    ofstream ofs (nume_fisier);
    ofs<< n<< endl;

    for (int i = 0; i < n; i++)
        ofs << vt[i].code << '\t' << vt[i].book_code << '\t' << vt[i].subs_code <<'\t'
            << vt[i].imprumut.to_simple_int()    << '\t' << vt[i].term << '\t'
            << vt[i].restituire.to_simple_int() << endl;
}

void comanda_vector::descarca()
{
    n = 0;
    alocat = 0;
    delete [] vt;
    vt = 0;
}
