package com.mediastep.beecow.common.custom;

import org.springframework.data.domain.Pageable;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PageableExecutionCustomUtils {

    public enum alias {
        TOTAL_COUNT,
        TOTAL_REVENUE
    }

    protected static <T> PageImplCustom<T> getPage(List<T> content, Pageable pageable, Tuple tuple) {
        long totalCount = 0;
        Number totalRevenue = 0;
        if (Objects.nonNull(tuple)) {
            totalCount = (Long) tuple.get(alias.TOTAL_COUNT.name());
            totalRevenue = (Number) tuple.get(alias.TOTAL_REVENUE.name());
        }
        if (!pageable.isUnpaged() && pageable.getOffset() != 0L) {
            return content.size() != 0 && pageable.getPageSize() > content.size()
                    ? new PageImplCustom(content, pageable, pageable.getOffset() + (long) content.size(), totalRevenue)
                    : new PageImplCustom(content, pageable, totalCount, totalRevenue);
        } else {
            return !pageable.isUnpaged() && pageable.getPageSize() <= content.size()
                    ? new PageImplCustom(content, pageable, totalCount, totalRevenue)
                    : new PageImplCustom(content, pageable, content.size(), totalRevenue);
        }
    }

    protected PageableExecutionCustomUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
