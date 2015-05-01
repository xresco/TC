package com.abed.truecaller.Controllers.ResponseProcessors;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Abed on 5/1/15.
 */
public class ResponseProcessorWordCounter extends ResponseProcessor<HashMap<String,Integer>> {

    protected HashMap<String,Integer> process(String s)
    {
        HashMap<String,Integer> wordCounts= new HashMap<>();

        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            String current_token=st.nextToken().toLowerCase();
            if(!wordCounts.containsKey(current_token))
                wordCounts.put(current_token,1);
            else {
                int current_count=wordCounts.get(current_token);
                wordCounts.put(current_token,current_count+1);
            }
        }
        return wordCounts;
    }

}
