import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


class Main {
    public static void main(String[] args) {
        // System.out.println("Hello world!");
        cpu cpu = new cpu();
        cpu.main(new File("code.txt"));
    }
}