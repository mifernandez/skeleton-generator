package org.sklsft.generator.model.metadata;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="column")
public class ColumnMetaData {

	/*
	 * properties
	 */
	@XmlAttribute(required=true)
	private String name;
	@XmlAttribute(required=true)
	private DataType dataType;
	@XmlAttribute
	private boolean nullable = true;
	@XmlAttribute
	private String referenceTableName;
	@XmlAttribute
	private RelationType referenceTableRelation;
	@XmlAttribute
	private Format format;
	@XmlAttribute
	private boolean editable = true;
	@XmlAttribute
	private Visibility visibility;
	@XmlAttribute
	private String rendering;
	
	@XmlElementWrapper(name="annotations")
	@XmlElement(name="annotation")
	private List<String> annotations;
	
	/*
	 * getters and setters
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public String getReferenceTableName() {
		return referenceTableName;
	}
	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}
	public RelationType getReferenceTableRelation() {
		return referenceTableRelation;
	}
	public void setReferenceTableRelation(RelationType referenceTableRelation) {
		this.referenceTableRelation = referenceTableRelation;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public Visibility getVisibility() {
		return visibility;
	}
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	public String getRendering() {
		return rendering;
	}
	public void setRendering(String rendering) {
		this.rendering = rendering;
	}
	public List<String> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ColumnMetaData [name=" + name + ", dataType=" + dataType + ", nullable=" + nullable + ", referenceTableName=" + referenceTableName + ", referenceTableRelation="
				+ referenceTableRelation + ", format=" + format + ", editable=" + editable + ", visibility=" + visibility + ", rendering=" + rendering + ", annotations=" + annotations + "]";
	}	
}
