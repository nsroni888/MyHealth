package com.pialroni.myhealth;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

/**
 * Data Repository For Fetching Data
 */
public class DataRepository {

    private IDataBase dao;
    private  List<UserData>  allData;


    public DataRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        dao = database.Dao();
        allData = dao.getAllData();
    }

    /**
     * Inserting into database
     * @param model
     */
    public void insert(UserData model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    /**
     * creating a method to update data in database.
     * @param model
     */

    public void update(UserData model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    /**
     * creating a method to delete the data in our database.
     * @param model
     */
    public void delete(UserData model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    /**
     *  below is the method to delete all the courses.
     * @param
     */

    public void deleteAllData() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    /**
     * below method is to read all the courses.
     * @return List<UserData>
     */

    public List<UserData> getAllData() {
        return allData;
    }

    /**
     * Async Task for inserting data
     */
    private static class InsertCourseAsyncTask extends AsyncTask<UserData, Void, Void> {
        private IDataBase dao;

        private InsertCourseAsyncTask(IDataBase dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserData... model) {
            // below line is use to insert our modal in dao.
            dao.InsertData(model[0]);
            return null;
        }
    }

    /**
     * we are creating a async task method to update our course.
     */

    private static class UpdateCourseAsyncTask extends AsyncTask<UserData, Void, Void> {
        private IDataBase dao;

        private UpdateCourseAsyncTask(IDataBase dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserData... models) {
            // below line is use to update
            // our modal in dao.
            dao.UpdateData(models[0]);
            return null;
        }
    }

    /**
     * we are creating a async task method to delete course.
     */

    private static class DeleteCourseAsyncTask extends AsyncTask<UserData, Void, Void> {
        private IDataBase dao;

        private DeleteCourseAsyncTask(IDataBase dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserData... models) {
            // below line is use to delete 
            // our course modal in dao.
            dao.DeleteData(models[0]);
            return null;
        }
    }

    /**
     * we are creating a async task method to delete all courses.
     */

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private IDataBase dao;
        private DeleteAllCoursesAsyncTask(IDataBase dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
           // dao.deleteAllCourses();
            return null;
        }
    }
}
