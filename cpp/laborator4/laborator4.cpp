#include <iostream>

using namespace std;

int main()
{
    {

        cout<< "Exemplu 1"<<endl;
        char a1='a', c;
        int i=1, b; //b va obtine valoarea 97 in codul ascii
        c=a1+i;
        b=a1+i;//b va obtine valoarea 97 + 1
        cout<<"\n"<<b;
        cout<<"\n"<<c;// lui a1 i s-a atribuit valoarea 'a', iar tipul de date char + un x integer rezulta un rezultat char cu x pozitii mai avansat, in cazul dat a+1=b
        cout<< "Exemplu 2"<<endl;
        int a=6;
        b=4; bool c1;
        c1=(a==b);//rezultatul acestei afirmatii este fals
        cout<< "valoarea variabilei c= "<<c1;// pt ca rezultatul e fals se afiseaza zero
        cout<< "Exemplu 3"<<endl;
        int x=6, y=4, z=24;
        cout<<(x*y>=z)<<endl;//se va afisa adevarat (1) sau fals (0), aici e adevarat si se afiseaza 1
        cout<<((x=4)==y);// este adevarat si se va afisa 1
        cout<<""<<endl;
    }
    {
        cout << "rezultatul expresiei 2+5*3-7*4%2 este ="<<2+5*3-7*4%2 << endl;
        cout << "rezultatul expresiei (2*3+1)/6%2 este ="<<(2*3+1)/6%2  << endl;
        cout << "rezultatul expresiei 234561/100%1000 este ="<<234561/100%1000 << endl;
        cout << "rezultatul expresiei 5+3<=5+2-4*3 este ="<<(5+3<=5+2-4*3) << endl;
        cout << "rezultatul expresiei 123%10==123%100%10  && 123/100<123/10%10 este ="<<(123%10==123%100%10  && 123/100<123/10%10) << endl;
        cout << "rezultatul expresiei 1234%10+1234%10%10/100%10+1234/1000%10 este ="<<1234%10+1234%10%10/100%10+1234/1000%10 << endl;
        cout << "" << endl;
    }
    {
        int a=5,  b=7, c;
        c = (a>b) ? a : b;
        cout<<c;
        cout<<"\n alt exemplu\n";
        char x;
        x=(4==2+2 ? 'y': 'n');
        cout<<x;
        cout<<""<<endl;
    }
    {
        unsigned x, y;
        x=30;
        y=40;
        float a,b;
        a=3.5;
        b=17;
        2>5 ? 2:5;
        x>y ? x:y;
        a<b ? (a+b):(b-a);
        cout<<""<<endl;
    }
    {
        unsigned a, b;
        a=10;
        b=30;
        cout<<"a="<<a<<endl;
        cout<<"b="<<b<<endl;
        a=a+b;
        cout<<"a dupa schimbare = "<<a<<endl;
        b=a-b;
        cout<<"b dupa schimbare = "<<b<<endl;
        a=a-b;
        cout<<"valoarea finala a lui a = "<<a<<endl;
        cout<<"valoarea finala a lui b = "<<b<<endl;
        cout<<""<<endl;
    }
    {
        unsigned x, y, z;
        x=10;
        y=15;
        z=1;
        cout<<"x = "<<x<<endl;
        cout<<"y = "<<y<<endl;
        x=x*y;
        cout<<"dupa schimbare x = "<<x<<endl;
        y=x/y;
        cout<<"dupa schimbare y = "<<y<<endl;
        x=x/y;
        cout<<"valoarea finala a lui x = "<<x<<endl;
        cout<<"valoarea finala a lui y = "<<y<<endl;
        cout<<""<<endl;
        ! (x>=y) && z>y;
        cout<<""<<endl;
    }
    {
        unsigned x, a, b, c, m;
        a = 12;
        b = 34;
        x = 0;
        x=(a+b)%10;
        cout<<"valoarea lui x dupa schimbare: " <<x<<endl;
        cout<<"Dati un nr de 5 cifre"<<endl;
        cin >>c;
        while (c != 0)
        {
            m=m*10+c%10;
            c=c/10;
        }
        cout<<"oglinditul lui c este "<<m<<endl;
        {
        //pentru urmatoarea problema executam urmatorii pasi:
        // in primul rand transpunem conditiile problemei in limbaj matematic
        //fie x nr de elevi si y - numarul de banci
        //examinam primul caz: cate 2 elevi in banca: x/2+1 este numarul total de banci
        //examinam al doilea caz: cate 3 elevi: x/3+6 este numarul total de banci
        //respectiv x/2+1=x/3+6, pentru a lucra mai usor inmultim ambele parti cu 6
        //rezulta ca x/2+1=x/3+6 <=> 3x+6=2x+36
        //de aici rezolvam o ecuatie pentru a introduce in program conditiile problemei
        //3x-2x=36-6 <=> x=30
        //deoarece stim valoarea lui x, putem afla y, care este egal x/2+1 sau x/3+6

         unsigned x,y;
         x=36-6;
         y=x/2+1;
         cout<<"numarul elevilor este "<<x<<endl;
         cout<<"numarul bancilor este "<<y<<endl;
         cout<<""<<endl;
        }
    }
    unsigned a, p1, p2;
    cout <<"perimetrul p2 "<<endl;
    cin>>p2;
    float l1, l2;
    l1=p2;
    l2=p2/4;
    cout<<"latura primului patrat este "<<l1<<endl;
    cout<<"latura celui de-al doilea patrat este "<<l2<<endl;
    cout<<"aria primului patrat este "<<l1*l1<<endl;
    cout<<"aria celui de-al doilea patrat este "<<l2*l2<<endl;


return (0);
}
