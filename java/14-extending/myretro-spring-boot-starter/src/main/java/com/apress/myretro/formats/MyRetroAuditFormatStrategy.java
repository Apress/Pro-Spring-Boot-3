package com.apress.myretro.formats;

import com.apress.myretro.model.MyRetroAuditEvent;

public interface MyRetroAuditFormatStrategy {
    String format(MyRetroAuditEvent event);
    String prettyFormat(MyRetroAuditEvent event);
}
