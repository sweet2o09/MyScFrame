package com.caihan.scframe.update.defaultclass;

import android.content.Context;
import android.widget.Toast;

import com.caihan.scframe.update.ScUpdateUtil;
import com.caihan.scframe.update.error.UpdateError;
import com.caihan.scframe.update.listener.OnFailureListener;

/**
 * Created by caihan on 2017/5/22.
 * 默认的错误提示语监听
 */
public class DefaultFailureListener implements OnFailureListener {
    private Context mContext;

    public DefaultFailureListener(Context context) {
        mContext = context;
    }

    @Override
    public void onFailure(UpdateError error) {
        ScUpdateUtil.log(error.toString());
        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
    }
}
