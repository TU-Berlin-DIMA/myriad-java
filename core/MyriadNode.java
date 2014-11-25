/**
 * Created by Lyssa on 24/11/14.
 */

public class MyriadNode{

    /*
    * PRNG instance.
    * */
    private Generator gen;

    /*
    * Node identifier in [0..N-1]
    */
    private int nID;

    /*
    * Total number of processes.
    */
    private int N;

    /*
    * Total table size.
    */
    private int size;

    /*
    * Seed for AbstractPRNG.
    */
    public int seed;

    public MyriadNode(Generator gen, int nID, int N, int size, int seed){
        this.nID = nID;
        this.N = N;
        this.size = size;
        this.seed = seed;
    }

    /*
    * Copy constructor.
    * */
    public MyriadNode(MyriadNode m) {
        this(m.getGenerator(), m.getNodeID(), m.getNumProcess(), m.getSize(), m.getSeed());
    }
    /*
    * Run data generation plan and return
    */
    public boolean run(){

        return true;
    }

    /*
    * Return process identifier.
    */
    public int getNodeID(){
        return this.nID;
    }

    /*
    * Return number of processes intended to be run.
    */
    public int getNumProcess(){
        return this.N;
    }

    public Generator getGenerator(){
        return this.gen;
    }

    public int getSize(){
        return this.size;
    }

    public int getSeed(){
        return this.seed;
    }
}