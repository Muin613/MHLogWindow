package com.munin.mhlogwindow;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by munin on 2018/1/30.
 */

public class MHLogManager {
    LinkedList<ILogListener> observe;
    static MHLogManager mhLogManager = null;

    private MHLogManager() {
        observe = new LinkedList<ILogListener>();
    }

    public static synchronized MHLogManager newInstance() {
        if (mhLogManager == null) {
            mhLogManager = new MHLogManager();
        }
        return mhLogManager;
    }

    public void add(ILogListener logListener) {
        if (null != observe)
            if (!observe.contains(logListener))
                observe.add(logListener);
    }

    public void notify(MHLogData data) {
        if (null != observe) {
            Iterator<ILogListener> it = observe.iterator();
            while (it.hasNext()) {
                ILogListener listener = it.next();
                listener.notifyLog(data);
            }
        }
    }

}
