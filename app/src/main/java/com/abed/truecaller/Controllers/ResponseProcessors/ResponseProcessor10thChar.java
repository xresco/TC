package com.abed.truecaller.Controllers.ResponseProcessors;

/**
 * Created by Abed on 5/1/15.
 */
public class ResponseProcessor10thChar extends ResponseProcessor<Character> {

    protected  Character process(String s)
    {
        //since counting starts from zero
        return s.charAt(9);
    }

}
