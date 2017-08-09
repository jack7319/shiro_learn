package com.bizideal.mn.dialect;

import static at.pollux.thymeleaf.shiro.processor.ThymeleafFacade.evaluateAsStringsWithDelimiter;
import static at.pollux.thymeleaf.shiro.processor.ThymeleafFacade.getRawValue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

import com.bizideal.mn.utils.SessionHelper;

/**
 * @author 作者 liulq:
 * @data 创建时间：2017年3月10日 下午5:13:34
 * @version 1.0
 */
public class MyThymeleafProcessor extends AbstractAttributeTagProcessor {

	private static final String DELIMITER = ",";
	private static final String ATTRIBUTE_NAME = "pricipal";
	private static final int PRECEDENCE = 300;

	protected MyThymeleafProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, // 处理thymeleaf 的模型
				dialectPrefix, // 标签前缀名
				null, // No tag name: match any tag name
				false, // No prefix to be applied to tag name
				ATTRIBUTE_NAME, // 标签前缀的 属性 例如：< risk:sansiEncrypt="">
				true, // Apply dialect prefix to attribute name
				PRECEDENCE, // Precedence (inside dialect's precedence)
				true); // Remove the matched attribute afterwards
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		final String rawValue = getRawValue(tag, attributeName); // 获取标签内容表达式
		String exper = null;
		if (StringUtils.isNotBlank(rawValue)) {
			exper = rawValue; // 获取表达式
		}
		// 通过IStandardExpression 解析器 解析表达式获取参数
		final List<String> sessionIds = evaluateAsStringsWithDelimiter(context, exper, DELIMITER);
		final String elementCompleteName = tag.getElementCompleteName(); // 标签名
		// 创建模型
		final IModelFactory modelFactory = context.getModelFactory();
		final IModel model = modelFactory.createModel();
		// 添加模型 标签
		model.add(modelFactory.createOpenElementTag(elementCompleteName));
		for (String sessionId : sessionIds) {
			// 创建 html5标签 文本返回数据
			model.add(modelFactory.createText(HtmlEscape.escapeHtml5(SessionHelper.getAccount(sessionId))));
		}
		// 添加模型 标签
		model.add(modelFactory.createCloseElementTag(elementCompleteName));
		// 替换页面标签
		structureHandler.replaceWith(model, false);
	}

}
