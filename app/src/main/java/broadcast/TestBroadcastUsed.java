package broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by hrh on 2015/8/31.
 */
public class TestBroadcastUsed extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
    }
}
