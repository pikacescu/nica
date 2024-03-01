#ifndef __DATA_H__
#define __DATA_H__
#include <iostream>

struct data
{
    unsigned int an = 0, luna = 0, zi = 0;

    int  to_simple_int   (); //editabil si citibil de om
    bool from_simple_int (int n); //din editabil si citibil de om
    bool introdu();

    bool gt (data& dt); // >
    void add_zile(int zz);
    void dec_zile(int zz);
    void add_luni(int ln);
    void dec_luni(int ln);
    bool ge (data& dt); // >=
    bool lt (data& dt); // <
    bool le (data& dt); // <=
    bool in_termen(int term, data& dt); //daca termenul adaugat e mai mic sau egal ca dt
    bool is_null(); //no data

};
std::ostream& operator<< (std::ostream& os, data& dt);
data azi();

#endif
