package com.example.lekhn.findyourdoctor.utilities;

import java.util.ArrayList;

/**
 * Created by and-36 on 24/4/17.
 */

public interface IParser {
    void onPreExecute();

    void onPostExecute(ArrayList<?> resultArrayList);

    void onPostFailure();


}
