#include <iostream>
#include <cmath>
#include <ctime>
using namespace std;

void initrand()
{
    time_t tm;
    localtime(&tm);
    srand (time(&tm));
}

int main()
{
    initrand();
//#if 0
    cout << "Programarea structurata" << endl;
    cout << "Filipski Nicoleta" << endl << endl;

	{
		unsigned n;
		cout << "Introduceti un numar natural n " << endl;
		cin  >>n;
		cout << "Catul impartirii nr "<< n<<" la 7 este "  <<n/7 << endl;
		cout << "Restul impartirii nr "<< n<<" la 7 este " <<n%7 << endl << endl;
	}
    {

        unsigned m, r, y, z;
        cout << "Introduceti un numar natral de 3 cifre " << endl;
        cin  >>m;
        r =  m/100;
        y =  m%10;
        z =  m/10%10;
        cout << "Suma cifrelor componente ale numarului "<< m<<" este " <<r+y+z << endl << endl;
    }
    {
        unsigned x, a, b, c;

        cout << "Introduceti 4 numere naturale "<<endl;
        cin  >>x;
        cin  >>a;
        cin  >>b;
        cin  >>c;
        cout << a<< "*"<< x<< "^2+"<< b << "*"<<x<< "+"<<c<<"="<<a*(x*x)+b*x+c   << endl;
    }

    {
        const float s = -0.78, d  = 0.23, e = -2;
        cout << "arcsin("<<s<<") = "<<asin(s)  << endl;
        cout << "arccos("<<d<<") = "<<acos(d)  << endl;
        cout << "arctg ("<<e<<") = "<<atan(e)  << endl;
    }
    {
        unsigned x, y, z;
        float w, r;
        cout << "nr x" << endl;
        cin  >>x;
        cout << "nr y" << endl;
        cin  >>y;
        cout << "nr z" << endl;
        cin  >>z;
        w = float((x+y+z)/3);
        r = pow(x*y*z, 1/3);
        cout << "media aritmetica a numerelor x, y si z este "<<w << endl;
        cout << "media geometrica a numerelor x, y si z este "<<r << endl;
        cout <<endl;
    }
    {

        unsigned x, y, z;
        cout <<"Dati latura patratului in metri"<< endl;
        cin  >>x;
        y = x * 100;
        cout <<"perimetrul patratului in centimetri este de "<< y*4<< endl;
        cout <<"aria patratului in centimetri este de "<< y*y<< endl;
        cout <<endl;
    }
    {
        unsigned x;
        cout <<"Dati latura cubului"<< endl;
        cin  >>x;
        cout <<"Aria cubului: "<<6*x*x <<endl;
        cout <<"Volumul cubului: "<<x*x*x <<endl;
        cout <<endl;
    }
    {
         unsigned r, pi;
         pi = 3.14;
         cout <<"Dati raza cercului, considerand pi = 3.14"<< endl;
         cin  >>r;
         cout <<"Lungimea cercului este "<< 2*pi*r<< endl;
         cout <<"Aria cercului "<< pi*r*r<<endl;
    }
//#endif
    {

        unsigned int n, d;
        n = rand() % 10000;
        d = n - (rand() %  n);
        cout <<"Restul impartirii "<<n<<" la "<< d<< ": "<< n % d<< endl;
        cout <<"Catul impartirii "<<n<<" la "<< d<< ": "<< n / d<< endl;
        cout <<""<< endl;
    }
    const double PI = 3.1415926;
    {
        unsigned int n = rand() % 15;
        double r = float(rand() % 100);
        cout <<"Poligon "<<n <<" cu raza cercului circumscris "<<r<< endl;
        cout <<"Perimetrul poligonului " << n * 2 * (r* sin(PI / n))  << endl;
        cout <<"Perimetrul poligonului " << n * (r*sin(PI / n)) * (r*cos(PI / n)) << endl;
        double r_inscris = r * cos(PI / n);
        cout <<"Raza cercului inscris in poligon: "<< r_inscris<< endl;
        cout <<"Numar de diagonale: "<< n * (n - 3) / 2<< endl;

        cout <<""<< endl;
    }
    return 0;
}
