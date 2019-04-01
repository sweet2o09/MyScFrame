package app.caihan.myscframe.ui.recyclerview;

/**
 * @author caihan
 * @date 2019/3/31
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NormalMultipleEntity {
    public static final int TYPE_0 = 0;
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;

    public int type;
    public String content;

    public NormalMultipleEntity(int type) {
        this.type = type;
    }

    public NormalMultipleEntity(int type, String content) {
        this.type = type;
        this.content = content;
    }
}
