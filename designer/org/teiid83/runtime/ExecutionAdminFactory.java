/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid83.runtime;

import java.sql.Driver;
import org.teiid.designer.query.IQueryService;
import org.teiid.designer.runtime.spi.IExecutionAdmin;
import org.teiid.designer.runtime.spi.IExecutionAdminFactory;
import org.teiid.designer.runtime.spi.ITeiidServer;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.designer.type.IDataTypeManagerService;
import org.teiid.jdbc.TeiidDriver;
import org.teiid83.TeiidRuntimePlugin;
import org.teiid83.sql.QueryService;
import org.teiid83.type.DataTypeManagerService;

/**
 *
 */
public class ExecutionAdminFactory implements IExecutionAdminFactory {

    private DataTypeManagerService dataTypeManagerService;
    
    private QueryService queryService;

    @Override
    public IExecutionAdmin createExecutionAdmin(ITeiidServer teiidServer) throws Exception {
        return new ExecutionAdmin(teiidServer);
    }
    
    @Override
    public IDataTypeManagerService getDataTypeManagerService(ITeiidServerVersion teiidVersion) {
        if (dataTypeManagerService == null) {
            dataTypeManagerService = new DataTypeManagerService();
        }
        
        return dataTypeManagerService;
    }

    @Override
    public Driver getTeiidDriver(ITeiidServerVersion teiidVersion) {
        return TeiidDriver.getInstance();
    }

    @Override
    public IQueryService getQueryService(ITeiidServerVersion teiidVersion) {
        if (queryService == null) {
            queryService = new QueryService();
        }
        
        return queryService;
    }

    @Override
    public String getRuntimePluginPath() {
        return TeiidRuntimePlugin.getPluginPath();
    }
}
