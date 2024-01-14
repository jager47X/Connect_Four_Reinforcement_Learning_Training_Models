package dao;


import dto.BaseDto;
import dto.QTableDto;

import java.util.List;

public abstract class BaseDao <T extends BaseDto> {

        public BaseDao( ) {
        }

        public void addLine(int turn, T dto) {
                while(turn>dto.getHashedData().size()){
                       turn--;
                }
                dto.getHashedData().add(turn, dto.hashing());
                System.out.println("turn: " + turn );
               System.out.println("Total Size of HashedData: " + dto.getHashedData().size() );
                System.out.println("Total Line:");
                for (String Line : dto.getHashedData()) {
                        System.out.println(Line);
                }


        }



}
