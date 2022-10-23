package Safer;

import Safer.Key;
import Safer.Operations;

import java.util.Random;

public class SAFER {

    int[][] text = new int[4][16];
    int[][] editedText;
    int[][] keySet;
    int[][] matrix;
    int[][] matrix2;

    public SAFER() {
        Key key = new Key();
        //keySet = key.getKeySet();
    }


    public int[][] codeText(int[][] text) {
        Key key = new Key();
        keySet = key.getKeySet(text.length * 2);
        this.text = text;
        createMatrix();
        editedText = text;
        for (int i = 0; i < 8; i++) {
            overlayKey();
            nonLinearTransformation();
            secondOverlayKey();
            linearTransformation(matrix);
        }
        return editedText;
    }


    public int[][] decodeText(int[][] text){
        this.text = text;
        editedText = text;
        createMatrix();
        for (int i = 0; i < 8; i++) {
            linearTransformation(matrix2);
            secondOverlayKeyDecode();
            nonLinearTransformationDecode();
            overlayKeyDecode();
        }
        return editedText;
    }

    //for test - delete this
    public void testMethod(){
        editedText = text;
        testText();
        createMatrix();
        System.out.println("text:");
        textToString(text);

        nonLinearTransformation();

        System.out.println("Matrix conversation:");
        textToString(text);

        nonLinearTransformationDecode();

        System.out.println("Matrix1 conversation:");
        textToString(text);
    }

