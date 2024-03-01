#include <iostream>
#include <iomanip>
#include "meniu.h"
#include "carte.h"
using std::cout;
using std::cin;
using std::endl;

char tasta()
{
    string s;
    char c;
    cin >> s;
    c = s[0];
    if (c >= '0' && c <= '9') return c;
    if (c >= 'a' && c <= 'z') return c;
    if (c >= 'A' && c <= 'Z') return c;
    return 27;
}

void afisare_cititori (subs_vector& sbs)
{
    cout<< "   ============  lista cititori ============" << endl<< sbs<< endl;
}
void afisare_carti (book_vector& bks)
{
    cout<< "   ============  lista carti ============" << endl<< bks<< endl;
}
void afisare_comenzi (comanda_vector& cmds)
{
    cout<< "   ============  lista comenzi ============" << endl << cmds<< endl;
}
void afisare_tot (book_vector& bks, subs_vector& sbs, comanda_vector& cmds)
{
    afisare_carti    (bks);
    afisare_cititori (sbs);
    afisare_comenzi  (cmds);
}

void inregistreaza_carte (book_vector& bks)
{
    cout << "** Intruducere carte. Codul generat: "<< bks.cod_max<< endl
         << "      Introdu {an editare, nume autor,  prenumele autor, titlu, pret}:"<< endl;
    bool valid = bks.introdu();
    if (!valid)
        cout << "** Inregistrare carte esuat! Datele introduse sunt gresite. **"<< endl;
}

void pune_la_evidenta_cititor (subs_vector& sbs)
{
    cout << "** Introducere cititor. Codul generat:" << sbs.cod_max<< endl
         << "      Introdu {nume, prenume, data nasterii yyyy/mm/dd, adresa, sex(m/f)}:" << endl;
    bool valid = sbs.introdu();
    if (!valid)
        cout<< "** Inregistrare cititor esuat! Datele introduse sunt gresite. **"<< endl;
}
void inregistreaza_imprumut (comanda_vector& cmds)
{
    cout << "** Introducere imprumut. Codul generat:"<< cmds.cod_max << endl
         << "      Introdu imprumut {cod carte, cod client, imprumut yyyy/mm/dd, termen zile, restituire yyyy/mm/dd ori 0}:" << endl;
    bool valid = cmds.introdu();
    if (!valid)
        cout << "** Inregistrare comanda esuat! Datele introduse sunt gresite. **"<< endl;
}
void excludem_comenzi_restituite (comanda_vector& cmds)
{
    cout<< "Comenzi restituite excluse" << endl;
    cmds.exclude_restituite();
}
void creaza_nu_rest (book_vector& bks, subs_vector& sbs, comanda_vector& cmds)
{
    cout<< "Cream nu rest, clienti barbati care nu au returnat la timp" << endl;
    ofstream ofs ("NuRest.txt");
    comanda* begin = cmds.vt;
    for (int i = 0; i < cmds.n; i++)
    {
        if (begin[i].restituit_la_timp) continue;
        int ic = sbs.find_cod(begin[i].subs_code);
        subs client = sbs.vt[ic];
        if (client.gender != 'm') continue;
        ofs<< client.name << '\t'<< client.fname<< endl;
    }
}

void afiseaza_carti_dupa_an (book_vector& bks)
{
    bks.sortare_an_denumire();
    cout<< "Afisare carti dupa an. Introdu anul:" << endl;
    int an;
    cin>> an;
    cout<< "Cartile dupa anul "<< an<< " sunt:" << endl;

    book* it = bks.vt, *end = bks.vt + bks.n;
    for (; it < end && it->year <= an; it++);

    for (; it < end; it++) cout<< *it<< endl;

}
void afiseaza_cartile_vechime (book_vector& bks)
{
    bks.sortare_an_descrescator();
    cout<< "Afisare a cartilor cu cea mai mare vechime. Introdu numarul de carti:" << endl;
    int nr;
    cin>> nr;
    cout<< "Cele mai vechi "<< nr<< " carti sunt:" << endl;
    book* it = bks.vt;
    for (int i = 0; i < bks.n && i < nr; i++) cout<< it[i]<< endl;
}

void afiseaza_cartile_autor_desc (book_vector& bks)
{
    bks.sortare_autor_descrescator();
    cout<< "Afisare a cartilor autor descrescator:" << endl<< bks<< endl;
}

void determina_pret_mediu_carti (book_vector& bks)
{
    cout<< "Pretul mediu al cartilor este "<< bks.pret_mediu()<< endl;
}

bool controler (book_vector& bks, subs_vector& sbs, comanda_vector& cmds, data& today)
{
    switch (tasta())
    {
    case '1':
        inregistreaza_carte(bks);
        return true;
    case '2':
        pune_la_evidenta_cititor(sbs);
        return true;
    case '3':
        inregistreaza_imprumut(cmds);
        return true;
    case '4':
        excludem_comenzi_restituite(cmds);
        return true;
    case '5':
        afiseaza_carti_dupa_an(bks);
        return true;
    case '6':
        creaza_nu_rest(bks, sbs, cmds);
        return true;
    case '7':
        determina_pret_mediu_carti(bks);
        return true;
    case '8':
        afiseaza_cartile_vechime(bks);
        return true;
    case 'a':
        afisare_tot(bks, sbs, cmds);
        return true;
    case 'b':
        afiseaza_cartile_autor_desc(bks);
        return true;
    case 27: //ESC
    default: //orice alta tasta
        return false;
    }
    return false;
}
void meniu (book_vector& bks, subs_vector& sbs, comanda_vector& cmds, data& today)
{
    do
    {
        cout<< "   ================= meniu principal =================="<< endl;
        cout<< "1. Inregistreaza o noua carte"       << endl
            << "2. Pune la evidenta un cititor nou"  << endl
            << "3. Inregistreaza o noua imprumutare" << endl
            << "4. Exclude comenzile restituite"     << endl
            << "5. Afiseaza cartile dupa anul"       << endl
            << "6. Creeaza NuRest.txt cu abonatii barbati ce nu au restituit cartile in termenul prestabilit" << endl
            << "7. Determina pretul mediu al unei carti din biblioteca"  << endl
            << "8. Afiseaza atributele cartilor cu cea mai mare vechime" << endl
            << "9. Apasa orice altceva pentru a iesi din program" << endl
            << "a. Afiseaza toate datele" << endl
            << "b. Afiseaza cartile dupa autor, descrescator" << endl;
    }
    while (controler (bks, sbs, cmds, today));

}
