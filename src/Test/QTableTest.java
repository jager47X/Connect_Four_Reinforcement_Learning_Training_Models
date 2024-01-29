package Test;

import dto.QEntry;
import dto.QTableDto;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QTableTest {

    @Test
    void givenQTable_whenUpdatingQTable_thenNewEntryAdded() {
        QTableDto qTableDto = new QTableDto();

        // Test case 1: Adding a new entry
        qTableDto.updateQTable("state1", 1, 0.5);
        Set<QEntry> qEntrySet1 = qTableDto.getImportedPolicyNetWork().get("state1");
        assertNotNull(qEntrySet1);
        assertEquals(1, qEntrySet1.size());
        assertEquals(0.5, qEntrySet1.iterator().next().getQValue(1));
    }

    @Test
    void givenQTableWithExistingEntry_whenUpdatingQTable_thenExistingEntryUpdated() {
        QTableDto qTableDto = new QTableDto();

        // Test case 2: Updating an existing entry
        qTableDto.updateQTable("state1", 1, 0.5);
        qTableDto.updateQTable("state1", 1, 0.7);
        Set<QEntry> qEntrySet2 = qTableDto.getImportedPolicyNetWork().get("state1");
        assertNotNull(qEntrySet2);

        assertEquals(1, qEntrySet2.size());
        assertEquals(0.7, qEntrySet2.iterator().next().getQValue(1));
    }

    @Test
    void givenQTable_whenUpdatingQTableForDifferentState_thenNewEntryAddedForState() {
        QTableDto qTableDto = new QTableDto();

        // Test case 3: Adding a new entry for a different state
        qTableDto.updateQTable("state2", 2, 0.8);
        Set<QEntry> qEntrySet3 = qTableDto.getImportedPolicyNetWork().get("state2");
        assertNotNull(qEntrySet3);
        assertEquals(1, qEntrySet3.size());
        assertEquals(0.8, qEntrySet3.iterator().next().getQValue(2));
    }

    // Add more test cases as needed
}