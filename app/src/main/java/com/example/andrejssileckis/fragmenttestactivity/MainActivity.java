package com.example.andrejssileckis.fragmenttestactivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    CharSequence[] items = {"Home", "Work", "Garden", "Beach"};
    boolean[] itemChecked = new boolean[items.length];
    int request_Code = 1;
    int notificationID = 1;

    final JsonController jsonController = new JsonController();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createTabsOverlay();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        showDialog(0);
    }

    public void onClick1(View view) {
        final ProgressDialog dialog = ProgressDialog.show(this,
                "What am i doing ?", "Please wait...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onClick2(View view) {
        showDialog(1);
        progressDialog.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 35; i++) {
                    try {
                        Thread.sleep(1000);
                        progressDialog.incrementProgressBy( 100 / 15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void onClick3(View view) {
        startActivityForResult(new Intent(
                "com.example.andrejssileckis.dialogs.SecondActivity"), request_Code);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this)
                        .setTitle("Some plain text in dialog...")
                        .setPositiveButton("OK"
                                , new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whitchButton) {
                                Toast.makeText(getBaseContext(), "OK clicked"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getBaseContext(), "Cancel clicked"
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).setMultiChoiceItems(items, itemChecked
                                , new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(getBaseContext()
                                        , items[which] + (isChecked ? " checked !" : " unchecked!")
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }).create();
            case 1:
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Download something...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),
                                        "OK Clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),
                                        "Cancel clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                return progressDialog;
            case 2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Some Simple text here...");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(),
                                "OK clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(),
                                "Cancel clicked", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setMultiChoiceItems(items, itemChecked,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(getBaseContext(),
                                        items[which] + (isChecked ? " checked " : " unchecked"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                return builder.create();
        }
        return null;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_Code) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickNotification(View view) {
        displayNotification();
    }

    protected void displayNotification() {
        Intent intent = new Intent(
                this, MainActivity.class
        );
        long[] vibr = new long[]{100, 250, 100, 500};
        intent.putExtra("notificationID", notificationID);

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this)
                .setContentTitle("some title text....")
                .setContentText("is this content ?")
                .setSmallIcon(R.drawable.ic_notify_icon)
                .setVibrate(vibr);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(R.id.btn_displaynotif, builder.build());
    }

    public void onClickWebBrowser(View view) {
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse("http://habrahabr.ru"));
        startActivity(i);
        Toast.makeText(this, "Trying to open browser", Toast.LENGTH_LONG).show();
    }

    public void onClickMakeCalls(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "));
        startActivity(i);
    }

    public void onClickShowMap(View view, String string) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.streetview:cbll="+string));
                /*Uri.parse("geo: 56.953101, 23.721114?q=56.953101," +
                        "23.721114(IsThisMarker+SomewhereInJurmala")*/
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);
    }

    public void createTabsOverlay() {
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Main"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Text"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
    }
}
