package app.caihan.myscframe.ui.traverse;

import android.content.Context;

import com.caihan.scframe.framework.v1.support.impl.MvpBasePresenter;
import com.caihan.scframe.utils.log.ScLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author caihan
 * @date 2019/3/13
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class TraverseSDPresenter extends MvpBasePresenter<TraverseSDContract.View> {


    public TraverseSDPresenter(Context context) {
        super(context);
    }

    /**
     * 主目录:
     * 1.D:\AndroidCompile\images
     * 2.D:\AndroidCompile\daogou\images
     * 3.D:\AppDown\laidianyi\android
     * 4.D:\AppDown\Daogou\android
     * <p>
     * eg:
     * D:\AndroidCompile\daogou\images\指间茶店\1.1.2
     * D:\AndroidCompile\daogou\images\指间茶店\1.1.3
     *
     * @param pathName
     */
    public void find(String pathName) {
        File fileDir = new File(pathName);
        ScLog.debug("主目录 :: " + fileDir.getAbsolutePath());
        if (isDir(fileDir)) {
            ArrayList<File> delList = new ArrayList<>();
            //是文件夹,遍历文件夹下的文件夹
            List<File> twofiles = listDirInDir(fileDir);
            for (File file : twofiles) {
                ScLog.debug("二级目录 :: " + file.getAbsolutePath());
                if (isDir(file)) {
                    List<File> trefiles = listDirInDir(file);
                    ScLog.debug("二级目录下有" + trefiles.size() + "个子文件夹");
                    if (trefiles.size() > 2) {
                        for (int i = 0; i < trefiles.size() - 2; i++) {
                            ScLog.debug("添加需要删除的三级目录 :: " + trefiles.get(i).getAbsolutePath());
                            delList.add(trefiles.get(i));
                        }
                    }
                }
            }
            getView().getFile(delList);
            for (File file : delList) {
                ScLog.debug("要删除的目录 :: " + file.getAbsolutePath());
                delete(file);
            }
        } else {
            getView().getFile(new ArrayList<>());
        }
    }

    /**
     * 是否是文件夹
     *
     * @param file
     * @return
     */
    private boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 返回目录下的文件列表
     *
     * @param dir
     * @return
     */
    private List<File> listDirInDir(final File dir) {
        if (!isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            File file;
            HashMap<String, File> hashMap = new HashMap<>();
            long lastModified = 0;
            long lastModifiedOld1 = 0;//保留最新的
            long lastModifiedOld2 = 0;//保留第二新的
            long changeModified = 0;//中间参数
            for (int i = 0; i < files.length; i++) {
                file = files[i];
                if (!file.isDirectory()) {
                    continue;
                }
                lastModified = getFileLastModified(file);
                if (i == 0) {
                    lastModifiedOld1 = lastModified;
                    putHashMap(hashMap, 1, file);
                } else if (i == 1) {
                    lastModifiedOld2 = lastModified;
                    putHashMap(hashMap, 2, file);
                    if (lastModifiedOld1 < lastModifiedOld2) {
                        //调换位置
                        changeModified = lastModifiedOld2;
                        lastModifiedOld2 = lastModifiedOld1;
                        lastModifiedOld1 = changeModified;
                        changeHashMap(hashMap);
                    }
                } else {
                    if (lastModified > lastModifiedOld1) {
                        //当前是最新的,替换
                        lastModifiedOld2 = lastModifiedOld1;
                        lastModifiedOld1 = lastModified;
                        changeHashMap(hashMap);
                        putHashMap(hashMap, 1, file);
                    } else if (lastModified > lastModifiedOld2) {
                        //当前是第二新的
                        putHashMap(hashMap, 2, file);
                    }
                }
            }
            for (File file2 : files) {
                if (file2.isDirectory() && !inHashMap(hashMap, file2)) {
                    list.add(file2);
                }
            }
        }
        return list;
    }

    private boolean inHashMap(HashMap<String, File> hashMap, File file) {
        File file1 = hashMap.get("1");
        File file2 = hashMap.get("2");
        return file.getAbsolutePath().equals(file1.getAbsolutePath()) || file.getAbsolutePath().equals(file2.getAbsolutePath());
    }

    private void putHashMap(HashMap<String, File> hashMap, int type, File file) {
        if (type == 1) {
            hashMap.put("1", file);
        } else if (type == 2) {
            hashMap.put("2", file);
        }
    }

    private void changeHashMap(HashMap<String, File> hashMap) {
        File file1 = hashMap.get("1");
        File file2 = hashMap.get("2");
        putHashMap(hashMap, 1, file2);
        putHashMap(hashMap, 2, file1);
    }

    /**
     * 删除
     *
     * @param file
     * @return
     */
    private boolean delete(final File file) {
        if (file == null) return false;
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        return deleteFile(file);
    }

    private boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    private boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    private long getFileLastModified(final File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    @Override
    public void destroy() {

    }
}