package org.skeleton.generator.bc.command.file.impl.conf.spring;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesSpringRepositoryFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesSpringRepositoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/resources", "applicationContext-" + project.projectName + "-repository", FileType.XML, project);
	}

}
