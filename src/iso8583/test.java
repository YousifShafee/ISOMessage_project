/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iso8583;

import java.util.ArrayList;

/**
 *
 * @author Omar
 */
public class test {
    public static void main(String [] args){
        System.out.println(hexToBin("3531"));
        ArrayList<Integer> s = new ArrayList();
        s= getExistingElementNo("3531");
        System.out.println(s.toString());
        FieldType x = new FieldType();
    }
    private static String hexToBin(String hex){
    String bin = "";
    String binFragment = "";
    int iHex;
    hex = hex.trim();
    hex = hex.replaceFirst("0x", "");

    for(int i = 0; i < hex.length(); i++){
        iHex = Integer.parseInt(""+hex.charAt(i),16);
        binFragment = Integer.toBinaryString(iHex);

        while(binFragment.length() < 4){
            binFragment = "0" + binFragment;
        }
        bin += binFragment;
    }
    return bin;
}
    public static ArrayList<Integer> getExistingElementNo(String bitMap)
    { 
        ArrayList<Integer> res = new ArrayList();
        int counter=0; 
        String bin = hexToBin(bitMap);
        for(int i=0;i<bin.length();i++ ){
            if(bin.charAt(i)=='1'){
                res.add(counter, i+1);
                counter ++;
                }
        }  
        return res;
    }
}
