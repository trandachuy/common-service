package com.mediastep.beecow.common.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PageImplCustom<T> extends PageImpl<T> {
    private Number totalRevenue;

    public PageImplCustom(List<T> content, Number totalRevenue) {
        this(content, Pageable.unpaged(), CollectionUtils.isEmpty(content) ? 0L : (long) content.size(), totalRevenue);
    }

    public PageImplCustom(List<T> content, Pageable pageable, long count, Number totalRevenue) {
        super(content, pageable, count);
        this.totalRevenue = totalRevenue;
    }
}
