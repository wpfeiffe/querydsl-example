package com.wspfeiffer.apttestgradle.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.wspfeiffer.apttestgradle.entity.QThing;
import com.wspfeiffer.apttestgradle.entity.Thing;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ThingRepository extends
        PagingAndSortingRepository<Thing, Long>,
        QuerydslPredicateExecutor<Thing>,
        QuerydslBinderCustomizer<QThing> {

    @Override
    default void customize(QuerydslBindings bindings, QThing root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
