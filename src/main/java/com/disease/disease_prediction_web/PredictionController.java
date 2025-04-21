package com.disease.disease_prediction_web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.disease.DiseasePrediction;

@Controller
public class PredictionController {

    @PostMapping("/predict")
    public String predictDisease(
            @RequestParam("fever") String fever,  // "yes" or "no"
            @RequestParam("heartRate") double heartRate,
            @RequestParam("glucose") double glucose,
            @RequestParam("bloodPressure") double bloodPressure,
            @RequestParam("skinThickness") double skinThickness,
            @RequestParam("insulin") double insulin,
            @RequestParam("bmi") double bmi,
            @RequestParam("diabetesPedigreeFunction") double dpf,
            @RequestParam("age") double age,
            @RequestParam("cholesterol") double cholesterol,
            @RequestParam("oxygen") double oxygen,
            @RequestParam("respRate") double respRate,
            @RequestParam("symptomScore") double symptomScore,
            @RequestParam("bloodGroup") String bloodGroup,
            Model model) {

        try {
            // Convert fever to int (1 = yes, 0 = no)
            int feverInt = fever.equalsIgnoreCase("yes") ? 1 : 0;

            // Prepare the input values (14 attributes excluding class)
            double[] inputValues = {
                glucose, cholesterol, symptomScore, heartRate,
                bloodPressure, feverInt, dpf, bmi,
                insulin, skinThickness, oxygen, age,
                respRate, // 13th
                // 14th is bloodGroup, handled separately
            };

            DiseasePrediction prediction = new DiseasePrediction();
            String result = prediction.predictWithInput(inputValues, bloodGroup);

            model.addAttribute("prediction", result);
            model.addAttribute("error", "");

        } catch (Exception e) {
            model.addAttribute("prediction", "");
            model.addAttribute("error", "Prediction failed: " + e.getMessage());
        }

        return "result";
    }

    @GetMapping("/predict")
    public String showPredictionForm() {
        return "predict_form";
    }
}
