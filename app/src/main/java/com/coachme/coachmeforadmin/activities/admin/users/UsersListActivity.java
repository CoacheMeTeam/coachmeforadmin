package com.coachme.coachmeforadmin.activities.admin.users;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.AdminActivity;
import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.model.User;
import com.coachme.coachmeforadmin.utils.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


public class UsersListActivity extends Activity {
    private final String ID_COLUMN = "ID";
    private final String NAME_COLUMN = "NOM";
    private final String FIRSTNAME_COLUMN = "PRENOM";
    private final String GOAL_COLUMN = "OBJECTIF";

    private Button goBackButton;
    private List<User> users = new ArrayList<>();
    UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        initComponents();
        userDatabaseHelper = new UserDatabaseHelper();
        users = userDatabaseHelper.getUsers();

        addHeaders();
        addData(users);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void initComponents() {
        tableLayout = (TableLayout) findViewById(R.id.usersTable);
        goBackButton = (Button) findViewById(R.id.usersListGoBackButton);
    }

    private void addHeaders() {
        TableRow rowHeader = new TableRow(getApplicationContext());
        rowHeader.setBackgroundResource(R.drawable.rowborder_shape);
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {ID_COLUMN, NAME_COLUMN, FIRSTNAME_COLUMN, GOAL_COLUMN};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.START);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);
    }

    private void addData(List<User> users) {
        for (final User user : users) {
            TableRow row = new TableRow(getApplicationContext());
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] colText = {Integer.toString(user.getId()), user.getName(), user.getFirstName(), user.getGoal()};
            for (String text : colText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.START);
                tv.setTextSize(16);
                tv.setTextColor(Color.WHITE);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(text);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UpdateUserActivity.class);
                        intent.putExtra("userToUpdate", Helper.convertObjectToJson(user));
                        System.out.println(Helper.convertObjectToJson(user));
                        startActivity(intent);
                    }
                });
                row.addView(tv);
            }
            tableLayout.addView(row);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(intent);
    }
}