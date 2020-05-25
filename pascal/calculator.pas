var
   calculator,  alta: string;
   lungimea, i: integer;
   schimb: char;
begin

   calculator := 'programe si aplicatii';
   schimb := calculator[3];
   calculator[3] := calculator[4];
   calculator[4] := SCHIMB;


   alta := calculator;

   lungimea := integer(alta[0]);
   for i := 1 to lungimea do write (alta[i]);
   writeln ();
   for i := 1 to lungimea do
   begin
      write (alta[i]);
   end;
   writeln ();
   for i := lungimea downto 1  do
   begin
       write (alta[i]);
   end;
end.