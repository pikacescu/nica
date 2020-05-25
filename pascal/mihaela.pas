var
   mama, tata: string;
   copii, dan, nica: integer;
   str: string;
   lungimea: integer;
   i: integer;
begin

   mama := 'bajicureste toata ziua pe toti';
   tata := 'vaneaza cactusi indiferent de circumstanta';

   str := tata;

   lungimea := integer(str[0]);
   for i := 1 to lungimea do write (str[i]) ;



end.