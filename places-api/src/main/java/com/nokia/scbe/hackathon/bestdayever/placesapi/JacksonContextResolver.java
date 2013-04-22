package com.nokia.scbe.hackathon.bestdayever.placesapi;

import javax.ws.rs.ext.ContextResolver;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;


public class JacksonContextResolver implements ContextResolver<ObjectMapper> {
	
	private static final Logger LOG = Logger.getLogger(JacksonContextResolver.class);
	protected static ObjectMapper jacksonMapper = new ObjectMapper();
	protected static boolean initialized = false;

	protected static void init() {
		LOG.info("configuring main jackson mapper");
		jacksonMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, true);
		jacksonMapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		jacksonMapper.configure(Feature.WRITE_NULL_MAP_VALUES, false);
		jacksonMapper.configure(Feature.WRAP_ROOT_VALUE, false);
		// mapper.configure(Feature.INDENT_OUTPUT, true);
		jacksonMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jacksonMapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);

		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector secondary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		jacksonMapper.setAnnotationIntrospector(pair);

		initialized = true;
	}

//	@Override
	public ObjectMapper getContext(Class<?> type) {
		if (!initialized) {
			init();
		}
		return jacksonMapper;
	}

	public static ObjectMapper getInstance() {
		if (!initialized) {
			init();
		}
		return jacksonMapper;
	}

}
