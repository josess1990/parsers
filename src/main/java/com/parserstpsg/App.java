package com.parserstpsg;

import parsers.OpenTableParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        OpenTableParser otp = new OpenTableParser();
        System.out.println(otp.getRestaurants("123","3"));
    }
}
