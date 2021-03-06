/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.plugin.gdb.ide;

import static org.eclipse.che.ide.api.notification.StatusNotification.DisplayMode.FLOAT_MODE;
import static org.eclipse.che.ide.api.notification.StatusNotification.Status.FAIL;
import static org.eclipse.che.plugin.gdb.ide.GdbDebugger.ConnectionProperties.HOST;
import static org.eclipse.che.plugin.gdb.ide.GdbDebugger.ConnectionProperties.PORT;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import java.util.Map;
import org.eclipse.che.api.core.jsonrpc.commons.RequestHandlerConfigurator;
import org.eclipse.che.api.core.jsonrpc.commons.RequestHandlerManager;
import org.eclipse.che.api.core.jsonrpc.commons.RequestTransmitter;
import org.eclipse.che.api.debug.shared.model.Breakpoint;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.debug.BreakpointManager;
import org.eclipse.che.ide.api.debug.DebuggerServiceClient;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.debug.DebuggerDescriptor;
import org.eclipse.che.ide.debug.DebuggerManager;
import org.eclipse.che.ide.dto.DtoFactory;
import org.eclipse.che.ide.util.storage.LocalStorageProvider;
import org.eclipse.che.plugin.debugger.ide.debug.AbstractDebugger;
import org.eclipse.che.plugin.debugger.ide.debug.DebuggerLocationHandlerManager;

/**
 * The GDB debugger client.
 *
 * @author Anatoliy Bazko
 */
public class GdbDebugger extends AbstractDebugger {

  public static final String ID = "gdb";

  private GdbLocalizationConstant locale;
  private final AppContext appContext;

  @Inject
  public GdbDebugger(
      DebuggerServiceClient service,
      RequestTransmitter transmitter,
      RequestHandlerConfigurator configurator,
      GdbLocalizationConstant locale,
      DtoFactory dtoFactory,
      LocalStorageProvider localStorageProvider,
      EventBus eventBus,
      DebuggerManager debuggerManager,
      NotificationManager notificationManager,
      BreakpointManager breakpointManager,
      AppContext appContext,
      RequestHandlerManager requestHandlerManager,
      DebuggerLocationHandlerManager debuggerLocationHandlerManager) {

    super(
        service,
        transmitter,
        configurator,
        dtoFactory,
        localStorageProvider,
        eventBus,
        debuggerManager,
        notificationManager,
        breakpointManager,
        requestHandlerManager,
        debuggerLocationHandlerManager,
        ID);
    this.locale = locale;
    this.appContext = appContext;
  }

  @Override
  public void addBreakpoint(final Breakpoint breakpoint) {
    if (isConnected() && !isSuspended()) {
      notificationManager.notify(locale.messageSuspendToActivateBreakpoints(), FAIL, FLOAT_MODE);
    }

    super.addBreakpoint(breakpoint);
  }

  @Override
  protected DebuggerDescriptor toDescriptor(Map<String, String> connectionProperties) {
    String host = connectionProperties.get(HOST.toString());
    String port = connectionProperties.get(PORT.toString());
    String address = host + (port.isEmpty() || port.equals("0") ? "" : (":" + port));
    return new DebuggerDescriptor("", address);
  }

  public enum ConnectionProperties {
    HOST,
    PORT,
    BINARY,
    SOURCES
  }
}
