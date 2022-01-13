package com.springSecurityNew.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper
{
    public MyHttpServletRequestWrapper(HttpServletRequest request)
    {
        super(request);
    }

    private String getBodyAsString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append(" BODY_DATA START [ ");
        char[] charArr = new char[getContentLength()];
        try
        {
            BufferedReader reader = new BufferedReader(getReader());
            reader.read(charArr, 0, charArr.length);
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        buff.append(charArr);
        buff.append(" ] BODY_DATA END ");
        return buff.toString();
    }

    public String toString()
    {
        return getBodyAsString();
    }
}