package com.pialroni.myhealth;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface IDataBase {

    @Insert
    void InsertData(UserData userData);

    @Delete
    void DeleteData(UserData userData);

    @Update
    void UpdateData(UserData userData);



    @Query("SELECT * FROM User_Data")
   List<UserData> getAllData();



}
