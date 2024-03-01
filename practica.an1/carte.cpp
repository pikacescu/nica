#include <fstream>
#include <string>
#include <iostream>
#include <limits>

#include "carte.h"


std::ostream& operator << (std::ostream& os, book& b)
{
    os  << b.code       << ' '  << b.year        << ' '
        << b.autor.name << ' '  << b.autor.fname << " \""
        << b.title      << "\" "<< b.price;
    return os;
}
std::ostream& operator << (std::ostream& os, book_vector& bks)
{
    book* begin = bks.vt;
    for (int i = 0; i < bks.n; i++)
    {
        os<< begin[i];
        if (i < bks.n - 1) os<< std::endl;
    }
    return os;
}
using namespace std;

void book_vector::sortare_an_denumire()
{
    book* begin = vt, *end = vt + n;
	for (int i = 0, ds = end - begin - 1, unsorted = 1; unsorted; i++)
	{
		unsorted = 0;
		for (int j = 0; j < ds; j++)
        {
			if (begin[j + 1].year < begin[j].year ||
               (begin[j + 1].year == begin[j].year && begin[j + 1].title < begin[j].title ))
            {
                swap(begin[j], begin[j + 1]);
                unsorted = j;
            }

        }
		ds = unsorted;
	}
}
void book_vector::sortare_an_descrescator()
{
    book* begin = vt, *end = vt + n;
	for (int i = 0, ds = end - begin - 1, unsorted = 1; unsorted; i++)
	{
		unsorted = 0;
		for (int j = 0; j < ds; j++)
			if (begin[j + 1].year > begin[j].year)
            {
                swap(begin[j], begin[j + 1]);
                unsorted = j;
            }
		ds = unsorted;
	}
}

void book_vector::sortare_autor_descrescator()
{
    book* begin = vt, *end = vt + n;
	for (int i = 0, ds = end - begin - 1, unsorted = 1; unsorted; i++)
	{
		unsorted = 0;
		for (int j = 0; j < ds; j++)
        {
            bool swp = 0;
			if (begin[j + 1].autor.name > begin[j].autor.name)
                swp = 1;
            else if (begin[j + 1].autor.name == begin[j].autor.name && begin[j + 1].autor.fname > begin[j].autor.fname)
                swp = 1;
            if (swp)
            {
                swap(begin[j], begin[j + 1]);
                unsorted = j;
            }
        }
		ds = unsorted;
	}
}
float book_vector::pret_mediu()
{
    long double pret = 0;
    for (int i = 0; i < n; i++)
        pret += vt[i].price;
    pret /= n;
    return (float) pret;
}

void book_vector::alocare()
{
    if (n > 0 && n + 2 < alocat) return; //nu se apropie de limita
    book* tmp = vt;
    alocat = 2 * n; //dublam numarul de elemente rezervate

    if (n <= 0) n = 0;
    if (alocat < 50) alocat = 100; //rezervam 100 elemente pentru inceput

    vt = new book[alocat];
    for (int i = 0; i < n; i++)
        vt[i] = tmp[i];
    delete[] tmp;
}
void book_vector::incarca()
{
    ifstream ifs (nume_fisier);
    ifs>> n;

    alocat = 2 * n;
    if (alocat < 50) alocat = 100; //pentru inceput sa fie rezervate cel putin 100 de elemente
    vt = new book[alocat];
    for (int i = 0; i < n; i++)
    {
        ifs >> vt[i].code; ifs.ignore(1);
        if (vt[i].code > cod_max) cod_max = vt[i].code;


        ifs >> vt[i].year; ifs.ignore(1);
        getline (ifs, vt[i].autor.name, '\t');
        getline (ifs, vt[i].autor.fname, '\t');
        getline (ifs, vt[i].title, '\t');
        ifs >> vt[i].price;
    }
    if (cod_max == -1) cod_max = 1;
    else cod_max++;
}

void book_vector::adauga(book& bk)
{
    alocare();
    vt[n++] = bk;

}
bool book_vector::introdu()
{
    book bk;
    //cin >> bk.code;
    bk.code = cod_max;
    cin >> bk.year;
    if (bk.year < 1 || bk.year > (int)today.an) //conversie de tip, fix warning
    {
        cout << "** EROARE: An editare invalid! Introducere esuat."<< endl;
        return false;
    }
    cin >> bk.autor.name;
    cin >> bk.autor.fname;
    cin.ignore(numeric_limits<std::streamsize>::max(), '\n');
    getline (cin, bk.title);
    cin >> bk.price;
    if (bk.price < 0.01)
    {
        cout << "** EROARE: Pret invalid! Introducere esuat."<< endl;
        return false;
    }

    adauga (bk);
    cod_max++;
    return true;
}
void book_vector::sterge(int i)
{
    if (i >= n) return;
    n--;
    for (; i < n; i++)
        vt[i] = vt[i + 1];

}
void book_vector::salveaza()
{
    ofstream ofs (nume_fisier);
    ofs<< n<< endl;

    for (int i = 0; i < n; i++)
        ofs << vt[i].code  << '\t'<< vt[i].year  << '\t'
            << vt[i].autor.name << '\t'<< vt[i].autor.fname<< '\t'
            << vt[i].title      << '\t'<< vt[i].price      << endl;
}
void book_vector::descarca()
{
    n = 0;
    alocat = 0;
    delete [] vt;
    vt = 0;
}
