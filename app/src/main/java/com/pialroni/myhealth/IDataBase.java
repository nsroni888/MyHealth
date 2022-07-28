package com.pialroni.myhealth;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Interface for query from Room database
 */
@androidx.room.Dao
public interface IDataBase {

    /**
     * inserting data
     * @param userData
     */
    @Insert
    void InsertData(UserData userData);

    /**
     * deleting data
     * @param userData
     */
    @Delete
    void DeleteData(UserData userData);

    /**
     * update data
     * @param userData
     */
    @Update
    void UpdateData(UserData userData);

    /**
     * getting data
     * @param id
     * @return
     */

    @Query("SELECT * FROM USER_DATA WHERE di = :id")
    UserData getData(String id);


    /**
     * getting all data
     * @return  List<UserData>
     */
    @Query("SELECT * FROM User_Data")
   List<UserData> getAllData();



}
