#include <iostream>

using namespace std;

int main()
{
    {
        cout << "Ex.1" << endl;
        unsigned n, i, s;
         cout << "Dati un numar pana la care sa se calculeze suma"<< endl;
         cin >>n;
         s=0;
         for (i=1; i<=n; i++)
            s+=i;
          cout << "suma numerelor pana la numarul introdus este "<<s << endl;
    }
    {
         cout << "Ex.2" << endl;
         unsigned s, a, b, i;
          cout << "dati numarul a" << endl;
          cin >>a;
           cout << "dati numarul b" << endl;
           cin >>b;
           s=0;
           for (i=a; i<=b; i++)
           {
               if (i%2==0)
                    s+=i;
           }
            cout << "suma numerelor pare este "<<s << endl;
    }
    {
         cout << "Ex.3" << endl;
         unsigned a, n;
          cout << "Dati un numar n" << endl;
          cin >>n;
           cout << "dati un numar a" << endl;
           cin >>a;
         int t = 1;
         for (int i = 1; i <= n; i++)
              t *= a;
              cout <<t<<endl;
    }
    {
         cout << "Ex.4" << endl;
         unsigned n, s, i;
          cout << "Dati un numar n" << endl;
          cin >>n;
          s=0;
          for (i=1; i<=n; i++)
            s+=i*(2*i+1);
          cout <<"suma finala este "<<s<<endl;
    }
    {
         cout << "EX.4" << endl;
         unsigned i, n, p; //p este produsul factorial
         p=1;
          cout << "Dati nr n" << endl;
          cin >>n;
          for (i=1; i <= n; i++)
            p*=i;
           cout << "produsul factorial al numarului n este "<<p << endl;
    }
    {
         cout << "Ex.5" << endl;
         unsigned e, n;
         e=0;
          cout << "Dati un numar n" << endl;
          cin >>n;
          for (int i = 1; i<=n; i++)
            e+=i*(i+1);
          cout <<"e a devenit "<<e<<endl;
          e=0;
          for (int i=1; i <= n; i++)
            e+=i*i;
          cout<<"e a devenit"<<e<<endl;
          e=0;
          for (int i=1; i<=n; i++)
            e+=1/i;
           cout << "e a devenit"<<e << endl;
          e=0;
          for (int i=1; i<=n; i++)
          {
              int t = 1;
              for (int j = 1; j <= i ; j++)
                t *= j;
              e += t;
          }
          cout << "e a devenit"<<e << endl;
    }
    {
        cout << "Ex.7" << endl;
        unsigned a, b;
        cout << "Dati a" << endl;
        cin >>a;
        cout << "Dati b" << endl;
        cin >>b;

        for (int i = a, j = b; i <= j; i++)
        {
            cout<<"("<<a++<<","<<b--<<")"<<", ";
        }
    }
    cout << "" << endl;
    return 0;
}
