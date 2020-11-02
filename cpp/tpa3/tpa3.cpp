#include <iostream>
#include <cmath>
using namespace std;

int main()
{
    {
    unsigned x,y;
    cout << "Introduceti un numar natural" << endl;
    cin >>x;
    y=x%2;
    if(y==0)
        cout << "numarul introdus este par" << endl;
    else
        cout << "numarul introdus nu este par" << endl;
    cout << "" << endl;
    }
      {
     unsigned a,b,x;
     cout << "Dati numarul a" << endl;
     cin >>a;
     cout << "Dati numarul b" << endl;
     cin >>b;
     cout << "Dati numarul x" << endl;
     cin >>x;
     cout << "Sa se determine daca x apartine intervalului a-b (inclusiv)" << endl;
     if ((x>=a && x<=b)||(x<=a && x>=b))
        cout<<"x apartine intervalului a-b"<<endl;
     cout << "" << endl;cout << "" << endl;
    }
    {
    unsigned a,b,x;
    cout << "Dati numarul a" << endl;
    cin >>a;
    cout << "Dati numarul b" << endl;
    cin >>b;
    cout << "Dati numarul x" << endl;
    cin >>x;
    cout << "Sa se determine daca x NU apartine intervalului a-b (inclusiv)" << endl;
    if (!(x>=a && x<=b))
        cout<<"x nu apartine intervalului a-b"<<endl;
    cout << "" << endl;
    }
    {
      int x;
      cout<<"introduceti un numar intreg"<<endl;
      cin >>x;
      if (x>=0)
        cout<<"x este pozitiv"<<endl;
        cout << "" << endl;cout << "" << endl;
    }
    {
      int a,b;
      cout << "dati un nr intreg a" << endl;
      cin >>a;
      cout << "dati un nr intreg b" << endl;
      cin >>b;
      if ((a>0&&b>0)||(a < 0 && b < 0))
      cout << "numerele a si b au acelasi semn" << endl;
      cout << "" << endl;cout << "" << endl;
    }
    {
      int x;
      cout << "Dati un numar intreg" << endl;
      cin >>x;
      if ((x<1000 && x>99) || (x>-1000 && x<-99) )
      cout << "numarul introdus are 3 cifre" << endl;
      else
      cout << "numarul introdus nu are 3 cifre" << endl;
      cout << "" << endl;cout << "" << endl;

    }
    {
      unsigned x;
      cout<<"introduceti nota elevului"<<endl;
      cin >>x;
      if (x<5)
      cout <<"Elevul este corigent"<< endl;
      else
      cout <<"Elevul nu este corigent"<< endl;
      cout << "" << endl;cout << "" << endl;
    }
    {
      unsigned x;
      cout << "Introduceti un an calendaristic" << endl;
      cin >>x;
      if((x%4==0 && x%100 != 0)||(x%400==0))
        cout << "anul este bisect" << endl;
      else
        cout << "anul nu este bisect" << endl;
        cout << "" << endl;cout << "" << endl;
    }
    {
        unsigned x,y;
        cout << "introduceti un numar" << endl;
        cin>>x;
        y = sqrt(x);
        cout << "numarul ";
        if (y * y != x)
            cout << "nu ";
        cout << "este patrat perfect" << endl;
        cout << "" << endl;cout << "" << endl;

    }
    {
        unsigned x;
           cout << "introduceti un numar de trei cifre" << endl;
           cin >>x;
           cout << "numarul dat are "<< x%2+(x/10)%2+(x/100)%2 <<" cifre impare" << endl;
           cout << "" << endl;cout << "" << endl;
    }
    {
        unsigned x;
           cout << "introduceti un numar de trei cifre" << endl;
           cin >>x;
           cout << "" << endl;
           if (x/100==x%10==x/10%10)
            cout << "cifrele componente ale numarului sunt identice" << endl;
           else
             cout << "cifrele componente ale numarului nu sunt identice" << endl;
             cout << "" << endl;cout << "" << endl;
    }
    {
        unsigned x;
           cout << "introduceti un numar de 4 cifre" << endl;
           cin >>x;
           cout << "numarul dat are "<< x%2+(x/10)%2+(x/100)%2+(x/1000)%2 << " cifre impare" << endl;
           cout << "" << endl;cout << "" << endl;
    }
    {
        unsigned x;
           cout << "dati un numar de 4 cifre" << endl;
           cin >>x;
           cout << "suma miilor si zecilor este "<<x/1000+x%100/10 << endl;
           cout << "" << endl;cout << "" << endl;
    }
    {
        unsigned x;
        cout << "dati un numar de 5 cifre" << endl;
        cin >>x;
        if (x/1000+x/1000%10==x%10+x%100/10)
            cout << "suma primelor cifre este egala cu suma ultimelor doua" << endl;
        else
            cout<<"suma primelor cifre nu este egala cu suma ultimelor doua"<<endl;
    }
    return 0;
}
