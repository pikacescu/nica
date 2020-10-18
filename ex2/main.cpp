#include <iostream>

using namespace std;

int main()
{
    int a,b,x;
    cin >>a,b,x;
    if (x>=a && x<=b)
        cout <<"x apartine intervalului"<<endl;
    else
        cout <<"x nu apartine intervalului"<<endl;
    cout << "" << endl;
    return 0;
}
