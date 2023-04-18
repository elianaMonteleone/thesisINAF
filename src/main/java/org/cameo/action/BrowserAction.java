package org.cameo.action;

import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import org.cameo.element.Structure;
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
        var tree = getTree();

        var selectedNode = tree.getSelectedNode();

        if (selectedNode != null) {
            if (selectedNode.getUserObject() instanceof Package) {
                var parentPackage = (Package) selectedNode.getUserObject();
                var structure = new Structure();
                structure.execute(parentPackage);
                dialog.setVisible(true);
            }
            else if(selectedNode.getUserObject() instanceof Class){
                var parentPackage = (Class) selectedNode.getUserObject();
                var structure = new Structure();
                structure.executeClass(parentPackage);
                dialog.setVisible(true);
            }
        }

    }

}
