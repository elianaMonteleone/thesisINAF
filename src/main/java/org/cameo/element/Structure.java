package org.cameo.element;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.impl.ElementsFactory;



import java.io.IOException;



/**
 * @author Eliana
 */


/**
 * Class for the creation of the graphic elements
 *
 * Class Ticket
 * Profile Ticket
 * Stereotype Ticket
 */
public class Structure {


    private Project project;
    private ElementsFactory f;
    public static Stereotype stereotype;
    public static Property property;
    public static Property propertyAuthor;
    public static Property propertyStartDate;
    public static Property propertyTracker;
    public static Property propertyPriority;
    public static Property propertySubject;


    public Structure() {
        this.project = Application.getInstance().getProject();
        this.f = project.getElementsFactory();
    }

    /**
     * Method to display: Element class, Profile, Stereotype Ticket with an existing Package
     * Important: to create an element, we have to establish a Session, that is a kind of transaction
     * using SessionManager.
     * @param packageTicket
     */

    public void execute(Package packageTicket) throws IOException{
        this.project = Application.getInstance().getProject();
        SessionManager.getInstance().createSession(project, "Create a ticket");
        Class mdClass = f.createClassInstance();
        mdClass.setOwner(packageTicket);
        mdClass.setName("TicketClass");
        Profile profile = f.createProfileInstance();
        profile.setName("My Ticket");
        profile.setOwner(packageTicket);
        this.stereotype = f.createStereotypeInstance();
        this.stereotype.setName("Ticket");
        this.stereotype.setOwner(profile);

        //instantiation properties to the ticket
        this.property = f.createPropertyInstance();
        this.propertyAuthor = f.createPropertyInstance();
        this.propertyStartDate = f.createPropertyInstance();
        this.propertyTracker = f.createPropertyInstance();
        this.propertyPriority = f.createPropertyInstance();
        this.propertySubject = f.createPropertyInstance();


        SessionManager.getInstance().closeSession(project);
    }


    /**
     * Method to display elements of type: Class, Profile, Stereotype Ticket with an existing or new Block Element
     * @param element
     */
    public void executeClass(Class element) {
        this.project = Application.getInstance().getProject();
        SessionManager.getInstance().createSession(project, "Create a ticket");
        Class mdClass = f.createClassInstance();
        mdClass.setOwner(element);
        mdClass.setName("TicketClass");
        this.stereotype = f.createStereotypeInstance();
        this.stereotype.setName("Ticket");
        this.stereotype.setOwner(element);

        //instantiation properties to the ticket
        this.property = f.createPropertyInstance();
        this.propertyAuthor = f.createPropertyInstance();
        this.propertyStartDate = f.createPropertyInstance();
        this.propertyTracker = f.createPropertyInstance();
        this.propertyPriority = f.createPropertyInstance();
        this.propertySubject = f.createPropertyInstance();


        SessionManager.getInstance().closeSession(project);
    }








}
