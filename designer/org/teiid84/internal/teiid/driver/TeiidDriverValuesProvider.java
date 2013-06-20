/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid84.internal.teiid.driver;

import java.io.File;
import org.eclipse.core.runtime.IPath;
import org.eclipse.datatools.connectivity.drivers.DefaultDriverValuesProvider;
import org.eclipse.datatools.connectivity.drivers.IDriverValuesProvider;
import org.eclipse.datatools.connectivity.services.PluginResourceLocator;
import org.teiid84.TeiidRuntimePlugin;

/**
 * Provides values for the default instance of the Teiid Connection Driver profile
 */
public class TeiidDriverValuesProvider extends DefaultDriverValuesProvider {

    /**
     * Target directory
     */
    private static final String TARGET = "target"; //$NON-NLS-1$

    /**
     * sources jar component
     */
    private static final String SOURCES = "sources"; //$NON-NLS-1$

    /**
     * JAR File Extension
     */
    private static final String JAR = "jar"; //$NON-NLS-1$

    @Override
    public String createDefaultValue(String key) {
        if (key.equals(IDriverValuesProvider.VALUE_CREATE_DEFAULT))
            return Boolean.TRUE.toString();

        if (key.equals(IDriverValuesProvider.VALUE_JARLIST)) {

            IPath path = PluginResourceLocator.getPluginRootPath(TeiidRuntimePlugin.PLUGIN_ID);
            String jarPath = findPluginJar(path);

            if (jarPath == null) {
                /*
                 * Should normally exist in installed environment but when developing it will not.
                 * Use the backup version of checking the target directory for a build jar of this plugin.
                 */
                jarPath = findTargetJar(path);
            }

            return jarPath;
        }

        return super.createDefaultValue(key);
    }

    /**
     * Find the plugin jar based on the given path
     *
     * @param path
     * @return
     */
    private String findPluginJar(IPath path) {
        if (path == null)
            return null;

        if (!path.getFileExtension().equals(JAR))
            path = path.addFileExtension(JAR);

        String osPath = path.toOSString();
        File jarFile = new File(osPath);
        if (jarFile.exists())
            return osPath;

        return null;
    }

    /**
     * Find a built jar in a 'target' sub-directory of the given path location.
     *
     * This location only really occurs when developing / testing and a built
     * version is available in the maven target directory.
     *
     * @param path
     * @return
     */
    private String findTargetJar(IPath path) {
        if (path == null)
            return null;

        path = path.append(TARGET);

        File targetDir = new File(path.toOSString());
        if (! targetDir.isDirectory())
            return null;

        for (File file : targetDir.listFiles()) {
            if (! file.getName().endsWith(JAR))
                continue;

            // Ignore sources jar
            if (file.getName().contains(SOURCES))
                continue;

            if (file.getName().startsWith(TeiidRuntimePlugin.PLUGIN_ID))
                return file.getAbsolutePath();
        }

        return null;
    }
}
