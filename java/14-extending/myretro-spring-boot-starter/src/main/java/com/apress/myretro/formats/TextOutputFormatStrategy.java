package com.apress.myretro.formats;

import com.apress.myretro.model.MyRetroAuditEvent;

public class TextOutputFormatStrategy implements MyRetroAuditFormatStrategy {
    @Override
    public String format(MyRetroAuditEvent event) {
        return event.toString();
    }

    @Override
    public String prettyFormat(MyRetroAuditEvent event) {
        return "\n\n" + event.toString() + "\n";
    }
}
