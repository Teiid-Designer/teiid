/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid84;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class TeiidRuntimePlugin extends Plugin {

    public static String PLUGIN_ID;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        PLUGIN_ID = context.getBundle().getSymbolicName();
    }
}
