package org.cameo.element;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.PresentationElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.DiagramTypeConstants;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import java.awt.*;

/**
 * @author Eliana
 */
public class Diagram {

    private Project project;
    private ModelElementsManager manager;
    private PresentationElementsManager presentationElementsManager;

    public Diagram(){
        this.project = Application.getInstance().getProject();
        this.manager = ModelElementsManager.getInstance();
        this.presentationElementsManager = PresentationElementsManager.getInstance();
    }

    public void execute(Package parentPackage){
        SessionManager.getInstance().createSession(project, "Creating diagram and presentation elements for lesson 4");

        try {
            createDiagram(parentPackage);
            SessionManager.getInstance().closeSession(project);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
        }
    }

    private void createDiagram(Package parentPackage) throws ReadOnlyElementException {

        var diagram = manager.createDiagram(DiagramTypeConstants.UML_CLASS_DIAGRAM, parentPackage);
        diagram.setName("My diagram");

        var diagramPresentation = project.getDiagram(diagram);
        diagramPresentation.open();

        createDiagramElements(parentPackage, diagramPresentation);
    }


    private void createDiagramElements(Package parentPackage, DiagramPresentationElement diagramPresentation) throws ReadOnlyElementException {
        var stereotype = Finder.byName().find(parentPackage,
                com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class.class,
                "Ticket");
        var firstClassShape = presentationElementsManager.createShapeElement(stereotype, diagramPresentation);
        presentationElementsManager.reshapeShapeElement(firstClassShape, new Rectangle(50, 50, 200, 300));
    }
}
