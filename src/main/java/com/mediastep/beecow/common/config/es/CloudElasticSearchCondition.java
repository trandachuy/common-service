/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : Sep 26, 2017
 *
 */
package com.mediastep.beecow.common.config.es;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ObjectUtils;

/**
 * The Class CloudESCondition.
 */
public class CloudElasticSearchCondition implements Condition {
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment env = context.getEnvironment();
		Object elasticSearchConfig = null;
		try{
			elasticSearchConfig = context.getBeanFactory().getBean("elasticSearchConfiguration");
		}catch(Exception e){
			//do nothing
		}

		try{
			elasticSearchConfig = context.getBeanFactory().getBean("elasticsearchConfiguration");
		}catch(Exception e){
			//do nothing
		}
		String shieldUser = env.getProperty("spring.data.elasticsearch.properties.shield.user");
		String clusterNodes = env.getProperty("spring.data.elasticsearch.cluster-nodes");
		return !ObjectUtils.isEmpty(elasticSearchConfig) && !ObjectUtils.isEmpty(shieldUser) && !ObjectUtils.isEmpty(clusterNodes);
	}

}
