package Test;

import ReinforceLearning.ReinforceLearningAgentConnectFour;
import dao.BaseDao;
import dto.Connect4Dto;
import dto.QTableDto;
import GameEnviroment.Connect4;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AgentTest {

    @Test
    void testReinforceLearningAgent() {
        // Initialize any necessary data
        List<String> exportingData = new ArrayList<>();
        if (!BaseDao.getImportedGames().isEmpty()) {
            for (int i = 0; i < BaseDao.getImportedGames().size(); i++) {
                exportingData.addAll(BaseDao.getImportedGames().get(i));
            }
        }

        // Create and train the agent
        QTableDto Qtable = new QTableDto();
        for (int i = 0; i < 1000; i++) {
            Connect4 game = new Connect4();
            Connect4Dto connect4Dto = new Connect4Dto(game);
            ReinforceLearningAgentConnectFour agent = new ReinforceLearningAgentConnectFour(connect4Dto, Qtable);
            Qtable = agent.ReinforceLearning();
        }

        // Ensure that the QTable is not null after training
        assertNotNull(Qtable.getHashedData());
        // Add more assertions as needed to validate the behavior of the agent or QTable
    }
}
