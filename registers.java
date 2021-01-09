// import java.io.File;
// import java.util.Scanner;
// import java.io.FileNotFoundException;

// public class registers {
//     private int[] memory;
//     private int[] register;
//     private String[] code;
//     private int rPC;
//     private boolean rCP;
//     private int rST;

//     public registers() {
//         memory = new int[10000];
//         register = new int[10];
//         rPC = 0;
//         rCP = false;
//         rST = 0;
//     }

//     public void setRegister(int index, int value) {
//         register[index] = value;
//     }

//     public int getRegister(int index) {
//         return register[index];
//     }

//     public void setMemory(int index, int value) {
//         memory[index] = value;
//     }

//     public int getMemory(int index) {
//         return memory[index];
//     }

//     public void setProgramCounter(int value) {
//         rPC = value;
//     }

//     public int getProgramCounter() {
//         return rPC;
//     }

//     public void setCompareRegister(boolean value) {
//         rCP = value;
//     }

//     public int getCompareRegister() {
//         return rCP;
//     }

//     // I don't know how the stack works yet


//     // Just writing out the main loop (not final yet)

//     public void main(File program)
//     {
//         Scanner fileScanner = new Scanner(program);

//         int amountOfLines = 0;

//         while (fileScanner.hasNext())
//         {
//             fileScanner.next();
//             amountOfLines++;
//         }

//         // String[] code = new String[amountOfLines];

//         for (int i=0; i<amountOfLines; i++)
//         {
//             code[i] = fileScanner.next();
//         }
//     }
// }
