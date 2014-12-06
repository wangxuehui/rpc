package org.skyim.serializer;

import java.util.concurrent.ConcurrentHashMap;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class SchemaUtils {

    private static ConcurrentHashMap<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

    public static <T> Schema<T> getSchema( Class<T> clazz ) {
        @SuppressWarnings( "unchecked" )
        Schema<T> schema = (Schema<T>) cachedSchema.get( clazz );
        if ( schema == null ) {
            schema = RuntimeSchema.createFrom( clazz );
            cachedSchema.put( clazz, schema );
        }
        return schema;
    }
}
