package model.prank;

import configuration.ConfigurationManager;
import model.prank.Prank;
import model.prank.PrankGenerator;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class PrankGeneratorTest {

    @Test
    public void sizeOfLastGroupShouldbe10() throws IOException {

        PrankGenerator prank = new PrankGenerator(new ConfigurationManager("./resourcesTest/"));
        List<Prank> list = prank.generatePrank();

        //16 because we removed the sender one
        assertEquals(list.get(list.size()-1).getVictimsRecip().size(),10);
    }
}
