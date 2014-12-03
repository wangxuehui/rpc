package in.srid.serializer.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class ImmutableModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String value;

    public ImmutableModel(@JsonProperty( "value" ) @com.fasterxml.jackson.annotation.JsonProperty( "value" ) final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( value == null ) ? 0 : value.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        ImmutableModel other = (ImmutableModel) obj;
        if ( value == null ) {
            if ( other.value != null )
                return false;
        }
        else if ( !value.equals( other.value ) )
            return false;
        return true;
    }
}
