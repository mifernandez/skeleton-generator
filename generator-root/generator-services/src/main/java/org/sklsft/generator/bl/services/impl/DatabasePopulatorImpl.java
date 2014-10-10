package org.sklsft.generator.bl.services.impl;

import java.io.File;
import java.util.Set;

import javax.sql.DataSource;

import org.sklsft.generator.bc.backup.reader.BackupArgumentReaderFactory;
import org.sklsft.generator.bc.backup.reader.BackupFileLocator;
import org.sklsft.generator.bc.util.folder.FolderUtil;
import org.sklsft.generator.bl.services.interfaces.DatabasePopulator;
import org.sklsft.generator.exception.BackupFileNotFoundException;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.datasource.impl.BackupCommandArguments;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulatorImpl implements DatabasePopulator {

	private static final Logger logger = LoggerFactory.getLogger(DatabasePopulatorImpl.class);

	@Autowired
	private TablePopulator tablePopulator;
	@Autowired
	private BackupFileLocator backupLocator;
	@Autowired
	private BackupArgumentReaderFactory readerFactory;
	
	@Override
	public void populateDatabase(DataSource dataSource, InputDataSourceProvider inputDataSourceProvider, Project project, Set<String> tables) {

		logger.info("start populating database");

		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);

		for(int step=1; step<=maxSteps; step++){
			logger.info("start bulding step " + step);
			for (Package myPackage:project.model.packages) {
				logger.info("start populating package : " + myPackage.name);

				for (Table table:myPackage.tables) {

					if (tables == null || tables.contains(table.originalName)) {

						logger.info("start populating table : " + table.name);

						try {
							PersistenceMode mode = backupLocator.resolvePersistenceMode(step, table);
							BackupArgumentReader argumentReader = readerFactory.getBackupArgumentReader(mode, inputDataSourceProvider, table);
							BackupCommandArguments commandArgs = argumentReader.readBackupArgs(backupLocator.getBackupFilePath(step, table, mode));
							tablePopulator.populateTable(table, dataSource, commandArgs);
							logger.info("populating table : " + table.name + " completed");
						} catch (BackupFileNotFoundException e) {
							logger.warn(e.getMessage());
						}
					} else {
						logger.info("table : " + table.name + " skipped");
					}
				}
				logger.info("populating package " + myPackage.name + " completed");
			}
		}
		logger.info("populating database completed");

	}

}
