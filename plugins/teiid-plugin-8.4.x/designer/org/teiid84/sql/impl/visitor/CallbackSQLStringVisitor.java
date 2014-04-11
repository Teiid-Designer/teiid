/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid84.sql.impl.visitor;

import org.teiid.designer.query.sql.ISQLStringVisitorCallback;
import org.teiid.query.sql.LanguageObject;
import org.teiid.query.sql.lang.Criteria;
import org.teiid.query.sql.visitor.SQLStringVisitor;

/**
 *
 */
public class CallbackSQLStringVisitor extends SQLStringVisitor {

    private final ISQLStringVisitorCallback callback;

    /**
     * @param callback 
     */
    public CallbackSQLStringVisitor(ISQLStringVisitorCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void visitNode(LanguageObject languageObject) {
        callback.visitNode(languageObject);
    }

    @Override
    protected void addTabs(int level) {
        callback.addTabs(level);
    }

    @Override
    protected void visitCriteria(String keyWord, Criteria criteria) {
        callback.visitCriteria(keyWord, criteria);
    }

    @Override
    protected void append(Object value) {
        callback.append(value);
    }

    @Override
    protected void beginClause(int level) {
        callback.beginClause(level);
    }
}
