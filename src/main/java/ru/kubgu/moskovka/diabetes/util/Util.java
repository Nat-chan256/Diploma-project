package ru.kubgu.moskovka.diabetes.util;

import ru.kubgu.moskovka.diabetes.entity.User;

import java.time.LocalDate;
import java.time.ZoneId;

public class Util {

    public static int calculateAge(User user){
        LocalDate now = LocalDate.now();
        LocalDate userBirthDate = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearsInBetween = now.getYear() - userBirthDate.getYear();
        int monthsDifference = now.getMonthValue() - userBirthDate.getMonthValue();
        int monthsTotal = yearsInBetween * 12 + monthsDifference;
        return monthsTotal / 12;
    }
}
