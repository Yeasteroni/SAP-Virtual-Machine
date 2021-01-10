import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class cpu {
  private int[] memory;
  private int[] register;
  private int rPC;// Program Counter, stores memory location you are executing from.
  private int rCP;// If this is set to 0, then the compare was equal. If it is 1 then arg(1) < arg(2). If it is 2 then arg(1) > arg(2).
  private int rST;

  public cpu() {
    this.memory = new int[10000];
    this.register = new int[10];
    this.rPC = 0;
    this.rCP = 0;
    this.rST = 0;
  }

  // These are the individual opcodes

  // 0
  private void halt() {// Halts the program
    System.exit(0);
  }

  // 1
  private void clrr(int r1) {// Clears register r1
    if (isRegisterInBounds(r1) == true){
      register[r1] = 0;
      rPC += 2;
    }else{
      genericError("clrr", 1, r1 + " is not a valid register ID.");
    }
  }

  // 2
  private void clrx(int r1) {// Clears memory location specified by register r1
    if (isRegisterInBounds(r1) == true){
      if (isMemoryLocationInBounds(register[r1]) == true){
        memory[register[r1]] = 0;
        rPC += 2;
      }else{
        genericError("clrx", 1, " ");
      }
    }else{
      genericError("clrx", 1, r1 + " is not a valid register ID.");
    }
  }

  // 3
  private void clrm(int label) {// Clears memory location specified by label
    memory[label] = 0;
    rPC += 2;
  }

  // 4
  private void clrb(int r1, int r2) {// Clears number of memory locations specified by register r2, starting from the memory location specified by r1
    for (int i = 0; i < r2; i++) {
      memory[register[r1] + i] = 0;
    }
    rPC += 3;
  }

  // 5
  private void movir(int number, int r1) {// Move number to register r1
    register[r1] = number;
    rPC += 3;
  } 

  // 6
  private void movrr(int r1, int r2) {// Copy the contents of register r1 to register r2
    register[r2] = register[r1];
    rPC += 3;
  }

  // 7
  private void movrm(int r1, int label) {// Move contents of register r1 to memory location label
    memory[label] = register[r1];
    rPC += 3;
  }

  // 8
  private void movmr(int label1, int r1) {// Move contents of memory location label1 to register r1
    register[r1] = memory[label1];
    rPC += 3;
  }

  // 9
  private void movxr(int r1, int r2) {// This is probably wrong, the table is unclear. Move contents of memory location specified by r1 to r2
    register[r2] = memory[register[r1]];
    rPC += 3;
  }

  // 10
  private void movar(int label, int r1){// The table is unclear.
  }

  //11
  private void movb(int r1, int r2, int r3){// Move the block of memory locations, with initial memory location specified by the contents of register r1 and length specified by the contents of register r2, to the memory location specified by the contents of register r3 and however many memory locations specified afterwards by the contents of register r2.
    for (int i = 0, i < register[r3]){
      memory[register[r2 + i] = memory[register[r1 + i]];
    }
  }

  // 12
  private void addir(int constant, int r1){// Add the integer constant to the contents of register r1.
    register[r1] += constant;
    rPC += 3;
  }

  // 13
  private void addrr(int r1, int r2) {// Add contents of register r1 to register r2
    register[r2] += register[r1];
    rPC += 3;
  }

  // 34
  private void cmprr(int r1, int r2){// Compare the contents of register r1 with the contents of register r2.
    if (register[r1] == register[r2]){// If they are equal, then set rCP (compare register) to 0
      rCP = 0;
    }else if (register[r1] < register[r2]){// If the contents of register r1 is less than the contents of register r2, then set rCP (compare register) to 1.
      rCP = 1;
    }else{// If the contents of register r1 is more then the contents of register r2, then set rCP (compare register) to 2.
      rCP = 2;
    }
    rPC += 3;
  }

  // 45
  private void outcr(int r1){// Convert the integer stored in register r1 into an ASCII character and print it to the console.
    if (isRegisterInBounds(r1) == true){
      if (register[r1] < 128 && register[r1] >= 0){// If the contents of register r1 is a valid ASCII character, then convert it to that ASCII character and print it.
      System.out.print(String.valueOf( (char) (register[r1]) ));
      }else{// If the contents of register r1 is not a valid ASCII character, throw an appropriate error and terminate the program.
      genericError("outcr", 1, "The contents of register " + r1 + " with value of " + register[r1] + " is not a valid ASCII character.");
    }
    rPC += 2;
    }else{
      genericError("outcr", 1, r1 + " is not a valid register ID.");
    }
  }

  // 49
  private void printi(int r1) {// Print the contents of register r1 to the console as an integer.
    System.out.print(register[r1]);
    rPC += 2;
  }

  // 55
  private void outs(int label){// Print the string starting at the memory location label. The memory location label specifies the length of the string in characters (how many memory locations long the string is going to be), and each following memory location is converted to an ASCII character.
    int length = memory[label];
    int[] encoded = new int[length];
    String result = "";
    
    for (int i = 0; i < length; i++){
      encoded[i] = memory[label + i + 1];
    }
    for (int character : encoded){
      result += String.valueOf((char)(character));
    }

    System.out.print(result);
    rPC += 2;
  }

  // 57
  private void jmpne(int label){// If the compare register is not set to equal (0), then set rPC to the memory location specified by label.
    if (rCP != 0){
      rPC = label;
    }else{
      rPC += 1;
    }
  }

  public void run(String path){
    int[] binary = Utils.readTextFile(path);
    // copy binary into memory (excluding first 2 elements)
    for (int i = 2; i < binary.length; i++){
      memory[i - 2] = binary[i];
    }

    rPC = binary[1];
    while (rPC < memory.length){
      switch (memory[rPC]){
        case 0:  halt(); break; 
        case 1:  clrr(arg(1)); break;
        case 2:  clrx(arg(1)); break;
        case 3:  clrm(arg(1)); break;
        case 4:  clrb(arg(1), arg(2)); break;
        case 5:  movir(arg(1), arg(2)); break;
        case 6:  movrr(arg(1), arg(2)); break;
        case 7:  movrm(arg(1), arg(2)); break;
        case 8:  movmr(arg(1), arg(2)); break;
        case 9:  movxr(arg(1), arg(2)); break;
        case 10: //movar(arg(1), arg(2)); //Is not currently implemented
        case 11: //movb(arg(1), arg(2), arg(3)); //Is not currently implemented
        case 12: addir(arg(1), arg(2)); break;
        case 13: addrr(arg(1), arg(2)); break;
        case 14: 
        case 15: 
        case 16: 
        case 17: 
        case 18: 
        case 19: 
        case 20: 
        case 21: 
        case 22: 
        case 23: 
        case 24: 
        case 25: 
        case 26: 
        case 27: 
        case 28: 
        case 29: 
        case 30: 
        case 31: 
        case 32: 
        case 33: 
        case 34: cmprr(arg(1), arg(2)); break;
        case 35: 
        case 36: 
        case 37: 
        case 38: 
        case 39: 
        case 40: 
        case 41: 
        case 42: 
        case 43: 
        case 44: 
        case 45: outcr(arg(1)); break;
        case 46: 
        case 47: 
        case 48: 
        case 49: printi(arg(1)); break;
        case 50: 
        case 51: 
        case 52: 
        case 53:
        case 54:
        case 55: outs(arg(1)); break;
        case 56:
        case 57: jmpne(arg(1)); break;
        default: exit("The instruction code called at memory location " + rPC + " with value of " + memory[rPC] + " is not a valid instruction ID.");
      }
    }
    System.out.println("You have reached the end of the program.");
  }

  private int arg(int amount){//Returns the memory location that the argument specifies.
    return memory[rPC + amount];
  }

  private void genericError(String instructionName, int argumentWithError, String errorMessage){
    System.out.println("Error!");
    System.out.println("  Program Counter: " + rPC + ", Instruction: " + instructionName);
    System.out.println("  Malformed Statement: " + argumentWithError + ", Value: " + memory[rPC + argumentWithError]);
    System.out.println("  " + errorMessage);
    System.exit(1);
  }

  private void exit(String message){
    System.out.println(message);
    System.exit(1);
  }

  private boolean isRegisterInBounds(int r){
    if (r >= 0 && r < register.length){
      return true;
    }else{
      return false;
    }
  }

  private boolean isMemoryLocationInBounds(int memoryLocation){
    if (memoryLocation >= 0 || memoryLocation < memory.length){
      return true;
    }else{
      return false;
    }
  }
}