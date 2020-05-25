var
   trambulina: string;
   i: integer;
   lungimea, l: integer;
   sch: char;
   search : char;
begin
   trambulina := 'dan sare la trambulina...';
   lungimea := integer(trambulina[0]);
   l := lungimea;
   if lungimea mod 2 > 0 then dec (l);

   for i :=  1 to l do
   begin
      if i mod 2 > 0     then
      begin
         sch := trambulina[i];
         trambulina[i] := trambulina[i+1];
         trambulina[i+1] := sch;
      end;
   end;
   writeln (trambulina);

   for i := 1 to l do
   begin
      if i mod 2 = 0 then continue;
      sch := trambulina[i];
      trambulina[i] := trambulina[i+1];
      trambulina[i+1] := sch;
   end;
   writeln (trambulina);

   search := 'n';
   for i := 1 to lungimea + 1 do
   begin
      if trambulina[i] = search then break;
   end;
   writeln ('index: ', i, '; lungime ', lungimea);
   if i > lungimea then writeln ('litera ',  search, ' nu se contine in string')
   else   writeln ('litera ', search, ' se gaseste in pozitia ', i);
end.
