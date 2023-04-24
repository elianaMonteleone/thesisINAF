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


    /**
     * Method to enable the creation of Ticket (class, profile and stereoptype) depending on
     * the node of the tree presents in the Project (package or class)
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
       var parent = MDDialogParentProvider.getProvider().getDialogParent(true);
        var dialog = new TicketDialog(parent);
        var tree = getTree();

        var selectedNode = tree.getSelectedNode();

        if (selectedNode != null) {
            //if the selected node is type of Package, create the Class,Profile,Stereotype into it
            if (selectedNode.getUserObject() instanceof Package) {
                var parentPackage = (Package) selectedNode.getUserObject();
                var structure = new Structure();
                structure.execute(parentPackage);
                dialog.setVisible(true);
            }
            //else the selected node is type of Block, create the Class,Profile,Stereotype into it
            else if(selectedNode.getUserObject() instanceof Class){
                var parentPackage = (Class) selectedNode.getUserObject();
                var structure = new Structure();
                structure.executeClass(parentPackage);
                dialog.setVisible(true);
            }
        }

    }

}
