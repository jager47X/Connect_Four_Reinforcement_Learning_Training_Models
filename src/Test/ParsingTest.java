package Test;

import dao.QTableDao;
import dto.QEntry;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ParsingTest {

    @Test
    void testDaoConstructor() {
        QTableDao qTableDao = QTableDao.getInstance();

        assertNotNull(qTableDao);
        assertNotNull(qTableDao.getImportedMap());
    }

    @Test
    void testDaoUpdateQTable() {
        QTableDao qTableDao = QTableDao.getInstance();
        qTableDao.getImportedMap().clear(); // Clear the map before testing

        String state = "TestState";
        int action = 1;
        double qvalue = 0.5;

        qTableDao.updateQTable(state, action, qvalue);

        Map<String, Set<QEntry>> importedMap = qTableDao.getImportedMap();

        assertTrue(importedMap.containsKey(state));
        Set<QEntry> qEntrySet = importedMap.get(state);
        assertEquals(1, qEntrySet.size());

        QEntry qEntry = qEntrySet.iterator().next();
        assertEquals(qvalue, qEntry.getQValue(action));
    }


    @Test
    void testAddLocation() {
        QTableDao qTableDao = QTableDao.getInstance();

        String move = "P1L3R0.5";
        int location = qTableDao.addLocation(move);

        assertEquals(3, location);

        move = "P1L7R0.5";
        location = qTableDao.addLocation(move);

        assertEquals(7, location);

        move = "P2L1R0.5";
        location = qTableDao.addLocation(move);

        assertEquals(1, location);
    }

    @Test
    void testAddReward() {
        QTableDao qTableDao = QTableDao.getInstance();

        String move = "P0L3R0.5";
        double reward = qTableDao.addReward(move);

        assertEquals(0.5, reward);

        move = "P1L7R1.5";
        reward = qTableDao.addReward(move);

        assertEquals(1.5, reward);

        move = "P2L1R2.0";
        reward = qTableDao.addReward(move);

        assertEquals(2.0, reward);
    }
}
