
function ismaj (x : char) : boolean;
begin
   {ismaj := false;
   if (ord(x) >= ord('A')) and (ord(x) <= ord('Z')) then ismaj := true;}
   ismaj := (ord(x) >= ord('A')) and (ord(x) <= ord('Z')) ;

end;

function tomin (x : char) : char;
begin
   tomin := x;
   if ismaj(x) then tomin := chr(ord(x) - ord('A') + ord('a'));
end;

function tominuscula (s : string) : string;
var
   i : integer;
begin
   for i := 1 to length(s) do s[i] := tomin(s[i]);
   tominuscula := s;
end;
var
   x : string;
begin
   x := 'gygytftrfDRDRDES56565745,,,l,l';
   writeln (tominuscula(x));
end.
   x : string
