package core;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Lyssa on 24/11/14.
 */
public class Generator {

    public Generator(String spec) throws FileNotFoundException {

        FileReader fhdlr = new FileReader(spec);

    }

    // Adapter: create Scala program from XML specification
    public Generator(FileReader fhdlr) {

    }

}
