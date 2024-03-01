package com.videochat.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser //extends XParser
{


	List<String> paramNames = new ArrayList<>() ;//nume
    public List<String> paramValues = new ArrayList<>() ;//valori

    public Parser()
    {
    }

    //this function is used in ClnView to replace
    //certain symbol patten with a reference to a smiley
    public static String replace( String word, String pattern, String substitution )
    {
        if( pattern==null || word==null || substitution==null  ) return word;
        if( word.length()<pattern.length() ) return word;
        
        String result = word ;
        boolean go = true;
        while( go )
        {
            int index = 0;
            int length = result.length() - pattern.length();
            for( ; index < length ; index++ )
            {
                String s = result.substring( index, index + pattern.length() );
                if( s.equals( pattern ) )
                {
                    result = result.substring( 0, index ) + substitution + result.substring( index + pattern.length() );
                    break;
                }
            }
            go = index < length;
        }
        
        return result;
    }
    
    //this function is used to write with small letters in on the screen
    // it is based on HTML support in swing
    public static String h5(String _str)
    {
        return( "<html><h5>" + _str + "</h5><html>" );
    }

    boolean parsed = false;//partitionat sau nu

    String strCommand = "";
    String strCommands = "";

    //String strCommandLine = "";
    String strInfo = "";
    String strInfoLog = "";

    String str = "";
    String log = "";

    int numberOfParameters = -1;//numarul de perechi nume valoare delimitate din linia de comanda
    String command;

	public String getStrInfo()
	{
		return strInfo;
	}

	public String getCommand()
	{
		return command;
	}

    public synchronized int getNumberOfParameters()
    {
        return numberOfParameters;
    }

    public synchronized void reset(String _str)
    {
        strCommand = "";
        strCommands = "";

        //strCommandLine = "";
        strInfo = "";

        log = "";

        numberOfParameters = -1;
        str = _str;

        paramNames.clear();
        paramValues.clear();
    }

    public static String transformToSafeHtml(String html)
    {

        if( html == null ) return null;
        if( html.trim().isEmpty()) return "";

        System.out.println("in transformToSafeHtml():: html= " + html );

        StringBuilder result = new StringBuilder();
        String current = html;

        Pattern p = Pattern.compile( "([^ ]*)( +)(.*)"  );
        Matcher m;
        while(!current.isEmpty())
        {
            m = p.matcher( current );
            if( m.matches() )
            {
                if( Pattern.matches( "^www\\.(.+)", m.group(1) ) || Pattern.matches( "^http:(.+)", m.group(1) ) )
                    result.append(" <a href=\"").append(m.group(1)).append("\">").append(m.group(1)).append("</a>").append(m.group(2));
                else result.append(m.group(1)).append(m.group(2));
                current = m.group( 3 );
            }
            else
            {
                if( Pattern.matches( "^http:(.+)", current ) || Pattern.matches( "^www\\.(.+)", current ) )
                    result.append(" <a href=\"").append(current).append("\">").append(current).append("</a>");
                else result.append(current);
                current ="";
            }
        }
        return result.toString();
    }

    static Pattern ptATag = Pattern.compile( "^(.*)<\\s*a\\s+href=\\s*\"([^<>]+?)\"\\s*>(.*)<\\s*/\\s*a\\s*>(.*)" );
    static Pattern ptATag1 = Pattern.compile( "^(.*)<\\s*b\\s*>(.*)<\\s*/\\s*b\\s*>(.*)" );
    static Pattern ptATag2 = Pattern.compile( "^(.*)<\\s*i\\s*>(.*)<\\s*/\\s*i\\s*>(.*)" );
    static Pattern ptATag3 = Pattern.compile( "^(.*)<\\s*font\\s+color=\\s*\"([^<>]+?)\"\\s*>(.*)<\\s*/\\s*font\\s*>(.*)" );

    @SuppressWarnings("ReassignedVariable")
    public static String parseAllTags(String s)
    {
		if( s==null )return null;
		if( s.trim().isEmpty()  )return "";

		String current = s;
		System.out.println("_________________________________________________");
		System.out.println("current=" + current );

		boolean bold, italic, font;
		String color;

		bold = false;
		italic = false;
		font = false;
		color = null;

		Matcher m = ptATag1.matcher( current ) ;
		if( m.matches() )
		{
			bold = true;
			current = m.group(1) + m.group(2) + m.group(3);
		}
		m = ptATag2.matcher( current ) ;
		if( m.matches() )
		{
			italic = true;
			current = m.group(1) + m.group(2) + m.group(3);
		}
		m = ptATag3.matcher( current ) ;
		if( m.matches() )
		{
			font = true;
			color = m.group(2);
			current = m.group(1) + m.group(3) + m.group(4);
		}

		m = ptATag.matcher( current );
		if( !m.matches() ) current = transformToSafeHtml( current );
		else
		{
			current =parseAllTags( m.group(1) )+ "<a href=\"" + m.group(2) + "\">" + m.group(3) + "</a>" + parseAllTags( m.group( 4 ) );
		}

		//adaugarea tagurilor necesare:
		if( bold ) current = "<b>" + current + "</b>";
		if( italic ) current = "<i>" + current + "</i>";
		if( font && color!=null )
			if( !color.trim().isEmpty() )
				current = "<font " +  "color=\"" + color + "\">"  + current + "</font>";
		System.out.println("transformToSafeHTML(): current = " + current );
		return current;
    }

	static Pattern commandMatcher = Pattern.compile ("^command=([^=;]+);(.*)$");
	static Pattern params = Pattern.compile ("^([^=;]+)=([^=;]+);(.*)$" );
	static Pattern starts = Pattern.compile ("^command=.+");
	static Pattern goodChars = Pattern.compile ("^[^<>&\\t\\r\\\\'\\00]+:.*$" );
	static Pattern detachText = Pattern.compile ("^([^:]+):(.*)" );
	static Pattern goodCmd = Pattern.compile ("^([^=;]+=[^=;]+;)*$");

	public synchronized boolean parse( String _str )//depanarea unei linii de comanda
	{
		System.out.println("_str=" + _str);
		reset( _str );
		parsed = false;

        if(!starts.matcher(str).matches())
		{
			log = "any string commands must begin with [command=]";
			return false;
		} //verificare

		if(!goodChars.matcher(str).matches())
        {
			log = "bad character in command line";
			return false;
        }

		Matcher m = detachText.matcher( str );

		if( !m.matches() )//verificarea prezentei simbolului ":" in str
		{
			log = "command strings must end with ':'";
			return false;
		}

		strInfoLog = m.group(2);
		strInfo = strInfoLog;

		strCommands = m.group(1); //strCommands memorizeaza primul element delimitat(prima comanda posibil)
		m.reset();


        String cmd;
        String val;

        if(!goodCmd.matcher(strCommands).matches())
        {
			log = "bad command format";
			return false;
		}


        m = commandMatcher.matcher(strCommands);
        if(!m.find())return false;
        strCommand = m.group(1);

        String current = m.group(2);

        while(!current.isEmpty())
        {
            //System.out.println( "current:" + current );

			m = params.matcher( current );
			if( !m.matches() )
			{
				System.out.println( "Nu se poate de partitionat" );
				return false;
			}

			cmd  = m.group( 1 ).trim();
			val  = m.group( 2 ).trim();
			current = m.group( 3 ).trim();

			//strCommandLine += ( cmd + "=" + val + ";" );
			paramNames.add( cmd );
			paramValues.add( val );

        }

	    numberOfParameters = paramNames.size();

        log = "looks Ok in context of unknown command";

        command = strCommand;

        parsed = true;

        //noinspection ConstantValue
        return parsed;
    }

	public String param(int i)
	{
	    return paramNames.get(i);
	}

    public String val(int i)
    {
	    try
        {
            return paramValues.get(i);
        }
        catch( java.lang.IndexOutOfBoundsException e )
        {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return null;
        }
    }

}
