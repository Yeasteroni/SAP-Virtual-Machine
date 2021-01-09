// public class registers{
//   int[] memory;//The ram of the virtual machine
//   int[] register;//The general purpose registers
//   int rPC;//The program counter
//   int rCP;//The compare register
//   int rST;//The stack register, I'm not really sure how this works

//   public cpu(){
//     memory = new int[10000];
//     register = new int[10];
//     rPC = 0;
//     rCP = 0;
//     rST = 0;
//   }

//   public void setRegister(int index, int value){
//     register[index] = value;
//   }

//   public int getRegister(int index){
//     return register[index];
//   }

//   public void setMemory(int index, int value){
//     memory[index] = value;
//   }

//   public int getMemory(int index){
//     return memory[index];
//   }

//   public void setRPC(int value){
//     rPC = value;
//   }

//   public int getRPC(){
//     return rPC;
//   }

//   //The others will be handled differently
// }