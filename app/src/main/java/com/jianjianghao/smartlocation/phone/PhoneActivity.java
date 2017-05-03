package com.jianjianghao.smartlocation.phone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jianjianghao.smartlocation.R;


public class PhoneActivity extends Activity {
    private ListView mListView;
    private PhoneDbAdapter mDbAdapter;
    private PhoneSimpleCursorAdapter mCursorAdapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
      // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_phones);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);


      //  ActionBar actionBar = getSupportActionBar();
      //  actionBar.setHomeButtonEnabled(true);
       // actionBar.setDisplayShowHomeEnabled(true);
       // actionBar.setIcon(R.mipmap.ic_launcher);




        mListView = (ListView) findViewById(R.id.reminders_list_view);
        mListView.setDivider(null);
        mDbAdapter = new PhoneDbAdapter(this);
        mDbAdapter.open();
//        if (savedInstanceState == null) {
//            //Clear all data
//            mDbAdapter.deleteAllReminders();
//            //Add some data
//            insertSomeReminders();
//        }


        Cursor cursor = mDbAdapter.fetchAllReminders();

        String[] from = new String[]{ PhoneDbAdapter.COL_NAME,PhoneDbAdapter.COL_CONTENT};
        int[] to = new int[]{R.id.row_name,R.id.row_text};


        mCursorAdapter = new PhoneSimpleCursorAdapter( PhoneActivity.this,R.layout.phones_row,cursor,from,to,0);
        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);
                ListView modeListView = new ListView(PhoneActivity.this);
                String[] modes = new String[]{"编辑", "删除"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(PhoneActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int nId = getIdFromPosition(masterListPosition);
                        final Phone phone = mDbAdapter.fetchReminderById(nId);
                        //edit phone
                        if (position == 0) {
                            fireCustomDialog(phone);
                            //delete phone
                        } else if (position == 1) {
                            mDbAdapter.deleteReminderById(getIdFromPosition(masterListPosition));
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                        } else {
                            //
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.phone_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_reminder:
                            for (int nC = mCursorAdapter.getCount() - 1; nC >= 0; nC--) {
                                if (mListView.isItemChecked(nC)) {
                                    mDbAdapter.deleteReminderById(getIdFromPosition(nC));
                                    //多选删除
                                }
                            }
                            mode.finish();
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                            return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }
            });
        }
    }

    private int getIdFromPosition(int nC) {
        return (int) mCursorAdapter.getItemId(nC);
    }

    private void insertSomeReminders() {
        mDbAdapter.createReminder("赵三","1788888455", true);
        mDbAdapter.createReminder("赵三","1788888455", false);
        mDbAdapter.createReminder("赵三","1788888455", false);
        mDbAdapter.createReminder("赵三","1788888455", true);
        mDbAdapter.createReminder("赵三","1788888455", false);
        mDbAdapter.createReminder("赵三","1788888455", false);
    }

//创建自定义对话框
    private void fireCustomDialog(final Phone phone) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.phone_custom);
        TextView titleView = (TextView) dialog.findViewById(R.id.custom_title);
        final EditText editName = (EditText) dialog.findViewById(R.id.custom_edit_name);
        final EditText editCustom = (EditText) dialog.findViewById(R.id.custom_edit_reminder);
        Button commitButton = (Button) dialog.findViewById(R.id.custom_button_commit);
        final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.custom_check_box);
        LinearLayout rootLayout = (LinearLayout) dialog.findViewById(R.id.custom_root_layout);
        final boolean isEditOperation = (phone != null);
        //this is for an edit
        if (isEditOperation) {
            titleView.setText("编辑联系人");
            checkBox.setChecked(phone.getImportant() == 1);
            editName.setText(phone.getName());
            editCustom.setText(phone.getContent());
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));//修改编辑对话框的背景色
        }
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reminderName = editName.getText().toString();
                String reminderText = editCustom.getText().toString();
                if (isEditOperation) {
                    Phone phoneEdited = new Phone(phone.getId(),reminderName,
                            reminderText, checkBox.isChecked() ? 1 : 0);
                    mDbAdapter.updateReminder(phoneEdited);
                    //this is for new phone
                } else {
                    mDbAdapter.createReminder(reminderName,reminderText, checkBox.isChecked());
                }
                mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                dialog.dismiss();
            }
        });

        //取消修改
        Button buttonCancel = (Button) dialog.findViewById(R.id.custom_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phones, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                //create new Device
                fireCustomDialog(null);
                return true;
//            case R.id.action_all_del:
//                //批量删除
//                fireCustomDialog(null);
//                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;
        }
    }

    public void addPhone(View view){  fireCustomDialog(null); }

    public void backHome(View view){ finish();}

}
