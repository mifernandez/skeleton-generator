package org.sklsft.generator.command;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.sklsft.generator.bl.services.interfaces.DatabaseDumper;
import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.repository.backup.datasource.interfaces.OutputDataSourceProvider;
import org.sklsft.generator.repository.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to extract business data of your database<br/>
 * The format of file is the same of the $ separated file used to populate a database.<br/>
 * Argument required : 
 * <li>the workspace folder where the "data-model" folder will be detected
 * <li>the database name that must be declared in /data-model/CONTEXT/datasource-context.xml
 * Optional argument : if you want to restrict the tables to dump<br/>
 * Depending on the meta data that is going to be read, the main method will :
 * <li>load the project representation
 * <li>read the tables and create files in /data-model/DUMP/ as $ separated file (.txt)
 * 
 * @author Michael Fernandez
 *
 */
public class DatabaseDumpLauncher {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabaseDumpLauncher.class);
	
	private static final String DATASOURCE_CONTEXT_FILE ="CONTEXT" + File.separator + "datasource-context.xml";
	
	/**
	 * 
	 * @param args 1(optional)->a list of semicolon separated table names if you want to restrict the population with this list
	 */
	public static void main(String[] args) {
		
		if (args.length < 2) {
			throw new IllegalArgumentException("Path and datasource are Mandatory");
		}
		String workspacePath = args[0];
		String databaseName = args[1];
		
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		Set<String> tables = extractTables(args);
		
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-command.xml",sourcePath + File.separator + DATASOURCE_CONTEXT_FILE);){
			logger.info("Context loaded");
			
			Project project;
			
			try {
				logger.info("start loading project");
				
				ProjectMetaDataService projectMetaDataService = appContext.getBean(ProjectMetaDataService.class);
				ProjectLoader projectLoader = appContext.getBean(ProjectLoader.class);
				
				ProjectMetaData projectMetaData = projectMetaDataService.loadProjectMetaData(workspacePath);
				project = projectLoader.loadProject(projectMetaData);
				
				logger.info("loading project " + project.projectName + " completed");
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				
				OutputDataSourceProvider outputDataSourceProvider = appContext.getBean(OutputDataSourceProvider.class);
				DataSource dataSource = outputDataSourceProvider.getDataSource(databaseName);
				
				DatabaseDumper databaseDumper = appContext.getBean(DatabaseDumper.class);
				logger.info("Start dumping...");
				databaseDumper.dumpDatabase(dataSource, project, tables);
				logger.info("End dumping");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
	
	private static Set<String> extractTables(String[] args) {
		
		Set<String> tables = null;
		
		if (args.length > 2) {
			String tablesArg = args[2];
			String[] tableTokens = tablesArg.split(";");
			tables = new HashSet<String>();
			for (String table:tableTokens) {
				tables.add(table);
			}
		}
		
		return tables;
	}
}
