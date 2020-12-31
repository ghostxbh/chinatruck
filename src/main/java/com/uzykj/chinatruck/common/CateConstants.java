package com.uzykj.chinatruck.common;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @author ghostxbh
 */
public class CateConstants {
    private static final List<String> FIELDS_LIST = Lists.newArrayList();
    private static final String[] FIELDS_ARR = {
            "FAW", "J5P", "J6",
            "DONGFENG", "Tianlong",
            "SINOTRUK", "Howo",
            "SHACMAN", "F2000", "O LONG",
            "FOTON", "ETX(New)",
            "BEIBEN", "NG80",
            "LIUZHOU", "Cheng Long", "QS3452 XU Dump Truck",
            "HONGYAN", "IVECO", "GENLYON",
            "JAC", "Gallop",
            "CAMC", "CM6D",
            "C&C", "CM6D",
            "DAYUN", "N8E",
            "FAST", "12JS200T", "DC9J150TJ", "9JS119",
            "WEICHAI", "WD 615", "WP 10 ENGINE", "WP 12 ENGINE",

            "Cab", "Cabin", "Engine", "Air Intake Exhaust Mani Fold",
            "Filter", "Valve", "Clutch", "Gearbox", "Power Takeoff(PTO)",
            "Propeller Shaft", "TRANSMISSION AGENT", "Axle", "Brake Device",
            "Steering Device", "Frame", "Suspension Device", "Wheel", "Electric",
            "CNG&LNG Parts", "Parts"
    };

    static {
        FIELDS_LIST.addAll(Arrays.asList(FIELDS_ARR));
    }

    public static List<String> getFieldsList() {
        return FIELDS_LIST;
    }

    public static String[] getFieldsArr() {
        return FIELDS_ARR;
    }

    public static boolean isContain(String field) {
        boolean flag = false;
        String[] fieldsArr = getFieldsArr();
        for (String fields : fieldsArr) {
            if (field.equalsIgnoreCase(fields)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
