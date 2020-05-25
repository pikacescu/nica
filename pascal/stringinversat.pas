function inv (x : string) : string;
var
    lungimea : integer;
    sch : char;
    i : integer;
begin
    lungimea := integer(x[0]);
    for i := 1 to trunc(lungimea/2)  do
    begin
       sch := x[lungimea-i+1];
       x[lungimea-i+1] := x[i];
       x[i] := sch;

    end;
    inv := x ;


end;

function sumanumerelor( x : integer) : integer;
var  i: integer;
begin
  sumanumerelor := 0;
  for i := 1 to x do sumanumerelor := sumanumerelor + i;
end;

var x : string;
    y : string;
    z : string;
begin
    x := 'trebuie sa facem temele';
    y := 'nu stiu ce';
    z := 'nimeni';
    writeln ('suma numerelor pan a la 145 este ', sumanumerelor (145));
    writeln (inv(x));
    writeln (x);
    writeln (inv(x), x);
    writeln (inv(x), inv(y), x, y);
    writeln (inv(z), inv(inv(z)));

end.
