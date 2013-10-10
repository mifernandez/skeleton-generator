package org.skeleton.generator.bc.command.file.impl.presentation.jsf;

import java.io.IOException;

import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToMany;
import org.skeleton.generator.model.om.OneToOne;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.util.metadata.RelationType;

public class SimpleJsfListViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;

	public SimpleJsfListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\" + bean.myPackage.name + "\\"
				+ bean.className.toLowerCase(), bean.className + "List");

		this.bean = bean;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=" + (char) 34 + "http://www.w3.org/1999/xhtml" + (char) 34);
		writeLine("xmlns:ui=" + (char) 34 + "http://java.sun.com/jsf/facelets" + (char) 34);
		writeLine("xmlns:f = " + (char) 34 + "http://java.sun.com/jsf/core" + (char) 34);
		writeLine("xmlns:h = " + (char) 34 + "http://java.sun.com/jsf/html" + (char) 34);
		writeLine("xmlns:rich = " + (char) 34 + "http://richfaces.org/rich" + (char) 34);
		writeLine("xmlns:a4j = " + (char) 34 + "http://richfaces.org/a4j" + (char) 34);
		writeLine("xmlns:c=" + (char) 34 + "http://java.sun.com/jstl/core" + (char) 34);
		writeLine("template=" + (char) 34 + "/templates/template.xhtml" + (char) 34 + ">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton-generator -->");
		writeLine("<!-- -->");
		skipLine();

		writeLine("<ui:define name=" + (char) 34 + "content" + (char) 34 + ">");
		skipLine();

		writeLine("<script language=" + (char) 34 + "javascript" + (char) 34 + ">");
		writeLine("<!--");
		skipLine();

		writeLine("function selectUnselectAll(checkbox){");
		writeLine("var elements = checkbox.form.elements;");
		writeLine("for (var i = 0; i < elements.length; i++) {");
		writeLine("var element = elements[i];");
		writeLine("if (/" + this.bean.objectName + "Selected$/.test(element.id)) {");
		writeLine("element.checked = checkbox.checked;");
		writeLine("}");
		writeLine("}");
		writeLine("}");
		skipLine();

		writeLine("function selectBox() {");
		writeLine("checkbox = document.getElementById(" + (char) 34 + this.bean.objectName + "Form:" + this.bean.objectName + "List:selectUnselectAll" + (char) 34 + ");");
		writeLine("var elements = checkbox.form.elements;");
		writeLine("var allSelected=true;");
		writeLine("var selectedItemNumber=0;");
		writeLine("for (var elementIndex = 0; elementIndex < elements.length; elementIndex++) {");
		writeLine("var element = elements[elementIndex];");
		writeLine("if (/" + this.bean.objectName + "Selected$/.test(element.id)) {");
		writeLine("if (!element.checked){");
		writeLine("allSelected=false;");
		writeLine("}else{");
		writeLine("selectedItemNumber++;");
		writeLine("}");
		writeLine("}");
		writeLine("}");
		writeLine("checkbox.checked=allSelected;");
		writeLine("}");
		skipLine();

		writeLine("-->");
		writeLine("</script>");
		skipLine();

		writeLine("<br/>");
		writeLine("<rich:messages infoClass=" + (char) 34 + "infoMessage" + (char) 34 + " errorClass=" + (char) 34 + "errorMessage" + (char) 34 + " globalOnly=" + (char) 34 + "true" + (char) 34
				+ "/>");
		writeLine("<br/>");
		skipLine();

		writeLine("<h:form id=" + (char) 34 + this.bean.objectName + "Form" + (char) 34 + ">");
		skipLine();

		writeLine("<rich:toolBar>");
		writeLine("<rich:dropDownMenu value=" + (char) 34 + "Navigation" + (char) 34 + " submitMode=" + (char) 34 + "none" + (char) 34 + ">");

		for (Property property : this.bean.propertyList) {
			if (property.referenceBean != null && !property.relation.equals(RelationType.PROPERTY)) {
				writeLine("<rich:menuItem value=" + (char) 34 + "back to #{i18n." + property.referenceBean.objectName + "List}" + (char) 34);
				writeLine("rendered=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".loadedFrom == '" + property.referenceBean.className + "'}" + (char) 34);
				writeLine("submitMode=" + (char) 34 + "server" + (char) 34 + " action=" + (char) 34 + "#{" + property.referenceBean.controllerObjectName + ".display}" + (char) 34 + "/>");
			}
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			writeLine("<rich:menuItem value=" + (char) 34 + "#{i18n." + oneToMany.referenceBean.objectName + "List}" + (char) 34 + " submitMode=" + (char) 34 + "server" + (char) 34);
			writeLine("actionListener=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".listenSelected" + this.bean.className + "IdList}" + (char) 34 + " action=" + (char) 34 + "#{"
					+ oneToMany.referenceBean.controllerObjectName + ".loadFrom" + this.bean.className + "}" + (char) 34 + "/>");
		}

		for (OneToOne oneToOne : this.bean.oneToOneList) {
			writeLine("<rich:menuItem value=" + (char) 34 + "#{i18n." + oneToOne.referenceBean.objectName + "List}" + (char) 34 + " submitMode=" + (char) 34 + "server" + (char) 34
					+ " actionListener=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".listenSelected" + this.bean.className + "IdList}" + (char) 34 + " action=" + (char) 34 + "#{"
					+ oneToOne.referenceBean.controllerObjectName + ".loadFrom" + this.bean.className + "}" + (char) 34 + "/>");
		}

		writeLine("</rich:dropDownMenu>");
		skipLine();

		writeLine("<rich:dropDownMenu value=" + (char) 34 + "Actions" + (char) 34 + " submitMode=" + (char) 34 + "none" + (char) 34 + ">");

		if (this.bean.createEnabled) {
			writeLine("<rich:menuItem value=" + (char) 34 + "#{i18n.create}" + (char) 34 + " submitMode=" + (char) 34 + "ajax" + (char) 34 + " action=" + (char) 34 + "#{"
					+ this.bean.controllerObjectName + ".create" + this.bean.className + "}" + (char) 34 + " oncomplete=" + (char) 34 + "Richfaces.showModalPanel('" + bean.objectName + "ModalPanel')"
					+ (char) 34 + " reRender=" + (char) 34 + bean.objectName + "DetailPanelGroup" + (char) 34 + "/>");
		}
		if (this.bean.deleteEnabled) {
			writeLine("<rich:menuItem value=" + (char) 34 + "#{i18n.dropSelection}" + (char) 34 + " submitMode=" + (char) 34 + "ajax" + (char) 34);
			writeLine("actionListener=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".listenSelected" + this.bean.className + "IdList}" + (char) 34);
			writeLine("action=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".delete" + this.bean.className + "List}" + (char) 34);
			writeLine("onclick=" + (char) 34 + "if (!confirm('#{i18n.confirmDropSelection}')) return false" + (char) 34);
			writeLine("reRender=" + (char) 34 + this.bean.objectName + "PanelGroup" + (char) 34 + "/>");
		}

		skipLine();
		this.writeNotOverridableContent();
		skipLine();

		writeLine("</rich:dropDownMenu>");
		writeLine("</rich:toolBar>");
		skipLine();

		writeLine("<br/>");
		skipLine();

		writeLine("<div class=" + (char) 34 + "title" + (char) 34 + ">");
		writeLine("<h:outputText value=" + (char) 34 + "#{i18n." + bean.objectName + "List}" + (char) 34 + "/>");
		writeLine("</div>");
		skipLine();

		writeLine("<br/>");
		skipLine();

		writeLine("<h:panelGroup id=" + (char) 34 + this.bean.objectName + "PanelGroup" + (char) 34 + ">");
		writeLine("<h:panelGroup rendered=" + (char) 34 + "#{!" + this.bean.controllerObjectName + ".displaySuccessfull}" + (char) 34 + " styleClass=" + (char) 34 + "errorMessage" + (char) 34
				+ " layout=" + (char) 34 + "block" + (char) 34 + ">");
		writeLine("<h:outputText value=" + (char) 34 + "#{i18n.displayFailure}" + (char) 34 + "/>");
		writeLine("</h:panelGroup>");
		writeLine("<h:panelGroup rendered=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".displaySuccessfull}" + (char) 34 + ">");
		skipLine();

		writeLine("<br/>");
		skipLine();

		writeLine("<h:outputText value=" + (char) 34 + "#{i18n.noDataFound}" + (char) 34 + " rendered=" + (char) 34 + "#{empty " + this.bean.controllerObjectName + "." + this.bean.objectName
				+ "List}" + (char) 34 + "/>");
		skipLine();

		writeLine("<rich:dataTable rows=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".numberOfRows}" + (char) 34 + " rendered=" + (char) 34 + "#{not empty "
				+ this.bean.controllerObjectName + "." + this.bean.objectName + "List}" + (char) 34);
		writeLine("id=" + (char) 34 + this.bean.objectName + "List" + (char) 34 + " var=" + (char) 34 + this.bean.objectName + (char) 34 + " name=" + (char) 34 + "datatable" + (char) 34);
		writeLine("value=" + (char) 34 + "#{" + this.bean.controllerObjectName + "." + this.bean.objectName + "List}" + (char) 34 + " headerClass=" + (char) 34 + "datatableHeader" + (char) 34
				+ " rowClasses=" + (char) 34 + "datatableRow, datatableRowLight" + (char) 34 + ">");
		skipLine();

		writeLine("<c:set var=" + (char) 34 + this.bean.objectName + (char) 34 + " value=" + (char) 34 + "#{" + this.bean.objectName + "}" + (char) 34 + " scope=" + (char) 34 + "request" + (char) 34
				+ "/>");
		skipLine();

		writeLine("<rich:column>");
		writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
		writeLine("<h:selectBooleanCheckbox id=" + (char) 34 + "selectUnselectAll" + (char) 34 + " name=" + (char) 34 + "selectUnselectAll" + (char) 34 + " forceId=" + (char) 34 + "true" + (char) 34
				+ " onclick=" + (char) 34 + "selectUnselectAll(this)" + (char) 34 + "/>");
		writeLine("</f:facet>");
		writeLine("<h:selectBooleanCheckbox id=" + (char) 34 + this.bean.objectName + "Selected" + (char) 34 + " value=" + (char) 34 + "#{" + this.bean.objectName + ".selected}" + (char) 34
				+ " onclick=" + (char) 34 + "selectBox()" + (char) 34 + "/>");
		writeLine("</rich:column>");
		skipLine();

		for (Property property : this.bean.getVisiblePropertyList()) {
			if (property.visibility.isListVisible()) {
				writeLine("<rich:column sortBy=" + (char) 34 + "#{" + this.bean.objectName + "." + property.name + "}" + (char) 34 + " filterBy=" + (char) 34 + "#{" + this.bean.objectName + "."
						+ property.name + "}" + (char) 34 + " filterEvent=" + (char) 34 + "onkeyup" + (char) 34 + ">");
				writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
				writeLine("<h:outputText value=" + (char) 34 + "#{i18n." + this.bean.objectName + property.capName + "}" + (char) 34 + " />");
				writeLine("</f:facet>");

				writeListComponent(property, this.bean);

				writeLine("</rich:column>");
				skipLine();
			}
		}

		writeLine("<rich:column>");
		writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
		writeLine("<h:outputText value=" + (char) 34 + "Actions" + (char) 34 + " />");
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=" + (char) 34 + "2" + (char) 34 + ">");
		writeLine("<a4j:commandLink action=" + (char) 34 + "#{" + bean.controllerObjectName + ".edit" + bean.className + "}" + (char) 34 + " actionListener=" + (char) 34 + "#{"
				+ bean.controllerObjectName + ".listenSelected" + bean.className + "Id}" + (char) 34 + " oncomplete=" + (char) 34 + "Richfaces.showModalPanel('" + bean.objectName + "ModalPanel')"
				+ (char) 34 + " reRender=" + (char) 34 + bean.objectName + "DetailPanelGroup" + (char) 34 + ">");
		writeLine("<f:attribute name=" + (char) 34 + "selected" + bean.className + "Id" + (char) 34 + " value=" + (char) 34 + "#{" + bean.objectName + ".id}" + (char) 34 + " />");
		writeLine("<h:graphicImage url=" + (char) 34 + "/resources/images/icons/edit.png" + (char) 34 + " styleClass=" + (char) 34 + "imageIcon" + (char) 34 + " title=" + (char) 34 + "#{i18n.edit}"
				+ (char) 34 + "/>");
		writeLine("</a4j:commandLink>");

		if (this.bean.deleteEnabled) {
			writeLine("<a4j:commandLink  action=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".delete" + this.bean.className + "AndRefresh}" + (char) 34 + " actionListener=" + (char) 34
					+ "#{" + this.bean.controllerObjectName + ".listenSelected" + this.bean.className + "Id}" + (char) 34);
			writeLine("onclick=" + (char) 34 + "if (!confirm('#{i18n.confirmDrop}')) return false" + (char) 34 + " reRender=" + (char) 34 + this.bean.objectName + "PanelGroup" + (char) 34 + ">");
			writeLine("<f:attribute name=" + (char) 34 + "selected" + this.bean.className + "Id" + (char) 34 + " value=" + (char) 34 + "#{" + this.bean.objectName + ".id}" + (char) 34 + "/>");
			writeLine("<h:graphicImage url=" + (char) 34 + "/resources/images/icons/delete.png" + (char) 34 + " styleClass=" + (char) 34 + "imageIcon" + (char) 34 + " title=" + (char) 34
					+ "#{i18n.drop}" + (char) 34 + "/>");
			writeLine("</a4j:commandLink>");

		}
		writeLine("</h:panelGrid>");
		writeLine("</rich:column>");
		skipLine();

		writeLine("<f:facet name=" + (char) 34 + "footer" + (char) 34 + ">");
		writeLine("<rich:datascroller maxPages=" + (char) 34 + "5" + (char) 34 + "/>");
		writeLine("</f:facet>");
		skipLine();

		writeLine("</rich:dataTable>");

		if (bean.createEnabled) {
			writeLine("<br/>");
			writeLine("<br/>");
			skipLine();

			writeLine("<a4j:commandButton value=" + (char) 34 + "#{i18n.create}" + (char) 34 + " action=" + (char) 34 + "#{" + bean.controllerObjectName + ".create" + bean.className + "}" + (char) 34
					+ " styleClass=" + (char) 34 + "simpleButton" + (char) 34 + " oncomplete=" + (char) 34 + "Richfaces.showModalPanel('" + bean.objectName + "ModalPanel')" + (char) 34 + " reRender="
					+ (char) 34 + bean.objectName + "DetailPanelGroup" + (char) 34 + "/>");
		}

		writeLine("<br/>");
		skipLine();

		writeLine("</h:panelGroup>");
		writeLine("</h:panelGroup>");
		skipLine();

		writeLine("</h:form>");
		skipLine();

		writeLine("<rich:modalPanel id=" + (char) 34 + bean.objectName + "ModalPanel" + (char) 34 + " autosized=" + (char) 34 + "true" + (char) 34 + " width=" + (char) 34 + "800" + (char) 34
				+ " left=" + (char) 34 + "100" + (char) 34 + ">");
		writeLine("<ui:include src=" + (char) 34 + "/sections/" + this.bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Details.xhtml" + (char) 34 + "/>");
		writeLine("</rich:modalPanel>");
		skipLine();

		writeLine("</ui:define>");
		writeLine("</ui:composition>");
	}
}
