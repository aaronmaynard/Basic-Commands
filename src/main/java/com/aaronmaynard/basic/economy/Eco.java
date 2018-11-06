package com.aaronmaynard.basic.economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aaronmaynard.basic.Basic;
import com.aaronmaynard.basic.User;

public class Eco {
    public Eco() {
    }

    private static final Logger logger = Logger.getLogger("Basic");
    public static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    private static User getUserByName(String name) {
        if (name == null)
            throw new RuntimeException("Economy username cannot be null");

        return User.getByName(name);
    }

    @Deprecated
    public static double getMoney(String name) throws Exception {
        return getMoneyExact(name).doubleValue();
    }

    public static BigDecimal getMoneyExact(String name) throws Exception {
        User user = getUserByName(name);
        if (user == null) {
            throw new Exception("User does not exist: " + name);
        }
        return user.getMoney();
    }

    public static void setMoney(String name, BigDecimal balance) throws Exception {
        User user = getUserByName(name);
        if (user == null) {
            throw new Exception(name);
        }
        if (balance.compareTo(new BigDecimal(0)) < 0) {
            throw new Exception();
        }
        if (balance.signum() < 0
                && !(user.isAuthorized("basic.eco.loan"))) {
            throw new Exception();
        }
        try {
            user.setMoney(balance);
        } catch (Exception ex) {
            
        }
    }

    public static void add(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).add(amount, MATH_CONTEXT);
        setMoney(name, result);
    }

    public static void substract(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).subtract(amount, MATH_CONTEXT);
        setMoney(name, result);
    }

    public static void divide(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).divide(amount, MATH_CONTEXT);
        setMoney(name, result);
    }

    public static void multiply(String name, BigDecimal amount) throws Exception {
        BigDecimal result = getMoneyExact(name).multiply(amount, MATH_CONTEXT);
        setMoney(name, result);
    }

    public static void resetBalance(String name) throws Exception {
        if (Basic.get() == null) {
            throw new RuntimeException("Basic Eco is called before Basic is loaded.");
        }
        Number number = 100;
        BigDecimal big = new BigDecimal(number.toString());
        setMoney(name, big);
    }

    public static boolean hasEnough(String name, BigDecimal amount) throws Exception {
        return amount.compareTo(getMoneyExact(name)) <= 0;
    }

    public static boolean hasMore(String name, BigDecimal amount) throws Exception {
        return amount.compareTo(getMoneyExact(name)) < 0;
    }

    public static boolean hasLess(String name, BigDecimal amount) throws Exception {
        return amount.compareTo(getMoneyExact(name)) > 0;
    }

    public static boolean isNegative(String name) throws Exception {
        return getMoneyExact(name).signum() < 0;
    }

    public static String format(BigDecimal amount) {
        return "$" + amount;
    }

    public static boolean playerExists(String name) {
        return getUserByName(name) != null;
    }
}