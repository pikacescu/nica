#include <iostream>

using namespace std;

int main()
{
    cout << "Programarea structurata" <<endl;
    cout << "Filipski Nicoleta" <<endl;

    unsigned x;
    cout << "dati un numar natural x" <<endl;
    cin  >>  x;
    cout << "catul impartirii nr x la 7 este "   <<x/7 << endl;
    cout << "restul impartirii lui x la 7 este " <<x%7 << endl;

    unsigned m,a,b,c;
    cout << "introdu un numar de 3 cifre" <<endl;
    cin  >> m;
    a = m%10;
    b = m/100;
    c = m/10%10;
    cout << "suma cifrelor componente ale numarului m este " <<a+b+c <<endl;

    return 0;
}
