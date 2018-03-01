package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class DisplayGraphics extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        System.out.println(PriceHistoryClient.listPrice.length);
        for (int i = 0 ; i < PriceHistoryClient.listPrice.length ; i++){
            series.getData().add(new XYChart.Data(i, PriceHistoryClient.listPrice[i]));
        }


        Scene scene = new Scene(lineChart, 1000, 1000);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
