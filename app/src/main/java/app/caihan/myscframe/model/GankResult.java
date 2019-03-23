package app.caihan.myscframe.model;

import java.util.List;

/**
 * @author caihan
 * @date 2019/3/23
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class GankResult {

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankResult{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
