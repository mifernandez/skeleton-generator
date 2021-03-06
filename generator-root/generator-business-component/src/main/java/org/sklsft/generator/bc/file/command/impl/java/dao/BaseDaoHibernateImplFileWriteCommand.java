package org.sklsft.generator.bc.file.command.impl.java.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.om.Alias;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Property;
import org.springframework.util.StringUtils;

public class BaseDaoHibernateImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseDaoHibernateImplFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-repository\\src\\main\\java\\"
				+ bean.myPackage.baseDAOImplPackageName.replace(".", "\\"), bean.baseDaoClassName);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.Date;");
		javaImports.add("import javax.annotation.Resource;");
		javaImports.add("import org.hibernate.SessionFactory;");
		javaImports.add("import org.hibernate.criterion.Restrictions;");
		javaImports.add("import org.hibernate.Criteria;");
		javaImports.add("import org.hibernate.FetchMode;");
		javaImports.add("import org.springframework.stereotype.Repository;");
		javaImports.add("import " + this.bean.myPackage.model.daoExceptionPackageName + ".ObjectNotFoundException;");
		javaImports.add("import org.springframework.stereotype.Repository;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.baseDAOInterfacePackageName + "." + this.bean.baseDaoInterfaceName + ";");
	
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + bean.myPackage.baseDAOImplPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated base dao class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Repository");
		writeLine("public class " + this.bean.baseDaoClassName + " implements " + this.bean.baseDaoInterfaceName + " {");
		skipLine();

		writeLine("/*");
		writeLine(" * resources injected with spring");
		writeLine(" */");
		writeLine("@Resource(name=" + (char) 34 + this.bean.myPackage.model.project.projectName + "SessionFactory" + (char) 34 + ")");

		writeLine("protected SessionFactory sessionFactory;");
		skipLine();

		createLoadObjectList();
		createLoadObject();
		createExistsObject();
		createFindObject();
		createSaveObject();
		createUpdateObject();
		createDeleteObject();

		write("}");

	}

	private void createLoadObjectList() {

		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("@SuppressWarnings(" + (char) 34 + "unchecked" + (char) 34 + ")");
		writeLine("public List<" + this.bean.className + "> load" + this.bean.className + "List() {");
		writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");
		writeLine("return criteria.list();");
		writeLine("}");
		skipLine();

		writeLine("/**");
		writeLine(" * load object list eagerly");
		writeLine(" */");
		writeLine("@SuppressWarnings(" + (char) 34 + "unchecked" + (char) 34 + ")");
		writeLine("public List<" + this.bean.className + "> load" + this.bean.className + "ListEagerly() {");
		writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null) {
				writeLine("criteria.setFetchMode(" + (char) 34 + property.name + (char) 34 + ",FetchMode.JOIN);");

				for (Alias alias : property.referenceBean.getFindAliases()) {
					writeLine("criteria.setFetchMode(" + (char) 34 + property.name + "." + alias.propertyName + (char) 34 + ",FetchMode.JOIN);");

				}
			}
		}

		writeLine("return criteria.list();");

		writeLine("}");
		skipLine();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && !property.relation.equals(RelationType.PROPERTY)) {
				writeLine("/**");
				writeLine(" * load object list from list of " + property.name);
				writeLine(" */");
				writeLine("@SuppressWarnings(" + (char) 34 + "unchecked" + (char) 34 + ")");
				writeLine("public List<" + this.bean.className + "> load" + this.bean.className + "ListFrom" + property.capName + "List (List<Long> " + property.name + "IdList) {");
				writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");
				writeLine("if (" + property.name + "IdList == null || " + property.name + "IdList.isEmpty()){");
				writeLine("criteria.add(Restrictions.isNull(" + (char) 34 + property.name + ".id" + (char) 34 + "));");
				writeLine("} else {");
				writeLine("criteria.add(Restrictions.in(" + (char) 34 + property.name + ".id" + (char) 34 + ", " + property.name + "IdList));");
				writeLine("}");
				writeLine("return criteria.list();");
				writeLine("}");
				skipLine();

				writeLine("/**");
				writeLine(" * load object list eagerly from list of " + property.name);
				writeLine(" */");
				writeLine("@SuppressWarnings(" + (char) 34 + "unchecked" + (char) 34 + ")");
				writeLine("public List<" + this.bean.className + "> load" + this.bean.className + "ListEagerlyFrom" + property.capName + "List (List<Long> " + property.name + "IdList) {");
				writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");
				writeLine("if (" + property.name + "IdList == null || " + property.name + "IdList.isEmpty()){");
				writeLine("criteria.add(Restrictions.isNull(" + (char) 34 + property.name + ".id" + (char) 34 + "));");
				writeLine("} else {");
				writeLine("criteria.add(Restrictions.in(" + (char) 34 + property.name + ".id" + (char) 34 + ", " + property.name + "IdList));");
				writeLine("}");

				for (Property prop : this.bean.properties) {
					if (prop.referenceBean != null) {
						writeLine("criteria.setFetchMode(" + (char) 34 + prop.name + (char) 34 + ",FetchMode.JOIN);");

						for (Alias alias : prop.referenceBean.getFindAliases()) {
							writeLine("criteria.setFetchMode(" + (char) 34 + prop.name + "." + alias.propertyName + (char) 34 + ",FetchMode.JOIN);");

						}
					}
				}

				writeLine("return criteria.list();");
				writeLine("}");
				skipLine();

			}
		}
	}

	private void createLoadObject() {
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("public " + this.bean.className + " load" + this.bean.className + "(Long id) {");
		writeLine("return (" + this.bean.className + ")this.sessionFactory.getCurrentSession().load(" + this.bean.className + ".class,id);");
		writeLine("}");
		skipLine();
	}

	private void createExistsObject() {
		List<Property> findPropertyList = this.bean.getFindProperties();
		List<Alias> findAliasList = this.bean.getFindAliases();

		writeLine("/**");
		writeLine(" * exists object");
		writeLine(" */");

		write("public boolean exists" + this.bean.className + "(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
		for (int i = 1; i < findPropertyList.size(); i++) {
			write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
		}
		writeLine(") {");

		write("if (" + findPropertyList.get(0).name + " == null");
		for (int i = 1; i < findPropertyList.size(); i++) {
			write(" && " + findPropertyList.get(i).name + " == null");
		}
		writeLine(") {");

		writeLine("return false;");

		writeLine("}");

		write(this.bean.className + " " + this.bean.objectName + " = (" + this.bean.className + ")this.sessionFactory.getCurrentSession().createCriteria(");
		writeLine(this.bean.className + ".class)");

		for (Alias alias : findAliasList) {
			writeLine(".createAlias(" + (char) 34 + alias.propertyName + (char) 34 + "," + (char) 34 + alias.name + (char) 34 + ")");

		}
		for (Property property : findPropertyList) {
			if (StringUtils.isEmpty(property.joinedAliasName)) {
				writeLine(".add(Restrictions.eq(" + (char) 34 + property.lastPropertyName + (char) 34 + "," + property.name + "))");

			} else {
				writeLine(".add(Restrictions.eq(" + (char) 34 + property.joinedAliasName + "." + property.lastPropertyName + (char) 34 + "," + property.name + "))");

			}
		}

		writeLine(".uniqueResult();");
		writeLine("return " + this.bean.objectName + " != null;");
		writeLine("}");
		skipLine();
	}

	private void createFindObject() {
		List<Property> findPropertyList = this.bean.getFindProperties();
		List<Alias> findAliasList = this.bean.getFindAliases();

		writeLine("/**");
		writeLine(" * find object");
		writeLine(" */");
		write("public " + this.bean.className + " find" + this.bean.className + "(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
		for (int i = 1; i < findPropertyList.size(); i++) {
			write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
		}
		writeLine(") throws ObjectNotFoundException {");

		write("if (" + findPropertyList.get(0).name + " == null");
		for (int i = 1; i < findPropertyList.size(); i++) {
			write(" && " + findPropertyList.get(i).name + " == null");
		}
		writeLine(") {");
		writeLine("return null;");
		writeLine("}");

		write(this.bean.className + " " + this.bean.objectName + " = (" + this.bean.className + ")this.sessionFactory.getCurrentSession().createCriteria(");
		writeLine(this.bean.className + ".class)");

		for (Alias alias : findAliasList) {
			writeLine(".createAlias(" + (char) 34 + alias.propertyName + (char) 34 + "," + (char) 34 + alias.name + (char) 34 + ")");

		}
		for (Property property : findPropertyList) {
			if (StringUtils.isEmpty(property.joinedAliasName)) {
				writeLine(".add(Restrictions.eq(" + (char) 34 + property.lastPropertyName + (char) 34 + "," + property.name + "))");

			} else {
				writeLine(".add(Restrictions.eq(" + (char) 34 + property.joinedAliasName + "." + property.lastPropertyName + (char) 34 + "," + property.name + "))");

			}
		}

		writeLine(".uniqueResult();");
		writeLine("if (" + this.bean.objectName + " == null) {");
		writeLine("throw new ObjectNotFoundException(" + (char) 34 + "Invalid Object Reference" + (char) 34 + ");");
		writeLine("} else {");
		writeLine("return " + this.bean.objectName + ";");
		writeLine("}");
		writeLine("}");
		skipLine();
	}

	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");
		writeLine(" */");
		writeLine("public Long save" + this.bean.className + "(" + this.bean.className + " " + this.bean.objectName + ") {");
		writeLine("return (Long)this.sessionFactory.getCurrentSession().save(" + this.bean.objectName + ");");
		writeLine("}");
		skipLine();
		
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList){
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("public void save" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.className + " " + currentBean.objectName + ") {");
			writeLine(this.bean.objectName + ".get" + currentBean.className + "Collection().add(" + currentBean.objectName + ");");
            
			writeLine(currentBean.objectName + ".set" + bean.className + "(" + bean.objectName + ");");
			writeLine("}");
			skipLine();
		}
	}
	

	private void createUpdateObject() {
		writeLine("/**");
		writeLine(" * update object");
		writeLine(" */");
		writeLine("public void update" + this.bean.className + "(" + this.bean.className + " " + this.bean.objectName + ") {");
		writeLine("this.sessionFactory.getCurrentSession().update(" + this.bean.objectName + ");");
		writeLine("}");
		skipLine();
	}

	private void createDeleteObject() {
		writeLine("/**");
		writeLine(" * delete object");
		writeLine(" */");
		writeLine("public void delete" + this.bean.className + "(" + this.bean.className + " " + this.bean.objectName + ") {");
		writeLine("this.sessionFactory.getCurrentSession().delete(" + this.bean.objectName + ");");
		writeLine("}");
		skipLine();
	}
}
