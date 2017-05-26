/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.api.core.jsonrpc.commons.reception;

import org.eclipse.che.api.core.jsonrpc.commons.JsonRpcErrorTransmitter;
import org.eclipse.che.api.core.jsonrpc.commons.RequestHandlerManager;
import org.slf4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Result configurator provide means to configure result type in a
 * response that is to be received. Result types that are supported:
 * {@link String}, {@link Boolean}, {@link Double}, {@link Void} and
 * DTO. This configurator is used when we have defined request params
 * as a single object.
 */
public class ResultConfiguratorFromOne<P> {
    private final static Logger LOGGER = getLogger(ResultConfiguratorFromOne.class);

    private final RequestHandlerManager requestHandlerManager;

    private final String   method;
    private final Class<P> pClass;

    ResultConfiguratorFromOne(RequestHandlerManager requestHandlerManager, String method, Class<P> pClass) {
        this.requestHandlerManager = requestHandlerManager;

        this.method = method;
        this.pClass = pClass;
    }

    public <R> FunctionConfiguratorOneToMany<P, R> resultAsListOfDto(Class<R> rClass) {
        checkNotNull(rClass, "Result class must not be null");

        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result list items class: " + rClass);

        return new FunctionConfiguratorOneToMany<>(requestHandlerManager, method, pClass, rClass);
    }

    public FunctionConfiguratorOneToOne<P, String> resultAsDtoString() {
        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result list items class: " + String.class);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, String.class);
    }

    public FunctionConfiguratorOneToOne<P, Double> resultAsDtoDouble() {
        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result list items class: " + Double.class);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, Double.class);
    }

    public FunctionConfiguratorOneToOne<P, Boolean> resultAsDtoBoolean() {
        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result list items class: " + Boolean.class);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, Boolean.class);
    }

    public <R> FunctionConfiguratorOneToOne<P, R> resultAsDto(Class<R> rClass) {
        checkNotNull(rClass, "Result class must not be null");

        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result object class: " + rClass);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, rClass);
    }

    public FunctionConfiguratorOneToOne<P, Void> resultAsEmpty() {
        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result object class: " + Void.class);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, Void.class);
    }

    public FunctionConfiguratorOneToOne<P, String> resultAsString() {
        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result object class: " + String.class);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, String.class);
    }

    public FunctionConfiguratorOneToOne<P, Double> resultAsDouble() {
        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result object class: " + Double.class);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, Double.class);
    }

    public FunctionConfiguratorOneToOne<P, Boolean> resultAsBoolean() {
        LOGGER.debug("Configuring incoming request result: " +
                     "method: " + method + ", " +
                     "result object class: " + Boolean.class);

        return new FunctionConfiguratorOneToOne<>(requestHandlerManager, method, pClass, Boolean.class);
    }

    public ConsumerConfiguratorOneToNone<P> noResult() {
        LOGGER.debug("Configuring incoming request having no result");

        return new ConsumerConfiguratorOneToNone<>(requestHandlerManager, method, pClass);
    }
}