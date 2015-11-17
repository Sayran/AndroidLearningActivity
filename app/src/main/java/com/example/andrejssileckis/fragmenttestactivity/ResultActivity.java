package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/26/2015.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;


public class ResultActivity extends AppCompatActivity{
    // our FileController
    public FileController fileController;
    boolean longClick = false;
    // where our text file will be created
    public final String filePath = Environment.getExternalStorageDirectory() + "/SindreyAndroidFileTestFolder/";

    // name of our text file
    public String fileName;

    // filePath and fileName combined
    public String actualFile;

    public TextView contentsTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fileName= bundle.getString("element")+".txt";
        actualFile = filePath + fileName.replaceAll(" ","_").toLowerCase();

        try {
            // initialize our FileController here
            fileController = new FileController(this);
            // first, make sure the path to your text file is created
            fileController.createPath(filePath);

            // so we can show file contents to the user
            contentsTextView = (TextView) findViewById(R.id.contentsTextView);
            String contents = fileController.readTextFile(actualFile);
            contentsTextView.setText(contents);


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final TextView result = (TextView)findViewById(R.id.contentsTextView);
        final TextView tempResult = (TextView)findViewById(R.id.textView1);

        result.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textEdition();
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.create_text_file:
                fileController.createTextFile(actualFile);
                contentsTextView.setText(fileController.readTextFile(actualFile));
                break;

            case R.id.delete_text_file:
                fileController.deleteTextFile(actualFile);
                contentsTextView.setText("");
                break;

            case R.id.edit_text:
                String temp = fileController.readTextFile(actualFile);
                Toast.makeText(this, "Taking some changes in text.... Please Wait.....", Toast.LENGTH_SHORT).show();
                textEdition();
                break;
            case R.id.save_text:
                Toast.makeText(this, "You have pressed Save Text option. No saves for ya...", Toast.LENGTH_SHORT).show();
                contentsTextView.setText(fileController.readTextFile(actualFile));
                break;

            case android.R.id.home:
                Intent upIntent = new Intent(this,ThirdTabFragment.class);
                if(NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this)
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                }else{
                    NavUtils.navigateUpTo(this,upIntent);
                }
                return  true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void textEdition(){
        LayoutInflater layoutInflater = LayoutInflater.from(ResultActivity.this);
        View view = layoutInflater.inflate(R.layout.edit_text, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ResultActivity.this);
        alertDialogBuilder.setView(view);

        final  EditText userInput = (EditText) view.findViewById(
                R.id.editTextDialogUserInput);
        userInput.setVisibility(View.VISIBLE);
        userInput.setText(fileController.readTextFile(actualFile));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileController.updateTextFile(actualFile,userInput.getText().toString() );
                        contentsTextView.setText(fileController.readTextFile(actualFile));
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {
                                dialog.cancel();
                            }
                        })
        ;
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
