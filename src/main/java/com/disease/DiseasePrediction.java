package com.disease;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.DenseInstance;
import weka.core.Instance;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiseasePrediction {

    private static final String datasetPath = "D:\\java projects\\disease-prediction-web\\src\\main\\resources\\templates\\disease_prediction.arff";

    public String predictWithInput(double[] inputValues, String bloodGroup) throws Exception {
        // Load dataset
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(datasetPath));
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 1); // Last attribute is the class

        // Train classifier
        Classifier classifier = new J48();
        classifier.buildClassifier(data);

        // Create an instance for prediction (length = total attributes - 1 class)
        Instance instance = new DenseInstance(data.numAttributes());
        instance.setDataset(data);

        // Set numeric attributes
        for (int i = 0; i < inputValues.length; i++) {
            instance.setValue(i, inputValues[i]);
        }

        // Set blood group (last numeric index is 13, blood_group is index 14)
        instance.setValue(data.attribute("blood_group"), bloodGroup);

        // Predict
        double prediction = classifier.classifyInstance(instance);
        String predictionResult = data.classAttribute().value((int) prediction);

        // Save to log file
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (FileWriter fw = new FileWriter("prediction_results.txt", true)) {
            fw.write("=== Prediction at " + timestamp + " ===\n");
            for (int i = 0; i < inputValues.length; i++) {
                fw.write("Attr" + (i + 1) + ": " + inputValues[i] + "\n");
            }
            fw.write("Blood Group: " + bloodGroup + "\n");
            fw.write("Prediction: " + predictionResult + "\n\n");
        }

        // Return result as a string
        return predictionResult;
    }
}
