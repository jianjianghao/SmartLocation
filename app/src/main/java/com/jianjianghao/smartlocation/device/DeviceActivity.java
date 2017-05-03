package com.jianjianghao.smartlocation.device;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import com.jianjianghao.smartlocation.AccountActivity;
import com.jianjianghao.smartlocation.MainActivity;
import com.jianjianghao.smartlocation.R;
import com.jianjianghao.smartlocation.ServiceActivity;
import com.jianjianghao.smartlocation.bluetooth.BluetoothActivity;


public class DeviceActivity extends Activity {
    private ListView mListView;
    private DeviceDbAdapter mDbAdapter;
    private DeviceSimpleCursorAdapter mCursorAdapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        mListView = (ListView) findViewById(R.id.devices_list_view);
        mListView.setDivider(null);
        mDbAdapter = new DeviceDbAdapter(this);
        mDbAdapter.open();

//        if (savedInstanceState == null) {
//            //Clear all data
//            mDbAdapter.deleteAllDevices();
//            //Add some data
//            insertSomeDevices();
//        }


        Cursor cursor = mDbAdapter.fetchAllDevices();



        String[] from = new String[]{ DeviceDbAdapter.COL_NAME, DeviceDbAdapter.COL_NUMBER,DeviceDbAdapter.COL_MAN};
        int[] to = new int[]{R.id.row_name,R.id.row_number,R.id.row_man};


        mCursorAdapter = new DeviceSimpleCursorAdapter( DeviceActivity.this,R.layout.device_row,cursor,from,to,0);
        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeviceActivity.this);
                ListView modeListView = new ListView(DeviceActivity.this);
                String[] modes = new String[]{"编辑基本信息","获取设备参数", "设定参数阈值","删除设备"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(DeviceActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int nId = getIdFromPosition(masterListPosition);
                        final Device device = mDbAdapter.fetchDeviceById(nId);
                        //edit device
                        switch (position){
                            case 0:
                                    fireCustomDialog(device);
                                    break;
                            case 1:
                                startActivity(new Intent(DeviceActivity.this, DeviceShowActivity.class));
                                    break;
                            case 2:
                                startActivity(new Intent(DeviceActivity.this, DeviceSiteActivity.class));
                                    break;
                            case 3:
                                mDbAdapter.deleteDeviceById(getIdFromPosition(masterListPosition));
                                mCursorAdapter.changeCursor(mDbAdapter.fetchAllDevices());
                                break;
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
                    inflater.inflate(R.menu.device_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_device:
                            for (int nC = mCursorAdapter.getCount() - 1; nC >= 0; nC--) {
                                if (mListView.isItemChecked(nC)) {
                                    mDbAdapter.deleteDeviceById(getIdFromPosition(nC));
                                    //多选删除
                                }
                            }
                            mode.finish();
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllDevices());
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

    private void insertSomeDevices() {
        mDbAdapter.createDevice("赵三","1788888455","1788888455","1788888455", true);
        mDbAdapter.createDevice("赵三","1788888455","1788888455","1788888455", false);
        mDbAdapter.createDevice("赵三","1788888455","1788888455","1788888455", false);

          }

//创建自定义对话框
    private void fireCustomDialog(final Device device) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.device_custom);
        TextView titleView = (TextView) dialog.findViewById(R.id.custom_title);
        final EditText editName = (EditText) dialog.findViewById(R.id.custom_edit_name);
        final EditText editMan = (EditText) dialog.findViewById(R.id.custom_edit_man);
        final EditText editNumber = (EditText) dialog.findViewById(R.id.custom_edit_number);
        final EditText editContent = (EditText) dialog.findViewById(R.id.custom_edit_content);
        Button commitButton = (Button) dialog.findViewById(R.id.custom_button_commit);
        final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.custom_check_box);
        LinearLayout rootLayout = (LinearLayout) dialog.findViewById(R.id.custom_root_layout);
        final boolean isEditOperation = (device != null);
        //this is for an edit
        if (isEditOperation) {
            titleView.setText("编辑基本信息");
            checkBox.setChecked(device.getState() == 1);
            editName.setText(device.getName());
            editMan.setText(device.getMan());
            editContent.setText(device.getContent());
            editNumber.setText(device.getNumber());
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));//修改编辑对话框的背景色
        }
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = editName.getText().toString();
                String deviceMan = editMan.getText().toString();
                String deviceContent = editContent.getText().toString();
                String deviceNumber = editNumber.getText().toString();
                if (isEditOperation) {
                    Device deviceEdited = new Device(device.getId(),deviceName,
                            deviceContent,deviceMan,deviceNumber, checkBox.isChecked() ? 1 : 0);
                    mDbAdapter.updateDevice(deviceEdited);
                    //this is for new device
                } else {
                    mDbAdapter.createDevice(deviceName,deviceContent,deviceMan,deviceNumber, checkBox.isChecked());
                }
                mCursorAdapter.changeCursor(mDbAdapter.fetchAllDevices());
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
        getMenuInflater().inflate(R.menu.menu_devices, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bluetooth:
                fireCustomDialog(null);
                return true;
            case R.id.action_wifi:
                fireCustomDialog(null);
                return true;
            case R.id.action_wlan:
                fireCustomDialog(null);
                return true;
            default:
                return false;
        }
    }

    public void addDevice(View view){  fireCustomDialog(null); }

    public void backHome(View view){ finish();}
    public void showBluetooth(View view){ startActivity(new Intent(this, BluetoothActivity.class)); }
    public void showMain(View view){ startActivity(new Intent(this, MainActivity.class)); }
    public void showDevice(View view){ startActivity(new Intent(this, DeviceActivity.class)); }
    public void showService(View view){ startActivity(new Intent(this, ServiceActivity.class)); }
    public void showAccount(View view){ startActivity(new Intent(this, AccountActivity.class)); }

}
