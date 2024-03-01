/*
 * Index.java
 *
 * Created on 11 ianuarie 2006, 14:56
 */

package com.videochat.client;

/**
 *
 * @author olegciobanu
 */
public class Index implements Comparable
{
    int index;
    //String type;
    //private String gender;


    public static final int typeAudio = 1;
    public static final int typeVideo = 2;
    public static final int genderData    = 1;
    public static final int genderControl = 2;
    int type   = 0;//null; //type audio or video
    int gender = 0;//null; //data or control


    /** Creates a new instance of Index */
    public Index( int _index, int _type, int _gender)
    {
        index = _index;
        type = _type;
        gender = _gender;
    }

    public int getIndex(){return index;}

    public int getType(){return type;}

    public boolean equals( Object _value )
    {

        //System.out.println("localIndex = " + index);
        //System.out.println("localType = " + type);
        //System.out.println("Type = " + value.getType());
        //System.out.println("Index = " + value.getIndex());
        if( !(_value instanceof Index) )return false;
        Index value = (Index)_value;
        //if( value instanceof Index )
        //{
        if( ( this.index == ((Index)value).index ) && ( this.type == ( ((Index)value).type ) ))
        //    {
                return true;
        //    }
        //    else return false;
        //}
        return false;
    }

    public int hashCode()
    {
        //  index+10*a+20*b
        int a,b;
        //System.out.print("hashCode =");
        int local = 0;

        if( type == typeAudio ) a = 1;
        else a = 2;

        if( gender == genderData ) b = 1;
        else b = 2;

        local += index + 10*a + 20*b;

        //System.out.println( local );
        return local;
    }

    public int compareTo( Object val ) throws ClassCastException
    {
        //System.out.println("Index::compareTo");
        Index local = ( Index )val;
        if( this.hashCode()==local.hashCode() ) return 0;
        else if( this.hashCode() < local.hashCode()  ) return -1;
        else return 1;
    }

    public String toString()
    {
        return "Index::index= " + index + "; type = " + type +";" + "; gender = " + gender + ";";
    }

    public int getGender()
    {
        return gender;
    }
}
