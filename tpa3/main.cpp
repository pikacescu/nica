#include <iostream>

using namespace std;

int main()
{
    {
    unsigned x;
    cout << "Introduceti un numar natural" << endl;
    cin >>x;
    if (x%2=0)
    cout << "numarul introdus este par" << endl;
    else
    cout << "numarul introdus nu este par" << endl;
    cout << "" << endl;
      {
     unsigned a,b,x;
     cout << "Dati numarul a" << endl;
     cin >>a;
     cout << "Dati numarul b" << endl;
     cin >>b;
     cout << "Dati numarul x" << endl;
     cin >>x;
     cout << "Sa se determine daca x apartine intervalului a-b (inclusiv)" << endl;
     if (x>=a && x<=b)
        cout<<"x apartine intervalului a-b"<<endl;
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
     else if (x>=a && x<=b)
        cout<<"x nu apartine intervalului a-b"<<endl;
     cout << "" << endl;
    }
    {
        int x;
      cout<<"introduceti un numar intreg"<<endl;
      cin >>x;
      if (x>=0)
        cout<<"x este pozitiv"<<endl;
    }
    {
        int a,b;
      cout << "dati un nr intreg a" << endl;
      cin >>a;
      cout << "dati un nr intreg b" << endl;
      cin >>b;
      if ((a>0&&b>0)||(a<0&&n<0))
      cout << "numerele a si b au acelasi semn" << endl;
      cout << "" << endl;
    }
    {
        int x;
      cout << "Dati un numar intreg" << endl;
      cin >>x;


      cout << "" << endl;
      cout << "" << endl;
      cout << "" << endl;
    }
    return 0;
}
