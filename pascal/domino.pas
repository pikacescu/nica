{se dau N (N<_50.000) dominouri. sa se determine o modalitate de construire a unui sir care sa contina toate
dominourile respectand regula jocului domino. Aceasta prevede ca numerele inscrise pe fetele corespunzatoare a
doua dominouri consecutive trebuie sa fie egale. Dominourile pot fi alese in ordine oarecare si rotite}
uses Sysutils;
type domino = record
 first, second: integer;
end;
var
   n : domino;
   x : integer;
   i : integer;
   txt: TextFile;

begin
   //x.first := 1; x.second := 5;
   writeln ('dati x numere');

   Assign(txt, 'd:\nica\pascal\xx.txt');
   reset (txt);
   readln(txt, x);
   writeln(x);
   Close (txt);
   readln();

   //readln (X);
   //writeln('x = ', x);
   //for i := 1 to x do
   //begin
   //  readln (n.first, n.second);
   //  writeln();
   //end;
end.
