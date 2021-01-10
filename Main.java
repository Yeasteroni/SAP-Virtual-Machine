import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


class Main {
    public static void main(String[] args) {
      cpu cpu = new cpu();
      cpu.run("doubles.bin");
    }
}