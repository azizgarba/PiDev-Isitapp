package Pidev_isitapp;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sana
 */




import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
//import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import tn.esprit.codefellaz.services.ReclamationCRUD;
import tn.esprit.codefellaz.utils.MyConnection;


public class BarChar extends ApplicationFrame {


    public BarChar(final String title) throws IOException, SQLException {

        super(title);

        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400,400));
        setContentPane(chartPanel);

    }


   private CategoryDataset createDataset() throws  IOException, SQLException {

                // 0. Création d'un diagramme.
          DefaultCategoryDataset dataset = new DefaultCategoryDataset();
               MyConnection mc = MyConnection.getInstance();

          


         Double d;
         String ch;
//        try {
//            try {
//                Connexion.rs = Connexion.st.executeQuery("select * from marchandise");
//            } catch (SQLException ex) {
//
//            }
//            while (Connexion.rs.next()) {
//                d=Connexion.rs.getDouble(2);
//                ch=Connexion.rs.getString(1);
//                System.out.println(""+d);
//                System.out.println(""+ch);
//              dataset.addValue(d, ch, "");
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("    nndxc,;kx");
//        }

        ReclamationCRUD Rec = new ReclamationCRUD();
   List<Integer> list = Rec.getCountByCategory();
                dataset.addValue(list.get(0), "Freelancer", " ");
                dataset.addValue(list.get(1), "Service", " ");
                dataset.addValue(list.get(2), "Produit", " ");
                //dataset.addValue(19, "Taux de couverture Incar", " ");
        return dataset;

    }

    private JFreeChart createChart(final CategoryDataset dataset) {

        final JFreeChart chart = ChartFactory.createBarChart3D(
            " Categories ",      // chart title
            " ",               // domain axis label
            "  Le nombre de reclamations ",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips
            true                     // urls
        );

        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI /2.0)
        );

//        final CategoryItemRenderer renderer = plot.getRenderer();
//        renderer.setItemLabelsVisible(true);
//        final BarRenderer r = (BarRenderer) renderer;
 
    

        return chart;

    }

   
    public static void main(final String[] args) throws IOException, SQLException {

        final BarChar demo = new BarChar("Statistiques sur les categories des reclamations");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}
