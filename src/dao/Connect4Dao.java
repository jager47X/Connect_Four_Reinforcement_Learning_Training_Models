package dao;
import dto.Connect4Dto;
//import the ML target
import target.Connect4;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Connect4Dao extends BaseDao<Connect4Dto>{

    Connect4 board;
    private static Connect4Dao instance;
    public Connect4Dao(Connect4 board) {
        String path=CSV.getPath();
    }

/*
    private Connect4Dao(MongoCollection<String> collection){
        super(collection);
    }

    public static Connect4Dao getInstance(){
        if(instance != null){
            return instance;
        }
        instance = new Connect4Dao(CSV.getCollection("ItemDao"));
        return instance;
    }


    public List< Connect4Dto> query(String filter){
        return collection.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(ItemDto::fromDocument)
                .collect(Collectors.toList());
    }


    public List<Connect4Dto> getAll(){
        return  collection.find( )
                .into(new ArrayList<>())
                .stream()
                .map(ItemDto::fromDocument)
                .collect(Collectors.toList());

    }
*/
}
