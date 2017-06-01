package com.caihan.scframe.update.agent;

import com.caihan.scframe.update.error.UpdateError;

public interface ICheckAgent {
    void setInfo(String info);

    void setError(UpdateError error);
}