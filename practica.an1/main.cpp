#include <iostream>

#include "meniu.h"

#include "carte.h"
#include "subscriber.h"
#include "comenzi.h"
#include "data.h"

using namespace std;

void start(book_vector& bks, subs_vector& sbs, comanda_vector& cmds, data& today)
{
    today = azi();

    bks.incarca();
    sbs.incarca();
    cmds.incarca();

    bks.today = today;
    sbs.today = today;
    cmds.today = today;
    cmds.pbks = &bks;
    cmds.psbs = &sbs;


}
void finish(book_vector& bks, subs_vector& sbs, comanda_vector& cmds)
{
    bks.salveaza();
    sbs.salveaza();
    cmds.salveaza();

    bks.descarca();
    sbs.descarca();
    cmds.descarca();
}

using namespace std;
int main()
{
    book_vector bks;
    subs_vector sbs;
    comanda_vector cmds;
    data today;


    start(bks, sbs, cmds, today);

    meniu(bks, sbs, cmds, today);

    finish(bks, sbs, cmds);



    return 0;
}
