package org.skyim.serializer;

public interface Serializer {
    <T> byte[] serialize( T source );
}
