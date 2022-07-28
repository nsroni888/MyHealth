package com.pialroni.myhealth;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class UserDatabaseTestUnit {
    private UserDatabase db;
    private IDataBase dao;
 

    @Before
    public void before() {

        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                UserDatabase.class
        ).build();
        dao = db.Dao();

    }

    @Test
    public void LoadData() throws Exception{


        int prevSize = dao.getAllData().size();

        UserData userData = new UserData("12/07/22", "12.00",
                100, 90, 70, "Testing");
        userData.setDi(System.currentTimeMillis());

        dao.InsertData(userData);

        UserData userData1 = new UserData("11/04/22", "12.00",
                98, 70, 78, "Testing1");
        userData1.setDi((System.currentTimeMillis())+100);

        dao.InsertData(userData1);

        UserData userData2 = new UserData("11/07/22", "12.00",
                78, 80, 90, "Testing2");
        userData2.setDi(System.currentTimeMillis()+200);

        dao.InsertData(userData2);

        int curSize = dao.getAllData().size();

        assertEquals(prevSize+3,curSize);



    }


    @Test
    public void InsertData() throws Exception{


        UserData userData = new UserData("12/07/22", "12.00",
                100, 12, 100, "Testing");
        userData.setDi(System.currentTimeMillis());

        dao.InsertData(userData);
        UserData allData = dao.getData(String.valueOf(userData.getDi()));
        assertEquals(allData.getDi(),userData.getDi());



    }



    @Test
    public void DeleteData() throws Exception{


        UserData userData = new UserData("12/07/22", "12.00",
                100, 12, 100, "Testing");
        userData.setDi(System.currentTimeMillis());

        dao.InsertData(userData);
        dao.DeleteData(userData);

        UserData userDataNUll = dao.getData(String.valueOf(userData.getDi())) ;

        assertNull(userDataNUll);
        

    }
    @Test
    public void UpdateData() throws Exception{


        UserData userData = new UserData("12/07/22", "12.00",
                100, 12, 100, "Testing");
        userData.setDi(System.currentTimeMillis());

        dao.InsertData(userData);


        UserData userDataRetrive = dao.getData(String.valueOf(userData.getDi())) ;
        userDataRetrive.setDiastolic(120);
        dao.UpdateData(userDataRetrive);

        UserData userDataUpdate = dao.getData(String.valueOf(userData.getDi())) ;

        assertEquals(userDataUpdate.getDiastolic(),120);




    }



    @After
    public void finish() {
        db.close();
    }
}