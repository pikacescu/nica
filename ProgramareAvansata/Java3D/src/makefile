java_dir="D:\programming\Java\jdk1.5.0_05\bin"
javac_cmd=$(java_dir)\javac.exe  -Xlint
java_cmd=$(java_dir)\java.exe
javaw_cmd=$(java_dir)\javaw.exe
jar_cmd=$(java_dir)\jar.exe
this_dir=X:\Java\videochat

all:
	@echo please specify a target
	@echo targets available: compileclient, compileserver, runclien, runserver

compileall:
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -d $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           $(this_dir)/sources/com/celoxsoft/videochat/client/*.java
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -d $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           $(this_dir)/sources/com/celoxsoft/videochat/common/*.java
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -d $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           $(this_dir)/sources/com/celoxsoft/videochat/server/*.java
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -d $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           $(this_dir)/sources/com/celoxsoft/videochat/tools/*.java
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -d $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           $(this_dir)/sources/com/celoxsoft/videochat/video/*.java


compileclient: compileCommon compiletools
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -d $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           $(this_dir)/sources/com/celoxsoft/videochat/client/Cln.java \
	           $(this_dir)/sources/com/celoxsoft/videochat/client/ClnView.java \
	           $(this_dir)/sources/com/celoxsoft/videochat/client/PrivateClnSession.java \
	           $(this_dir)/*.java
	

compileCommon:
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           -d $(this_dir)/classes \
	           $(this_dir)/sources/com/celoxsoft/videochat/common/Parser.java \
	

compileserver: compileCommon compiletools
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           -d $(this_dir)/classes \
	           $(this_dir)/sources/com/celoxsoft/videochat/server/Svr.java \
	           $(this_dir)/sources/com/celoxsoft/videochat/server/SvrView.java \
	           $(this_dir)/sources/com/celoxsoft/videochat/server/User.java \
	

compiletools:
	$(javac_cmd) \
	           -classpath $(this_dir)/classes \
	           -sourcepath $(this_dir)/sources \
	           -d $(this_dir)/classes \
	           $(this_dir)/sources/com/celoxsoft/videochat/tools/WakeSvrUp.java \
	

runclient:
	$(javaw_cmd) -classpath $(this_dir)/classes client.videochat.com.videochat.ClnView

serverWakeUp:
	$(java_cmd) -classpath $(this_dir)/classes tools.videochat.com.videochat.WakeSvrUp

runserver:
	$(java_cmd) -classpath $(this_dir)/classes server.videochat.com.videochat.Svr

alljar: compileclient compileserver clientjar serverjar wakeserverupjar
	@echo Done!

newjar: compileall clientjar serverjar
	@echo newjar Done!

clientjar:
	@if exist $(this_dir)\VideoMessengerClient.*jar del $(this_dir)\VideoMessengerClient.*jar
	$(jar_cmd)  -cfM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/client
	$(jar_cmd)  -ufM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/common
	$(jar_cmd)  -ufM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/server
	$(jar_cmd)  -ufM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/tools
	$(jar_cmd)  -ufM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/video
	@echo Manifest-Version: 1.0>        $(this_dir)\Manifest.mf
	@echo Created-By: Caliogstro's TEAM>> $(this_dir)\Manifest.mf
	@echo Main-Class: client.videochat.com.videochat.ClnView>>  $(this_dir)\Manifest.mf
	$(jar_cmd) -ufm $(this_dir)/VideoMessengerClient.jar $(this_dir)/Manifest.mf
	@copy $(this_dir)\VideoMessengerClient.jar $(this_dir)\VideoMessengerClient.cjar
	@if exist $(this_dir)\Manifest.mf del $(this_dir)\Manifest.mf
	@if exist $(this_dir)\tmp*.tml    del $(this_dir)\tmp*.tml

serverjar:
	@if exist $(this_dir)VideoMessengerServer.*jar del $(this_dir)VideoMessengerServer.*jar 
	$(jar_cmd) -cfM $(this_dir)/VideoMessengerServer.jar -C $(this_dir)/classes com/celoxsoft/videochat/server
	$(jar_cmd) -ufM $(this_dir)/VideoMessengerServer.jar -C $(this_dir)/classes com/celoxsoft/videochat/common
	$(jar_cmd)  -ufM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/tools
	$(jar_cmd)  -ufM $(this_dir)/VideoMessengerClient.jar -C $(this_dir)/classes com/celoxsoft/videochat/video
	@echo Manifest-Version: 1.0>        $(this_dir)\Manifest.mf
	@echo Created-By: Caliogstro's TEAM>> $(this_dir)\Manifest.mf
	@echo Main-Class: server.videochat.com.videochat.Svr>>      $(this_dir)\Manifest.mf
	$(jar_cmd) -ufm $(this_dir)/VideoMessengerServer.jar $(this_dir)/Manifest.mf
	@copy $(this_dir)\VideoMessengerServer.jar $(this_dir)\VideoMessengerServer.cjar
	@if exist $(this_dir)\Manifest.mf del $(this_dir)\Manifest.mf
	@if exist $(this_dir)\tmp*.tml    del $(this_dir)\tmp*.tml

wakeserverupjar:
	@if exist $(this_dir)ServerWakeUp.*jar del $(this_dir)ServerWakeUp.*jar 
	$(jar_cmd) -cfM $(this_dir)/ServerWakeUp.jar -C $(this_dir)/classes com/celoxsoft/videochat/tools/WakeSvrUp.class
	@echo Manifest-Version: 1.0>        $(this_dir)\Manifest.mf
	@echo Created-By: Caliogstro's TEAM>> $(this_dir)\Manifest.mf
	@echo Main-Class: tools.videochat.com.videochat.WakeSvrUp>>      $(this_dir)\Manifest.mf
	$(jar_cmd) -ufm $(this_dir)/ServerWakeUp.jar $(this_dir)/Manifest.mf
	@copy $(this_dir)\ServerWakeUp.jar $(this_dir)\ServerWakeUp.cjar
	@if exist $(this_dir)\Manifest.mf del $(this_dir)\Manifest.mf
	@if exist $(this_dir)\tmp*.tml    del $(this_dir)\tmp*.tml

