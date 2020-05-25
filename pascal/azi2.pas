var
   zi: string;
   lungimea: integer;
   i, j: integer;
   schimb: char;
begin
   zi := 'orice zi';
   lungimea := integer(zi[0]);
   for i := 1 to trunc(lungimea/2) do
   begin
      j := i * 2;
      schimb := zi[j];
      zi[j] := zi[j-1];
      zi[j-1] := schimb;
   end;
   writeln (zi);
end.