function nimic (x : string) : string;
var
   trn : char;
   i : integer;
   lungimea : integer ;

begin
   lungimea := integer (x[0]);

   for i := 1 to trunc (lungimea/2) do
   begin
      trn := x[i];
      x[i] := x[lungimea-i+1];
      x[lungimea-i+1] := trn;
   end;
   nimic := x;

end;








var
   x : string;
   y : string;


begin
   x := 'rakan si xayah';
   y := 'orn si nimeni';

   writeln (nimic(x));
end.
