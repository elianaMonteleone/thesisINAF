package org.cameo.action;

import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.actions.BrowserContextAMConfigurator;
import com.nomagic.magicdraw.actions.MDActionsCategory;
import com.nomagic.magicdraw.ui.browser.Tree;


/**
 * @author Eliana
 */


/**
 * Class for the integration of the ticket action into the browser context menu
 */
public class BrowserConfiguration implements BrowserContextAMConfigurator {

    private final BrowserAction browserAction;

    public BrowserConfiguration(BrowserAction browserAction) {
        this.browserAction = browserAction;
    }


    /**
     * Method should add or remove actions for given browser context menu.
     * @param actionsManager
     * @param tree
     */
    @Override
    public void configure(ActionsManager actionsManager, Tree tree) {
        var category = new MDActionsCategory("", "");
        category.addAction(browserAction);
        actionsManager.addCategory(category);
    }

    @Override
    public int getPriority() {
        return LOW_PRIORITY;
    }
}
