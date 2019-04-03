package model.prank;

import configuration.ConfigurationManager;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PrankGeneratorTest {

    @Test
    public void sizeOfLastGroupShouldbe16() throws IOException {

        PrankGenerator prank = new PrankGenerator(new ConfigurationManager());
        List<Prank> list = prank.generatePrank();

        //16 because we removed the sender one
        assertEquals(list.get(list.size()-1).getVictimsRecip().size(),16);
    }
}
