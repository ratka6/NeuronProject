import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by krzysiek on 23.01.2017.
 */
public class Graph {

    public static void draw(String chartName, List<Double> list) {
        XYSeries series = new XYSeries("XYGraph");
        for(int i = 0; i < list.size(); i++) {
            series.add(i+1, list.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart("Errors", "Epoki", "Bledy", dataset);

        try {
            ChartUtilities.saveChartAsJPEG(new File("chart" + chartName + ".jpg"), chart, 1500, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
