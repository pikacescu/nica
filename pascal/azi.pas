var
   duminica: string;
   lungimea: integer;
   i: byte;
   schimb: char;
begin
   duminica := 'ultima zi';
   lungimea := integer(duminica[0]);

   for i := 1 to trunc(lungimea/2) do
   begin
      schimb := duminica[i];
      duminica[i] := duminica[lungimea-i+1];
      duminica[lungimea-i+1] := schimb;

   end;
   writeln (duminica);

end.