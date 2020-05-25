var
   nimic : string;
   i : integer;
   lungimea : integer;
   tmp : char;
begin
   nimic := 'nimic nu s-a intamplat bla bla bla';
   lungimea := integer(nimic[0]);
   for i := 1 to trunc (lungimea/2) do
   begin
      tmp := nimic[i];
      nimic[i] := nimic[lungimea-i+1];
      nimic[lungimea-i+1 ] := tmp;

   end;
   writeln (nimic);

end.