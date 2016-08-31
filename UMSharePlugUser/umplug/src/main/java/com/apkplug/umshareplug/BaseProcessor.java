package com.apkplug.umshareplug;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

/**
 * Created by qinfeng on 2016/7/20.
 */
public abstract class BaseProcessor extends Processor {

    public static BundleContext context;
    DispatchAgent dispatchAgent;

    public BaseProcessor(BundleContext context) {
        super(context);
        this.context = context;
        dispatchAgent = new DispatchAgent(context);
    }
}
