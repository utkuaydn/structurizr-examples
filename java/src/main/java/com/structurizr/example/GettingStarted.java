package com.structurizr.example;

import com.structurizr.Workspace;
import com.structurizr.export.plantuml.StructurizrPlantUMLExporter;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;

/**
 * A "getting started" example that illustrates how to
 * create a software architecture diagram using code.
 *
 * The live workspace is available to view at https://structurizr.com/share/25441
 */
public class GettingStarted {

    public static void main(String[] args) throws Exception {
        // all software architecture models belong to a workspace
        Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();

        // create a model to describe a user using a software system
        Person user = model.addPerson("User", "A user of my software system.");
        SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
        user.uses(softwareSystem, "Uses");

        // create a system context diagram showing people and software systems
        ViewSet views = workspace.getViews();
        SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        // add some styling to the diagram elements
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        StructurizrPlantUMLExporter exporter = new StructurizrPlantUMLExporter();
        String parsed = exporter.export(contextView).getDefinition();

        SourceStringReader reader = new SourceStringReader(parsed);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        reader.outputImage(os, new FileFormatOption(FileFormat.SVG));
        String svg = os.toString(StandardCharsets.UTF_8);
        os.close();
        Files.write(Paths.get("parsed.svg"), svg.getBytes());
    }
}