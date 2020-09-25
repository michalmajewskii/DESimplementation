
package com.company;

import java.awt.MouseInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.File;

import static com.company.Main.*;

public class Generator {

    public static String generate() {

        int HowManyGenerate = 16;
        String keyString = "";
        int []array = new int[16];
        while (HowManyGenerate > 0) {
            int mouseX = 1, mouseY = 1;
            int x = mouseX, y = mouseY;
            int counter = 0;
            int[][] image = new int[65][65];
            boolean flag = true;
            while (flag) {
                mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
                mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();

                if ((mouseX / 30 != x || mouseY / 16 != y) && mouseY <= 1024 && mouseX <= 1920) {
                    if (image[(int) mouseY / 16][(int) mouseX / 30] != 5)
                        counter++;
                    image[(int) mouseY / 16][(int) mouseX / 30] = 5;
                    if (counter > 256) flag = false;
                    x = mouseX / 30;
                    y = mouseY / 16;
                }
            }
            int[][] new64x64 = arrayProcessing(image);
            int[][] new2x4 = generate8BytesIntArray(new64x64);

            int abc = get4BinaryNumber(new2x4);
            System.out.println("binary => " + Integer.toBinaryString(abc) + "   ||  decimal => " + abc);
            array[16-HowManyGenerate] = abc;

            HowManyGenerate--;
        }
        for (int i = 0 ; i < 16 ; i++){
            keyString+=Integer.toHexString(array[i]);
        }
        return keyString;

    }

    static public int[][] generate8BytesIntArray (int [][]new64x64) {
        //tablice 16x16
        // gdy liczba 5 jest nieparzysta to 1 a jak jest albo nie ma nic to 0
        int [][] new2x4 = new int [2][4];
        int xOperationNumber = 0;
        int yOperationNumber = 0;
        int fivesCounter = 0;

        while(yOperationNumber < 4){
            while(xOperationNumber < 2){
                fivesCounter = 0;
                for(int i  = yOperationNumber*16; i < 16*yOperationNumber+16 ; i++){
                    for (int j = 32*xOperationNumber ; j < 32*xOperationNumber+32 ; j++){
                        if(new64x64[j][i] == 1){
                            fivesCounter++;
                        }
                    }
                }
                if(fivesCounter%2 == 1)
                    new2x4[xOperationNumber][yOperationNumber] = 1;
                else
                    new2x4[xOperationNumber][yOperationNumber] = 0;
                xOperationNumber++;
            }
            xOperationNumber = 0;
            yOperationNumber++;
        }
        return new2x4;
    }

    static public int[][] arrayProcessing( int [][]image){
        int[][] new64x64 = new int [64][64];
        for (int[] row : new64x64)
            Arrays.fill(row, 0);
        int newX, newY;
        int N = 64, K=5000;
        for(int i = 0 ; i < 64 ; i++)
            for (int j = 0 ; j < 64; j++){
                if(image[i][j] == 5){
                    newX = (i +j)%N;
                    newY = (int) (j + K * Math.sin((N/2.0)*Math.PI)%N);
                    new64x64[newX][newY] = 1;
                }
            }
        return new64x64;
    }


    static public int get4BinaryNumber(int [][]array) {
        int decimalNumber = 0;
        int binaryNumber = 0b0;
        int [][]a = {{8,2},{4,1}};
        for(int i = 0 ; i < 2 ; i++){
            for(int j = 0 ; j < 2 ; j++){
                if(array[j][i] == 1)
                    decimalNumber += a[j][i];
                binaryNumber = array[j][i];
                binaryNumber = binaryNumber >> 1;
            }
        }
        return decimalNumber;
    }
}
