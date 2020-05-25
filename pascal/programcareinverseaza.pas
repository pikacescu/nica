function nimic (x : string) : string;
var
   sch : char;
   i : integer;
   lungimea : integer;
begin
   lungimea := integer (x[0]);

   for i := 1 to trunc(lungimea/2) do
   begin
      sch := x[i];
      x[i] := x[lungimea-i+1];
      x[lungimea-i+1] := sch;
   end;

   nimic := x;

end;
function xxx (x : string): string;
var i: integer;
begin
   xxx := '';
   for i := integer(x[0]) downto 1 do xxx := xxx + x[i];
end;

var
   x, y : string;
begin
   x := 'un oarecare stringg';
   y := 'o oarecare lectie';
   writeln (nimic(x));
   writeln (nimic(y));
   writeln (xxx ('salut la toti'));
end.