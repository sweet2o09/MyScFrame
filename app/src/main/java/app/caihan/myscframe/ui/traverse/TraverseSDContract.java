package app.caihan.myscframe.ui.traverse;

import com.caihan.scframe.framework.v1.support.MvpView;

import java.io.File;
import java.util.ArrayList;

/**
 * @author caihan
 * @date 2019/3/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public interface TraverseSDContract {

    interface View extends MvpView {

        void getFile(ArrayList<File> delList);
    }

}