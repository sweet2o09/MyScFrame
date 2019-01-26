package app.caihan.myscframe.ui.ninephoto;

import com.caihan.scframe.utils.IUnProguard;
import com.caihan.scframe.utils.text.BaseParser;
import com.caihan.scframe.widget.photo.NormalNinePhotoItem;

import java.util.List;

/**
 * @author caihan
 * @date 2019/1/17
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class NinePhotoShowItem implements IUnProguard {

    private String headImageUrl;//头像
    private String title;//标题
    private String content;//内容
    private String showAsLargeWhenOnlyOne;//单张是否显示大图 0=否 1=是
    private String showTwoItemSpanCount;//张数为2,4 是否修改显示效果 0=否 1=是
    private List<NormalNinePhotoItem> imageList;//图片列表

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getShowAsLargeWhenOnlyOne() {
        return BaseParser.parseInt(showAsLargeWhenOnlyOne) == 1;
    }

    public void setShowAsLargeWhenOnlyOne(String showAsLargeWhenOnlyOne) {
        this.showAsLargeWhenOnlyOne = showAsLargeWhenOnlyOne;
    }

    public boolean getShowTwoItemSpanCount() {
        return BaseParser.parseInt(showTwoItemSpanCount) == 1;
    }

    public void setShowTwoItemSpanCount(String showTwoItemSpanCount) {
        this.showTwoItemSpanCount = showTwoItemSpanCount;
    }

    public List<NormalNinePhotoItem> getImageList() {
        return imageList;
    }

    public void setImageList(List<NormalNinePhotoItem> imageList) {
        this.imageList = imageList;
    }
}
