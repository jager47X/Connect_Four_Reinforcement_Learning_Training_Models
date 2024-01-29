package Test;

import dto.QEntry;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QEntryTest {

    @Test
    void testConstructor() {
        QEntry qEntry = new QEntry();

        assertNotNull(qEntry);
        assertTrue(qEntry.getQEntry().isEmpty());
    }

    @Test
    void testUpdateQEntry() {
        QEntry existingQEntry = new QEntry();
        existingQEntry.updateQEntry(3, 0.5);
        existingQEntry.updateQEntry(3, 0.7);

        // Use the existingQEntry to store the result of updateQEntry
        existingQEntry = existingQEntry.updateQEntry(3, 0.9);

        assertNotNull(existingQEntry);
        Map<Integer, Double> qValues = existingQEntry.getQEntry();

        assertEquals(1, qValues.size());
        assertEquals(0.9, qValues.get(3));

    }


    @Test
    void testSetAndGetQValue() {
        QEntry qEntry = new QEntry();

        qEntry.setQValue(1, 0.5);
        qEntry.setQValue(2, 0.7);

        assertEquals(0.5, qEntry.getQValue(1));
        assertEquals(0.7, qEntry.getQValue(2));
        assertEquals(0.0, qEntry.getQValue(3)); // Default value for non-existing key
    }

    @Test
    void testGetAction() {
        QEntry qEntry = new QEntry();
        qEntry.setQValue(1, 0.5);
        qEntry.setQValue(2, 0.7);
        qEntry.setQValue(4, 0.3);

        assertEquals(Set.of(1, 2, 4), qEntry.getAction());
    }
}
