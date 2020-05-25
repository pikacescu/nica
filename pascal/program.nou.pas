program p15;
var
   pisica, caine: integer;
      sarpe: string;
begin
   pisica:=1;
   caine:=0;
   sarpe:='numarul insusi';
   sarpe[1]:='l';

   inc(caine);
   dec(pisica);
   writeln (pisica,'*',sarpe,'=',sarpe);
   writeln (caine,'+',sarpe,'=',sarpe);
   pisica := 0;
   {if (pisica > 5) then writeln ('pisica mai mare ca cinci');}
   if (pisica > 5) then
      writeln ('pisica mare')
   else if (pisica < 100) then
      writeln ('pisica medie')
   else
      writeln ('pisica mare');






end.