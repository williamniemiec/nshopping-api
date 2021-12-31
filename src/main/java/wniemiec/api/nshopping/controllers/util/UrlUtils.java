package wniemiec.api.nshop.controllers.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Responsible for providing URL utilities.
 */
public class UrlUtils {

    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    private UrlUtils() {
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public static List<Integer> decodeIntList(String s) {
        List<Integer> list = new ArrayList<>();

        for (String number : s.split(",")) {
            list.add(Integer.parseInt(number));
        }

        return list;
    }

    public static String decodeParam(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
