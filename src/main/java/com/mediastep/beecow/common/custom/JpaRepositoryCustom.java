package com.mediastep.beecow.common.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import javax.persistence.metamodel.SingularAttribute;

@NoRepositoryBean
public interface JpaRepositoryCustom<ENTITY, ID> {
    <N extends Number> PageImplCustom<ENTITY> findAll(@Nullable Specification<ENTITY> spec, Pageable pageable,
                                            Class<ENTITY> domainClass, SingularAttribute<ENTITY, N> attributeTotal);
}
