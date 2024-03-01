#include <ctime>
#include <iomanip>
#include "data.h"
std::ostream& operator<< (std::ostream& os, data& dt)
{
    if (dt.is_null()) {os<< "----/--/--"; return os;}
    std::ios_base::fmtflags f( os.flags() );
    os  << std::setfill('0')
        << std::setw(4) << dt.an   << '/'
        << std::setw(2) << 1 + dt.luna << '/'
        << std::setw(2) << 1 + dt.zi;
    os.flags( f );
    return os;
}

data azi()
{
    //googluit pentru asta
    //link la tutorial
    //https://www.tutorialspoint.com/cplusplus/cpp_date_time.htm
    time_t now = time(0);
    tm *ltm = localtime(&now);

    //conversia de tip e pentru a corecta warningurile
    return {(unsigned int)ltm->tm_year + 1900, 1 + (unsigned int)ltm->tm_mon, (unsigned int)ltm->tm_mday};
}


unsigned int __luni__a__[] = {31, 28, 31,  30,    31,  30,  31,  31,     30,  31,  30,  31};
unsigned int __zile__a__[] = {31, 59, 90, 120,   151, 181, 212, 243,    273, 304, 334, 365};
unsigned int __luni__b__[] = {31, 29, 31,  30,       31, 30, 31, 31,        30, 31, 30, 31};
unsigned int __zile__b__[] = {31, 60, 91, 121,   152, 182, 213, 244,    274, 305, 335, 366};


bool bisect(int an)
{
    if (!(an % 400)) return true;
    if (!(an % 100)) return false;
    if (!(an % 4)) return true;
    return false;
}
unsigned int* getLuni(int an)
{
    unsigned int* __luni__ = __luni__a__;
    if (bisect(an))
        __luni__ = __luni__b__;
    return __luni__;
}
unsigned int getZile(int an, int luna)
{
    unsigned int* __luni__ = getLuni(an);
    return __luni__[luna];
}

void data::add_zile(int zz)
{
    if (zz < 0){dec_zile(zz); return;}
    while(zz > 0)
    {
        unsigned int lz = getZile(an, luna);
        zi += zz % lz;
        if (zi >= lz) add_luni(1);
        zi %= lz;
        zz -= lz;
    }
}

void data::dec_zile(int zz)
{
    if (zz < 0) {add_zile(zz); return;}
    while(zz > 0)
    {
        unsigned int lz = getZile(an, luna);
        zi += lz;
        zi -= zz % lz;
        if (zi <= lz) dec_luni(1);
        zi %= lz; //normalizam
        zz -= lz;
    }
}
void data::add_luni(int ln)
{
    if (ln < 0) {dec_luni(-ln); return;}
    luna += ln % 12;
    if (luna >= 12) an++;
    luna %= 12;
    an += ln / 12;
}
void data::dec_luni(int ln)
{
    if (ln < 0) {add_luni(-ln); return;}
    luna += 12;
    luna -= ln % 12;
    if (luna < 12) an--;
    luna %= 12;
    an -= ln / 12;
}

//nici o comparatie cu null nu returneaza true, by convention in orice limbaje de programare
bool data::gt (data& dt)
{
    if (is_null() || dt.is_null()) return false;
    return to_simple_int() >  dt.to_simple_int();
}
bool data::ge (data& dt)
{
    if (is_null() || dt.is_null()) return false;
    return to_simple_int() >= dt.to_simple_int();
}
bool data::lt (data& dt)
{
    if (is_null() || dt.is_null()) return false;
    return to_simple_int() <  dt.to_simple_int();
}
bool data::le (data& dt)
{
    if (is_null() || dt.is_null()) return false;
    return to_simple_int() <= dt.to_simple_int();
}
bool data::in_termen(int term, data& dt)
{
    data d1;
    d1.from_simple_int(to_simple_int());
    d1.add_zile(term);
    return d1.le(dt);
}
bool data::is_null()
{
    if (an == 0) return true;
    return false;
}

bool data::introdu()
{
    using namespace std;
    cin>> an;
    if (!is_null())
    {
        cin.ignore(1);
        cin >> luna;
        if (luna < 1 || luna > 12) return false;
        luna--;
        cin.ignore(1);
        cin >> zi;
        if (zi < 1 || zi > getZile(an, luna)) return false;
        zi--;
    }
    return true;
}
bool data::from_simple_int(int n)
{
    if (n == 0)
    {
        luna = 0;
        zi = 0;
        return true;
    }
    zi = n % 100; n /= 100;
    luna = n % 100; n /= 100;
    an = n;

    if (n != 0)
    {
        if (luna < 1 || luna > 12) return false;
        luna--;
        if (zi < 1 || zi > getZile(an, luna)) return false;
        zi--;
    }
    return true;

}
int data::to_simple_int()
{
    if (is_null()) return 0;
    return (an) * 10000 + (luna + 1) * 100 + zi + 1;
}