    public void testMethod2(){
        testText();
        System.out.println("before");
        textToString(text);
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                text[j][i] = Operations.binPow(45, text[j][i], 257);
            }
        }

        System.out.println("before");
        textToString(text);

        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                text[j][i] = Operations.binLog(text[j][i]);
            }
        }

        System.out.println("after");
        textToString(text);

    }

    public void testText() {
        Random random = new Random();
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[j].length; i++) {
                text[j][i] = random.nextInt(256);
            }
        }
    }

    //coding operations
    public void overlayKey() {
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                if (thirstType(i)) {
                    editedText[j][i] = Operations.sumMod2(editedText[j][i], keySet[2 * j][i]);
                } else {
                    editedText[j][i] = Operations.sum(editedText[j][i], keySet[2 * j][i]);
                }
            }
        }
    }

    public void nonLinearTransformation() {
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                if (thirstType(i)) {
                    editedText[j][i] = Operations.binPow(45 ,editedText[j][i], 257) % 256;
                } else {
                    editedText[j][i] = Operations.binLog(editedText[j][i]) % 256;
                }
            }
        }
    }

    public void secondOverlayKey() {
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                if (!thirstType(i)) {
                    editedText[j][i] = Operations.sumMod2(editedText[j][i], keySet[2 * j + 1][i]);
                } else {
                    editedText[j][i] = Operations.sum(editedText[j][i], keySet[2 * j + 1][i]);
                }
            }
        }
    }

    public void linearTransformation(int[][] matrix) {
        for (int blockNum = 0; blockNum < editedText.length; blockNum++) {
            int[] result = new int[16];
            for (int i = 0; i < editedText[0].length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    result[i] = (result[i] + Operations.multiply(editedText[blockNum][j], matrix[j][i])) % 256;
                }
            }
            editedText[blockNum] = result;
        }
    }

    //decoding operations
    public void overlayKeyDecode(){
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                if (thirstType(i)) {
                    editedText[j][i] = Operations.sumMod2(editedText[j][i], keySet[2 * j][i]);
                } else {
                    editedText[j][i] = Operations.subtraction(editedText[j][i], keySet[2 * j][i]);
                }
            }
        }
    }

    public void nonLinearTransformationDecode() {
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                if (!thirstType(i)) {
                    editedText[j][i] = Operations.binPow(45, editedText[j][i], 257) % 256;
                } else {
                    editedText[j][i] = Operations.binLog(editedText[j][i]) % 256;
                }
            }
        }
    }

    public void secondOverlayKeyDecode() {
        for (int j = 0; j < text.length; j++) {
            for (int i = 0; i < text[0].length; i++) {
                if (!thirstType(i)) {
                    editedText[j][i] = Operations.sumMod2(editedText[j][i], keySet[2 * j + 1][i]);
                } else {
                    editedText[j][i] = Operations.subtraction(editedText[j][i], keySet[2 * j + 1][i]);
                }
            }
        }
    }

    // 1 4 5 8 9 12 13 16
    public boolean thirstType(int number) {
        number++;
        if (number == 1 || number == 4 || number == 5 || number == 8 || number == 9 || number == 12 || number == 13 || number == 16) {
            return true;
        }
        return false;
    }

    public void createMatrix() {
        matrix = new int[][]{
                {2, 2, 1, 1, 16, 8, 2, 1, 4, 2, 4, 2, 1, 1, 4, 4},
                {1, 1, 1, 1, 8, 4, 2, 1, 2, 1, 4, 2, 1, 1, 2, 2},
                {1, 1, 4, 4, 2, 1, 4, 2, 4, 2, 16, 8, 2, 2, 1, 1},
                {1, 1, 2, 2, 2, 1, 2, 1, 4, 2, 8, 4, 1, 1, 1, 1},
                {4, 4, 2, 1, 4, 2, 4, 2, 16, 8, 1, 1, 1, 1, 2, 2},
                {2, 2, 2, 1, 2, 1, 4, 2, 8, 4, 1, 1, 1, 1, 1, 1},
                {1, 1, 4, 2, 4, 2, 16, 8, 2, 1, 2, 2, 4, 4, 1, 1},
                {1, 1, 2, 1, 4, 2, 8, 4, 2, 1, 1, 1, 2, 2, 1, 1},
                {2, 1, 16, 8, 1, 1, 2, 2, 1, 1, 4, 4, 4, 2, 4, 2},
                {2, 1, 8, 4, 1, 1, 1, 1, 1, 1, 2, 2, 4, 2, 2, 1},
                {4, 2, 4, 2, 4, 4, 1, 1, 2, 2, 1, 1, 16, 8, 2, 1},
                {2, 1, 4, 2, 2, 2, 1, 1, 1, 1, 1, 1, 8, 4, 2, 1},
                {4, 2, 2, 2, 1, 1, 4, 4, 1, 1, 4, 2, 2, 1, 16, 8},
                {4, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2, 1, 2, 1, 8, 4},
                {16, 8, 1, 1, 2, 2, 1, 1, 4, 4, 2, 1, 4, 2, 4, 2},
                {8, 4, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 2, 1, 4, 2}};

        matrix2 = new int[][]{
                {2, -2, 1, -2, 1, -1, 4, -8, 2, -4, 1, -1, 1, -2, 1, -1},
                {-4, 4, -2, 4, -2, 2, -8, 16, -2, 4, -1, 1, -1, 2, -1, 1},
                {1, -2, 1, -1, 2, -4, 1, -1, 1, -1, 1, -2, 2, -2, 4, -8},
                {-2, 4, -2, 2, -2, 4, -1, 1, -1, 1, -1, 2, -4, 4, -8, 16},
                {1, -1, 2, -4, 1, -1, 1, -2, 1, -2, 1, -1, 4, -8, 2, -2},
                {-1, 1, -2, 4, -1, 1, -1, 2, -2, 4, -2, 2, -8, 16, -4, 4},
                {2, -4, 1, -1, 1, -2, 1, -1, 2, -2, 4, -8, 1, -1, 1, -2},
                {-2, 4, -1, 1, -1, 2, -1, 1, -4, 4, -8, 16, -2, 2, -2, 4},
                {1, -1, 1, -2, 1, -1, 2, -4, 4, -8, 2, -2, 1, -2, 1, -1},
                {-1, 1, -1, 2, -1, 1, -2, 4, -8, 16, -4, 4, -2, 4, -2, 2},
                {1, -2, 1, -1, 4, -8, 2, -2, 1, -1, 1, -2, 1, -1, 2, -4},
                {-1, 2, -1, 1, -8, 16, -4, 4, -2, 2, -2, 4, -1, 1, -2, 4},
                {4, -8, 2, -2, 1, -2, 1, -1, 1, -2, 1, -1, 2, -4, 1, -1},
                {-8, 16, -4, 4, -2, 4, -2, 2, -1, 2, -1, 1, -2, 4, -1, 1},
                {1, -1, 4, -8, 2, -2, 1, -2, 1, -1, 2, -4, 1, -1, 1, -2},
                {-2, 2, -8, 16, -4, 4, -2, 4, -1, 1, -2, 4, -1, 1, -1, 2}
        };

    }

    public void textToString(int[][] text) {
        for (int i = 0; i < text.length; i++) {
            for (int j = 0; j < text[i].length; j++) {
                System.out.print(text[i][j] + "; ");
            }
            System.out.println();
        }
    }

}
