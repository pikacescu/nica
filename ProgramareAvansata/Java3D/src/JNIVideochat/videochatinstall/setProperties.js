//
var shell = new ActiveXObject("WScript.Shell");

var jre_home    = shell.RegRead ("HKLM\\SOFTWARE\\JavaSoft\\Java Runtime Environment\\1.5\\RuntimeLib")
//shell.Popup (jre_home);

shell.RegWrite("HKLM\\SOFTWARE\\Celoxsoft\\VideoChat\\1.0.0\\JRERuntimeLib", jre_home);
//xxx();
//shell.Popup ("salut");
var x = Session;
//var y = StringList;
//shell.Popup(Session.Property("Manufacturer"));
//shell.Popup(Session.ProductProperty("Manufacturer"));
var msiDoActionStatusNoAction = 0;
var msiDoActionStatusSuccess  = 1; var IDOK     = 1;
var msiDoActionStatusUserExit = 2; var IDCANCEL = 2;
var msiDoActionStatusFailure  = 3; var IDABORT  = 3;
var msiDoActionStatusSuspend  = 4; var IDRETRY  = 4;
var msiDoActionStatusFinished = 5; var IDIGNORE = 5;

//shell.Popup ("salut");

function xxx()
{
   shell.Popup ("salut");
}

function main()
{
    shell.Popup ("main");
    return 1;//msiDoActionStatusSuccess;
}
function MsiGetProductCode(szComponent, lpProductBuf)
{
    shell.Popup (szComponent);
    return 1;//msiDoActionStatusSuccess;
}