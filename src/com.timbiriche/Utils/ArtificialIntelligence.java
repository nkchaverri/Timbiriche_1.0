package com.timbiriche.Utils;

import com.timbiriche.controllers.BoxController;
import com.timbiriche.models.Box;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class ArtificialIntelligence
{
    public static int aiEasyGetBoxId(Box[][] matrix) {
        return getRandomElementFromArray(getBoxesWithEmptyLines(matrix, 3, 0));
    }

    public static char aiEasyGetLineChar(Box box) {
        return getRandomElementFromArray(getEmptyLinesFromBox(box));
    }

    public static int aiIntermediateGetBoxId(Box[][] matrix) {
        int boxId = 0;

        int[] boxesWithOneOrZeroMarkedLines = getBoxesWithEmptyLines(matrix, 1, 0);
        System.out.println( "boxesWithOneOrZeroMarkedLines: " + Arrays.toString( boxesWithOneOrZeroMarkedLines ) );
        shuffleArray(boxesWithOneOrZeroMarkedLines);
        System.out.println( "boxesWithOneOrZeroMarkedLines after shuffle: " + Arrays.toString( boxesWithOneOrZeroMarkedLines ) );

        for (int index = 0; index < boxesWithOneOrZeroMarkedLines.length; index++) {
            Box currentBox = BoxController.searchBoxById(boxesWithOneOrZeroMarkedLines[index]);
            if (canBoxHandleIntelligentMove(currentBox)) {
                boxId = currentBox.getBoxId();
                break;
            }
        }
        System.out.println("boxId after canBoxHandleIntelligentMove: " + boxId );
        if (boxId == 0) {
            boxId = getRandomElementFromArray(getBoxesWithEmptyLines(matrix, 3, 0));
        }

        System.out.println("final boxId: " + boxId);

        return boxId;
    }

    public static char aiIntermediateGetLineChar(Box box) {
        char line = getPossibleLine(box);

        if (line == ' ') {
            line = getRandomElementFromArray(getEmptyLinesFromBox(box));
        }

        return line;
    }
    public static char getPossibleLine(Box box) {
        char possibleLine = ' ';
        int nearBoxId;
        char[] emptyLines = getEmptyLinesFromBox(box);
        shuffleArray(emptyLines);

        for (int index = 0; index < emptyLines.length; index++) {
            Box nearBox = null;
            switch (emptyLines[index]) {
                case 'U':
                    nearBoxId = BoxController.searchUpBox(box.getRowPosition(), box.getColPosition());
                    if ( nearBoxId == -1 ){
                        possibleLine = emptyLines[index];
                    }else{
                        nearBox = BoxController.searchBoxById(nearBoxId);
                    }
                    break;
                case 'D':
                    nearBoxId = BoxController.searchDownBox(box.getRowPosition(), box.getColPosition());
                    if ( nearBoxId == -1 ){
                        possibleLine = emptyLines[index];
                    }else{
                        nearBox = BoxController.searchBoxById(nearBoxId);
                    }
                    break;
                case 'L':
                    nearBoxId = BoxController.searchLeftBox(box.getRowPosition(), box.getColPosition());
                    if ( nearBoxId == -1 ){
                        possibleLine = emptyLines[index];
                    }else{
                        nearBox = BoxController.searchBoxById(nearBoxId);
                    }
                    break;
                case 'R':
                    nearBoxId = BoxController.searchRightBox(box.getRowPosition(), box.getColPosition());
                    if ( nearBoxId == -1 ){
                        possibleLine = emptyLines[index];
                    }else{
                        nearBox = BoxController.searchBoxById(nearBoxId);
                    }
                    break;
            }

            if ( possibleLine != ' ' ){
                return possibleLine;
            }

            if (nearBox != null) {
                if (nearBox.getMarkedPositions() <= 1) {
                    possibleLine = emptyLines[index];
                    break;
                }
            }
        }

        return possibleLine;
    }


    private static boolean canBoxHandleIntelligentMove(Box box) {
        return getPossibleLine(box) != ' ';
    }

    public static int[] getBoxesWithEmptyLines(Box[][] matrix, int maxMarkedLines, int exactMarkedLines) {
        int[] idOfBoxesWithEmptyLinesDirty = new int[matrix.length*matrix[0].length];

        for (int row = 0, total = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++, total++) {
//                System.out.print(matrix[row][column].getBoxId()+" ");
                if (exactMarkedLines > 0 && matrix[row][column].getMarkedPositions() == exactMarkedLines) {
                    idOfBoxesWithEmptyLinesDirty[total] = matrix[row][column].getBoxId();
                } else if (exactMarkedLines == 0 && matrix[row][column].getMarkedPositions() <= maxMarkedLines) {
                    idOfBoxesWithEmptyLinesDirty[total] = matrix[row][column].getBoxId();
                }
            }
//            System.out.println();
        }

        int zeroCount = 0;
        for (int i = 0; i < idOfBoxesWithEmptyLinesDirty.length; i++) {
            if (idOfBoxesWithEmptyLinesDirty[i] == 0) {
                zeroCount++;
            }
        }

        int[] idOfBoxesWithEmptyLinesClean = new int[idOfBoxesWithEmptyLinesDirty.length-zeroCount];
        for (int i = 0, indexForClean = 0; i < idOfBoxesWithEmptyLinesDirty.length; i++) {
            int boxId = idOfBoxesWithEmptyLinesDirty[i];
            if (boxId != 0) {
                idOfBoxesWithEmptyLinesClean[indexForClean] = boxId;
                indexForClean++;
            }
        }

        return idOfBoxesWithEmptyLinesClean;
    }

    public static char[] getEmptyLinesFromBox( Box box) {
        char[] allLinesFromBox = BoxController.getAvailablePositions(box);

        int emptyCount = 0;
        for (int i = 0; i < allLinesFromBox.length; i++) {
            if (allLinesFromBox[i] == ' ') {
                emptyCount++;
            }
        }

        char[] notMarkedLines = new char[allLinesFromBox.length-emptyCount];
        for (int i = 0, indexForNotMarkedLines = 0; i < allLinesFromBox.length; i++) {
            char line = allLinesFromBox[i];
            if (line != ' ') {
                notMarkedLines[indexForNotMarkedLines] = line;
                indexForNotMarkedLines++;
            }
        }

        return notMarkedLines;
    }

    public static void printArray(int[] array) {
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
    }

    public static int getRandomElementFromArray(int[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    public static char getRandomElementFromArray(char[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffleArray(char[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
}
