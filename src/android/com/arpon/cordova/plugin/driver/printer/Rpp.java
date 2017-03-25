package com.arpon.cordova.plugin.driver.printer;

import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver;

import android.os.Handler;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class Rpp extends CordovaPlugin {

    private static final String ACTION_CONNECT = "connect";
    private static final String ACTION_IS_CONNECTED = "isConnected";
    private static final String ACTION_CLOSE = "close";
    private static final String ACTION_PRINT = "print";
    private static final String ACTION_PRINT_AND_CLOSE = "printAndClose";

    private static BluetoothPrintDriver mChatService = null;
    private static BluetoothAdapter mBluetoothAdapter = null;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        if(mChatService == null) { mChatService = new BluetoothPrintDriver(null, new Handler()); }
        if(mBluetoothAdapter == null) { mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        JSONObject r = new JSONObject();

        if(ACTION_CONNECT.equals(action)) {

            if(BluetoothPrintDriver.IsNoConnection()){
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(args.getString(0));
                mChatService.connect(device);
            }

            callbackContext.success("OK");
            return true;
        } else if(ACTION_IS_CONNECTED.equals(action)) {

            if (BluetoothPrintDriver.IsNoConnection()) {
                callbackContext.error("NO CONNECTED");
            } else {
                callbackContext.success("OK");
            }

            return true;
        } else if(ACTION_CLOSE.equals(action)) {
            mChatService.stop();
            callbackContext.success("OK");

            return true;
        } else if(ACTION_PRINT.equals(action)) {

            JSONObject data = args.getJSONObject(0);
            if(this.printLines(data)) {
                callbackContext.success("OK");
            } else {
                callbackContext.error("ERROR");
            }

            return true;
        } else if(ACTION_PRINT_AND_CLOSE.equals(action)) {

            JSONObject data = args.getJSONObject(0);
            if(this.printLines(data)) {
                callbackContext.success("OK");
                mChatService.stop();
            } else {
                callbackContext.error("ERROR");
            }

            return true;
        }

        return false;
    }

    private boolean printLines(JSONObject data) throws JSONException {

        if(BluetoothPrintDriver.IsNoConnection()){
            return false;
        }

        JSONArray lines = data.getJSONArray("lines");
        BluetoothPrintDriver.Begin();

        if(data.has("marginTop")) {
            int marginTop = data.getInt("marginTop");
            for(int i = 1; i <= marginTop; i++) {
                BluetoothPrintDriver.LF();
                BluetoothPrintDriver.CR();
            }
        }

        for (int i = 0; i < lines.length(); ++i) {
            JSONObject line = lines.getJSONObject(i);

            setLineFormat(line);

            BluetoothPrintDriver.BT_Write(line.getString("text"));
            BluetoothPrintDriver.BT_Write("\r");
        }

        if(data.has("marginBottom")) {
            int marginTop = data.getInt("marginBottom");
            for(int i = 1; i <= marginTop; i++) {
                BluetoothPrintDriver.LF();
                BluetoothPrintDriver.CR();
            }
        }

        return true;
    }

    private void setLineFormat(JSONObject data) throws JSONException {

        BluetoothPrintDriver.InitPrinter();

        if(data.has("size")) { BluetoothPrintDriver.SetFontEnlarge((byte)data.getInt("size")); }
        if(data.has("s")) { BluetoothPrintDriver.SetFontEnlarge((byte)data.getInt("s")); }

        if(data.has("align")) { BluetoothPrintDriver.SetAlignMode((byte)data.getInt("align")); }
        if(data.has("a")) { BluetoothPrintDriver.SetAlignMode((byte)data.getInt("a")); }

        if(data.has("bold") || data.has("b")) { BluetoothPrintDriver.SetBold((byte)0x01); }
        if(data.has("underline") || data.has("u")) { BluetoothPrintDriver.SetUnderline((byte)0x02); }
        if(data.has("hightlight") || data.has("h")) { BluetoothPrintDriver.SetBlackReversePrint((byte)0x01); }

    }

}
