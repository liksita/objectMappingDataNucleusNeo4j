//package com.haw_hamburg.de.objectMapping.dataNucleus.Neo4j.helper;
//
//
//import java.awt.Color;
//import java.io.File;
//import java.io.IOException;
//import org.gephi.appearance.api.AppearanceController;
//import org.gephi.appearance.api.AppearanceModel;
//import org.gephi.appearance.api.Function;
//import org.gephi.appearance.api.Partition;
//import org.gephi.appearance.api.PartitionFunction;
//import org.gephi.appearance.plugin.PartitionElementColorTransformer;
//import org.gephi.appearance.plugin.palette.Palette;
//import org.gephi.appearance.plugin.palette.PaletteManager;
//import org.gephi.graph.api.Column;
//import org.gephi.graph.api.DirectedGraph;
//import org.gephi.graph.api.GraphController;
//import org.gephi.graph.api.GraphModel;
//import org.gephi.io.exporter.api.ExportController;
//import org.gephi.io.importer.api.Container;
//import org.gephi.io.importer.api.EdgeDirectionDefault;
//import org.gephi.io.importer.api.ImportController;
//import org.gephi.io.processor.plugin.DefaultProcessor;
//import org.gephi.project.api.ProjectController;
//import org.gephi.project.api.Workspace;
//import org.gephi.statistics.plugin.Modularity;
//import org.openide.util.Lookup;
//
///**
// * This demo shows how to get partitions and apply color transformation to them.
// * <p>
// * Partitions are always created from an attribute column, in the data since
// * import or computed from an algorithm. The demo so the following tasks:
// * <ul><li>Import a graph file.</li>
// * <li>Create and export to partition1.pdf the graph colored from the 'source'
// * column.</li>
// * <li>Run modularity algorithm, detecting communities. The algorithm create a
// * new column and label nodes with a community number.</li>
// * <li>Create and export to partition2.pdf the graph colored from the
// * 'modularity_class' column</li></ul>
// * Partitions are built from the <code>PartitionController</code> service. Then
// * the color transformer is created and passed with the partition to the
// * controller to apply colors.
// *
// * @author Mathieu Bastian
// */
//public class PartitionGraph {
//
//    public void script() {
//        //Init a project - and therefore a workspace
//        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
//        pc.newProject();
//        Workspace workspace = pc.getCurrentWorkspace();
//
//        //Get controllers and models
//        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
//        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
//        AppearanceController appearanceController = Lookup.getDefault().lookup(AppearanceController.class);
//        AppearanceModel appearanceModel = appearanceController.getModel();
//
//        //Import file
//        Container container;
//        try {
//            File file = new File(getClass().getResource("/home/diana/Dokumente/userPostsDataNucleusNeo4j.gml").toURI());
//            container = importController.importFile(file);
//            container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return;
//        }
//
//        //Append imported data to GraphAPI
//        importController.process(container, new DefaultProcessor(), workspace);
//
//        //See if graph is well imported
//        DirectedGraph graph = graphModel.getDirectedGraph();
//        System.out.println("Nodes: " + graph.getNodeCount());
//        System.out.println("Edges: " + graph.getEdgeCount());
//
//        //Partition with 'source' column, which is in the data
//        Column column = graphModel.getNodeTable().getColumn("source");
//        Function func = appearanceModel.getNodeFunction(graph, column, PartitionElementColorTransformer.class);
//        Partition partition = ((PartitionFunction) func).getPartition();
//        Palette palette = PaletteManager.getInstance().generatePalette(partition.size());
//        partition.setColors(palette.getColors());
//        appearanceController.transform(func);
//
//        //Export
//        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
//        try {
//            ec.exportFile(new File("partition1.pdf"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return;
//        }
//
//        //Run modularity algorithm - community detection
//        Modularity modularity = new Modularity();
//        modularity.execute(graphModel);
//
//        //Partition with 'modularity_class', just created by Modularity algorithm
//        Column modColumn = graphModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS);
//        Function func2 = appearanceModel.getNodeFunction(graph, modColumn, PartitionElementColorTransformer.class);
//        Partition partition2 = ((PartitionFunction) func2).getPartition();
//        System.out.println(partition2.size() + " partitions found");
//        Palette palette2 = PaletteManager.getInstance().randomPalette(partition2.size());
//        partition2.setColors(palette2.getColors());
//        appearanceController.transform(func2);
//
//        //Export
//        try {
//            ec.exportFile(new File("partition2.pdf"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return;
//        }
//    }
//}
//
