program p4;

var
  x, y, z: integer;
  mesaj, mesaj1 : string;
begin
  x:=277;
  y:=333;
  mesaj := 'salut la toti';
  mesaj1 := ' norok la toti';
  mesaj := mesaj1;


  writeln (x,'+',y);
  writeln (x+y);
  writeln (x,'+',y,'=',x+y);
  writeln (mesaj, ' si iar salut');



end.

