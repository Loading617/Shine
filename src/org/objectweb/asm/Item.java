package org.objectweb.asm;

final class Item {

    int index;

    int type;

    int intVal;

    long longVal;

    String strVal1;

    String strVal2;

    String strVal3;

    int hashCode;

    int next;

    Item() {
    }
    Item(final int index){
        this.index = index;
    }
    Item(final int index, final item i){
        this.index = index;
        this.type = i.type;
        this.intVal = i.intVal;
        this.longVal = i.longVal;
        this.strVal1 = i.strVal1;
        this.strVal2 = i.strVal2;
        this.strVal3 = i.strVal3;
        this.hashCode = i.hashCode;
    }
    void set (final int intVal) {
        this.type = ClassWriter.INT;
        this.intVal = intVal;
        this.hashCode = 0x7FFFFFF & (type + intVal);
    }
}