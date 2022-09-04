package com.rodin.mavenBasics.logic;

public class SumAllDigitsString {

    public int calculateSumAllDigitsInString(String str) {
        int ans = 0;
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                tmp.append(str.charAt(i));
            } else {
                if (tmp.length() > 0)
                    ans += Integer.parseInt(tmp.toString());
                tmp = new StringBuilder();
            }
        }
        if (tmp.length() > 0)
            ans += Integer.parseInt(tmp.toString());
        return ans;
    }
}
