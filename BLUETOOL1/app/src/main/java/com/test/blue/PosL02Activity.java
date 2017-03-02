package com.test.blue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.test.device.BlueToothBean;
import com.test.newland.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PosL02Activity extends BaseActivity implements OnClickListener {


    private TextView tvMessage;
    private ListView lvList;
    private ArrayAdapter<String> adapter;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final List<BlueToothBean> devices = new ArrayList<BlueToothBean>();
    /**
     * 默认蓝牙接受处理
     */
    private final BroadcastReceiver discoveryReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ifAddressExist(device.getAddress())) {
                    return;
                }
                BlueToothBean btContext = new BlueToothBean(
                        device.getName() == null ? device.getAddress() : device.getName(),
                        device.getAddress());
                devices.add(btContext);
                adapter.add(btContext.getName() + "\n点击链接设备");

            }
        }
    };

    private HashMap<String, String> map = new HashMap<String, String>();
    private Dialog dialog;
    private String name, address;
    private boolean isConnected = false;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    tvMessage.setText(msg.obj.toString());
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("config", Activity.MODE_PRIVATE);
        address = sp.getString("BluetoothAddress", "");
        name = sp.getString("BluetoothName", "");

        initData();
        //
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(discoveryReciever, filter);
    }

    private void initData() {


        adapter = new ArrayAdapter<String>(this, R.layout.blue_tooth_item_adapter);
        initView();

        if (bluetoothAdapter.isEnabled()) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    bluetoothAdapter.startDiscovery();
                }
            }).start();
        } else {
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enabler, 0);
        }

    }

    private void initView() {
        TextView tvLockBluetooth = (TextView) findViewById(R.id.bluetooth_name);
        tvLockBluetooth.setText(name);
        TextView tvConnectName = (TextView) findViewById(R.id.bluetooth_name_now);
        findViewById(R.id.back).setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvMessage = (TextView) findViewById(R.id.pbbt_tv_msg);

        Button btnSearch = (Button) findViewById(R.id.btn_connect_bluetooth);
        btnSearch.setOnClickListener(this);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.bbpos_blue_tooth_dialog);
        dialog.setTitle("蓝牙装置");
        ListView lvList = (ListView) dialog.findViewById(R.id.bbtd_lv2);
        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int n = position;
                new Thread() {
                    public void run() {
                        try {
                            address = devices.get(n).getAddress();
                            name = devices.get(n).getName();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }.start();
                Toast.makeText(PosL02Activity.this, "蓝牙名字" + name + "地址" + address, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.findViewById(R.id.bbtd_btn_cancle).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // controller.stopScan();
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
    }

    /**
     * �?查是蓝牙地址是否已经存在
     */
    private boolean ifAddressExist(String addr) {
        for (BlueToothBean devcie : devices) {
            if (addr.equals(devcie.getAddress())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        bluetoothAdapter.startDiscovery();
                    }
                }).start();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else if (id == R.id.btn_connect_bluetooth) {
            dialog.show();
			/*
			 * if ("".equals(address)) { dialog.show(); } else { // 直接连接蓝牙刷卡�?
			 * new Thread() { public void run() { try {
			 * initMe3xDeviceController(new BlueToothV100ConnParams( address));
			 * } catch (Exception e) { e.printStackTrace();
			 * 
			 * 
			 * } }; }.start(); }
			 */
        }
    }


}
