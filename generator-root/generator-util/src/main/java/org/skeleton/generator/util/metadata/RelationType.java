package org.skeleton.generator.util.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 * A skeleton.xml file is a database like representation of meta data<br/>
 * Relations are therefore indicated in a reverse way i.e from the point of view of a child from its parent excepted for unique component which is the classical way to represent a hibernate one-to-one component<br/>
 * In the current release, the following relations are supported :
 * <li>many-to-one : the referenced bean will have a bidirectional one-to-many collection
 * <li>many-to-one : the referenced bean will have a unidirectional (not really if envers auditing is activated) one-to-many collection<br/>
 * the collection will be managed by the referenced bean
 * <li>simple unique property, not really a relation
 * <li>unique component : a one-to-one relation where the referenced bean is managed by the current bean
 * <li>one-to-one : a bi-directional one-to-one relation
 * <li>property : default behavior of a referenced bean
 * 
 * @author Nicolas Thibault
 *
 */
public enum RelationType {
	MANY_TO_ONE("many to one"),
    MANY_TO_ONE_COMPONENT("many to one component"),
    UNIQUE("unique"),
    UNIQUE_COMPONENT("unique component"),
    ONE_TO_ONE("one to one"),
    ONE_TO_ONE_COMPONENT("one to one component"),
    PROPERTY("");
    
    private static final Map<String, RelationType> reverseMap = new HashMap<String, RelationType>();
	static{
		for(RelationType relationType : values()){
			reverseMap.put(relationType.getValue(), relationType);
		}
	}
	
	private String value;
	
	private RelationType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static RelationType byValue(String value){
		RelationType relationType = reverseMap.get(value);
		if(relationType==null) {
			throw new IllegalArgumentException("No RelationType corresponding to value " + value);
		}
		return relationType;
	}
	
	public static Boolean isUnique(RelationType relationType)
    {
        switch (relationType)
        {
            case UNIQUE:
                return true;

            case ONE_TO_ONE:
                return true;

            case ONE_TO_ONE_COMPONENT:
                return true;

            default:
                return false;

        }
    }

    public static Boolean isComponentLink(RelationType relationType)
    {
        switch (relationType)
        {
            case UNIQUE_COMPONENT:
                return true;

            case ONE_TO_ONE_COMPONENT:
                return true;

            case MANY_TO_ONE_COMPONENT:
                return true;

            default:
                return false;

        }
    }
}