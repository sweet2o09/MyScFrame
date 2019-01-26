package app.caihan.myscframe.utils;

import android.content.Context;

import com.caihan.scframe.utils.file.IFileConfigure;
import com.caihan.scframe.utils.file.ScFileOptionsUtils;

import java.io.File;

/**
 * @author caihan
 * @date 2019/1/26
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class FileOptionsUtils extends ScFileOptionsUtils {


    public FileOptionsUtils(Context context, ScFileInternalDir internalDir) {
        super(context, internalDir);
    }

    public FileOptionsUtils(Context context, ScFileExternalDir externalDir) {
        super(context, externalDir);
    }

    public FileOptionsUtils(Context context, String dir) {
        super(context, dir);
    }

    public FileOptionsUtils(Context context, File rootFile, String dir) {
        super(context, rootFile, dir);
    }

    public FileOptionsUtils(Context context) {
        super(context, new IFileConfigure(){

            @Override
            public String getExternalStorageAppBaseChild() {
                return "ScFrameDemoFile";
            }
        });
    }
}
