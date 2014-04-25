package computation.utils;

import org.apache.commons.digester.Digester;

import computation.model.Computation;
import computation.model.ComputationConfiguration;
import computation.model.ComputationSetting;
import computation.model.ComputationTask;
import computation.model.ComputationTaskInput;
import computation.model.ComputationTaskOutput;
import computation.model.ComputationTransition;

/**
 * 
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
public final class DigesterRulesBuilder {
	
	/**
	 * Computation id bean property.
	 */
	private static final String COMPUTATION_ID_PROP = "computationId";

	/**
	 * Path to computation description xml element.
	 */
	private static final String COMP_DESC_ELEMENT = "computation/description";
	
	/**
	 * Computation element.
	 */
	private static final String COMPUTATION_ELEMENT = "computation";
	
	/**
	 * Computation id attribute.
	 */
	private static final String ID_ATTR = "id";
	
	/**
	 * Description.
	 */
	private static final String DESCRIPTION = "description";
	
	
	/**
	 * 
	 * Constructor.
	 */
	private DigesterRulesBuilder() { }
	
	/**
	 * Creates digester with reules needed to import computation.
	 * @return return prepared digester.
	 */
	public static Digester createComputationImport() {
		
		String compTaskElement = "computation/tasks/task";
		String compTransElement = "computation/transitions/transition";
		String compSettingsElement="computation/settings/setting";
		String inputElement = "computation/tasks/task/input";
		String outputElement = "computation/tasks/task/output";
		String start = "start";
		String className = "className";
		String name = "name";
		String id=ID_ATTR;
		String result = "result";
		String valueName="valueName";
		
		Digester digester = new Digester();
		digester.setClassLoader(Thread.currentThread().getContextClassLoader());
		
		digester.addObjectCreate(COMPUTATION_ELEMENT, Computation.class);
		digester.addSetProperties(COMPUTATION_ELEMENT, ID_ATTR, COMPUTATION_ID_PROP);
		digester.addBeanPropertySetter(COMP_DESC_ELEMENT, DESCRIPTION);
		
		
		digester.addObjectCreate(compSettingsElement, ComputationSetting.class);
		digester.addSetProperties(compSettingsElement, name, name);
		digester.addSetProperties(compSettingsElement, "value", "val");
		digester.addSetProperties(compSettingsElement, DESCRIPTION, DESCRIPTION);
		digester.addSetNext(compSettingsElement, "addSetting", ComputationSetting.class.getName());
		
		
		digester.addObjectCreate(compTaskElement, ComputationTask.class);
		digester.addBeanPropertySetter("computation/tasks/task/description", DESCRIPTION);
		digester.addSetProperties(compTaskElement, ID_ATTR, "taskId");
		digester.addSetProperties(compTaskElement, name, "taskName");
		digester.addSetProperties(compTaskElement, start, start);
		digester.addSetProperties(compTaskElement, className, className);
		
		digester.addObjectCreate(inputElement, ComputationTaskInput.class.getName());
		digester.addSetProperties(inputElement, name, name);
		digester.addSetProperties(inputElement, id, id);
		digester.addSetProperties(inputElement, valueName, valueName);
		digester.addSetNext(inputElement, "addInput");
		
		digester.addObjectCreate(outputElement, ComputationTaskOutput.class.getName());
		digester.addSetProperties(outputElement, name, name);
		digester.addSetProperties(outputElement, result, "strResult");
		digester.addSetProperties(outputElement, id, id);
		digester.addSetProperties(inputElement, valueName, valueName);
		digester.addSetNext(outputElement, "addOutput");	
		digester.addSetNext(compTaskElement, "addTask", ComputationTask.class.getName());
		
	
		digester.addObjectCreate(compTransElement, ComputationTransition.class);
		digester.addSetProperties(compTransElement, ID_ATTR, "transitionId");
		digester.addSetProperties(compTransElement, "from", "previousOutputId");
		digester.addSetProperties(compTransElement, "to", "nextInputId");
		digester.addSetNext(compTransElement, "addTransiton", ComputationTransition.class.getName());
		
		return digester;
	}
	
	/**
	 * Creates digester with rules for computation config.
	 * @return prepared digester.
	 */
	public static Digester createConfigurationImport() {
		String computationElement = COMPUTATION_ELEMENT;
		
		Digester digester = new Digester();
		digester.setClassLoader(Thread.currentThread().getContextClassLoader());
		
		digester.addObjectCreate(computationElement, ComputationConfiguration.class);
		digester.addSetProperties(computationElement, ID_ATTR, "configurationId");
		digester.addBeanPropertySetter(COMP_DESC_ELEMENT, DESCRIPTION);
		
		return digester;
	}

}
