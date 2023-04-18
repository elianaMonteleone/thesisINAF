package org.cameo.element;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.ElementsFactory;


/**
 * @author Eliana
 */
public class Structure {

    private Project project;
    private ElementsFactory f;

    public Structure(){
        this.project = Application.getInstance().getProject();
        this.f = project.getElementsFactory();
    }

    public void execute( String description){
        this.project = Application.getInstance().getProject();
        SessionManager.getInstance().createSession(project, "Create a ticket");
        //ElementsFactory f = project.getElementsFactory();
        Package packageTicket = f.createPackageInstance();
        //add created package into a root of the project
        packageTicket.setOwner(project.getPrimaryModel());
        Class mdClass = f.createClassInstance();
        mdClass.setOwner(packageTicket);
        mdClass.setName("TicketClass");
        Profile profile = f.createProfileInstance();
        profile.setName("My Ticket");
        profile.setOwner(packageTicket);
        Stereotype stereotype = f.createStereotypeInstance();
        stereotype.setName("Ticket");
        stereotype.setOwner(profile);
        Property property = f.createPropertyInstance();
        property.setName("Description: ");
        var stringType = (Type) Finder.byQualifiedName()
                .find(project, "UML Standard Profile::UML2 Metamodel::PrimitiveTypes::String");

        property.setType(stringType);
        property.setOwner(stereotype);
        Comment comment = f.createCommentInstance();
        comment.setBody(description);
        comment.setOwner(stereotype);
        // apply changes and add a command into the command history.
        SessionManager.getInstance().closeSession(project);
    }


    private void addStereotype(Class stereotypeClass) throws ReadOnlyElementException {

    var profile = StereotypesHelper.getProfile(project, "MyTicket");
    var stereotype = StereotypesHelper.getStereotype(project, "Ticket", profile);
    StereotypesHelper.addStereotype(stereotypeClass, stereotype);
    }






}
