package com.akiong




import grails.converters.JSON
import java.text.Format
import java.text.SimpleDateFormat
import org.apache.commons.lang.RandomStringUtils

class PasswordGeneratorService {

    static transactional = false

    private def returnCharExceptIL() {
        def temp = RandomStringUtils.randomNumeric(1);
        def error = true
        if (!temp.equals("I") && !temp.equals("i") && !temp.equals("l") && !temp.equals("L")) {
            error = false
        }
        while (error) {
            temp = RandomStringUtils.randomNumeric(1);
            if (!temp.equals("I") && !temp.equals("i") && !temp.equals("l") && !temp.equals("L")) {
                error = false
            }
        }
        return temp
    }

    private def removeIL(def a) {
        def charIL = new ArrayList()
        charIL.add("I")
        charIL.add("i")
        charIL.add("l")
        charIL.add("L")

        charIL.each {
            def index = a.indexOf(it)
            if (index >= 0) {
                a = a.replaceAll(it, returnCharExceptIL())
            }
        }
        return a
    }

    List generateRegistrationCode() {
        def a = new String();
        def b = new String();
        def length = 8;
        List list = new ArrayList();
        a = RandomStringUtils.randomAlphanumeric(8);
        a = removeIL(a)

        list.add(a.substring(0, 4));
        list.add(a.substring(4, 8));
//      b = RandomStringUtils.randomNumeric(8);
        //      list.add(b.substring(0,4));
        //      list.add(b.substring(4,8));
        //      list.add(getExpired(1));
        return list;
    }

    List generateActivationCode() {
        def a = new String();
        a = RandomStringUtils.randomNumeric(6);
        List list = new ArrayList();
        list.add(a.substring(0, 3));
        list.add(a.substring(3, 6));
        list.add(getExpired(1));
        return list;
    }

    List generatePin() {
        List list = new ArrayList();
        list.add(new Random().nextInt(999999).toString());
        list.add(getExpired(0));
        return list;
    }

    String getExpired(int type) {
        Calendar cal = Calendar.getInstance();
        if (type == 1) {
            cal.add(Calendar.DAY_OF_MONTH, 2);
        }
        else {
            cal.add(Calendar.MINUTE, 10);
        }
        Format formatter = new SimpleDateFormat("EEE, d/MM/yy hh:mm");
        def expiredDate = formatter.format(cal.getTime());
        return expiredDate
    }

}
