package org.cameo.element;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.ui.browser.actions.gotostereotype.DefaultGoToStereotypeFactory;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.jmi.helpers.CoreHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;
import com.nomagic.uml2.impl.ElementsFactory;


/**
 * @author Eliana
 */
public class Structure {

    private Project project;
    private ElementsFactory factory;
    private ModelElementsManager manager;

    public Structure(){
        this.project = Application.getInstance().getProject();
        this.factory = project.getElementsFactory();
        this.manager = ModelElementsManager.getInstance();
    }

    public void execute(Package parentPackage){
        try {
            SessionManager.getInstance().createSession(project, "Creating ticket stereotype");
            createModelElements(parentPackage);
            SessionManager.getInstance().closeSession(project);
        } catch (Exception e) {
            Application.getInstance().getGUILog().showMessage("Exception occured: " + e.getMessage());
            SessionManager.getInstance().cancelSession(project);
        }
    }


    private void createModelElements(Package parentPackage) throws ReadOnlyElementException {
        var firstClass = createClass(parentPackage, "First class");
        addStereotype(firstClass);


    }
    private Class createClass(Package parentPackage, String name) throws ReadOnlyElementException {

        var mdClass = factory.createClassInstance();
        mdClass.setName(name);
        manager.addElement(mdClass, parentPackage);

        return mdClass;
    }


    private void addStereotype(Class stereotypeClass) throws ReadOnlyElementException {

    var profile = StereotypesHelper.getProfile(project, "MyTicket");
    var stereotype = StereotypesHelper.getStereotype(project, "Ticket", profile);
    StereotypesHelper.addStereotype(stereotypeClass, stereotype);
    }






}
