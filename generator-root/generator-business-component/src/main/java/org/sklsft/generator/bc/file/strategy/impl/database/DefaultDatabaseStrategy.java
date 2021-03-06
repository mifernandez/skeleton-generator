package org.sklsft.generator.bc.file.strategy.impl.database;

import org.sklsft.generator.bc.file.command.impl.sql.definition.oracle.OracleMainDefinitionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.sql.definition.oracle.OracleTableDefinitionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.sql.definition.oracle.OracleTableFkDefinitionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.sql.definition.postgresql.PostgresqlMainDefinitionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.sql.definition.postgresql.PostgresqlTableDefinitionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.sql.definition.postgresql.PostgresqlTableFkDefinitionFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;

public class DefaultDatabaseStrategy implements LayerStrategy {

	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode databaseTreeNode = new FileWriteCommandTreeNode("Database Files");

		FileWriteCommandTreeNode definitionFilesTreeNode = new FileWriteCommandTreeNode("Definition Files");
		databaseTreeNode.add(definitionFilesTreeNode);

		FileWriteCommandTreeNode mainFileTreeNode;
		switch (project.databaseEngine) {
		case ORACLE:
			mainFileTreeNode = new FileWriteCommandTreeNode(new OracleMainDefinitionFileWriteCommand(project));
			break;
			
		case POSTGRESQL:
			mainFileTreeNode = new FileWriteCommandTreeNode(new PostgresqlMainDefinitionFileWriteCommand(project));
			break;

		default:
			throw new IllegalArgumentException("unhandled database");
		}
		definitionFilesTreeNode.add(mainFileTreeNode);
		
		
		/*FileWriteCommandTreeNode batchFileTreeNode;
		switch (project.databaseEngine) {
		case ORACLE:
			batchFileTreeNode = new FileWriteCommandTreeNode("No batch File");
			break;
			
		case POSTGRESQL:
			batchFileTreeNode = new FileWriteCommandTreeNode(new PostgresqlBatchFileWriteCommand(project));
			break;

		default:
			throw new IllegalArgumentException("unhandled database");
		}
		definitionFilesTreeNode.add(batchFileTreeNode);*/
		

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			definitionFilesTreeNode.add(packageTreeNode);
			for (Table table : myPackage.tables) {
				FileWriteCommandTreeNode tableTreeNode;
				FileWriteCommandTreeNode tableFkTreeNode;
				switch (project.databaseEngine) {

				case ORACLE:
					tableTreeNode = new FileWriteCommandTreeNode(new OracleTableDefinitionFileWriteCommand(table));
					tableFkTreeNode = new FileWriteCommandTreeNode(new OracleTableFkDefinitionFileWriteCommand(table));
					break;
					
				case POSTGRESQL:
					tableTreeNode = new FileWriteCommandTreeNode(new PostgresqlTableDefinitionFileWriteCommand(table));
					tableFkTreeNode = new FileWriteCommandTreeNode(new PostgresqlTableFkDefinitionFileWriteCommand(table));
					break;

				default:
					throw new IllegalArgumentException("unhandled database");
				}
				packageTreeNode.add(tableTreeNode);
				packageTreeNode.add(tableFkTreeNode);
			}
		}
		
		return databaseTreeNode;
	}
}
