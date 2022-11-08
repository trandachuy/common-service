package com.mediastep.beecow.common.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@Repository
@Transactional(
        readOnly = true
)
public class JpaRepositoryCustomImpl<ENTITY, ID> implements JpaRepositoryCustom<ENTITY, ID> {

    private final EntityManager em;

    public JpaRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public <N extends Number> PageImplCustom<ENTITY> findAll(@Nullable Specification<ENTITY> spec, Pageable pageable,
                                                             Class<ENTITY> domainClass, SingularAttribute<ENTITY, N> attributeTotal) {
        TypedQuery<ENTITY> query = this.getQuery(spec, pageable, domainClass);
        return isUnpaged(pageable)
                ? new PageImplCustom(query.getResultList(),
                (Number) this.executeGetTotal(spec, domainClass, attributeTotal).get(PageableExecutionCustomUtils.alias.TOTAL_REVENUE.name()))
                : this.readPage(query, domainClass, pageable, spec, attributeTotal);
    }

    private static boolean isUnpaged(Pageable pageable) {
        return pageable.isUnpaged();
    }

    protected <S extends ENTITY, N extends Number> PageImplCustom<S> readPage(TypedQuery<S> query, Class<S> domainClass,
                                                                              Pageable pageable, @Nullable Specification<S> spec,
                                                                              SingularAttribute<S, N> attributeTotal) {
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        return PageableExecutionCustomUtils.getPage(query.getResultList(), pageable, this.executeGetCountAndTotal(spec, domainClass, attributeTotal));
    }


    private <S extends ENTITY, N extends Number> TypedQuery<Tuple> getCountAndTotal(@Nullable Specification<S> spec,
                                                                                    Class<S> domainClass,
                                                                                    SingularAttribute<S, N> attributeTotal) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<S> root = this.applySpecificationToCriteria(spec, domainClass, query);
        query.multiselect(
                builder.sum(root.get(attributeTotal)).alias(PageableExecutionCustomUtils.alias.TOTAL_REVENUE.name()),
                query.isDistinct()
                        ? builder.countDistinct(root).alias(PageableExecutionCustomUtils.alias.TOTAL_COUNT.name())
                        : builder.count(root).alias(PageableExecutionCustomUtils.alias.TOTAL_COUNT.name())
        );
        return this.em.createQuery(query);
    }


    private <S extends ENTITY, N extends Number> TypedQuery<Tuple> getTotal(@Nullable Specification<S> spec,
                                                                            Class<S> domainClass,
                                                                            SingularAttribute<S, N> attributeTotal) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<S> root = this.applySpecificationToCriteria(spec, domainClass, query);
        query.multiselect(
                builder.sum(root.get(attributeTotal)).alias(PageableExecutionCustomUtils.alias.TOTAL_REVENUE.name())
        );
        return this.em.createQuery(query);
    }

    private <S extends ENTITY, N extends Number> Tuple executeGetTotal(@Nullable Specification<S> spec,
                                                                       Class<S> domainClass,
                                                                       SingularAttribute<S, N> attributeTotal) {
        return this.getTotal(spec, domainClass, attributeTotal).getSingleResult();
    }

    protected <S extends ENTITY> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<S> root = this.applySpecificationToCriteria(spec, domainClass, query);
        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        query.orderBy(Collections.emptyList());
        return this.em.createQuery(query);
    }

    private <S extends ENTITY, N extends Number> Tuple executeGetCountAndTotal(@Nullable Specification<S> spec,
                                                                               Class<S> domainClass,
                                                                               SingularAttribute<S, N> attributeTotal) {
        return this.getCountAndTotal(spec, domainClass, attributeTotal).getSingleResult();
    }

    private static Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        long total = 0L;
        Long element;
        for (Iterator var3 = totals.iterator(); var3.hasNext(); total = total + (element == null ? 0L : element)) {
            element = (Long) var3.next();
        }
        return total;
    }

    protected TypedQuery<ENTITY> getQuery(@Nullable Specification<ENTITY> spec, Pageable pageable, Class<ENTITY> domainClass) {
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return this.getQuery(spec, domainClass, sort);
    }

    protected <S extends ENTITY> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);
        Root<S> root = this.applySpecificationToCriteria(spec, domainClass, query);
        query.select(root);
        if (sort.isSorted()) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }
        return this.em.createQuery(query);
    }

    private <S, U extends ENTITY> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec,
                                                                       Class<U> domainClass,
                                                                       CriteriaQuery<S> query) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");
        Root<U> root = query.from(domainClass);
        if (spec == null) {
            return root;
        } else {
            CriteriaBuilder builder = this.em.getCriteriaBuilder();
            Predicate predicate = spec.toPredicate(root, query, builder);
            if (predicate != null) {
                query.where(predicate);
            }

            return root;
        }
    }

}
