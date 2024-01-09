package dao;


import dto.BaseDto;
import target.Connect4;

import java.util.HashMap;

public abstract class BaseDao  <T extends BaseDto> {

        // Constructor
        public BaseDao() {

        }

    // Getter methods

        // toString method for easy printing
        @Override
        public String toString() {
            return "";
        }

}
