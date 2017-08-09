package com.bizideal.mn.dialect;

import java.util.LinkedHashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

/**
 * @author 作者 liulq:
 * @data 创建时间：2017年3月10日 下午5:43:06
 * @version 1.0
 */
public class MyDialet extends AbstractProcessorDialect {

	private static final String NAME = "LIULQ";
	private static final String PREFIX = "liulq";

	public MyDialet() {
		super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		return createStandardProcessorsSet(dialectPrefix);
	}

	private Set<IProcessor> createStandardProcessorsSet(String dialectPrefix) {
		LinkedHashSet<IProcessor> processors = new LinkedHashSet<IProcessor>();
		processors.add(new MyThymeleafProcessor(dialectPrefix));
		processors.add(new IsForceoutProcessor(dialectPrefix));
		return processors;
	}

}
