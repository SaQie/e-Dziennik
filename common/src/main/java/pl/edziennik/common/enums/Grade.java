package pl.edziennik.common.enums;

import jakarta.persistence.EntityNotFoundException;

public enum Grade {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    public final int grade;

    Grade(int grade) {
        this.grade = grade;
    }

    public static Grade getByGrade(int grade) {
        for (Grade ratingConst : Grade.values()) {
            if (ratingConst.grade == grade) {
                return ratingConst;
            }
        }
        throw new EntityNotFoundException("Grade " + grade + " not found");
    }
}
