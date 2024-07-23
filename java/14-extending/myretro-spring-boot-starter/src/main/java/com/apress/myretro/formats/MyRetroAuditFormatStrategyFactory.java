package com.apress.myretro.formats;

import com.apress.myretro.annotations.MyRetroAuditOutputFormat;

public class MyRetroAuditFormatStrategyFactory {

    public static MyRetroAuditFormatStrategy getStrategy(MyRetroAuditOutputFormat outputFormat) {
        switch (outputFormat) {
            case JSON:
                return new JsonOutputFormatStrategy();
            case TXT:
            default:
                return new TextOutputFormatStrategy();
        }
    }
}
