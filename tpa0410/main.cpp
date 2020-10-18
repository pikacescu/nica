#include <iostream>

using namespace std;

int main()
{
    {
        unsigned a, b, c;
        a=10; b=47; c=23;
        //examinam primul caz
        a=b*a;
        cout << "a="<<a << endl;
        b=c*a;
        cout << "b="<<b << endl;
        c=a+b+c;
        cout << "c="<<c << endl;
        cout << "" << endl;

    }
    {
       unsigned a, b, c;
        a=10; b=47; c=23;
        //examinam al doilea caz
        b=c*a;
        cout << "b="<<b << endl;
        a=b*a;
        cout << "a="<<a << endl;
        c=a+b+c;
        cout << "c="<<c << endl;
        cout << "" << endl;
    }
    {
        unsigned a, b, c;
        a=10; b=47; c=23;
        //examinam al treilea caz
        c=a+b+c;
        cout << "c="<<c << endl;
        b=c*a;
        cout << "b="<<b << endl;
        a=b*a;
        cout << "a="<<a << endl;
        cout << "" << endl;
    }
    {
        unsigned a, b, c;
        a=10; b=47; c=23;
        b=a*c;
        cout << "b="<<b << endl;
        c=a+b+c;
        cout << "c="<<c << endl;
        a=b*a;
        cout << "a="<<a << endl;
        cout << "" << endl;
    }
    {
        unsigned n;
        cout << "introduceti un nr de 3 cifre n" << endl;
        cin >> n;
        n= n%100;
        cout << "dupa schimbare nr n este "<<n << endl;
        cout << "" << endl;
    }
    {
        unsigned p, x;
         cout << "dati un numar de pagini" << endl;
         cin >>x;
         p=(x/4*3)+(x/4/5);
        cout << "numarul de pagini citite este "<<p << endl;
        cout << "" << endl;
    }
    {
        unsigned n, m, x, y, M;
        n=18;
        m=30;
        x=14;
        y=37;
        M=m+(n-x)*60-y    //prin n-x aflam diferenta de ore, iar prin m si y notam minutele
                          //inmultind diferenta de ore cu 60, aflam diferenta de ore transformata in minute
                          //la diferenta de ore se aduna 30 de minute ale orei din care se scade (18.30), iar din aceasta suma se scad cele 37 min ale orei 14.37

        cout << "M="<<M << endl;
    }
    cout<< "" << endl;
    return 0;
}
