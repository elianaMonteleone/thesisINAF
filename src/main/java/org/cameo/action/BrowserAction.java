package org.cameo.action;

import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import org.cameo.ui.TicketDialog;
import java.awt.event.ActionEvent;

/**
 * @author Eliana
 */
public class BrowserAction extends DefaultBrowserAction {

    public BrowserAction(String id, String name) {
        super(id, name, null, null);
    }

    public void actionPerformed(ActionEvent actionEvent) {
       var parent = MDDialogParentProvider.getProvider().getDialogParent(true);
        var dialog = new TicketDialog(parent);
        dialog.setVisible(true);
    }
}
