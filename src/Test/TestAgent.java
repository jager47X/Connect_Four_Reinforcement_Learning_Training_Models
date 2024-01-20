package Test;
import ReinforceLearning.Connect4Handler.ReinforceLearningAgentConnectFour;
import dao.BaseDao;
import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Connect4;
import java.util.ArrayList;
import java.util.List;

public class TestAgent {

    public static void main(String[] args) {
        //train agent//choose human or self learn or train agent
        //for human interaction episodes=1;
        Connect4 game=new Connect4();
        Connect4Dto connect4Dto=new Connect4Dto(game);
        List<String> exportingData = new ArrayList<>();
        if(!BaseDao.getImportedData().isEmpty()){
            exportingData.addAll(BaseDao.getImportedData());
        }
        System.out.print("Importing the Data....");
        ReinforceLearningAgentConnectFour Agent = new ReinforceLearningAgentConnectFour(connect4Dto);
        Agent.ReinforceLearning();
        System.out.println("Test:Completed");
    }
}