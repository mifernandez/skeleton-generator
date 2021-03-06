package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;

import org.sklsft.generator.bc.metadata.interfaces.TableFactory;
import org.sklsft.generator.model.metadata.ColumnMetaData;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.Format;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.util.SQLNaming;
import org.springframework.stereotype.Component;



@Component
public class TableFactoryImpl implements TableFactory {



	@Override
	public Table scanTable(TableMetaData tableMetaData, Package myPackage) {
		Table table = new Table();
        table.myPackage = myPackage;
        table.originalName = tableMetaData.getName();
        table.name = SQLNaming.rename(table.originalName, myPackage.model.project.databaseEngine);
        table.cardinality = tableMetaData.getCardinality();
        table.columns = new ArrayList<Column>();

        Column idColumn = new Column();
        idColumn.name = "ID";
        idColumn.originalName = "ID";
        idColumn.dataType = DataType.LONG;
        idColumn.relation = RelationType.PROPERTY;
        idColumn.nullable = false;
        idColumn.unique = true;
        table.columns.add(idColumn);
        
        return table;
	}

	@Override
	public Table fillTable(TableMetaData tableMetaData, Package myPackage) {
		Table table = myPackage.model.findTable(tableMetaData.getName());
		
        for (ColumnMetaData columnMetaData : tableMetaData.getColumns()) {
            Column column = new Column();
            column.originalName = columnMetaData.getName();
            column.name = SQLNaming.rename(column.originalName, table.myPackage.model.project.databaseEngine);
            column.dataType = columnMetaData.getDataType();
            column.nullable = (columnMetaData.isNullable());
            if (columnMetaData.getReferenceTableRelation() != null) {
            	column.relation = columnMetaData.getReferenceTableRelation();
            } else {
            	column.relation = RelationType.PROPERTY;
            }
            
            column.deleteCascade = (column.relation.equals(RelationType.MANY_TO_ONE_COMPONENT));
            column.referenceTable = myPackage.model.findTable(columnMetaData.getReferenceTableName());
            column.unique = (RelationType.isUnique(column.relation));
            if (columnMetaData.getFormat()!=null) {
            	column.format = columnMetaData.getFormat();
            } else {
            	column.format = Format.DEFAULT;
            }
            column.editable = columnMetaData.isEditable();
            if (columnMetaData.getVisibility()!=null) {
            	column.visibility = columnMetaData.getVisibility();
            } else {
            	column.visibility = Visibility.VISIBLE;
            }
            column.rendering = columnMetaData.getRendering();
            column.annotations = columnMetaData.getAnnotations();
            table.columns.add(column);
        }
		
        return table;
	}
	
}
