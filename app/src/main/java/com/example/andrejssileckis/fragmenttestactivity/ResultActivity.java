package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/26/2015.
 */
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ResultActivity extends AppCompatActivity{
    // our FileController
    public FileController mFileController;
    public static boolean mLongClick = false;
    // where our text file will be created
    public final static String FILE_PATH = Environment.getExternalStorageDirectory() +
            "/SindreyAndroidFileTestFolder/";

    // name of our text file
    public String mFileName;

    // FILE_PATH and mFileName combined
    public String mActualFile;

    public TextView mContentsTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent ReceivedIntent = getIntent();
        Bundle bundle = ReceivedIntent.getExtras();
        mFileName= bundle.getString("element")+".txt";
        mActualFile = FILE_PATH + mFileName.replaceAll(" ","_").toLowerCase();

        try {
            // initialize our FileController here
            mFileController = new FileController(this);
            // first, make sure the path to your text file is created
            mFileController.createPath(FILE_PATH);

            // so we can show file contents to the user
            mContentsTextView = (TextView) findViewById(R.id.contentsTextView);
            String contents = mFileController.readTextFile(mActualFile);
            mContentsTextView.setText(contents);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        finally {
            TextView mResult = (TextView) findViewById(R.id.contentsTextView);
//        final TextView mTempResult = (TextView)findViewById(R.id.textView1);

            mResult.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    textEdition();
                    return false;
                }
            });
        }
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
        int ID= item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (ID) {
            case R.id.create_text_file:
                mFileController.createTextFile(mActualFile);
                mContentsTextView.setText(mFileController.readTextFile(mActualFile));
                break;

            case R.id.delete_text_file:
                mFileController.deleteTextFile(mActualFile);
                mContentsTextView.setText("");
                break;

            case R.id.edit_text:
//                String temp = mFileController.readTextFile(mActualFile);
                Toast.makeText(this, "Taking some changes in text.... Please Wait.....",
                        Toast.LENGTH_SHORT).show();
                textEdition();
                break;
            case R.id.save_text:
                Toast.makeText(this, "You have pressed Save Text option. No saves for ya...",
                        Toast.LENGTH_SHORT).show();
                mContentsTextView.setText(mFileController.readTextFile(mActualFile));
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
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.edit_text, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ResultActivity.this);
        alertDialogBuilder.setView(view);

        final  EditText userInput = (EditText) view.findViewById(
                R.id.editTextDialogUserInput);
        userInput.setVisibility(View.VISIBLE);
        userInput.setText(mFileController.readTextFile(mActualFile));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFileController.updateTextFile(mActualFile,userInput.getText().toString() );
                        mContentsTextView.setText(mFileController.readTextFile(mActualFile));
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
