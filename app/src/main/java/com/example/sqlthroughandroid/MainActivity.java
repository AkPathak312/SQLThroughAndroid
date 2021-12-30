package com.example.sqlthroughandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    String ip="192.168.0.104";
    String port="1433";
    String username="amionic";
    String password="amionic";
    String database="session1";
    String url="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    TextView textView;

    Connection connection=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection= DriverManager.getConnection(url,username,password);
            Log.d("SQL Connection","SUCCESS");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.d("SQL Error",e.getMessage());
        }
        getOffices();
    }

    private void getOffices() {
        if(connection!=null){
            try {
                Statement stmt=connection.createStatement();
                ResultSet resultSet=stmt.executeQuery("Select * from Offices;");
                while(resultSet.next()){
                    textView.setText(textView.getText().toString()+"\n"+resultSet.getString("Title")+" - "+resultSet.getString(5));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public void insertData(View view) {
        int countryId=142;
        if(connection!=null){
            try {
                Statement stmt=connection.createStatement();
                boolean result=stmt.execute("insert into Offices(CountryID,Title,Phone,Contact) values("+countryId+",'test','285-285-1474','test contact');");
                Log.d("Insert Result",result+"");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}