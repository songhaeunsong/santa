package site.ssanta.santa.api.mountain.util;

import org.springframework.stereotype.Component;

@Component
public class MountainExpCalculator {

    private static final int EASY_HEIGHT = 300;
    private static final int NORMAL_HEIGHT = 600;
    private static final int HARD_HEIGHT = 900;
    private static final int EXPERT_HEIGHT = 1200;

    private static final int EASY_BASE_EXP = 15;
    private static final int NORMAL_BASE_EXP = 25;
    private static final int HARD_BASE_EXP = 35;
    private static final int EXPERT_BASE_EXP = 45;

    private static final double EASY_BONUS_RATE = 0.1;
    private static final double NORMAL_BONUS_RATE = 0.15;
    private static final double HARD_BONUS_RATE = 0.2;
    private static final double EXPERT_BONUS_RATE = 0.25;

    public int calculateExp(int heightInMeters) {
        if (heightInMeters <= 0) {
            return 0;
        }
        if (heightInMeters > EXPERT_HEIGHT) {
            heightInMeters = EXPERT_HEIGHT;
        }

        if (heightInMeters <= EASY_HEIGHT) {
            return calculateExpForLevel(heightInMeters, EASY_BASE_EXP, EASY_BONUS_RATE);
        } else if (heightInMeters <= NORMAL_HEIGHT) {
            return calculateExpForLevel(heightInMeters, NORMAL_BASE_EXP, NORMAL_BONUS_RATE);
        } else if (heightInMeters <= HARD_HEIGHT) {
            return calculateExpForLevel(heightInMeters, HARD_BASE_EXP, HARD_BONUS_RATE);
        } else {
            return calculateExpForLevel(heightInMeters, EXPERT_BASE_EXP, EXPERT_BONUS_RATE);
        }
    }

    private int calculateExpForLevel(int height, int baseExp, double bonusRate) {
        int heightUnits = (height + 99) / 100;
        double totalExp = baseExp + (heightUnits * bonusRate * baseExp);
        return (int) Math.round(totalExp);
    }
}