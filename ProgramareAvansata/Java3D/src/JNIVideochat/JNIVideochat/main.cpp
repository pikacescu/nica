#include<iostream>
#include<jni.h>
#include<string.h> 

#define NUM_OF_OPT 10
#include<windows.h>

using namespace std;

typedef jint (CALLBACK *MYPROC)(JavaVM**, void**, JavaVMInitArgs*);

string getString( byte *data, int dataLength )
{
	char *result = new char[ dataLength ];
	for( int index = 0; index<dataLength ; index++ )
	{
		result[index] = data[index];
	}
	string s = string( result ); 
	free( result );
	return s;
}

string* getPaths( void )
{
	string javaPath = "";
	string classPath = "";
    
    LONG result;
	DWORD dataLength = 200;
	byte data[200];
	HKEY hKey;
	string local = "SOFTWARE\\Celoxsoft\\Videochat";
	
    if
		(
			RegOpenKeyEx
			(
				HKEY_LOCAL_MACHINE,
				local.c_str(),
				0,
				KEY_QUERY_VALUE,
				&hKey
			)!= ERROR_SUCCESS
		)
	{
		return NULL;
	}
    
	result = RegQueryValueEx
		(
			hKey,
			"CurrentVersion",
			NULL,
			NULL,
			(LPBYTE) data,
			(LPDWORD)&dataLength
		);
	RegCloseKey( hKey );
    if( result != ERROR_SUCCESS ) return NULL;
	
	local +="\\"; 
	local += getString(data, dataLength);

	result = RegOpenKeyEx
		(
			HKEY_LOCAL_MACHINE,
			local.c_str(),
			0,
			KEY_QUERY_VALUE,
			&hKey
		);
	if( result != ERROR_SUCCESS ) return NULL;
	
	dataLength = 200;

	result = RegQueryValueEx
	(
		hKey,
		"JRERuntimeLib",
		NULL,
		NULL,
		(LPBYTE)data,
		(LPDWORD)&dataLength
	);

	
	
	if( result == ERROR_SUCCESS )
	{
		javaPath = getString( data, dataLength );
	}

	dataLength = 200;

	result = RegQueryValueEx
	(
		hKey,
		"InstallPath",
		NULL,
		NULL,
		(LPBYTE)data,
		(LPDWORD)&dataLength
	);

	if( result == ERROR_SUCCESS )
	{
		classPath = getString( data, dataLength );
	}

	RegCloseKey( hKey );

	if( javaPath.c_str()!="" && classPath.c_str()!="" )
	{
		string* results= new string[2];
		results[0] = javaPath;
		results[1] = classPath;
		return results;
	}

	return NULL;
}

int main()
{
	HMODULE handle;
	int i = 1;
	JNIEnv *env = 0;
    JavaVM *jvm = 0;

	JavaVMInitArgs jargs;
    jint ret;

	JavaVMOption options[NUM_OF_OPT];

	jargs.version = JNI_VERSION_1_4;
	jargs.ignoreUnrecognized = 0;
	jargs.nOptions = 1 ;

	string *paths = getPaths();
	if( paths==NULL )
	{
		MessageBox( NULL, "Did not find necessary information.", "Message", MB_OK );
		return 0;
	}
	string javaPath, classPath; 
	javaPath = string(paths[0]);
	classPath = string(paths[1]);

	delete[]( paths );

	//cout<<"javaPath = "<<javaPath.c_str()<<endl;
	//cout<<"classPath = "<<classPath.c_str()<<endl;

	if( javaPath.length()==0 || classPath.length()==0 ) return 0;
	
	classPath.insert( 0, "-Djava.class.path=");
	classPath += "\\VideoMessengerClient.jar";
	options[0].optionString = ( (char*)classPath.c_str() );
	options[0].extraInfo = 0;

	jargs.options = options;
	MYPROC funcPointer;
	
	handle = LoadLibrary( javaPath.c_str() );
	if( handle==NULL ) cerr<<"Library is not found."<<endl;
	else
	{
		cerr<<"Library is found."<<endl;
		funcPointer =(MYPROC)( GetProcAddress( handle, "JNI_CreateJavaVM" ) );
		if( funcPointer==NULL ) cerr<<"Process is not found."<<endl;
		else
		{
			cerr<<"Process is found."<<endl;
			ret = (funcPointer)(&jvm, (void**)&env, &jargs);
			if (ret < 0) 
			{
				cerr<< "Can't create Java VM, ret = "<<ret<< endl;
				FreeLibrary( handle );
				exit(1);
			}
			jclass cls = env->FindClass("com/celoxsoft/videochat/client/ClnView");
			if( cls == NULL )
			{
				cerr<<"couldn't find ClnView class."<<endl;
				FreeLibrary( handle );
				return 0;
			}
			else cerr<<"did find the necessarry class"<<endl;

			jmethodID mid = env->GetStaticMethodID(cls, "main", "([Ljava/lang/String;)V");
			if( mid==NULL )
			{
				cerr<<"Couldn't find main method."<<endl;
				FreeLibrary( handle );
				exit( 1 );
			}

			jstring jstr;
			jstr = (env)->NewStringUTF("");
			if( jstr=NULL )
			{
                FreeLibrary( handle );
				return 0;
			}
			jclass stringClass = (env)->FindClass("java/lang/String");
			jobjectArray args = (env)->NewObjectArray( 1, stringClass, jstr);
			if( args==NULL )
			{
				env->DeleteLocalRef( jstr );
				FreeLibrary( handle );
				 return 0;
			}

			env->CallStaticVoidMethod(cls, mid, args );

			SleepEx( INFINITE, TRUE );
			
			jvm->DestroyJavaVM();
			FreeLibrary( handle );
			return 0;
		}
	}
}