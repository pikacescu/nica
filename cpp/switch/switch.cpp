#include <iostream>

using namespace std;

int main()
{
    {
        cout << "Ex.1: Sa se determine anotimpul din care face parte luna" << endl;
        cout << "" << endl;
        unsigned x;
        cout << "Introduceti numarul de ordine al lunii anului" << endl;
        cin >>x;
        switch (x)
        {
        case 1:
            cout << "Luna ianuarie-anotimpul iarna" << endl; break;
        case 2:
            cout << "Luna februarie-anotimpul iarna" << endl; break;
        case 3:
            cout << "Luna martie-anotimpul primavara" << endl; break;
        case 4:
            cout << "Luna aprilie-anotimpul primavara" << endl; break;
        case 5:
            cout << "Luna mai-anotimpul primavara" << endl; break;
        case 6:
            cout << "Luna iunie-anotimpul vara" << endl; break;
        case 7:
            cout << "Luna iulie-anotimpul vara" << endl; break;
        case 8:
            cout << "Luna august-anotimpul vara" << endl; break;
        case 9:
            cout << "Luna septembrie-anotimpul toamna" << endl; break;
        case 10:
            cout << "Luna octombrie-anotimpul toamna" << endl; break;
        case 11:
            cout << "Luna noiembrie-anotimpul toamna" << endl; break;
        case 12:
            cout << "Luna decembrie-anotimpul iarna" << endl; break;
        default:
            cout << "Numarul introdus trebuie sa fie cuprins in intervalul [1-12] pentru" << endl;
            cout << "a reprezenta numarul de ordine al unei luni a anului. Va rugam incercati" << endl;
            cout << "din nou!" << endl; break;
        }
        cout << "" << endl;cout << "" << endl;

    }
    {
        cout << "Ex.2: Sa se determine numarul de zile corespunzatoare lunii" << endl;
        cout << "" << endl;
        unsigned x;
        cout << "Introduceti numarul de ordine al lunii anului" << endl;
        cin >>x;
        switch (x)
        {
        case 1:
            cout << "Luna ianuarie-31 de zile" << endl; break;
        case 2:
            cout << "Luna februarie-28 sau 29 de zile" << endl; break;
        case 3:
            cout << "Luna martie-31 de zile" << endl; break;
        case 4:
            cout << "Luna aprilie-30 de zile" << endl; break;
        case 5:
            cout << "Luna mai-31 de zile" << endl; break;
        case 6:
            cout << "Luna iunie-30 de zile" << endl; break;
        case 7:
            cout << "Luna iulie-31 de zile" << endl; break;
        case 8:
            cout << "Luna august-31 de zile" << endl; break;
        case 9:
            cout << "Luna septembrie-30 de zile" << endl; break;
        case 10:
            cout << "Luna octombrie-31 de zile" << endl; break;
        case 11:
            cout << "Luna noiembrie-30 de zile" << endl; break;
        case 12:
            cout << "Luna decembrie-31 de zile" << endl; break;
        default:
            cout << "Numarul introdus trebuie sa fie cuprins in intervalul [1-12] pentru" << endl;
            cout << "a reprezenta numarul de ordine al unei luni a anului. Va rugam incercati" << endl;
            cout << "din nou!" << endl; break;
        }
        cout << "" << endl;cout << "" << endl;
    }
    {
        cout << "Ex.3: Sa se determine daca litera introdusa este vocala sau consoana" << endl;
        char x;
        cout << "Introduceti o litera" << endl;
        cin >>x;
        if (x >= 'A' && x <= 'Z') x -= 'A' - 'a';
        switch (x)
        {
        case 'a': case 'e': case 'i': case 'o': case 'u':
            cout << "litera introdusa este o vocala" << endl;
            break;
        default:
            cout << "Litera introdusa este o consoana" << endl;
            break;
            cout << "" << endl;cout << "" << endl;

        }
        {
            cout << "Ex.4: Aflati ziua saptamanii dupa numarul de ordine" << endl;
            unsigned x;
            cout << "Dati numarul de ordine al zilei saptamanii" << endl;
            cin >>x;
            switch (x)
            {
            case 1:
                cout << "luni" << endl; break;
            case 2:
                cout << "marti" << endl; break;
            case 3:
                cout << "miercuri" << endl; break;
            case 4:
                cout << "joi" << endl; break;
            case 5:
                cout << "vineri" << endl; break;
            case 6:
                cout << "sambata" << endl; break;
            case 7:
                cout << "duminica" << endl; break;
            default:
                cout << "Nu ati introdus un numar de ordine corespunzator unei zile a saptamanii." << endl;
                cout << "Incercati din nou!" << endl;
            }
            cout << "" << endl;cout << "" << endl;
        }
        {
            cout <<"Ex.5: Convertiti numarele in cifre romane"<<endl;
            unsigned x;
            cout<<"Introduceti unul dintre următoarele numere: 1, 5, 10, 50, 100, 500 sau 1000"<<endl;
            cin >>x;
            switch (x)
            {
            case 1:
                cout << "I" << endl; break;
            case 5:
                cout << "V" << endl; break;
            case 10:
                cout << "X" << endl; break;
            case 50:
                cout << "L" << endl; break;
            case 100:
                cout << "C" << endl; break;
            case 500:
                cout << "D" << endl; break;
            case 1000:
                cout << "M" << endl; break;
            default:
                cout << "Nu ati introdus unul dintre numerele cerute. Incercati din nou!" << endl;
            }
        }

    }
    cout << "" << endl;
    return 0;
}
