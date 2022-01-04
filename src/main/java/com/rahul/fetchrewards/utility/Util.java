package com.rahul.fetchrewards.utility;

import com.rahul.fetchrewards.models.Payer;
import com.rahul.fetchrewards.repository.PayerRepository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    @Getter
    private static Map<String, Integer> subtractedPoints = new HashMap<>();

    public static void subtractPoints(String payer, int pointsToSubtract) {
        subtractedPoints.computeIfPresent(payer, (k, v) -> v + (-1 * pointsToSubtract));
        subtractedPoints.put(payer, -1 * pointsToSubtract);
    }

    public static List<Payer> getSubtractedPointsForPayers() {
        List<Payer> pointsSpentForPayer = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : subtractedPoints.entrySet()) {
            pointsSpentForPayer.add(new Payer(entry.getKey(), entry.getValue()));
        }

        return pointsSpentForPayer;
    }

    private static Integer getTotalPresentPoints(PayerRepository payerRepository) {
        int total = 0;
        for(Payer payer : payerRepository.findAll()) {
            total += payer.getPoints();
        }
        return total;
    }

    public static boolean validatePoints(Integer points, PayerRepository payerRepository) {
        if(points < 0) return false;
        int totalPoints = getTotalPresentPoints(payerRepository);

        return points <= totalPoints;
    }

    public static Map<String, Integer> getPayerPoints(List<Payer> payerList) {
        Map<String, Integer> result = new HashMap<>();
        for (Payer p : payerList) {
            result.put(p.getPayer(), p.getPoints());
        }

        return result;
    }
}
