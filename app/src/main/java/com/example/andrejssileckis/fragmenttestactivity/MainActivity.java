package com.example.andrejssileckis.fragmenttestactivity;

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
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private CharSequence[] mItems = {"Home", "Work", "Garden", "Beach"};
    private boolean[] mItemChecked = new boolean[mItems.length];
    public int mRequestCode = 1;
    public int mNotificationID = 1;

    public final static JsonController JSON_CONTROLLER = new JsonController();
    private ProgressDialog mProgressDialog;

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
        // Inflate the menu; this adds mItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mItem) {
        // Handle action bar mItem clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = mItem.getItemId();
        return super.onOptionsItemSelected(mItem);
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
        mProgressDialog.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 35; i++) {
                    try {
                        Thread.sleep(1000);
                        mProgressDialog.incrementProgressBy( 100 / 15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void onClick3(View view) {
        startActivityForResult(new Intent(
                "com.example.andrejssileckis.dialogs.SecondActivity"), mRequestCode);
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
                        ).setMultiChoiceItems(mItems, mItemChecked
                                , new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick
                                    (DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(getBaseContext()
                                        , mItems[which] + (isChecked ? " checked !" : " unchecked!")
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }).create();
            case 1:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setTitle("Download something...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),
                                        "OK Clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),
                                        "Cancel clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                return mProgressDialog;
            case 2:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                mBuilder.setTitle("Some Simple text here...");
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(),
                                "OK clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(),
                                "Cancel clicked", Toast.LENGTH_SHORT).show();

                    }
                });
                mBuilder.setMultiChoiceItems(mItems, mItemChecked,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick
                                    (DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(getBaseContext(),
                                        mItems[which] + (isChecked ? " checked " : " unchecked"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                return mBuilder.create();
        }
        return null;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCode) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickNotification(View view) {
        displayNotification();
    }

    protected void displayNotification() {
        Intent mNotificationIntent = new Intent(
                this, MainActivity.class
        );
        long[] vibr = new long[]{100, 250, 100, 500};
        mNotificationIntent.putExtra("mNotificationID", mNotificationID);

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("some title text....")
                .setContentText("is this content ?")
                .setSmallIcon(R.drawable.ic_notify_icon)
                .setVibrate(vibr);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(mNotificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(R.id.btn_displaynotif, mBuilder.build());
    }

    public void onClickWebBrowser(View view) {
        Intent mBrowseOpenIntent = new Intent("android.intent.action.VIEW");
        mBrowseOpenIntent.setData(Uri.parse("http://habrahabr.ru"));
        startActivity(mBrowseOpenIntent);
        Toast.makeText(this, "Trying to open browser", Toast.LENGTH_LONG).show();
    }

    public void onClickMakeCalls(View view) {
        Intent mPhoneCallIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "));
        startActivity(mPhoneCallIntent);
    }

    public void onClickShowMap(View view, String string) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.streetview:cbll="+string));
                /*Uri.parse("geo: 56.953101, 23.721114?q=56.953101," +
                        "23.721114(IsThisMarker+SomewhereInJurmala")*/
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void createTabsOverlay() {
        final TabLayout TAB_LAYOUT = (TabLayout) findViewById(R.id.tab_layout);
        TAB_LAYOUT.addTab(TAB_LAYOUT.newTab().setText("Main"));
        TAB_LAYOUT.addTab(TAB_LAYOUT.newTab().setText("Map"));
        TAB_LAYOUT.addTab(TAB_LAYOUT.newTab().setText("Text"));
        TAB_LAYOUT.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), TAB_LAYOUT.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(TAB_LAYOUT));
        TAB_LAYOUT.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
