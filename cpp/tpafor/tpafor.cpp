#include <iostream>
#include <math.h>
using namespace std;

int main()
{
    {
        cout << "EX.1" << endl;
        int a, b, p;
        cout << "dati numarul a" << endl;
        cin >>a;
        cout << "dati numarul b" << endl;
        cin >>b;
        cout << "Sa se calculeze produsul numerelor impare de pe intervalul a-b" << endl;
        p=1;
        if (!(a&1))a++;//se verifica daca numarul este par testand bitul din dreapta
        for(int i=a; i<=b; i+=2)
                p*=i;
        cout << "produsul numerelor impare este "<<p << endl;
    }
    {
        cout << "EX.2" << endl;
        int a, b;
        cout << "Dati numarul a" << endl;
        cin >>a;
        cout << "Dati numarul b" << endl;
        cin >>b;
        cout << "Sa se afiseze numerele pare din intervalul a-b" << endl;
        if (a&1)a++;
        for (int i=a; i<=b; i+=2)
            cout<<i<<endl;
        cout << "" << endl;
    }
    {
        cout << "EX.3" << endl;
        unsigned n, s;
        cout << "Dati n" << endl;
        cin >>n;
        cout << "sa se calculeze suma numerelor pana la n dupa formula s=1*3+2*5...n(2n+1)" << endl;
        s=0;

        for (int i = 1; i <= n; i++)
            s+=i*(2*i+1);
        cout << "Suma finala este "<<s << endl;
        cout << "" << endl;
    }
    {
        cout << "EX.4"<< endl;
        unsigned n;
        int s = 0;
        cout << "Dati n" << endl;
        cin >>n;
        cout << "Sa se calculeze valoarea expresiei E=1*2-2*3+3*4+...+(-1)^n*n(n+1)" << endl;
        for (int i = 1, j=1; i <= n; i++, j = -j)
            s+= j*i*(i+1);
        cout << "Valoarea finala a expresiei este "<<s << endl<<endl;
    }
    {
        cout << "EX.5" << endl;
        unsigned s=0, a, b;
        cout << "Dati a" << endl;
        cin >>a;
        cout << "Dati b" << endl;
        cin >>b;
        cout << "Sa se scrie numerele divizibile prin 5 de pe intervalul a-b in ordine descrescatoare" << endl;
        int b1 =  b - b % 5;
        for (int i=b1; i>=a; i-=5)
             cout << i << endl;
        cout << "Sa se afiseze ultima cifra a fiecarui numar din intervalul a-b" << endl;
        for (int i=a; i<=b; i++)
            cout <<i%10<<" ";
        cout << "" << endl;
    }
    cout << "" << endl;
    return 0;
}
