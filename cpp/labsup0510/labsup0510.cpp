#include <iostream>

using namespace std;

int main()
{
    {
        unsigned m;
        float pi=3.14;
        cout<<"Dati numarul de grade pe care vreti sa-l transformati in radiani"<<endl;
        cin >>m;
        cout<<"numarul m de grade in radiani va fi = "<<(pi/180)*m<<endl;
        cout<<""<<endl;
        cout<<""<<endl;
    }
    {
        unsigned r;
        float pi;
        pi=3.14;
        cout<<"dati un numar de radiani pe care vreti sa-l transformati in grade"<<endl;
        cin >>r;
        cout<<"numarul r de radiani in grade va fi = "<<(180/pi)*r<<endl;
        cout<<""<<endl;
    }
    {
        unsigned n;
         cout << "introduceti un numar n" << endl;
         cin >>n;
         cout << "" << endl;
         cout << "1. atunci cand n este un numar de zile:" << endl;
         cout << "ore="<<n*24 << endl;
         cout << "minute="<<n*24*60 << endl;
         cout << "secunde="<<n*24*60*60 << endl;
         cout << "" << endl;
         cout << "2. atunci cand n este un numar de saptamani" << endl;
         cout << "ore="<<n*7*24 << endl;
         cout << "minute="<<n*7*24*60 << endl;
         cout << "secunde="<<n*7*24*60*60 << endl;
         cout << "" << endl;
         cout << "luna mai are:" << endl;
         cout << "zile=31" << endl;
         cout << "ore="<<31*24 << endl;
         cout << "minute="<<31*24*60 << endl;
         cout << "secunde="<<31*24*60*60 << endl;
         cout << "" << endl;
    }
    {
        unsigned n;
         cout << "introduceti un numar n" << endl;
         cin >>n;
         cout << "1. daca n sunt metri,n in cm va fi "<<n*100 << endl;
         cout << "2. daca n sunt kg, n in mg va fi "<<n*1000*1000 << endl;
         cout << "3. daca n sunt kg, acestea contin "<<n/1000<<" tone" << endl;
         cout << "" << endl;
         cout << "4. raportat la timp, socotim ca n este un nr de ani" << endl;
         cout << "ani="<<n << endl;
         cout << "luni="<<n*12 << endl;
         cout << "saptamani="<<n*12/4 << endl;
         cout << "zile="<<n*12*365 << endl;
         cout << "" << endl;
    }
    {
        unsigned a, p, s;
         cout << "introduceti nr anilor" << endl;
         cin>>a;
         cout << "introduceti nr de procente ale dobanzii" << endl;
         cin>>p;
         cout << "introduceti suma de lei depusa" << endl;
         cin>>s;
         cout << "suma in primul an este "<<s << endl;
         cout << "suma in anul a "<< s/100*p*a+a*s<< endl;
         cout << "" << endl;
    }
    {
        float a, b, c, d;
        cout<<"dati nr a"<<endl;
        cin >>a;
        cout<<"dati nr b"<<endl;
        cin >>b;
        cout<<"dati nr c"<<endl;
        cin >>c;
        cout<<"a = "<<a<<endl;
        cout<<"b = "<<b<<endl;
        cout<<"c = "<<c<<endl;
        d=a;
        a=b;
        b=c;
        c=d;
        cout<<"a = "<<a<<endl;
        cout<<"b = "<<b<<endl;
        cout<<"c = "<<c<<endl;
    }
    cout << "" << endl;
    return 0;
}
