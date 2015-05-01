package com.abed.truecaller.Controllers.ResponseProcessors;

import java.util.ArrayList;

/**
 * Created by Abed on 5/1/15.
 */
public class ResponseProcessorEvery10thChar extends ResponseProcessor<ArrayList<Character>> {

    protected ArrayList<Character> process(String s)
    {
        ArrayList<Character> chars= new ArrayList<>();

        int i=9;
        while(i<s.length())
        {
            chars.add(s.charAt(i));
            i+=10;
        }

        return chars;
    }

}
