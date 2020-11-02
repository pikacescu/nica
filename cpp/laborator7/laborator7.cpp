#include <iostream>

using namespace std;

int main()
{
    {
        char x;
        cout << "dati un caracter" << endl;
        cin >>x;
        if (x >= 'A' && x <= 'Z')
            cout <<"litera majuscula"<<endl;
        else if (x >= 'a' && x <= 'z')
            cout <<"litera minuscula"<<endl;
        else if (x>='0' && x <= '9')
            cout <<"cifra"<<endl;
        else
            cout <<"caracter special"<<endl;
    }
    {
        char x;
        cout << "Dati un caracter" << endl;
        cin >>x;
        if (x >= 'A' && x <= 'Z') x -= 'Z' - 'z';
        if (x=='a' || x=='e' || x=='i' || x=='o' || x=='u')
            cout << "caracterul introdus este o vocala" << endl;
        else if (x >='0' && x <='9')
            cout <<"caracterul introdus nu este o litera"<<endl;
    }
    {
        unsigned i, p, n;
        cout<<"dati  numarul pana la care sa se inmulteasca cifrele"<<endl;
        cin >>n;
        p=1; //p este produsul
        for (i=1; i<=n; i++)
        {
            p=i*p;
        }
        cout <<"produsul final este "<<p<<endl;
        cout<<"ultima cifra a produsului este" <<p%10<<endl;
    }
    {
        float nm, b;
        cout<<"dati nota medie a elevului"<<endl;
        cin >>nm;
        if (nm<5)
            b=0;
        else if (nm>=5 && nm<7)
            b=100;
        else if (nm >=7 && nm<8)
            b=20*nm;
        else if (nm>=8 && nm<10)
            b=25*nm;
        else if (nm=10)
            b=30*nm;
        else if (nm>10 || nm<0)
            cout <<"nu ati introdus o nota, incercati din nou"<< endl;
        cout<<"bursa elevului constituie "<<b<<" lei"<<endl;
    }
    return 0;
}
