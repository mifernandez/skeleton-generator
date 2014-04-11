package org.skeleton.generator.bc.factory.model.impl;

import java.util.ArrayList;

import org.skeleton.generator.bc.factory.model.interfaces.TableFactory;
import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;
import org.skeleton.generator.model.om.Column;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.util.metadata.DataType;
import org.skeleton.generator.util.metadata.Format;
import org.skeleton.generator.util.metadata.RelationType;
import org.skeleton.generator.util.metadata.Visibility;
import org.skeleton.generator.util.naming.SQLNaming;
import org.springframework.stereotype.Component;



@Component
public class TableFactoryImpl implements TableFactory {

	public Table buildTable(TableMetaData tableMetaData, Package myPackage) {
		Table table = new Table();
		myPackage.tables.add(table);
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

        for (ColumnMetaData columnMetaData : tableMetaData.getColumns()) {
            Column column = new Column();
            column.originalName = columnMetaData.getName();
            column.name = SQLNaming.rename(column.originalName, table.myPackage.model.project.databaseEngine);
            column.dataType = DataType.byValue(columnMetaData.getDataType());
            column.nullable = (columnMetaData.isNullable());
            if (columnMetaData.getReferenceTableRelation() != null) {
            	column.relation = RelationType.byValue(columnMetaData.getReferenceTableRelation());
            } else {
            	column.relation = RelationType.PROPERTY;
            }
            
            column.deleteCascade = (column.relation.equals(RelationType.MANY_TO_ONE_COMPONENT) || column.relation.equals(RelationType.ONE_TO_ONE_COMPONENT));
            column.referenceTable = myPackage.model.findTable(columnMetaData.getReferenceTableName());
            column.unique = (RelationType.isUnique(column.relation));
            if (columnMetaData.getFormat()!=null) {
            	column.format = Format.byValue(columnMetaData.getFormat());
            } else {
            	column.format = Format.DEFAULT;
            }
            column.editable = columnMetaData.isEditable();
            if (columnMetaData.getVisibility()!=null) {
            	column.visibility = Visibility.byValue(columnMetaData.getVisibility());
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