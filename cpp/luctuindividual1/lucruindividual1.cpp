        #include <iostream>
        #include <math.h>

        using namespace std;

        int main()
        {
            unsigned S, b;
            float a, Si, Sc,c, h, Bt, B;
            cout << "Se da aria unui triunghi dreptunghic" << endl;
            cin >>S;
            cout << "Se da o cateta a triunghiului" << endl;
            cin >>b;
            cout  << endl << "Sa se afle cealalta cateta dupa formula S=(a*b)/2 :" << endl;
            cout << "S=(a*b)/2 => a=(S*2)/b" << endl;
            a=((float)S*2)/b; //"a" este a doua cateta
            cout << "A doua cateta are lungimea de "<<a<<" unitati"<< endl;
            cout << "" << endl;
            cout << "Sa se afle ipotenuza triunghiului dupa formula c^2=a^2+b^2" << endl;
            cout << "c^2=a^2+b^2 => c=sqrt(a^2+b^2)" << endl;
            c=sqrt(a*a+b*b); //"c" este ipotenuza triunghiului
            cout << "Ipotenuza triunghiului are lungimea de "<<c<<" unitati" << endl <<endl;
            cout << "Sa se afle inaltimea triunghiului dupa formula S=(c*h)/2" << endl;
            cout << "S=(c*h)/2 => h=(S*2)/c" << endl;
            h=(S*2)/c; //"h" este inaltimea triunghiului
            cout << "Inaltimea triunghiului are lungimea de "<<h<<" unitati" << endl << endl;
            cout << "Sa se afle masura unghiului ascutit in radiani dupa formula Bt=arctg(b/a)" << endl;
            Bt = atan2 (b, a);
            cout << "masura unghiului ascutit al triunghiului constituie "<<atan2 (b, a)<<" radiani"<< endl;
            cout << "Sa se afle masura unghiului ascutit in grade dupa formula B=(180*Bt)/pi"<< endl;
            B=(180*Bt)/ M_PI;
            cout << "masura unghiului ascutit al triunghiului constituie "<<B<<" grade" << endl << endl;
            cout << "Sa se afle aria circumferintei circumscrise dupa formula razei: R=c/2" << endl;
            cout << "R=c/2; Si=pi*R*R => Si=pi*(c/2)*(c/2)" << endl;
            Si=M_PI*(c/2)*(c/2);
            cout << "Aria circumferintei circumscrise constituie "<<Si<<" unitati" << endl << endl;
            cout << "Sa se afle aria circumferintei inscrise dupa formula S=p*r" << endl;
            cout << "S=p*r, p=1/2*P, P=a+b+c => S=r*(a+b+c)/2 => r=S/((a+b+c)/2)" << endl;
            cout << "r=S/((a+b+c)/2) => Sc=pi*(S/((a+b+c)/2)*S/((a+b+c)/2)" << endl;
            Sc=M_PI*(S/((a+b+c)/2)*S/((a+b+c)/2));
            cout << "Aria circumferintei inscrise constituie "<<Sc<<" unitati" << endl;

            return 0;
        }
