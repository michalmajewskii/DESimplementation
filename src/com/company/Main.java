package com.company;
import java.util.Scanner;

public class Main{

    public static void main(String []args){
        System.out.println("Plase choose the operation: \n1 -> encryption \n2 -> decryption ");
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        char operation = '/';
        while(flag ){
            operation = scan.next().charAt(0);
            if (operation == '1' || operation == '2') flag = false;
            else System.out.println("error");
        }
        switch (operation){
            case '1':
                encryption();
                break;
            case '2':
                decryption();
                break;
             }
        }

        public static void encryption(){
            Scanner scan = new Scanner(System.in);
            DES des = new DES();
            System.out.println("Type a message to encryption: ");
            boolean flagE = true;
            String msg ="";
            while(flagE){
                msg = scan.next();
                if(isHexadecimal(msg)) flagE = false;
                else System.out.println("error! Please type hexadecimal number with a length of 16 !");
            }
            System.out.println("Generating... Move the mouse...) ");
            Generator generator= new Generator();
            String key = generator.generate();
            int i;
            String keys[] = des.getKeys(key);
            String encriptedText = des.permutation(des.initialTable, msg); //wywołanie Permutacji Początkowej IP
            for (i = 0; i < 16; i++) { //16 rund
                encriptedText = des.round(encriptedText, keys[i], i);
            }
            encriptedText = encriptedText.substring(8, 16) + encriptedText.substring(0, 8);
            encriptedText = des.permutation(des.inverseInitialTable, encriptedText);
            System.out.println("message-> " + msg);
            System.out.println("key-> " + key);
            System.out.println("encryption result-> " + encriptedText);
        }

        public static void decryption(){
            Scanner scan = new Scanner(System.in);
            DES des = new DES();
            System.out.println("Type a message to decode: ");
            boolean flagD1 = true, flagD2 = true;
            String msg ="";
            String key ="";
            while(flagD1){
                msg = scan.next();
                if(isHexadecimal(msg)) flagD1 = false;
                else System.out.println("error! Please type hexadecimal number with a length of 16 !");
            }
            System.out.println("enter the key: ");
            while(flagD2){
                key = scan.next();
                if(isHexadecimal(key)) flagD2 = false;
                else System.out.println("It is supposed to be a hexadecimal number with a length of 16");
            }
            int index;
            String []keys1 = des.getKeys(key);
            String dec = des.permutation(des.initialTable, msg);
            for (index = 15; index > -1; index--) {
                dec = des.round(dec, keys1[index], 15 - index);
            }
            dec = dec.substring(8, 16)+ dec.substring(0, 8);
            dec = des.permutation(des.inverseInitialTable, dec);
            System.out.println("message-> " + msg);
            System.out.println("key-> " + key);
            System.out.println("decryption result-> " + dec);
        }

        static public boolean isHexadecimal (String s){
        return s.matches("-?[0-9a-fA-F]+") && s.length() == 16;
         }
    }
