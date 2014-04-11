package org.skeleton.generator.bc.command.file.impl.conf.pom;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesRepositoryPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesRepositoryPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-repository", "pom", FileType.XML, project);
	}

}