#include <iostream>

using namespace std;

int main()
{
    {

    }cout << "Ex.1: Sa se determine luna corespunzatoare numarului de ordine" << endl;
        cout << "" << endl;
        unsigned x;
        cout << "Introduceti numarul de ordine al lunii anului" << endl;
        cin >>x;
        switch (x)
        {
        case 1:
            cout << "Luna ianuarie" << endl; break;
        case 2:
            cout << "Luna februarie" << endl; break;
        case 3:
            cout << "Luna martie" << endl; break;
        case 4:
            cout << "Luna aprilie" << endl; break;
        case 5:
            cout << "Luna mai" << endl; break;
        case 6:
            cout << "Luna iunie" << endl; break;
        case 7:
            cout << "Luna iulie" << endl; break;
        case 8:
            cout << "Luna august" << endl; break;
        case 9:
            cout << "Luna septembrie" << endl; break;
        case 10:
            cout << "Luna octombrie" << endl; break;
        case 11:
            cout << "Luna noiembrie" << endl; break;
        case 12:
            cout << "Luna decembrie" << endl; break;
        default:
            cout << "Numarul introdus trebuie sa fie cuprins in intervalul [1-12] pentru" << endl;
            cout << "a reprezenta numarul de ordine al unei luni a anului. Va rugam incercati" << endl;
            cout << "din nou!" << endl; break;
        }
        cout << "" << endl;cout << "" << endl;
        {
            cout << "Ex.2: Sa se determine denumirea cifrei" << endl;
        cout << "" << endl;
        unsigned x;
        cout << "Introduceti o cifra" << endl;
        cin >>x;
        switch (x)
        {
        case 1:
            cout << "unu" << endl; break;
        case 2:
            cout << "doi" << endl; break;
        case 3:
            cout << "trei" << endl; break;
        case 4:
            cout << "patru" << endl; break;
        case 5:
            cout << "cinci" << endl; break;
        case 6:
            cout << "sase" << endl; break;
        case 7:
            cout << "sapte" << endl; break;
        case 8:
            cout << "opt" << endl; break;
        case 9:
            cout << "noua" << endl; break;
        default:
           cout << "valoarea introdusa de dvs este un numar, va rugam sa introduceti o cifra" << endl;
        }
        cout << "" << endl;cout << "" << endl;
        }
        {
            cout << "Ex.4: Aflati ziua saptamanii dupa numarul de ordine in 3 limbi" << endl;
            unsigned x;
            cout << "Dati numarul de ordine al zilei saptamanii" << endl;
            cin >>x;
            switch (x)
            {
            case 1:
                cout << "luni, lundi, Monday" << endl; break;
            case 2:
                cout << "marti, mardi, Tuesday" << endl; break;
            case 3:
                cout << "miercuri, mercredi, Wednesday" << endl; break;
            case 4:
                cout << "joi, jeudi, Thursday" << endl; break;
            case 5:
                cout << "vineri, vendredi, Friday" << endl; break;
            case 6:
                cout << "sambata, samedi, Saturday" << endl; break;
            case 7:
                cout << "duminica, dimanche, Sunday" << endl; break;
            default:
                cout << "Nu ati introdus un numar de ordine corespunzator unei zile a saptamanii." << endl;
                cout << "Incercati din nou!" << endl;
            }
            cout << "" << endl;cout << "" << endl;
        }
    cout << "" << endl;
    return 0;
}
