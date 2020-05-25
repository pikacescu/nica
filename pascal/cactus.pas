program p19;

var
  floare: string;
  ac, culoare: integer;
  i:integer;
  schimb: integer;
  schimb2: char;
  len : integer;
begin
   floare := 'multi cactusi';
   inc(floare[1]);
   ac := 100;
   culoare := 25;
   schimb2 := floare[3];
   floare[3] := floare[7];
   floare[7] := schimb2;
   len := integer(floare[0]);
   for i := 1 to len do
   begin
      if  (floare[i] <= 'z') and (floare [i] >= 'a') then
      begin
         floare[i] := char (  integer (floare[i]) +  integer ('A') - integer ('a')   );
      end;
   end;

   writeln (floare);
   writeln ('ac+culoare=', ac, '+', culoare, '=', ac + culoare);
   if (ac > 25) then writeln ('cactusul are multe ace');

   if (culoare > ac) then writeln ('nimic')
   else
      writeln ('si multe culori');

   for i := 3 to 12 do
   begin
      write (floare[i]);
   end;
   writeln ();
   for i := 12 downto 3 do
   begin
      write (floare[i]);
   end

end.