@if exist x: @goto deldisks
@subst x: d:\lucru
@echo disk x created Ok!
@goto endapp
:deldisks
@subst /D x:
@echo disk x removed Ok!
:endapp

@echo Ok!