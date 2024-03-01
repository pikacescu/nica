var shell = new ActiveXObject("WScript.Shell");

var jre_key     = "HKLM\\SOFTWARE\\JavaSoft\\Java Runtime Environment\\";
var jre_ver     = shell.RegRead (jre_key + "CurrentVersion");
var jre_home    = shell.RegRead (jre_key + jre_ver + "\\JavaHome")
var jre_exe_cmd = jre_home + "\\bin\\java.exe";

var jdk_key     = "HKLM\\SOFTWARE\\JavaSoft\\Java Development Kit\\";
var jdk_ver     = shell.RegRead(jdk_key + "CurrentVersion");
var jdk_home    = shell.RegRead(jdk_key + jdk_ver + "\\JavaHome");

try
{
  shell.RegRead("HKCR\\.cjar\\");
}catch(e)
{
  shell.RegWrite("HKCR\\.cjar\\", "cjarfile");
  shell.RegWrite("HKCR\\cjarfile\\", "Executable Console Jar File");
  shell.RegWrite("HKCR\\cjarfile\\shell\\open\\command\\", "\"" + jre_exe_cmd + "\"  -jar \"%1\" %*");
}

var ForReading   = 1;
var ForWriting   = 2;
var ForAppending = 8;


var fso, file;
fso = new ActiveXObject("Scripting.FileSystemObject");

var proj_dir = "x:\\Java\\videochat\\";
var makefilepath = proj_dir + "makefile";
var inifilepath = proj_dir + "svr.ini";

try{fso.CreateFolder(proj_dir + "logs");}catch(e){}

try
{
  fso.GetFile(makefilepath);
}catch(e)
{
  writeMakeFile(makefilepath);
}

try
{
  fso.GetFile(inifilepath);
}catch(e)
{
  writeIniFile(inifilepath);
}

function writeIniFile(filepath)
{
  var inifile = fso.CreateTextFile(filepath, true);
  inifile.WriteLine("[PORT] 8888");
  inifile.WriteLine("[CONNECTIONS_POOLING] 50");
  inifile.WriteLine("[BIND_ADDRESS] localhost");
  inifile.WriteLine("");
}







