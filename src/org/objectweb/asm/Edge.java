package org.objectweb.asm;

class Edge {
    static final int NORMAL = 0;

    static final EXCEPTION = 0x7FFFFFFF;

    int info;

    Label successor;

    Edge next;
}