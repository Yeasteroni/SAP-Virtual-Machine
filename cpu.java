import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class cpu {
    private int[] memory;
    private int[] register;
    private String[] code;
    private int rPC;
    private boolean rCP;
    private int rST;

    public cpu() {
        // will find out about this.
        this.memory = new int[10000];
        this.register = new int[10];
        this.rPC = 0;
        this.rCP = false;
        this.rST = 0;
    }

    public void halt() {// Halts the program
        System.exit(0);
    }

    public void clrr(int r1) {// Clears register r1
        register[r1] = 0;
    }

    public void clrx(int r1) {// Clears memory location specified by register r1
        memory[register[r1]] = 0;
    }

    public void clrm(int label) {// Clears memory location specified by label
        memory[label] = 0;
    }

    public void clrb(int r1, int r2) {// Clears number of memory locations specified by register r2, starting from memory location specified by r1
        for (int i = 0; i < r2; i++) {
            memory[register[r1] + i] = 0;// The worksheet doesn't specify whether r1 is an int or a memory location
        }
    }

    public void movir(int number, int r1) {// Move number to register r1
        register[r1] = number;
    }

    public void movrr(int r1, int r2) {// Copy the contents of register r1 to register r2
        register[r2] = register[r1];
    }

    public void movrm(int r1, int label) {// Move contents of register r1 to memory location label
        memory[label] = register[r1];
    }

    public void movmr(int label1, int r1) {// Move contents of memory location label1 to register r1
        register[r1] = memory[label1];
    }

    public void movxr(int r1, int r2) {// This is probably wrong, the table is unclear. Move contents of memory location specified by r1 to r2
        register[r2] = memory[register[r1]];
    }

    public void addrr(int r1, int r2) {// Add contents of register r1 to register r2
        register[r2] += register[r1];
    }

    public void printi(int r1) {// Print contents of register r1 to the console
        System.out.println(register[r1]);
    }

    

    public void main(File program)
    {

        // file stuff is being weird will figure out

        try
        {
            Scanner fileScanner = new Scanner(program);

            int amountOfLines = 0;

            while (fileScanner.hasNext())
            {
                fileScanner.next();
                amountOfLines++;
            }

            code = new String[amountOfLines];

            for (int i=0; i<amountOfLines; i++)
                if (fileScanner.hasNext())
                {
                    code[i] = fileScanner.next().toString(); 
                    System.out.println(fileScanner.next());
                }


            // System.out.println("code: " + code.toString());

            // for (String elem : code)
            //     System.out.println(elem + "\n");

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Exception: " + e);
        }
    }
}