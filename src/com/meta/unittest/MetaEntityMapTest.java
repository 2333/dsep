package com.meta.unittest;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.meta.entity.MetaAttributeMap;
import com.meta.entity.MetaEntityMap;
import com.meta.service.MetaEntityMapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-logger.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml"})
public class MetaEntityMapTest {
	@Autowired
	private MetaEntityMapService metaEntityMapService;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		List<MetaEntityMap> metaEntityMaps = metaEntityMapService.getEntityMaps("ET20130106", "E20130201", "JSJ");
		Set<MetaAttributeMap> metaAttrMaps= metaEntityMaps.get(0).getAttributeMaps();
		for(MetaAttributeMap attributeMap : metaAttrMaps){
			attributeMap.getOriginAttr();
		}
	}

}