function writeMakeFile()
{
  var makefile = fso.CreateTextFile(proj_dir + "makefile", true);
  makefile.WriteLine("java_dir=\"" + jdk_home + "\\bin\"");
  makefile.WriteLine("javac_cmd=$(java_dir)\\javac.exe  -Xlint");
  makefile.WriteLine("java_cmd=$(java_dir)\\java.exe");
  makefile.WriteLine("javaw_cmd=$(java_dir)\\javaw.exe");
  makefile.WriteLine("jar_cmd=$(java_dir)\\jar.exe");
  makefile.WriteLine("this_dir=X:\\Java\\videochat");
  makefile.WriteLine("");

  makefile.WriteLine("all:");
  makefile.WriteLine("\t@echo please specify a target");
  makefile.WriteLine("\t@echo targets available: compileclient, compileserver, runclien, runserver");
  makefile.WriteLine("");


  
  
  makefile.WriteLine("compileclient: compileCommon compiletools");  
  makefile.WriteLine("\t$(javac_cmd) \\");
  makefile.WriteLine("\t           -classpath $(this_dir)/classes \\");
  makefile.WriteLine("\t           -d $(this_dir)/classes \\");
  makefile.WriteLine("\t           -sourcepath $(this_dir)/sources \\");

  var f = fso.GetFolder(proj_dir + "sources\\com\\celoxsoft\\videochat\\client");
  var sf = new Enumerator(f.Files);
  var srcname;
  for (; !sf.atEnd(); sf.moveNext())
  {
    srcname  =  sf.item().name;
    if(/\.java$/.test(srcname))makefile.WriteLine("\t           $(this_dir)/sources/com/celoxsoft/videochat/client/" + sf.item().name + " \\");
  }
  makefile.WriteLine("\t");
  makefile.WriteLine("");


  makefile.WriteLine("compileCommon:");
  makefile.WriteLine("\t$(javac_cmd) \\");
  makefile.WriteLine("\t           -classpath $(this_dir)/classes \\");
  makefile.WriteLine("\t           -sourcepath $(this_dir)/sources \\");
  makefile.WriteLine("\t           -d $(this_dir)/classes \\");
  
  f = fso.GetFolder(proj_dir + "sources\\com\\celoxsoft\\videochat\\common");
  
  sf = new Enumerator(f.Files);
  
  for (; !sf.atEnd(); sf.moveNext())
  {
    srcname  =  sf.item().name;
    if(/\.java$/.test(srcname))makefile.WriteLine("\t           $(this_dir)/sources/com/celoxsoft/videochat/common/" + sf.item().name + " \\");
  }
  
  makefile.WriteLine("\t");
  makefile.WriteLine("");
	

  makefile.WriteLine("compileserver: compileCommon compiletools");
  makefile.WriteLine("\t$(javac_cmd) \\");
  makefile.WriteLine("\t           -classpath $(this_dir)/classes \\");
  makefile.WriteLine("\t           -sourcepath $(this_dir)/sources \\");
  makefile.WriteLine("\t           -d $(this_dir)/classes \\");
  f = fso.GetFolder(proj_dir + "sources\\com\\celoxsoft\\videochat\\server");
  sf = new Enumerator(f.Files);
  for (; !sf.atEnd(); sf.moveNext())
  {
    srcname  =  sf.item().name;
    if(/\.java$/.test(srcname))makefile.WriteLine("\t           $(this_dir)/sources/com/celoxsoft/videochat/server/" + sf.item().name + " \\");
  }
  makefile.WriteLine("\t");
  makefile.WriteLine("");

  makefile.WriteLine("compiletools:");
  makefile.WriteLine("\t$(javac_cmd) \\");
  makefile.WriteLine("\t           -classpath $(this_dir)/classes \\");
  makefile.WriteLine("\t           -sourcepath $(this_dir)/sources \\");
  makefile.WriteLine("\t           -d $(this_dir)/classes \\");
  f = fso.GetFolder(proj_dir + "sources\\com\\celoxsoft\\videochat\\tools");
  sf = new Enumerator(f.Files);
  for (; !sf.atEnd(); sf.moveNext())
  {
    srcname  =  sf.item().name;
    if(/\.java$/.test(srcname))makefile.WriteLine("\t           $(this_dir)/sources/com/celoxsoft/videochat/tools/" + sf.item().name + " \\");
  }
  makefile.WriteLine("\t");
  makefile.WriteLine("");

  makefile.WriteLine("runclient:");
  makefile.WriteLine("\t$(javaw_cmd) -classpath $(this_dir)/classes com.celoxsoft.videochat.client.ClnView");
  makefile.WriteLine("");

  makefile.WriteLine("serverWakeUp:");
  makefile.WriteLine("\t$(java_cmd) -classpath $(this_dir)/classes com.celoxsoft.videochat.tools.WakeSvrUp");
  makefile.WriteLine("");

  makefile.WriteLine("runserver:");
  makefile.WriteLine("\t$(java_cmd) -classpath $(this_dir)/classes com.celoxsoft.videochat.server.Svr");
  makefile.WriteLine("");

  makefile.WriteLine("alljar: compileclient compileserver clientjar serverjar wakeserverupjar");
  makefile.WriteLine("\t@echo Done!");
  makefile.WriteLine("");

  makefile.WriteLine("clientjar:");
  makefile.WriteLine("\t@if exist $(this_dir)\\VideoMessengerClient.*jar del $(this_dir)\\VideoMessengerClient.*jar");
  makefile.WriteLine("\t$(jar_cmd)  -cfM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/client");
  makefile.WriteLine("\t$(jar_cmd)  -ufM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/common");
  makefile.WriteLine("\t@echo Manifest-Version: 1.0>        $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@echo Created-By: Caliogstro's TEAM>> $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@echo Main-Class: com.celoxsoft.videochat.client.ClnView>>  $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t$(jar_cmd) -ufm $(this_dir)/VideoMessengerClient.jar $(this_dir)/Manifest.mf");
  makefile.WriteLine("\t@copy $(this_dir)\\VideoMessengerClient.jar $(this_dir)\\VideoMessengerClient.cjar");
  makefile.WriteLine("\t@if exist $(this_dir)\\Manifest.mf del $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@if exist $(this_dir)\\tmp*.tml    del $(this_dir)\\tmp*.tml");
  makefile.WriteLine("");

  makefile.WriteLine("serverjar:");
  makefile.WriteLine("\t@if exist $(this_dir)\VideoMessengerServer.*jar del $(this_dir)\VideoMessengerServer.*jar ");
  makefile.WriteLine("\t$(jar_cmd) -cfM $(this_dir)/VideoMessengerServer.jar -C $(this_dir)/classes com/celoxsoft/videochat/server");
  makefile.WriteLine("\t$(jar_cmd) -ufM $(this_dir)/VideoMessengerServer.jar -C $(this_dir)/classes com/celoxsoft/videochat/common");
  makefile.WriteLine("\t@echo Manifest-Version: 1.0>        $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@echo Created-By: Caliogstro's TEAM>> $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@echo Main-Class: com.celoxsoft.videochat.server.Svr>>      $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t$(jar_cmd) -ufm $(this_dir)/VideoMessengerServer.jar $(this_dir)/Manifest.mf");
  makefile.WriteLine("\t@copy $(this_dir)\\VideoMessengerServer.jar $(this_dir)\\VideoMessengerServer.cjar");
  makefile.WriteLine("\t@if exist $(this_dir)\\Manifest.mf del $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@if exist $(this_dir)\\tmp*.tml    del $(this_dir)\\tmp*.tml");
  makefile.WriteLine("");

  makefile.WriteLine("wakeserverupjar:");
  makefile.WriteLine("\t@if exist $(this_dir)\ServerWakeUp.*jar del $(this_dir)\ServerWakeUp.*jar ");
  makefile.WriteLine("\t$(jar_cmd) -cfM $(this_dir)/ServerWakeUp.jar -C $(this_dir)/classes com/celoxsoft/videochat/tools/WakeSvrUp.class");
  makefile.WriteLine("\t@echo Manifest-Version: 1.0>        $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@echo Created-By: Caliogstro's TEAM>> $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@echo Main-Class: com.celoxsoft.videochat.tools.WakeSvrUp>>      $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t$(jar_cmd) -ufm $(this_dir)/ServerWakeUp.jar $(this_dir)/Manifest.mf");
  makefile.WriteLine("\t@copy $(this_dir)\\ServerWakeUp.jar $(this_dir)\\ServerWakeUp.cjar");
  makefile.WriteLine("\t@if exist $(this_dir)\\Manifest.mf del $(this_dir)\\Manifest.mf");
  makefile.WriteLine("\t@if exist $(this_dir)\\tmp*.tml    del $(this_dir)\\tmp*.tml");
  makefile.WriteLine("");
  makefile.Close();
}