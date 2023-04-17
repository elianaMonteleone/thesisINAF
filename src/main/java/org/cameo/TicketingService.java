package org.cameo;

import org.cameo.action.BrowserAction;
import org.cameo.action.BrowserConfiguration;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.plugins.Plugin;

public class TicketingService extends Plugin {
    @Override
    public void init() {
        var action = new BrowserAction("TicketingService", "Ticket");
        var browserConfiguration = new BrowserConfiguration(action);
        ActionsConfiguratorsManager.getInstance().addContainmentBrowserContextConfigurator(browserConfiguration);

    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public boolean isSupported() {
        return true;
    }
}
