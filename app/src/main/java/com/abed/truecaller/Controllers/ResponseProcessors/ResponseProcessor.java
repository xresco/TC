package com.abed.truecaller.Controllers.ResponseProcessors;

/**
 * Created by Abed on 5/1/15.
 */
public abstract class ResponseProcessor<T> {
    private DataProcessListener<T> listener;

    public ResponseProcessor setListener(DataProcessListener<T> listener) {
        this.listener = listener;
        return this;
    }

    protected abstract T process(String s);

    public final void processResponse(String s)
    {
        T processed_data=process(s);
        if(listener!=null)
            listener.dataProcessedSuccessfully(processed_data);
    }

    public  interface DataProcessListener<E>{
        void dataProcessedSuccessfully(E response);
    }
}
