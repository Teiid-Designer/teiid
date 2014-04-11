/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid772.sql.impl.validator;

import java.util.List;
import org.teiid.designer.query.IQueryResolver;
import org.teiid.designer.query.metadata.IQueryMetadataInterface;
import org.teiid.query.resolver.QueryResolver;
import org.teiid.query.sql.lang.Command;
import org.teiid.query.sql.lang.DynamicCommand;
import org.teiid.query.sql.proc.CreateUpdateProcedureCommand;
import org.teiid.query.sql.symbol.ElementSymbol;
import org.teiid.query.sql.symbol.GroupSymbol;
import org.teiid772.sql.impl.CrossQueryMetadata;

/**
 *
 */
public class WrappedQueryResolver implements IQueryResolver<Command, GroupSymbol, ElementSymbol> {
    
    @Override
    public void resolveCommand(Command command, GroupSymbol gSymbol, int commandType, 
                               IQueryMetadataInterface metadata) throws Exception {
        
        CrossQueryMetadata cqMetadata = new CrossQueryMetadata(metadata);
        QueryResolver.resolveCommand(command, gSymbol, commandType, cqMetadata);
    }

    @Override
    public void postResolveCommand(Command command, GroupSymbol gSymbol, int commandType,
                                   IQueryMetadataInterface metadata, List<ElementSymbol> projectedSymbols) {

        if (command instanceof CreateUpdateProcedureCommand) {

            /**
             * This was added to designer to avoid a validation failure, see TEIIDDES-624
             */
            CreateUpdateProcedureCommand updateCommand = (CreateUpdateProcedureCommand) command;

            if (updateCommand.getResultsCommand() instanceof DynamicCommand) {
                DynamicCommand dynamicCommand = (DynamicCommand) updateCommand.getResultsCommand();

                if (dynamicCommand.isAsClauseSet()) {
                    updateCommand.setProjectedSymbols(projectedSymbols);
                }
            }
        }
    }
}
