var
   cal, vaca: string;
   alta: integer;
   lungimea, i : integer;
   schimb : char;
begin
   cal := 'profesorul meu de edcatie fizica';

   vaca := 'profesoara mea de istorie';
   lungimea := integer(cal[0]);

   schimb := cal[1];
   cal[1] := cal[lungimea];
   cal[lungimea] := schimb;
   schimb := cal[2];
   cal[2] := cal[lungimea-1];
   cal[lungimea-1] := schimb;

   for i := 1  to lungimea do
   begin
      write (cal[i]);
   end;

   writeln ();

   for i := lungimea downto 1 do
   begin
      write (cal[i]);
   end;
end.