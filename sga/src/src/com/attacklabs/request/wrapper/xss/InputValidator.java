/*
 * InputValidator.java
 *
 * Created on February 13, 2007, 11:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.attacklabs.request.wrapper.xss;

/**
 *
 * @author anurag agarwal
 */
public class InputValidator {
    
    /** Creates a new instance of InputValidator */
    public InputValidator() {
    }
    //These are the characters which we want to filter.
    private String[] filterChars = {"<", ">", "&lt", "&gt", "&#", "\"", "\\", "0x"};
    
    //These are the characters which will get replaced with the subsequent character in the same position from filterChars array.
    private String[] replacementChars = {" ", " ", " ", " ", "#", " ", "/", "0 x"};


    /*
    protected String filterUrl(String url)       {
        StringBuffer sb = new StringBuffer(url);
        
       
        for(int position = url.toLowerCase().indexOf("http://"); position >= 0; )  {
            sb.replace(position, position + "http://".length(), "/");
            url = sb.toString();
            position = url.toLowerCase().indexOf("http://");
        }
        
        return sb.toString();
    }*/
    
    /*
     *Filter the characters in the request parameter.
     *@param param request parameter from the query string which we want to filter.
     *@return filtered character.
     */
    public String filterRequest(String param)      {
        String value = param;        
        //If param != null then only filter the characters.
        if( param!=null) {            
            /*Check for every character in the filterChars array. If the character is found then replace that character from 
             *the character in the same position from replacementChars array. */
            for(int i = 0; i < filterChars.length; i++)     {
                value = filterCharacters(filterChars[i], replacementChars[i], value);
            }
        }
        
        return value;

    }
    
    /*
     *The actual function which checks for the character to be filtered and replaces it with the replacement character.
     *@param originalChar character which we want to filter.
     *@param newChar character which we want to replace it with, if found in the parameter.
     *@param param the parameter from the query string.
     *@return updated parameter after the characters which we want to filter are replaced (if found).
     */
    private String filterCharacters(String originalChar, String newChar, String param) {
        StringBuffer sb = new StringBuffer(param);
        /*Loop through the entire string and check for as many instances of the character in the parameter string.
         *If found, then replace it with the replacement character.
         */
        for(int position = param.toLowerCase().indexOf(originalChar); position >= 0; )  {
            sb.replace(position, position + originalChar.length(), newChar);
            param = sb.toString();
            position = param.toLowerCase().indexOf(originalChar);
        }
        return sb.toString();
    }    
}
