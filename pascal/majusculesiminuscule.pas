


function minuscul (x : char) : boolean;
begin
   minuscul := (ord(x) >= ord('a')) and (ord(x) <= ord('z'));
end;

function tomaj (x : char) : char;
begin
   tomaj := x;
   if  minuscul(x) then tomaj := chr(ord(x) + ord('A') - ord ('a'));

end;

function trmaj (x : string) : string;
var
   i : integer;
begin
   for i := 1 to length(x) do
   begin
      x[i] := tomaj(x[i]);

   end;
   trmaj := x;
end;
var
   x, y, z : string;
begin
   x := ' SSSSdihfuhf..222,876..,,,';
   y := 'hfhfhfHHHYYY44867>>>>><,,.,';
   z := '';

   writeln (trmaj(x));
   writeln (trmaj(y));
   writeln (trmaj(z));
end.