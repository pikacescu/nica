@set progname=%~n0
@set dotnetver=v4.0.30319
@set frmdir=Framework
@set frmdir=Framework64
@set dotnetdir=%windir%\Microsoft.NET
@set envdir=%dotnetdir%\%frmdir%\%dotnetver%
@set exename=%progname%.exe
@if exist %exename% del %exename%
@echo Compilam programul %progname%
%envdir%\csc.exe /nologo /target:exe /out:%exename% %progname%.cs
@if exist %exename% %exename%
@if exist %exename% del %exename%