import ro.mpp2025.model.ComputerRepairRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test2 {
    @Test
    @DisplayName("Test1")
    public void test1 () {
        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest();
        assertEquals("", computerRepairRequest.getOwnerName());
        assertEquals("", computerRepairRequest.getOwnerAddress());

        computerRepairRequest.setOwnerName("Alex");
        computerRepairRequest.setOwnerAddress("adresa X");
        assertEquals("Alex", computerRepairRequest.getOwnerName());
        assertEquals("adresa X", computerRepairRequest.getOwnerAddress());
    }
}
