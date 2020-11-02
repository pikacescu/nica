#include <iostream>

using namespace std;

int fx(int x)
{
    x += 1;
    return 2 * x;
}

int main()
{
    int x = 4444, y = 5555;
    int *px = reinterpret_cast<int*>(123);
    px = reinterpret_cast<int*>(1234);
    px = &x;
    cout << reinterpret_cast<intptr_t >(px)  << endl;
    cout << *px<< endl;
    *px = 1234;
    cout << reinterpret_cast<intptr_t >(px)  << endl;
    cout << x << endl;
    cout << &x << endl;
    int& rx = x;
    cout<< rx<< endl;
    rx = 2345;
    px = &y;
    rx = y;
    //rx = 7777;
    cout<< x<< endl;
    cout<< y<< endl;
    fx(x);


    return 0;
}
