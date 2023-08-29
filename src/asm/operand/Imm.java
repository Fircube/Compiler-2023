package src.asm.operand;

public class Imm extends Operand{
    public int value;
    public Boolean stackRelated = false;

    public Imm(int value){
        this.value = value;
    }

    public Imm(int value,boolean stackRelated){
        this.value = value;
        this.stackRelated=stackRelated;
    }

    public String toString(){
        return Integer.toString(value);
    }
}
