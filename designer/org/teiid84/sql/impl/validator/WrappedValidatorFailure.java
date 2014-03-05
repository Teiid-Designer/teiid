/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid84.sql.impl.validator;

import org.teiid.designer.validator.IValidator.IValidatorFailure;
import org.teiid.query.validator.ValidatorFailure;

/**
 *
 */
public class WrappedValidatorFailure implements IValidatorFailure {

    private ValidatorFailure failure;

    /**
     * @param failure
     */
    public WrappedValidatorFailure(ValidatorFailure failure) {
        this.failure = failure;
    }

    @Override
    public VFStatus getStatus() {
        return failure.getStatus();
    }
    
}
